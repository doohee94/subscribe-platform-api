set FOREIGN_KEY_CHECKS = 0;

truncate table store_services_list;
truncate table service_category;
truncate table service_image;
truncate table service_option;
truncate table service;
truncate table category;
truncate table store;
truncate table user_authority;
truncate table customer;
truncate table authority;
truncate table user;
truncate table subscribe;
truncate table picked_option;
truncate table payment_result;
truncate table pay_info;
truncate table comm_code;
truncate table group_code;

set FOREIGN_KEY_CHECKS = 1;

insert into user (user_id, created_date, last_modified_date, email, user_name, password, user_status)
values (2, now(), now(), 'USER','USER','$2a$10$ss0EtziPLj6QiVATRUmoKuJ.TwQ3z8jKq1XT9eorzxnAgXjdp5zma','WAITING');

insert into user (user_id, created_date, last_modified_date, email, user_name, password, user_status)
values (5, now(), now(), 'maro@naver.com','USER2','$2a$10$ss0EtziPLj6QiVATRUmoKuJ.TwQ3z8jKq1XT9eorzxnAgXjdp5zma','WAITING');

insert into user (user_id, created_date, last_modified_date, email, user_name, password, user_status)
values (3, now(), now(), 'STORE','STORE','$2a$10$ss0EtziPLj6QiVATRUmoKuJ.TwQ3z8jKq1XT9eorzxnAgXjdp5zma','WAITING');

insert into user (user_id, created_date, last_modified_date, email, user_name, password, user_status)
values (1, now(), now(), 'string','string','$2a$10$ss0EtziPLj6QiVATRUmoKuJ.TwQ3z8jKq1XT9eorzxnAgXjdp5zma','WAITING');

insert into user (user_id, created_date, last_modified_date, email, user_name, password, user_status)
values (4, now(), now(), 'ADMIN','ADMIN','$2a$10$ss0EtziPLj6QiVATRUmoKuJ.TwQ3z8jKq1XT9eorzxnAgXjdp5zma','WAITING');

insert into authority (id, created_date, last_modified_date, authority)
values (1,now(),now(),'ADMIN');
insert into authority (id, created_date, last_modified_date, authority)
values (2,now(),now(),'USER');
insert into authority (id, created_date, last_modified_date, authority)
values (3,now(),now(),'STORE');

insert into user_authority (user_id, authority_id) values (1,1);
insert into user_authority (user_id, authority_id) values (1,2);
insert into user_authority (user_id, authority_id) values (1,3);

insert into user_authority (user_id, authority_id) values (2,2);
insert into user_authority (user_id, authority_id) values (3,3);
insert into user_authority (user_id, authority_id) values (4,1);
insert into user_authority (user_id, authority_id) values (5,2);


insert into customer (customer_id, created_date, last_modified_date, address1, address2, zip_code, phone_number,user_id)
values (1,now(),now(),'인천광역시','남동구','1234','010213123',2);
insert into customer (customer_id, created_date, last_modified_date, address1, address2, zip_code, phone_number,user_id)
values (2,now(),now(),'인천광역시','동구','1234','01098765432',5);


insert into store (store_id, business_num, store_name, user_id)
values (1, '12341234','견과류가게',1);


insert into category values(1, now(), now(),'식품');
insert into category values(2, now(), now(),'생활');
insert into category values(3, now(), now(),'패션의류');
insert into category values(4, now(), now(),'뷰티');
insert into category values(5, now(), now(),'스포츠/레저');
insert into category values(6, now(), now(),'패션잡화');


insert into service (service_id, created_date, last_modified_date, available_day, detail_contents, service_name,
                     service_cycle, store_id)
values (1, now(), now(), '월','안녕하세요','견과류 1', 'WEEK',1);

insert into service (service_id, created_date, last_modified_date, available_day, detail_contents, service_name,
                     service_cycle, store_id)
values (2, now(), now(), '1','안녕하세요','옷 1', 'MONTH',1);

insert into service (service_id, created_date, last_modified_date, available_day, detail_contents, service_name,
                     service_cycle, store_id)
values (3, now(), now(), '수','안녕하세요','견과류 3', 'WEEK',1);

insert into service_image(service_image_id, extension_name, fake_name, image_seq, image_type, image_name, service_id)
values (1, '.png', '9feafe5f-75cb-4c7a-a049-7166a4420183', 1, 'THUMBNAIL', 'test이미지', 1);


insert into service_category (service_category_id, category_id, service_id)
values (1,1,1);

insert into service_category (service_category_id, category_id, service_id)
values (2,3,2);

insert into service_category (service_category_id, category_id, service_id)
values (3,1,3);

#
# insert into service_image (service_image_id, extension_name, fake_name, image_seq, image_type, image_name, service_id)
# values ();
insert into service_option (service_option_id, max_count, option_name, price, stock, service_id, created_date, last_modified_date)
values (1,2,'호두', 3000, 66, 1, now(), now());
insert into service_option (service_option_id, max_count, option_name, price, stock, service_id, created_date, last_modified_date)
values (2,1,'아몬드', 2000, 30, 1, now(), now());
insert into service_option (service_option_id, max_count, option_name, price, stock, service_id, created_date, last_modified_date)
values (3,1,'피스타치오', 1500, 30, 1, now(), now());
insert into service_option (service_option_id, max_count, option_name, price, stock, service_id, created_date, last_modified_date)
values (4,1,'캐슈넛', 800, 30, 1, now(), now());
insert into service_option (service_option_id, max_count, option_name, price, stock, service_id, created_date, last_modified_date)
values (5,1,'니트', 20000, 30, 2, now(), now());

insert into store_services_list (store_store_id, services_list_service_id)
values (1,1);
insert into store_services_list (store_store_id, services_list_service_id)
values (1,2);
insert into store_services_list (store_store_id, services_list_service_id)
values (1,3);

insert into subscribe(subscribe_id, status, cancel_date,  pay_scheduled_price, pay_scheduled_date, customer_id, service_id, created_date, last_modified_date, subscribe_start_date, shopping_date)
values (1, 'SUBSCRIBE', null,  23000, now(), 1,1, now()-interval 2 day, now()-interval 2 day, now()-interval 2 day, now()-interval 2 day );
insert into subscribe(subscribe_id, status, cancel_date, pay_scheduled_price, pay_scheduled_date, customer_id, service_id, created_date, last_modified_date, subscribe_start_date, shopping_date)
values (2, 'SUBSCRIBE', null, 19000, now(), 1,3, now(), now(), now(), now());
insert into subscribe(subscribe_id, status, cancel_date, pay_scheduled_price, pay_scheduled_date, customer_id, service_id, created_date, last_modified_date, subscribe_start_date, shopping_date)
values (3, 'SHOPPING', null, 0, now(), 1,2, now(), now(), now(), now());

insert into  picked_option(picked_option_id, option_id, quantity, subscribe_id, created_date, last_modified_date)
values (1, 1, 2, 1, now(), now());
insert into  picked_option(picked_option_id, option_id, quantity, subscribe_id, created_date, last_modified_date)
values (2, 2, 1, 1, now(), now());
insert into  picked_option(picked_option_id, option_id, quantity, subscribe_id, created_date, last_modified_date)
values (3, 3, 1, 2, now(), now());
insert into  picked_option(picked_option_id, option_id, quantity, subscribe_id, created_date, last_modified_date)
values (4, 4, 2, 2, now(), now());
insert into  picked_option(picked_option_id, option_id, quantity, subscribe_id, created_date, last_modified_date)
values (5, 5, 2, 3, now(), now());

insert into group_code(group_code_id, description, group_name)
values(1, '구독취소사유그룹', '구독취소사유');

insert into comm_code(comm_code_id, created_date, last_modified_date, code_name, description, group_code_id)
values(1, now(), now(), '단순변심','구독취소사유-단순변심', 1);
insert into comm_code(comm_code_id, created_date, last_modified_date, code_name, description, group_code_id)
values(2, now(), now(), '가격상승','구독취소사유-가격상승', 1);
insert into comm_code(comm_code_id, created_date, last_modified_date, code_name, description, group_code_id)
values(3, now(), now(), '제품불만족','구독취소사유-제품불만족', 1);
insert into comm_code(comm_code_id, created_date, last_modified_date, code_name, description, group_code_id)
values(4, now(), now(), '기타','구독취소사유-기타', 1);