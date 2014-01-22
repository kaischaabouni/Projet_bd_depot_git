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



