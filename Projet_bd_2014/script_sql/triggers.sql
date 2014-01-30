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

CREATE OR REPLACE TRIGGER ContrainteAffectationP
BEFORE INSERT OR UPDATE ON AffectationP
for each row
DECLARE
        nb Integer;
        nb1  Integer;
        nb2  Integer;
BEGIN		
        select count(*) into nb1 from (select q.NumPersoP from   VolsPassager v, AvionsPassagers a, Modele m , Qualification q
                where (:new.NumVol = v.NumVolP and :new.DateVol = v.DateVolP and v.NumAvionP = a.NumAvionP and a.Modele = m.Modele
                                and :new.NumPersoP = q.NumPersoP and a.Modele = q.Modele));

        select count(*) into nb2 from (select q.NumPersoP from  VolsFret v, AvionsFret a, Modele m , Qualification q
                where (:new.NumVol = v.NumVolF and :new.DateVol = v.DateVolF and v.NumAvionF = a.NumAvionF and a.Modele = m.Modele 
					and :new.NumPersoP = q.NumPersoP and a.Modele = q.Modele) );
					
        if (nb1 = 0 and nb2 = 0) then 
			raise_application_error (-20997,'Le pilote affecte n a pas la qualification pour le modele d avion effectuant ce vol ou bien il n existe pas');
        end if;
END;
/

CREATE OR REPLACE TRIGGER ContraintesVolFret
AFTER INSERT OR UPDATE ON VolsFret
DECLARE
        nb Integer;
BEGIN
        select count(*) into nb from (select * from VolsFret a, VolsFret b
			where ( a.NumVolF <> b.NumVolF  and a.DateVolF = b.DateVolF  and a.NumAvionF  = b.NumAvionF 
			and  b.HeureDepGMT < (a.HeureDepGMT + (a.Duree/(24.0*60.0))) and a.HeureDepGMT <= b.HeureDepGMT));

        if (nb > 0) then raise_application_error (-20999,'Un avion ne peut pas etre affecte a 2 vols au meme temps');
        end if;

        select count(*) into nb from (select v.NumAvionF from AvionsFret a INNER JOIN VolsFret v 
			ON a.NumAvionF  = v.NumAvionF where (a.VolumeMax < v.VolumeMin) );

        if (nb > 0) then raise_application_error (-20999,'13,L avion affect?? ?? ce vol ne satisfait pas les contraintes de volume');
        end if;

        select count(*) into nb from (select v.NumAvionF from AvionsFret a INNER JOIN VolsFret v 
			ON a.NumAvionF  = v.NumAvionF where (a.PoidsMax < v.PoidsMin) );

        if (nb > 0) then raise_application_error (-20999,'L avion affect?? ?? ce vol ne satisfait pas les contraintes de poids');
        end if;

        select  count(*) into nb from (select v.NumAvionF from AvionsFret a, Modele m , VolsFret v 
			where (a.NumAvionF  = v.NumAvionF and a.modele = m.Modele and m.RayonAction < v.Distance ));
			
        if (nb > 0) then raise_application_error (-20999,'L avion affect?? ?? ce vol ne permet pas d effectuer cette distance');
        end if;
END;
/

CREATE OR REPLACE TRIGGER ContrainteVolPassagers
AFTER INSERT OR UPDATE ON VolsPassager
DECLARE
        nb Integer;
BEGIN
        select count(*) into nb from (select * from VolsPassager a, VolsPassager b
			where ( a.NumVolP <> b.NumVolP  and a.DateVolP = b.DateVolP  and a.NumAvionP  = b.NumAvionP 
				and  b.HeureDepGMT < (a.HeureDepGMT + (a.Duree/(24.0*60.0))) and a.HeureDepGMT <= b.HeureDepGMT ));

        if (nb > 0) then raise_application_error (-20999,'Un avion affect?? ?? 2 vols le meme jour');
        end if;
        select count(*) into nb from (select v.NumAvionP from AvionsPassagers a INNER JOIN VolsPassager v 
			ON a.NumAvionP  = v.NumAvionP where (a.Nb1Cl < v.Nb1ClMin) );
        if (nb > 0) then raise_application_error (-20999,'L avion affect?? ?? ce vol ne satisfait pas le nombre min en premiere classe');
        end if;

        select count(*) into nb from (select v.NumAvionP from AvionsPassagers a INNER JOIN VolsPassager v 
			ON a.NumAvionP  = v.NumAvionP where (a.Nb2Cl < v.Nb2ClMin) );
        if (nb > 0) then raise_application_error (-20999,'L avion affect?? ?? ce vol ne satisfait pas le nombre min en deuxieme classe');
        end if;

        select  count(*) into nb from (select v.NumAvionP from AvionsPassagers a, Modele m , VolsPassager v 
			where (a.NumAvionP  = v.NumAvionP and a.modele = m.Modele and m.RayonAction < v.Distance ));
        if (nb > 0) then raise_application_error (-20999,'L avion affect?? ?? ce vol ne permet pas d effectuer cette distance');
        end if;

END;
/

CREATE OR REPLACE TRIGGER TerminaisonFret
AFTER INSERT OR UPDATE ON VolsFret
for each row
DECLARE
BEGIN
if (INSERTING) then 
	if (:new.Termine = 'O') then
		update Pilotes p set p.NbHeuresVolTotal = p.NbHeuresVolTotal + :new.Duree
                where p.NumPersoP in (select aff.NumPersoP from AffectationP aff 
					where (aff.NumVol = :new.NumVolF and aff.DateVol = :new.DateVolF ));
		update Hotesses h set h.NbHeuresVolTotal = h.NbHeuresVolTotal + :new.Duree
                where h.NumPersoH in (select aff.NumPersoH from AffectationH aff 
					where (aff.NumVol = :new.NumVolF and aff.DateVol = :new.DateVolF ));
		update Qualification q set q.NbHeuresTotal = q.NbHeuresTotal + :new.Duree
                where ((q.NumPersoP in (select NumPersoP from AffectationP aff 
					where (aff.NumVol = :new.NumVolF and aff.DateVol = :new.DateVolF )))
                                and (q.Modele in (select m.Modele from Modele m, AvionsFret a 
									where (a.NumAvionF = :new.NumAvionF and m.Modele = a.Modele)))) ;
		update Client c set c.Points = c.Points + :new.Duree 
			where c.NumClient in  ( select d.NumClient from Client d, Reservation r , ResaFret rf 
				where (d.NumClient = r.NumClient and r.NumResa = rf.NumResa and rf.NumVolF = :new.NumVolF and rf.DateVolF = :new.DateVolF ));
				
	end if;
end if;
if (UPDATING) then
	if (:old.Termine = 'O') then
		raise_application_error (-20999,'Le vol est terminé, on ne peut plus le modifier');
	end if;
	if (:new.Termine = 'O' and :old.Termine = 'N') then
		update Pilotes p set p.NbHeuresVolTotal = p.NbHeuresVolTotal + :new.Duree
                where p.NumPersoP in (select aff.NumPersoP from AffectationP aff 
					where (aff.NumVol = :new.NumVolF and aff.DateVol = :new.DateVolF ));
      update Hotesses h set h.NbHeuresVolTotal = h.NbHeuresVolTotal + :new.Duree
                where h.NumPersoH in (select aff.NumPersoH from AffectationH aff 
					where (aff.NumVol = :new.NumVolF and aff.DateVol = :new.DateVolF ));
      update Qualification q set q.NbHeuresTotal = q.NbHeuresTotal + :new.Duree
                where ((q.NumPersoP in (select NumPersoP from AffectationP aff 
					where (aff.NumVol = :new.NumVolF and aff.DateVol = :new.DateVolF )))
                                and (q.Modele in (select m.Modele from Modele m, AvionsFret a 
									where (a.NumAvionF = :new.NumAvionF and m.Modele = a.Modele)))) ;
		update Client c set c.Points = c.Points + :new.Duree 
			where c.NumClient in  ( select d.NumClient from Client d, Reservation r , ResaFret rf 
				where (d.NumClient = r.NumClient and r.NumResa = rf.NumResa and rf.NumVolF = :new.NumVolF and rf.DateVolF = :new.DateVolF ));

	end if;	
end if;
END;
/

CREATE OR REPLACE TRIGGER TerminaisonPassager
AFTER INSERT OR UPDATE ON VolsPassager
for each row
DECLARE
        nb Integer;
BEGIN
if (INSERTING) then 
	if(:new.Termine = 'O') then
        update Pilotes set NbHeuresVolTotal = NbHeuresVolTotal + :new.Duree
                where NumPersoP in (select NumPersoP from AffectationP aff 
					where (aff.NumVol = :new.NumVolP and aff.DateVol = :new.DateVolP ));

        update Hotesses set NbHeuresVolTotal = NbHeuresVolTotal + :new.Duree
                where NumPersoH in (select NumPersoH from AffectationH aff 
					where (aff.NumVol = :new.NumVolP and aff.DateVol = :new.DateVolP ));

        update Qualification q set q.NbHeuresTotal = q.NbHeuresTotal + :new.Duree
                where ((q.NumPersoP in (select NumPersoP from AffectationP aff 
					where (aff.NumVol = :new.NumVolP and aff.DateVol = :new.DateVolP )))
                                and (q.Modele in (select m.Modele from Modele m, AvionsPassagers a 
									where (a.NumAvionP = :new.NumAvionP and m.Modele = a.Modele)))) ;
		update Client c set c.Points = c.Points + :new.Duree 
			where c.NumClient in  ( select d.NumClient from Client d, Reservation r , ResaPassager rp 
				where (d.NumClient = r.NumClient and r.NumResa = rp.NumResa and rp.NumVolP = :new.NumVolP and rp.DateVolP = :new.DateVolP ));
	end if;
end if;
if (UPDATING) then
	if (:old.Termine = 'O') then
		raise_application_error (-20999,'Le vol est terminé, on ne peut plus le modifier');
	end if;
	if(:new.Termine = 'O' and :old.Termine = 'N') then
        update Pilotes set NbHeuresVolTotal = NbHeuresVolTotal + :new.Duree
                where NumPersoP in (select NumPersoP from AffectationP aff 
					where (aff.NumVol = :new.NumVolP and aff.DateVol = :new.DateVolP ));

        update Hotesses set NbHeuresVolTotal = NbHeuresVolTotal + :new.Duree
                where NumPersoH in (select NumPersoH from AffectationH aff 
					where (aff.NumVol = :new.NumVolP and aff.DateVol = :new.DateVolP ));

        update Qualification q set q.NbHeuresTotal = q.NbHeuresTotal + :new.Duree
                where ((q.NumPersoP in (select NumPersoP from AffectationP aff 
					where (aff.NumVol = :new.NumVolP and aff.DateVol = :new.DateVolP )))
                                and (q.Modele in (select m.Modele from Modele m, AvionsPassagers a 
									where (a.NumAvionP = :new.NumAvionP and m.Modele = a.Modele)))) ;
		update Client c set c.Points = c.Points + :new.Duree 
			where c.NumClient in  ( select d.NumClient from Client d, Reservation r , ResaPassager rp 
				where (d.NumClient = r.NumClient and r.NumResa = rp.NumResa and rp.NumVolP = :new.NumVolP and rp.DateVolP = :new.DateVolP ));
	end if;
end if;
END;
/

CREATE OR REPLACE TRIGGER ContrainteResaPassager
AFTER INSERT OR UPDATE ON ResaPassager
for each row
DECLARE
	nb  Integer;
	nb2 Integer;
BEGIN
	select count(*) into nb from ( select * from  VolsPassager v, AvionsPassagers a, Places p     
	where (:new.NumVolP = v.NumVolP and :new.DateVolP = v.DateVolP and v.NumAvionP = a.NumAvionP and a.NumAvionP = p.NumAvionP and p.NumPlace = :new.NumPlace));
	if (nb = 0) then raise_application_error (-20999,'La place réservée n existe pas dans cet avion');
	end if;	
END;
/


CREATE OR REPLACE TRIGGER PilotesAffecte2Vols
AFTER INSERT OR UPDATE ON AffectationP
DECLARE
        nb Integer;
		nb2 Integer;
		nb3 Integer;
BEGIN
		select count(*) into nb from (select a.NumVol , b.NumVol from  AffectationP	a, AffectationP b , VolsPassager c , VolsPassager d 
			where ( a.NumVol <> b.NumVol    and a.NumPersoP  = b.NumPersoP 
				and a.NumVol = c.NumVolP and a.DateVol = c.DateVolP and b.NumVol = d.NumVolP and b.DateVol = d.DateVolP
				and a.DateVol = b.DateVol   and  d.HeureDepGMT < (c.HeureDepGMT + (c.Duree/(24.0*60.0))) and c.HeureDepGMT <= d.HeureDepGMT	
			)
		);
		select count(*) into nb2 from (select a.NumVol , b.NumVol from  AffectationP	a, AffectationP b , VolsFret c , VolsFret d 
			where ( a.NumVol <> b.NumVol    and a.NumPersoP  = b.NumPersoP 
				and a.NumVol = c.NumVolF and a.DateVol = c.DateVolF and b.NumVol = d.NumVolF and b.DateVol = d.DateVolF
				and a.DateVol = b.DateVol   and  d.HeureDepGMT < (c.HeureDepGMT + (c.Duree/(24.0*60.0))) and c.HeureDepGMT <= d.HeureDepGMT	
			)
		);
		select count(*) into nb3 from (select a.NumVol , b.NumVol from  AffectationP	a, AffectationP b , VolsPassager c , VolsFret d 
			where ( a.NumVol <> b.NumVol    and a.NumPersoP  = b.NumPersoP 
				and a.NumVol = c.NumVolP and a.DateVol = c.DateVolP and b.NumVol = d.NumVolF and b.DateVol = d.DateVolF
				and a.DateVol = b.DateVol   
				and  ((d.HeureDepGMT < (c.HeureDepGMT + (c.Duree/(24.0*60.0))) and c.HeureDepGMT <= d.HeureDepGMT) 
					or (c.HeureDepGMT < (d.HeureDepGMT + (d.Duree/(24.0*60.0))) and d.HeureDepGMT <= c.HeureDepGMT)
				)	
			)
		);			
        if (nb > 0 or nb2 > 0 or nb3 > 0) then raise_application_error (-20999,'Ce Pilote est affecte en ce moment à un autre vol');
        end if;
END;
/

CREATE OR REPLACE TRIGGER HotessesAffecte2Vols
AFTER INSERT OR UPDATE ON AffectationH
DECLARE
        nb Integer;
BEGIN
		select count(*) into nb from (select a.NumVol , b.NumVol from  AffectationH	a, AffectationH b , VolsPassager c , VolsPassager d 
			where ( a.NumVol <> b.NumVol    and a.NumPersoH  = b.NumPersoH 
				and a.NumVol = c.NumVolP and a.DateVol = c.DateVolP and b.NumVol = d.NumVolP and b.DateVol = d.DateVolP
				and a.DateVol = b.DateVol   and  d.HeureDepGMT < (c.HeureDepGMT + (c.Duree/(24.0*60.0))) and c.HeureDepGMT <= d.HeureDepGMT	
			)
		);
        if (nb > 0) then raise_application_error (-20999,'Cette Hotesse est affectee en ce moment à un autre vol');
        end if;
END;
/
