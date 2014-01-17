create sequence test_seq 
start with 100
increment by 1 
nomaxvalue; 

--test_seq.NEXTVAL
--test_seq.CURRVAL


insert into Modele values(1,4,2000 ) ;

insert into AvionsFret values(1, 1, 2500, 2500 ) ;

insert into  VolsFret values(1,current_date ,'Lyon','Paris',CURRENT_TIMESTAMP, 4,800, 2000, 1500, 1, 'N');
insert into  VolsFret values(2,current_date ,'Lyon','Paris',CURRENT_TIMESTAMP, 4,800, 2000, 1500, 1, 'N');

insert into  VolsFret values(2,current_date ,'Lyon','Paris',CURRENT_TIMESTAMP, 4,800, 3000, 1500, 1, 'N');
