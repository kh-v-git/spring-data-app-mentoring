insert into event
    (date, title, ticket_price)
values ('2023-06-11', 'The Matrix', 5.25),
       ('2023-07-14', 'Pirates of the Barbarian', 9.99),
       ('2023-08-14', 'Transformers', 7.25);

DECLARE @ID_1 UNIQUEIDENTIFIER
SET @ID_1 = NEWID()
DECLARE @ID_2 UNIQUEIDENTIFIER
SET @ID_2 = NEWID()
DECLARE @ID_3 UNIQUEIDENTIFIER
SET @ID_3 = NEWID()

insert into [user]
    (uuid, name, email)
values (@ID_1, 'John Doe', 'john_doe@gmail.com'),
       (@ID_2, 'Anna Martinez', 'anna_martinez@yahoo.com'),
       (@ID_3, 'Federico Tarantino', 'federico_tarantino@microsoft.com');


DECLARE @ID_acc_1 UNIQUEIDENTIFIER
SET @ID_acc_1 = NEWID()
DECLARE @ID_acc_2 UNIQUEIDENTIFIER
SET @ID_acc_2 = NEWID()
DECLARE @ID_acc_3 UNIQUEIDENTIFIER
SET @ID_acc_3 = NEWID()


DECLARE @ID_user_1 BIGINT
SET @ID_user_1 = (select id
                  from [user]
                  where name = 'John Doe')
DECLARE @ID_user_2 BIGINT
SET @ID_user_2 = (select id
                  from [user]
                  where name = 'Anna Martinez')
DECLARE @ID_user_3 BIGINT
SET @ID_user_3 = (select id
                  from [user]
                  where name = 'Federico Tarantino')

insert into user_account
    (uuid, amount, user_id)
values (@ID_acc_1, 550.25, @ID_user_1),
       (@ID_acc_2, 450.69, @ID_user_2),
       (@ID_acc_3, 750.44, @ID_user_3)


DECLARE @ID_user_1 BIGINT
SET @ID_user_1 = (select id
                  from [user]
                  where name = 'John Doe')
DECLARE @ID_user_2 BIGINT
SET @ID_user_2 = (select id
                  from [user]
                  where name = 'Anna Martinez')
DECLARE @ID_user_3 BIGINT
SET @ID_user_3 = (select id
                  from [user]
                  where name = 'Federico Tarantino')


DECLARE @ID_event_1 BIGINT
SET @ID_event_1 = (select id
                   from event
                   where title = 'The Matrix')
DECLARE @ID_event_2 BIGINT
SET @ID_event_2 = (select id
                   from event
                   where title = 'Pirates of the Barbarian')
DECLARE @ID_event_3 BIGINT
SET @ID_event_3 = (select id
                   from event
                   where title = 'Transformers')

insert into ticket
    (seat_number, ticket_category, booking_status, event_id, user_id)
values (1, 'LUX', 0,  @ID_event_1, @ID_user_1),
       (2, 'PREMIUM', 0, @ID_event_1, @ID_user_2),
       (3, 'PREMIUM', 0, @ID_event_1, @ID_user_3),
       (4, 'STANDARD', 0, @ID_event_1, @ID_user_1),
       (5, 'STANDARD', 0, @ID_event_1, @ID_user_2),
       (6, 'STANDARD', 0, @ID_event_1, @ID_user_3),
       (1, 'LUX', 0, @ID_event_2, @ID_user_2),
       (2, 'PREMIUM', 0, @ID_event_2, @ID_user_1),
       (3, 'PREMIUM', 0, @ID_event_2, @ID_user_3),
       (4, 'STANDARD', 0, @ID_event_2, @ID_user_2),
       (5, 'STANDARD', 0, @ID_event_2, @ID_user_1),
       (6, 'STANDARD', 0, @ID_event_2, @ID_user_3),
       (1, 'LUX', 0, @ID_event_2, @ID_user_3),
       (2, 'PREMIUM', 0, @ID_event_2, @ID_user_1),
       (3, 'PREMIUM', 0, @ID_event_2, @ID_user_2),
       (4, 'STANDARD', 0, @ID_event_2, @ID_user_3),
       (5, 'STANDARD', 0, @ID_event_2, @ID_user_1),
       (6, 'STANDARD', 0, @ID_event_2, @ID_user_2)
