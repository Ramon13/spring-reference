delete from restaurant_payment_method;
delete from permission;
delete from user_group;
delete from item_order;
delete from f_order;
delete from user;
delete from permission_group;
delete from item;
delete from payment_method;
delete from restaurant;
delete from cuisine;
delete from city;
delete from country_state;

insert into cuisine (name) 
  values ('Thai'), ('Indian');

insert into country_state (name) 
  values ('Assam'), ('Bihar'); 
  
insert into city (name, state_id) 
  values ('Dhuburi', 1), ('Dibrugarh', 1), ('Ara', 2), ('Barauni', 2);

insert into restaurant (name, fee, cuisine_id, address_city_id, postal_code, street, district, number, complement, creation_timestamp, update_timestamp) 
  values 
  ('Oppa Gangnan', 2.33, 1, 1, '282001', 'Forest Colony', 'NA', '101', 'NA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
  ('Ghandi is milord', 1.33, 2, 2, '400001', 'Colaba', 'NA', '303', 'NA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
  
insert into payment_method (name) 
  values ('Cash'), ('Credit Card'), ('Debit Card');

insert into restaurant_payment_method (restaurant_id, payment_id) 
  values (1, 1), (1, 2), (1, 3), (2, 1);


insert into item (name, description, price, active, restaurant_id) 
  values ('spiced tofu', 'carrot, coriander, cumin seeds...', 10.39, false, 1);

insert into permission_group (name) 
  values ('default');    

insert into permission (name, description, group_id) 
  values ('create_new_user', 'creates a new user', 1);

insert into user (name, email, password, creation_date) 
  values ('John Doe', 'john@a.com', 'admin1admin', CURRENT_TIMESTAMP);

insert into user_group (user_id, group_id) 
  values (1,1);

INSERT INTO f_order (id, sub_total, shipping_tax, total, creation_date, confirmation_date, cancel_date, dispatch_date, postal_code, street, district, number, complement, address_city_id, payment_method_id, restaurant_id, status)
VALUES (1, 100.00, 15.00, 115.00, CURRENT_TIMESTAMP, NULL, NULL, NULL, '12345-678', 'Main Street', 'NYC', 123, 'Apartment 45', 1, 1, 1, 'DELIVERED');

INSERT INTO item_order (id, amount, unity_price, total, notes, order_id, item_id)
VALUES (1, 2, 50.00, 100.00, 'No onions', 1, 1);
