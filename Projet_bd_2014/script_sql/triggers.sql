DROP TRIGGER AI_VolsPassager;
DROP TRIGGER AI_VolsFret;
DROP TRIGGER AI_AvionsPassagers;
DROP TRIGGER AI_AvionsFret;
DROP TRIGGER AI_Client;
DROP TRIGGER AI_Reservation;
DROP TRIGGER AI_Pilotes;
DROP TRIGGER AI_Hotesses;
DROP SEQUENCE Hotesses_seq;
DROP SEQUENCE Pilotes_seq;
DROP SEQUENCE Reservation_seq;
DROP SEQUENCE Client_seq;
DROP SEQUENCE AvionsPassagers_seq;
DROP SEQUENCE AvionsFret_seq;
DROP SEQUENCE NumVolF_NumVolP_seq;

CREATE SEQUENCE AvionsPassagers_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER AI_AvionsPassagers
  before insert  on AvionsPassagers
  for each row
begin
  if :NEW.NumAvionP is null then
   select AvionsPassagers_seq.nextval into :NEW.NumAvionP FROM dual;
  end if;
end;
/

CREATE SEQUENCE AvionsFret_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER AI_AvionsFret
  before insert  on AvionsFret
  for each row
begin
  if :NEW.NumAvionF is null then
   select AvionsFret_seq.nextval into :NEW.NumAvionF FROM dual;
  end if;
end;
/

CREATE SEQUENCE NumVolF_NumVolP_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER AI_VolsFret
  before insert  on VolsFret
  for each row
begin
  if :NEW.NumVolF is null then
   select NumVolF_NumVolP_seq.nextval into :NEW.NumVolF FROM dual;
  end if;
end;
/

CREATE OR REPLACE TRIGGER AI_VolsPassager
  before insert  on VolsPassager
  for each row
begin
  if :NEW.NumVolP is null then
   select NumVolF_NumVolP_seq.nextval into :NEW.NumVolP FROM dual;
  end if;
end;
/

CREATE SEQUENCE Client_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER AI_Client
  before insert  on Client
  for each row
begin
  if :NEW.NumClient is null then
   select Client_seq.nextval into :NEW.NumClient FROM dual;
  end if;
end;
/

CREATE SEQUENCE Reservation_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER AI_Reservation
  before insert  on Reservation
  for each row
begin
  if :NEW.NumResa is null then
   select Reservation_seq.nextval into :NEW.NumResa FROM dual;
  end if;
end;
/

CREATE SEQUENCE Pilotes_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER AI_Pilotes
  before insert  on Pilotes
  for each row
begin
  if :NEW.NumPersoP is null then
   select Pilotes_seq.nextval into :NEW.NumPersoP FROM dual;
  end if;
end;
/

CREATE SEQUENCE Hotesses_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER AI_Hotesses
  before insert  on Hotesses
  for each row
begin
  if :NEW.NumPersoH is null then
   select Hotesses_seq.nextval into :NEW.NumPersoH FROM dual;
  end if;
end;
/

CREATE OR REPLACE TRIGGER ContraintesVolFret
AFTER INSERT OR UPDATE ON VolsFret
DECLARE
	nb Integer;
BEGIN
	select count(*) into nb from (select v.NumAvionF from AvionsFret a INNER JOIN VolsFret v ON a.NumAvionF  = v.NumAvionF where (a.VolumeMax < v.VolumeMin) );
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne satisfait pas les contraintes de volume');
	end if;

	select count(*) into nb from (select v.NumAvionF from AvionsFret a INNER JOIN VolsFret v ON a.NumAvionF  = v.NumAvionF where (a.PoidsMax < v.PoidsMin) );
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne satisfait pas les contraintes de poids');
	end if;	
	
	select  count(*) into nb from (select v.NumAvionF from AvionsFret a, Modele m , VolsFret v where (a.NumAvionF  = v.NumAvionF and a.modele = m.Modele 
																						and m.RayonAction < v.Distance ));
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne permet pas d effectuer cette distance');
	end if;																						
END;
/

CREATE OR REPLACE TRIGGER ContrainteVolPassagers
AFTER INSERT OR UPDATE ON VolsPassager
DECLARE
	nb Integer;
BEGIN
	select count(*) into nb from (select v.NumAvionP from AvionsPassagers a INNER JOIN VolsPassager v ON a.NumAvionP  = v.NumAvionP where (a.Nb1Cl < v.Nb1ClMin) );
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne satisfait pas le nombre min en premiere classe');
	end if;

	select count(*) into nb from (select v.NumAvionP from AvionsPassagers a INNER JOIN VolsPassager v ON a.NumAvionP  = v.NumAvionP where (a.Nb2Cl < v.Nb2ClMin) );
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne satisfait pas le nombre min en deuxieme classe');
	end if;	
	
	select  count(*) into nb from (select v.NumAvionP from AvionsPassagers a, Modele m , VolsPassager v where (a.NumAvionP  = v.NumAvionP and a.modele = m.Modele 
																						and m.RayonAction < v.Distance ));
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne permet pas d effectuer cette distance');
	end if;		
	
END;
/

--CREATE OR REPLACE TRIGGER ContrainteAffectationP
--AFTER INSERT OR UPDATE ON AffectationP
--DECLARE
--	nb Integer;
--BEGIN
	--select count(*) into nb from (select * from AffectationP aff,  VolsPassager v, AvionsPassagers a, Modele m , Qualification q 
	--	where (aff.NumVol = v.NumVolP and aff.DateVol = v.DateVolP and v.NumAvionP = a.NumAvionP and a.Modele = m.Modele 
	--			and aff.NumPersoP = q.NumPersoP and a.Modele = q.Modele) );
	--if (nb = 0) then raise_application_error (-20999,'Le pilote affecte n a pas la qualification pour le modele d avion effectuant ce vol');
	--end if;	
	
	--select count(*) into nb from (select * from AffectationP aff,  VolsFret v, AvionsFret a, Modele m , Qualification q 
	--	where (aff.NumVol = v.NumVolF and aff.DateVol = v.DateVolF and v.NumAvionF = a.NumAvionF and a.Modele = m.Modele 
	--			and aff.NumPersoP = q.NumPersoP and a.Modele = q.Modele) );
	--if (nb = 0) then raise_application_error (-20999,'Le pilote affecte n a pas la qualification pour le modele d avion effectuant ce vol');
	--end if;		
--END;
--/


CREATE OR REPLACE TRIGGER ContrainteAffectationP
AFTER INSERT OR UPDATE ON AffectationP
for each row
DECLARE
	nb1  Integer;
	nb2  Integer;
BEGIN
	select count(*) into nb1 from (select q.NumPersoP from   VolsPassager v, AvionsPassagers a, Modele m , Qualification q 
		where (:new.NumVol = v.NumVolP and :new.DateVol = v.DateVolP and v.NumAvionP = a.NumAvionP and a.Modele = m.Modele 
				and :new.NumPersoP = q.NumPersoP and a.Modele = q.Modele));
	
	select count(*) into nb2 from (select q.NumPersoP from  VolsFret v, AvionsFret a, Modele m , Qualification q 
		where (:new.NumVol = v.NumVolF and :new.DateVol = v.DateVolF and v.NumAvionF = a.NumAvionF and a.Modele = m.Modele 
				and :new.NumPersoP = q.NumPersoP and a.Modele = q.Modele) );
	if (nb1 = 0 and nb2 = 0) then raise_application_error (-20999,'Le pilote affecte n a pas la qualification pour le modele d avion effectuant ce vol');
	end if;		
END;
/

--on cherche si le NumPlace  ajouté, associé à l'avion utilisé pour ce vol existe ou pas dans la table Places
CREATE OR REPLACE TRIGGER ContrainteResaPassager
AFTER INSERT OR UPDATE ON ResaPassager
for each row
DECLARE
	nb  Integer;
BEGIN
	select count(*) into nb from ( select * from  VolsPassager v, AvionsPassagers a, Places p     
	where (:new.NumVolP = v.NumVolP and :new.DateVolP = v.DateVolP and v.NumAvionP = a.NumAvionP and a.NumAvionP = p.NumAvionP and p.NumPlace = :new.NumPlace));
	if (nb = 0) then raise_application_error (-20999,'La place réservée n existe pas dans cet avion');
	end if;	
END;
/



