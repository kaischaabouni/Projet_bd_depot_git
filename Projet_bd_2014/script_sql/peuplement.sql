ALTER SESSION SET NLS_DATE_FORMAT ='DD/MM/YYYY';
ALTER SESSION SET NLS_TIMESTAMP_FORMAT ='HH:MI:SS';
ALTER SESSION SET DDL_LOCK_TIMEOUT = 10;

INSERT INTO Modele VALUES ('A320', 20, 2500);
INSERT INTO Modele VALUES ('A760', 35, 5000);
INSERT INTO Modele VALUES ('R400', 15, 4000);
INSERT INTO Modele VALUES ('B747', 65, 10000);

INSERT INTO AvionsPassagers VALUES ('','A320', 50, 150);
INSERT INTO AvionsPassagers VALUES ('','B747', 70, 230);
INSERT INTO AvionsPassagers VALUES ('','B747', 70, 230);

INSERT INTO AvionsFret VALUES ('', 'R400',500, 13000);
INSERT INTO AvionsFret VALUES ('', 'R400',500, 13000);

INSERT INTO VolsPassager VALUES ('','10/04/2014','Grenoble','Paris', '11:30:00',2,600,30,100,1,'N');
INSERT INTO VolsFret VALUES ('','10/01/2014','Grenoble','Paris', '12:30:00',2,600,30,100,1,'N');
INSERT INTO VolsPassager VALUES ('','15/01/2014','Nice','Paris', '08:30:00',2,1200,30,100,2,'N');
INSERT INTO VolsPassager VALUES ('','13/01/2014','Toulouse','Nice', '09:00:00',2,800,30,100,1,'N');
INSERT INTO VolsFret VALUES ('','10/01/2014','Caen','Paris', '06:30:00',2,600,30,100,2,'N');

INSERT INTO Places VALUES (1, 4,1, 'hublot');
INSERT INTO Places VALUES (2, 5,1, 'couloir');
INSERT INTO Places VALUES (3, 6,2, 'couloir');
INSERT INTO Places VALUES (3, 7,2, 'couloir');

INSERT INTO Client VALUES ('', 'Robert','Redford',15,'gambeta','Grenoble','38000','France','1334453',0);
INSERT INTO Client VALUES ('', 'Bob','Dylan', 12,'foch','Grenoble', '38000','France','2454435',0);
INSERT INTO Client VALUES ('', 'Jean','Claude',6,'république','Paris','12','France','272819', 0);
INSERT INTO Client VALUES ('', 'Cyril','Anouna',22,'gabriel Peri','Cannes','06200','France','2742876', 0);
INSERT INTO Client  VALUES ('', 'Patrick','Sebastien',44,'Garibaldi','Nice','06000', 'France','43481',0);

INSERT INTO Reservation VALUES ('',1,'10/04/2014');
INSERT INTO Reservation VALUES ('',2,'08/05/2014');
INSERT INTO Reservation VALUES ('',3,'03/06/2014');

INSERT INTO ResaPassager VALUES (1,'10/04/2014',4,1,800);
INSERT INTO ResaPassager VALUES (3,'15/01/2014',5,2,950);
--INSERT INTO ResaPassager VALUES (3,'15/01/2014',7,3,200);

INSERT INTO ResaFret VALUES (2,'10/01/2014',2,200,1500,1800);
INSERT INTO ResaFret VALUES (5,'10/01/2014',2,150,900,1300);

INSERT INTO Pilotes VALUES ('','george','delajungle',2,'lePerrier','grenoble',38000,'France',150);
INSERT INTO Pilotes VALUES ('','kaba','mamadi',15,'jean médecin', 'nice', 06000,'France',28);
INSERT INTO Pilotes VALUES ('','benkassem','youssef',18,'jean jaurès','Paris',75000,'France',42);

INSERT INTO Hotesses VALUES ('','georgette','delajungle',23,'moulins','grenoble',38000,'France',68,'russe');
INSERT INTO Hotesses VALUES ('','kabaLisa','mamadi',55,'raimbaldi', 'nice', 06000,'France',78,'japonais');
INSERT INTO Hotesses VALUES ('','lolita','youssef',181,'delavega','Paris',75000,'France',57,'allemand');

INSERT INTO Qualification VALUES (1,'A320',99);
INSERT INTO Qualification VALUES (2,'B747',55);

INSERT INTO AffectationP VALUES (1,1,'10/04/2014');
INSERT INTO AffectationP VALUES (2,3,'15/01/2014');

INSERT INTO AffectationH VALUES (1,1,'10/04/2014');
INSERT INTO AffectationH VALUES (2,3,'15/01/2014');
