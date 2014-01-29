drop table Qualification ;
drop table AffectationH ;
drop table AffectationP ;
drop table Hotesses ;
drop table Pilotes ;
drop table ResaFret ;
drop table ResaPassager ;
drop table Reservation ;
drop table Client ;
drop table Places ;
drop table VolsFret ;
drop table VolsPassager ;
drop table AvionsFret ;
drop table AvionsPassagers ;
drop table Modele;



create table Modele(
Modele        varchar2(30) not null,
NbPilotes     integer not null,
RayonAction   integer  not null,
constraint ck1_Modele CHECK (NbPilotes > 0),
constraint ck2_Modele CHECK (RayonAction > 0),
constraint pk_Modele PRIMARY KEY (Modele)
);

create table AvionsPassagers(
NumAvionP    integer,
Modele       varchar2(30) not null, 
Nb1Cl        integer not null,
Nb2Cl        integer not  null,
constraint ck1_AvionsPassagers CHECK (Nb1Cl > 0),
constraint ck2_AvionsPassagers CHECK (Nb2Cl > 0),
constraint pk1_AvionsPassagers PRIMARY KEY (NumAvionP),
constraint pk2_AvionsPassagers FOREIGN KEY (Modele) references Modele(Modele) ON DELETE CASCADE
);

create table AvionsFret(
NumAvionF    integer,
Modele       varchar2(30) not null, 
VolumeMax        float not null,
PoidsMax        float not  null,
constraint ck1_AvionsFret CHECK (VolumeMax > 0),
constraint ck2_AvionsFret CHECK (PoidsMax > 0),
constraint pk1_AvionsFret PRIMARY KEY (NumAvionF),
constraint pk2_AvionsFret FOREIGN KEY (Modele) references Modele(Modele) ON DELETE CASCADE
);

create table VolsPassager(
NumVolP           varchar2(30) not null,
DateVolP          Date not null,
Origine           varchar2(30),
Destination       varchar2(30) not null,
HeureDepGMT       timestamp not null,
Duree             integer not null,
Distance          integer not null,
Nb1ClMin          integer  not null,
Nb2ClMin          integer  not null,
NumAvionP         integer not null,
Termine           varchar2(1) CHECK (Termine in ('O','N')),
constraint ck1_VolsPassager CHECK (Duree > 0),
constraint ck2_VolsPassager CHECK (Distance > 0),
constraint ck3_VolsPassager CHECK (Nb1ClMin > 0),
constraint ck4_VolsPassager CHECK (Nb2ClMin  > 0),
constraint pk_VolsPassager PRIMARY KEY(NumVolP,DateVolP),
constraint  fk_VolsPassager FOREIGN KEY (NumAvionP) references AvionsPassagers(NumAvionP) ON DELETE CASCADE
);

create table VolsFret(
NumVolF varchar2(30) not null,
DateVolF  Date not null,
Origine varchar2(30) not null,
Destination varchar2(30) not null,
HeureDepGMT timestamp,
Duree integer not null,
Distance  integer not null,
VolumeMin float not null,
PoidsMin  float not null,
NumAvionF integer not null ,
Termine varchar2(1) CHECK (Termine in ('O','N')),
constraint ck1_VolsFret CHECK (Duree > 0),
constraint ck2_VolsFret CHECK (Distance > 0),
constraint ck3_VolsFret CHECK (VolumeMin   > 0),
constraint ck4_VolsFret CHECK (Distance > 0),
constraint ck5_VolsFret CHECK (PoidsMin > 0),
constraint pk_VolsFret PRIMARY KEY (NumVolF,DateVolF),
constraint fk_VolsFret FOREIGN KEY (NumAvionF) references AvionsFret(NumAvionF)
);

create table Places(
NumAvionP              integer not null,
NumPlace               integer not null,
Classe                 integer not null,
Position               varchar2(7) not null,
constraint   ck1_Places CHECK  (NumPlace >0 ),
constraint   ck2_Places CHECK  (Classe=1 or Classe=2 ),
constraint   ck3_Places CHECK (Position IN ('hublot','couloir', 'centre')),
constraint   pk_Places PRIMARY KEY (NumAvionP,NumPlace),
constraint   fk_Places FOREIGN KEY (NumAvionP)  references AvionsPassagers(NumAvionP)
);

create table Client(
NumClient integer NOT NULL,
NomC varchar2(30) NOT NULL,
PrenomC varchar2(30) NOT NULL,
Numero integer NOT NULL,
Rue varchar2(30) NOT NULL,
Ville varchar2(30) NOT NULL,
CP varchar2(30) NOT NULL,
Pays varchar2(30) NOT NULL,
Passeport varchar2(30) NOT NULL,
Points integer NOT NULL,
CONSTRAINT ck_Client CHECK  (NumClient > 0 ),
CONSTRAINT pk_Client PRIMARY KEY (NumClient)
);

create table Reservation(
NumResa integer NOT NULL,
NumClient integer NOT NULL,
DateResa Date not null,
CONSTRAINT pk_Resa PRIMARY KEY (NumResa),
CONSTRAINT fk_Resa FOREIGN KEY (NumClient) references Client(NumClient)
);

create table ResaPassager(
NumVolP   varchar2(30) not null,
DateVolP  Date not null,
NumPlace  integer not null, 
NumResa   integer not null,
Prix      float not null,
constraint ck_ResaPassager  CHECK (NumResa > 0),
constraint pk_ResaPassager  PRIMARY KEY (NumVolP,DateVolP,NumPlace,NumResa),
constraint fk1_ResaPassager  FOREIGN KEY (NumVolP,DateVolP) references VolsPassager(NumVolP,DateVolP) ON DELETE CASCADE,
constraint fk2_ResaPassager  FOREIGN KEY (NumResa) references Reservation(NumResa) ON DELETE CASCADE
);

create table ResaFret(
NumVolF     varchar2(30) not null,
DateVolF    Date not null,
NumResa  integer not null,
Volume     float not null,
Poids      float not null,
Prix       float not null,
constraint ck1_ResaFret  CHECK  (Volume > 0),
constraint ck2_ResaFret  CHECK  (Poids > 0),
constraint ck3_ResaFret  CHECK  (Prix > 0),
constraint pk_ResaFret  PRIMARY KEY (NumVolF,DateVolF,NumResa),
constraint fk1_ResaFret  FOREIGN KEY (NumVolF,DateVolF) references VolsFret(NumVolF,DateVolF) ON DELETE CASCADE,
constraint fk2_ResaFret FOREIGN KEY (NumResa) references  Reservation(NumResa) ON DELETE CASCADE
);


create table Pilotes(
NumPersoP        integer not null,
NomP             varchar2(30) not null,
PrenomP          varchar2(30) not null,
Numero           integer not null,
Rue              varchar2 (30)not null,
Ville            varchar2(30) not null,
CP               varchar2(30) not null,
Pays             varchar2(30) not null,
NbHeuresVolTotal integer not null,
constraint ck1_Pilotes CHECK (NumPersoP > 0),
constraint ck2_Pilotes CHECK (Numero > 0),
constraint ck3_Pilotes CHECK (NbHeuresVolTotal > 0),
constraint pk_Pilotes PRIMARY KEY (NumPersoP)
);


create table Hotesses(

NumPersoH        integer not null,
NomH             varchar2(30) not null,
PrenomH          varchar2(30) not null,
Numero           integer not null,
Rue              varchar2(30) not null,
Ville            varchar2(30) not null,
CP               varchar2(30) not null,
Pays             varchar2(30) not null,
NbHeuresVolTotal integer not null,
Langue2          varchar2(30) not null,
constraint ck1_Hotesses CHECK (NbHeuresVolTotal > 0),
constraint pk_Hotesses PRIMARY KEY (NumPersoH)
);

create table AffectationP(
NumPersoP  integer not null,
NumVol     varchar2(30) not null,
DateVol   Date not null,
constraint pk_AffectationP PRIMARY KEY (NumPersoP,NumVol,DateVol),
constraint fk1_AffectationP FOREIGN KEY (NumPersoP) references Pilotes(NumPersoP) ON DELETE CASCADE
);

create table AffectationH(
NumPersoH  integer not null,
NumVol     varchar2(30) not null,
DateVol    Date not null,
constraint pk_AffectationH PRIMARY KEY (NumPersoH,NumVol,DateVol),
constraint fk1_AffectationH FOREIGN KEY (NumPersoH) references Hotesses(NumPersoH) ON DELETE CASCADE,
constraint fk2_AffectationH FOREIGN KEY (NumVol,DateVol) references VolsPassager(NumVolP,DateVolP) ON DELETE CASCADE
);

create table Qualification(
NumPersoP integer,
Modele varchar2(30) not null,
NbHeuresTotal integer, 
constraint pk_Qualification  PRIMARY KEY (NumPersoP, Modele),
constraint fk1_Qualification FOREIGN KEY (NumPersoP) references Pilotes(NumPersoP) ON DELETE CASCADE,
constraint fk2_Qualification FOREIGN KEY (Modele) references Modele(Modele) ON DELETE CASCADE
);
