create sequence vols_seq 
start with 100
increment by 1 
nomaxvalue; 




--1--
--Planification d'un nouveau vol
--insertion d'un vol pour la date 10/29/09 , de Lyon à Paris
--chercher un avion disponible ( pas affecté à un vol de date  10/29/09 , 
select  NumAvionF  from AvionsFret where not exists (select a.NumAvionF from AvionsFret a, VolsFret v where v.NumAvionF = a.NumAvionF and v.DateVolF > )


insert into  VolsFret values(vols_seq.NEXTVAL, to_date('10/29/09', 'MM/DD/YY') ,'Lyon','Paris',CURRENT_TIMESTAMP, 200,800, 2000, 1500, 1, 'N');







--4--
--Confirmation de la terminaison d'un vol et mise à jour des informations clients, pilotes, hotesses
--Exemple: confirmer la terminaison du vol fret n°7 de la date  10/29/09
SET AUTOCOMMIT OFF;
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
update VolsFret set Termine = 'O' where (NumAvionF = 7 and DateVolF = to_date('10/29/09', 'MM/DD/YY')) ;
--chercher la durée du vol
select Duree from VolsFret where (NumAvionF = 7 and DateVolF = to_date('10/29/09', 'MM/DD/YY'));
--mettre a jour les point des clients de ce vol, ajouter la duree d = 200

update Client c set c.Points = c.Points + 200  
	where (c.NumClient in (select r.NumClient from  Reservation r, ResaFret rf 
		where r.NumResa = rf.NumResa and rf.Numvol = 7 and rf.DateVol = to_date('10/29/09', 'MM/DD/YY'))) ;		
					
update Pilotes p set p.NbHeuresVolTotal = p.NbHeuresVolTotal + 200 
	where (p.NumPersoP in (select aff.NumPersoP from AffectationP aff where (aff.Numvol = 7  and aff.DateVol = to_date('10/29/09', 'MM/DD/YY') )));

update Hotesses h set h.NbHeuresVolTotal = h.NbHeuresVolTotal + 200 
	where (h.NumPersoH in (select aff.NumPersoH from AffectationH aff where (aff.Numvol = 7  and aff.DateVol = to_date('10/29/09', 'MM/DD/YY') )));
commit;

--5--
--Consultation des commandes d'un client
--Exemple: consulter les commandes du client Pierre Dupont
SET AUTOCOMMIT OFF;
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
--afficher les données concernants ses reservations Fret
select rf.* from Reservation r , ResaFret rf, Client c 
	where (c.NumClient = r.NumClient and r.NumResa = rf.NumResa and c.NomC='Dupont' and c.PrenomC = 'Pierre' ) ;
--afficher les données concernants ses reservations Passagers
select rp.* from Reservation r , ResaPassager rp, Client c 
	where (c.NumClient = r.NumClient and r.NumResa = rp.NumResa and c.NomC='Dupont' and c.PrenomC = 'Pierre' ) ;
commit;

