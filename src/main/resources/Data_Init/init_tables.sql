create table "event"
(
    "id"    BIGINT identity unique,
    "date"  date         not null,
    "title" varchar(255) not null,
    primary key ("id")
);

create table "user"
(
    "id"    BIGINT identity unique,
    "uuid"  varchar(255) not null,
    "name"  varchar(255) not null,
    "email" varchar(255) not null,
    primary key ("id")
);

create table "ticket"
(
    "id"              BIGINT identity unique,
    "seat_number"     int          not null,
    "ticket_category" varchar(255) not null,
    "event_id"        BIGINT,
    "user_id"         BIGINT,
    primary key ("id"),
    foreign key (event_id) references event (id),
    foreign key (user_id) references [user] (id)
);

create table "user_account"
(
    "id"      BIGINT identity unique,
    "uuid"    varchar(255) not null,
    "amount"  decimal      not null,
    "user_id" BIGINT       not null,
    primary key ("id"),
    foreign key ("user_id") references [user] (id)
);