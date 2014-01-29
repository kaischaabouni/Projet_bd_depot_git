create sequence vols_seq 
start with 100
increment by 1 
nomaxvalue; 

SET AUTOCOMMIT OFF;



--1--
--Planification d'un nouveau vol READ COMMITTED
   * Affectation d'un Avion  
        on parcours la table vol
         chercher tous les vols dont la destination est identique a l'origine du vol a planifier
         on recherche le max de  la date de tous les vols selectionnés(pour voir la derniere date ou le vol a atteri)
	  if(date d'arrive du vol selectionné< = date du vol à planifier)
           on insere le le vol avec le numero d'avion qui sera selectionné)  

    * Affectation d'un pilote 
        
        verifier que le pilote est qualifie pour cet Avion
         si le pilote n'a jamais ete affecte on l'insere.
         sinon (verifier toutes les contraintes liées au pilotes)
		selectionner toutes les affections du pilote
 
                     parcourir la selection des affectations cherchant le maximum des date par rapport a la date a affecter
                       
                     si on trouve le max on verifie deux cas 
                       1 cas : 
                              la date a affecter est inferieur au max dans ce cas: 
                               verifier les contraintes sur les heures , la duree et le temps de repos du pilotes
			       verifier les contraintes sur les heures , la duree et le temps de repos du pilotes
                               
         * Affectation d'un hotessse
            verifier les places places reservées 
            verfier les contraintes liées au hotesses(disponibilite et lieu du dernier vol) 
             choisir les hotesses a affecter.
       

--insertion d'un vol pour la date 10/29/09 , de Lyon à Paris
--chercher un avion disponible ( pas affecté à un vol de date  10/29/09 , 
select  NumAvionF  from AvionsFret where not exists (select a.NumAvionF from AvionsFret a, VolsFret v where v.NumAvionF = a.NumAvionF and v.DateVolF > )


insert into  VolsFret values(vols_seq.NEXTVAL, to_date('10/29/09', 'MM/DD/YY') ,'Lyon','Paris',CURRENT_TIMESTAMP, 200,800, 2000, 1500, 1, 'N');


2) Modification de la planification d'un vol existant  SERIALIZABLE
   remplacer un avion par un autre      

     verifier le modele de l'avion a remplacer 
     verfier si l'avion remplaçant est disponible et le modele est equivalent a celui qui a eté remplacer
     verifier que les  places s'il s'agit d'un avion passagers sont dans cet avion sont au minimum egales a l'avion remplacer.
     affecter les memes numerotation que l'avion a remplacer.
  
    Modification de l'equipages 
   pilotes:
     rechercher dans la tables pilotes, les pilotes disponibles(voir planification )
      si le  nombre est suffisant on affecte les pilotes 
      sinon on interdit la supression.
   hotesses:
    rechercher dans la tables hotesses les hotesses disponibles(voir planification )
      si le  nombre est suffisant on affecte les pilotes 
      sinon on interdit la supression.  

        
    
    
3) supppression d'un vol 
     selon les vols (fret ou  passagers) recupere les numero de vol  et la date de vol a supprimer
      si un avion est est diponible , on reaffecte et on suprime l'ancicien et sinon on refuse la suppression
       dans ce cas on lit dans la table place pour rechercher les places qui sont disponible ; on affecte toutes les places comme prevu dans le vol precedent.
 



--4--
--Confirmation de la terminaison d'un vol et mise à jour des informations clients, pilotes, hotesses
--Exemple: confirmer la terminaison du vol fret n°7 de la date  10/29/09
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
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
--afficher les données concernants ses reservations Fret
select rf.* from Reservation r , ResaFret rf, Client c 
	where (c.NumClient = r.NumClient and r.NumResa = rf.NumResa and c.NomC='Dupont' and c.PrenomC = 'Pierre' ) ;
--afficher les données concernants ses reservations Passagers
select rp.* from Reservation r , ResaPassager rp, Client c 
	where (c.NumClient = r.NumClient and r.NumResa = rp.NumResa and c.NomC='Dupont' and c.PrenomC = 'Pierre' ) ;
commit;

--6--
--Reservation de la part du client
--Exemple: Client n° 19 veut réserve un vol passager 
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
--Le client choisit un vols pour la réservation en choisissant: horaires=10h , depart = Lyon , destination = Paris
select NumVolP from VolsPassager where (Origine ='Lyon' and Destination = 'Paris' and  to_hour(HeureDepGMT) < 11 and  to_hour(HeureDepGMT) > 9)
--Le client choisit les places 56 et 57
insert into Reservation values(1,19,to_date('10/29/09', 'MM/DD/YY'));
insert into ResaPassager values(4,to_date('10/29/09', 'MM/DD/YY'),56,1,200);
insert into ResaPassager values(4,to_date('10/29/09', 'MM/DD/YY'),57,1,200);
--Le client peut rajouter d'autres vols dans la réservation

--On affiche les points du client
select Points from Client where NumClient = 19 ;
--On vérifie si les points sont utilisables

--Si l'utilisation est possible on remet le prix à 0

--On remet le nombre de point à 0
update Client set Points = 0 where NumClient = 19 ;
COMMIT ;

--7--
--Modification de la réservation d'un client
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
--changement de places de 57 à 55
update ResaPassager set NumPlace = 55 where (NumVolP= 4 and DateVolP = to_date('10/29/09', 'MM/DD/YY') and NumResa = 1 and NumPlace = 57) ;
--changement d'un vol
----suppression de cette reservation
delete Reservation where NumResa = 1 ;
----ajout d'une nouvelle réservation comme dans la fonctionnalité 6

COMMIT ;

--8--
--Suppression de la réservation d'un client
--
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
delete Reservation where NumResa = 1 ;
COMMIT;

--9--
--Ajout et suppression d'un personnel de vol
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
--ajout d'un pilote
insert into AffectationP values(1,1, to_date('10/29/09', 'MM/DD/YY') );
--ajout d'une hotesse
insert into AffectationH values(1,1, to_date('10/29/09', 'MM/DD/YY') );
--suppression d'un pilote
delete AffectationP where NumPersoP = 1;
--suppression d'une hotesse
delete AffectationH where NumPersoH = 1;

COMMIT;

