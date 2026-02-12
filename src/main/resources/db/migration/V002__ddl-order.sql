CREATE TABLE f_order (
    id bigint not null,
    sub_total real not null,
    shipping_tax real not null,
    total real not null,
    creation_date varchar(64) not null,
    confirmation_date varchar(64),
    cancel_date varchar(64),
    dispatch_date varchar(64),
    postal_code varchar(32) not null,
    street varchar(32) not null,
    district varchar(255) not null, 
    number integer not null,
    complement varchar(255) not null,
    address_city_id bigint not null, 
    payment_method_id bigint not null,
    restaurant_id bigint not null,
    status varchar(10) not null,
    primary key (id),
    foreign key (address_city_id) references city (id),
    foreign key (payment_method_id) references payment_method (id),
    foreign key (restaurant_id) references restaurant (id)
);

CREATE TABLE item_order (
    id bigint not null,
    amount number not null,
    unity_price real not null,
    total real not null,
    notes varchar(255),
    item_id bigint not null,
    order_id bigint not null,
    primary key (id),
    foreign key (order_id) references f_order(id),
    foreign key (item_id) references item(id)
);