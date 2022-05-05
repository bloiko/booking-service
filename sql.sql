-- auto-generated definition
create table ticket_order
(
    id        bigint auto_increment,
    username  varchar(100) not null,
    ticket_id bigint       not null,
    constraint order_id_uindex
        unique (id),
    constraint order_order__fk
        foreign key (id) references ticket (id)
);

alter table ticket_order
    add primary key (id);


-- auto-generated definition
create table ticket
(
    id          bigint auto_increment
        primary key,
    source      varchar(100) null,
    destination varchar(100) null,
    trip_date   datetime     null,
    price       bigint       null
);

-- auto-generated definition
create table hibernate_sequence
(
    next_val int not null
);

