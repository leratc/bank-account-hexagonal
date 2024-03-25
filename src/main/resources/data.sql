insert into bank_account (id, first_name, last_name, overdraft_amount_authorization, account_type)
values (1, 'Jean', 'Dujardin',1000,'CURRENT');

insert into bank_account (id, first_name, last_name, overdraft_amount_authorization, account_type)
values (2, 'Albert', 'Dupontel',0,'BOOKLET');

insert into account_transaction (id, transaction_type, amount, transaction_date, account_id)
values (1,'DEPOSIT',100, '2024-01-15 08:00:00.0' ,1);

insert into account_transaction (id, transaction_type, amount, transaction_date, account_id)
values (2,'DEPOSIT',200, '2024-03-16 08:00:00.0' ,1);

insert into account_transaction (id, transaction_type, amount, transaction_date, account_id)
values (3,'DEPOSIT',800, '2024-03-17 08:00:00.0' ,1);

insert into account_transaction (id, transaction_type, amount, transaction_date, account_id)
values (4,'WITHDRAWAL',500, '2024-03-18 08:00:00.0' ,1);

insert into account_transaction (id, transaction_type, amount, transaction_date, account_id)
values (5,'DEPOSIT',1500, '2024-03-18 18:00:00.0' ,1);

insert into account_transaction  (id, transaction_type, amount, transaction_date, account_id)
values (6,'WITHDRAWAL',400, '2024-03-18 19:00:00.0' ,1);


insert into account_transaction (id, transaction_type, amount, transaction_date, account_id)
values (11,'DEPOSIT',800, '2024-01-15 08:00:00.0' ,2);

insert into account_transaction (id, transaction_type, amount, transaction_date, account_id)
values (12,'DEPOSIT',400, '2024-03-16 08:00:00.0' ,2);

insert into account_transaction (id, transaction_type, amount, transaction_date, account_id)
values (13,'DEPOSIT',200, '2024-03-17 08:00:00.0' ,2);

insert into account_transaction (id, transaction_type, amount, transaction_date, account_id)
values (14,'WITHDRAWAL',300, '2024-03-18 08:00:00.0' ,2);

insert into account_transaction (id, transaction_type, amount, transaction_date, account_id)
values (15,'DEPOSIT',1500, '2024-03-18 18:00:00.0' ,2);

insert into account_transaction (id, transaction_type, amount, transaction_date, account_id)
values (16,'WITHDRAWAL',400, '2024-03-18 19:00:00.0' ,2);



