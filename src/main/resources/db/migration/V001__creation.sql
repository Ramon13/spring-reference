CREATE TABLE country_state (
  id integer, 
  name varchar(16), 
  primary key (id)
);

CREATE TABLE city (
  id integer, 
  name varchar(64), 
  state_id bigint, 
  primary key (id),
  foreign key (state_id) references country_state(id)
);

CREATE TABLE cuisine (
  id integer, 
  name varchar(32), 
  primary key (id)
);

CREATE TABLE payment_method (
  id integer, 
  name varchar(32), 
  primary key (id)
);

CREATE TABLE permission_group (
  id integer, 
  name varchar(16), 
  primary key (id)
);

CREATE TABLE user (
  id integer, 
  creation_date varchar(255), 
  email varchar(255), 
  name varchar(255), 
  password varchar(255), 
  primary key (id)
);

CREATE TABLE permission (
  id integer, 
  description varchar(255), 
  name varchar(255), 
  group_id bigint not null, 
  primary key (id),
  foreign key (group_id) references permission_group(id)
);

CREATE TABLE restaurant (
  id integer, 
  complement varchar(255), 
  district varchar(255), 
  number varchar(255), 
  postal_code varchar(255), 
  street varchar(255), 
  creation_timestamp varchar(255) not null, 
  fee numeric(38,2), 
  name varchar(255) not null, 
  update_timestamp varchar(255) not null, 
  address_city_id bigint, 
  cuisine_id bigint not null, 
  primary key (id),
  foreign key (address_city_id) references city (id),
  foreign key (cuisine_id) references cuisine (id)
);

CREATE TABLE restaurant_payment_method (
  restaurant_id bigint not null, 
  payment_id bigint not null,
  foreign key (restaurant_id) references restaurant (id),
  foreign key (payment_id) references payment_method (id)
);

CREATE TABLE item (
  id integer, 
  active boolean not null, 
  description varchar(255), 
  name varchar(255), 
  price numeric(38,2), 
  restaurant_id bigint, 
  primary key (id),
  foreign key (restaurant_id) references restaurant(id)
);  

CREATE TABLE user_group (
  user_id bigint not null, 
  group_id bigint not null,
  foreign key (user_id) references user (id),
  foreign key (group_id) references permission_group (id)
);