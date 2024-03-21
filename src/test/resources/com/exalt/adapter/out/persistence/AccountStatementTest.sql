insert into BANK_ACCOUNT (ID, FIRST_NAME, LAST_NAME, OVERDRAFT_AMOUNT_AUTHORIZATION, ACCOUNT_TYPE)
values (1, 'Jean', 'Dujardin',1000,'CURRENT');

insert into BANK_ACCOUNT (ID, FIRST_NAME, LAST_NAME, OVERDRAFT_AMOUNT_AUTHORIZATION, ACCOUNT_TYPE)
values (2, 'Albert', 'Dupontel',0,'BOOKLET');

insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (1,'DEPOSIT',100, '2024-01-15 08:00:00.0' ,1);

insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (2,'DEPOSIT',200, '2024-03-16 08:00:00.0' ,1);

insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (3,'DEPOSIT',800, '2024-03-17 08:00:00.0' ,1);

insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (4,'WITHDRAWAL',500, '2024-03-18 08:00:00.0' ,1);

insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (5,'DEPOSIT',1500, '2024-03-18 18:00:00.0' ,1);

insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (6,'WITHDRAWAL',400, '2024-03-18 19:00:00.0' ,1);


insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (11,'DEPOSIT',800, '2024-01-15 08:00:00.0' ,2);

insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (12,'DEPOSIT',400, '2024-03-16 08:00:00.0' ,2);

insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (13,'DEPOSIT',200, '2024-03-17 08:00:00.0' ,2);

insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (14,'WITHDRAWAL',300, '2024-03-18 08:00:00.0' ,2);

insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (15,'DEPOSIT',1500, '2024-03-18 18:00:00.0' ,2);

insert into ACCOUNT_TRANSACTION (ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, ACCOUNT_ID)
values (16,'WITHDRAWAL',400, '2024-03-18 19:00:00.0' ,2);


