CREATE OR REPLACE TRIGGER ContrainteCapaciteFret
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
END;
/

CREATE OR REPLACE TRIGGER ContrainteCapacitePassagers
AFTER INSERT OR UPDATE ON VolsPassager
DECLARE
	nb Integer;
BEGIN
	select count(*) into nb from (select * from AvionsPassagers a INNER JOIN VolsPassager v ON a.NumAvionP  = v.NumAvionP where (a.VolumeMax < v.VolumeMin) );
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne satisfait pas les contraintes de volume');
	end if;

	select count(*) into nb from (select * from AvionsPassagers a INNER JOIN VolsPassager v ON a.NumAvionP  = v.NumAvionP where (a.PoidsMax < v.PoidsMin) );
	if (nb > 0) then raise_application_error (-20999,'L avion affecté à ce vol ne satisfait pas les contraintes de poids');
	end if;	
END;
/

