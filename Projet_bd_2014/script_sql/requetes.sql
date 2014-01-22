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
SET AUTOCOMMIT OFF;
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

--confirmer la terminaison du vol fret n°7 de la date  10/29/09
update VolsFret set Termine = 'O' where (NumAvionF = 7 and DateVolF = to_date('10/29/09', 'MM/DD/YY')) ;
--chercher a durée du vol
select Duree from VolsFret where (NumAvionF = 7 and DateVolF = to_date('10/29/09', 'MM/DD/YY'));
--mettre a jour les point des clients de ce vol, ajouter la duree d = 200
update Client c set c.Points = c.Points + 200  where c.

