create sequence test_seq 
start with 100
increment by 1 
nomaxvalue; 

--test_seq.NEXTVAL
--test_seq.CURRVAL


insert into Modele values(1,4,2000 ) ;
insert into Modele values(2,6,2500 ) ;
insert into Modele values(3,8,3000 ) ;
insert into Modele values(4,5,3500 ) ;



insert into AvionsFret values(1, 1, 2500, 2500 ) ;

insert into  VolsFret values(1,to_date('10/29/09', 'MM/DD/YY') ,'Lyon','Paris',CURRENT_TIMESTAMP, 4,800, 2000, 1500, 1, 'N');
insert into  VolsFret values(2,to_date('10/29/09', 'MM/DD/YY') ,'Lyon','Paris',CURRENT_TIMESTAMP, 4,800, 2000, 1500, 1, 'N');
insert into  VolsFret values(3,to_date('10/29/09', 'MM/DD/YY') ,'Lyon','Paris',CURRENT_TIMESTAMP, 4,800, 2000, 1500, 1, 'N');
--!!! volume sup
insert into  VolsFret values(7,current_date ,'Lyon','Paris',CURRENT_TIMESTAMP, 4,800, 3000, 1500, 1, 'N');
--!!! poids superieur
insert into  VolsFret values(8,current_date ,'Lyon','Paris',CURRENT_TIMESTAMP, 4,800, 2000, 3500, 1, 'N');
--!!! distance > rayon d action
insert into  VolsFret values(9,current_date ,'Lyon','Paris',CURRENT_TIMESTAMP, 4,3000, 2000, 2500, 1, 'N');


insert into AvionsPassagers values(2, 1, 20, 120 ) ;

insert into  VolsPassager values(4,to_date('10/29/09', 'MM/DD/YY') ,'Lyon','Paris',CURRENT_TIMESTAMP, 1,800, 10, 105, 2, 'N');
insert into  VolsPassager values(5,to_date('10/29/09', 'MM/DD/YY') ,'Lyon','Paris',CURRENT_TIMESTAMP, 1,800, 20, 120, 2, 'N');
--!!!classe 1 sup 
insert into  VolsPassager values(10,current_date ,'Lyon','Paris',CURRENT_TIMESTAMP, 1,800, 21, 120, 2, 'N');
--!!!classe 2 sup
insert into  VolsPassager values(11,current_date ,'Lyon','Paris',CURRENT_TIMESTAMP, 1,800, 20, 121, 2, 'N');
--!!!distance > rayon d action
insert into  VolsPassager values(12,current_date ,'Lyon','Paris',CURRENT_TIMESTAMP, 1,3000, 20, 120, 2, 'N');

-- tester trigger affectation
insert into Pilotes values(1,'kaba', 'mamady', 13, 'berriat', 'grenoble',38000,'France', 20 );
insert into Pilotes values(2,'benkassem', 'youssef', 14, 'jean jaures', 'grenoble',38000,'France', 30 );

insert into Qualification values(1,1,20);
insert into Qualification values(2,2,20);

insert into AffectationP values(1,1, to_date('10/29/09', 'MM/DD/YY') );
--error  pas qualifiquation pour ce modele


--tester trigger resa passager , verifier si place existe dans l'avion
----les places de l'avion passager n�2
insert into Places values(2,1,1,'hublot');
insert into Places values(2,2,1,'hublot');
insert into Places values(2,3,1,'hublot');
insert into Places values(2,4,1,'hublot');
----les clients
insert into Client values(1,'chaabouni','kais',31,'antoine','st martin', 38400, 'France', 'Tunisie', 700);
insert into Client values(2,'shimabukuro','emmanuel',30,'physique','st martin', 38400, 'France', 'France', 400);
----
insert into Reservation values(1,1,to_date('10/29/09', 'MM/DD/YY'));
insert into Reservation values(2,1,to_date('10/29/09', 'MM/DD/YY'));
insert into Reservation values(3,2,to_date('10/29/09', 'MM/DD/YY'));
insert into Reservation values(4,2,to_date('10/29/09', 'MM/DD/YY'));
----
insert into ResaPassager values(4,to_date('10/29/09', 'MM/DD/YY'),1,1,200);
insert into ResaPassager values(4,to_date('10/29/09', 'MM/DD/YY'),2,1,200);
insert into ResaPassager values(4,to_date('10/29/09', 'MM/DD/YY'),3,2,200);
insert into ResaPassager values(4,to_date('10/29/09', 'MM/DD/YY'),4,3,200);
----!!!!
insert into ResaPassager values(4,to_date('10/29/09', 'MM/DD/YY'),5,4,200);
