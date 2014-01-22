CREATE OR REPLACE TRIGGER ContraintesVolFret
AFTER INSERT OR UPDATE ON VolsFret
DECLARE
	nb Integer;
BEGIN
	select count(*) into nb from (select * from AvionsFret a INNER JOIN VolsFret v ON a.NumAvionF  = v.NumAvionF where (a.VolumeMax < v.VolumeMin) );
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne satisfait pas les contraintes de volume');
	end if;

	select count(*) into nb from (select * from AvionsFret a INNER JOIN VolsFret v ON a.NumAvionF  = v.NumAvionF where (a.PoidsMax < v.PoidsMin) );
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne satisfait pas les contraintes de poids');
	end if;	
	
	select  count(*) into nb from (select * from AvionsFret a, Modele m , VolsFret v where (a.NumAvionF  = v.NumAvionF and a.modele = m.Modele 
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
	select count(*) into nb from (select * from AvionsPassagers a INNER JOIN VolsPassager v ON a.NumAvionP  = v.NumAvionP where (a.Nb1Cl < v.Nb1ClMin) );
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne satisfait pas le nombre min en premiere classe');
	end if;

	select count(*) into nb from (select * from AvionsPassagers a INNER JOIN VolsPassager v ON a.NumAvionP  = v.NumAvionP where (a.Nb2Cl < v.Nb2ClMin) );
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne satisfait pas le nombre min en deuxieme classe');
	end if;	
	
	select  count(*) into nb from (select * from AvionsPassagers a, Modele m , VolsPassager v where (a.NumAvionP  = v.NumAvionP and a.modele = m.Modele 
																						and m.RayonAction < v.Distance ));
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne permet pas d effectuer cette distance');
	end if;		
	
END;
/

CREATE OR REPLACE TRIGGER ContrainteAffectationP
AFTER INSERT OR UPDATE ON AffectationP
DECLARE
	nb Integer;
BEGIN
	select count(*) into nb from (select * from AffectationP aff,  VolsPassager v, AvionsPassagers a, Modele m , Qualification q 
		where (aff.NumVol = v.NumVolP and aff.DateVol = v.DateVolP and v.NumAvionP = a.NumAvionP and a.Modele = m.Modele 
				and aff.NumPersoP = q.NumPersoP and a.Modele = q.Modele) );
	if (nb = 0) then raise_application_error (-20999,'Le pilote affecte n a pas la qualification pour le modele d avion effectuant ce vol');
	end if;	
	
	select count(*) into nb from (select * from AffectationP aff,  VolsFret v, AvionsFret a, Modele m , Qualification q 
		where (aff.NumVol = v.NumVolF and aff.DateVol = v.DateVolF and v.NumAvionF = a.NumAvionF and a.Modele = m.Modele 
				and aff.NumPersoP = q.NumPersoP and a.Modele = q.Modele) );
	if (nb = 0) then raise_application_error (-20999,'Le pilote affecte n a pas la qualification pour le modele d avion effectuant ce vol');
	end if;		
END;
/
