INSERT INTO address (id,country, region, city, street, house, flat, created, modified)
values (1,'Russia','SFO','Novosibirsk','Pushkina','25','112','12-01-2021','13-01-2021'),
       (2,'Russia','SZR','Moscow','Pushkina','17','322','12-01-2021','13-01-2021'),
       (3,'Russia','SZR','SPB','Pushkina','16','444','12-01-2021','13-01-2021'),
       (4,'Russia','Ural','Izhevsk','Pushkina','162','15','12-01-2021','13-01-2021'),
       (5,'Russia','Ural','Chelyab','Pushkina','654','15','12-01-2021','13-01-2021'),
       (6,'Russia','DVR','Vladivostok','Mamontova','23','122','12-01-2021','13-01-2021');

INSERT INTO customer (id,registred_address_id, actual_address_id, first_name, last_name, middle_name, sex)
values (1,1,2,'Sergey','Fedorov','Sergeevich','male'),
       (2,3,4,'Genadiy','Pluskin','Olegovich','male'),
       (3,5,6,'Olga','Lopatina','Vladimirovna','female');
alter sequence if exists address_id_seq increment by 1 minvalue 10  maxvalue 10000
    start with 10 restart 11;
alter sequence if exists customer_id_seq increment by 1 minvalue 10  maxvalue 10000
    start with 10 restart 11;