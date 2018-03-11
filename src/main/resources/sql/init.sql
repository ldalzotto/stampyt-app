drop table car if exists
drop table garage if exists
create table car (id BINARY(16) not null, brand varchar(255), color varchar(255), commisioning_date timestamp, model varchar(255), price float, registration_number varchar(255), garage_id BINARY(16) not null, primary key (id))
create table garage (id BINARY(16) not null, address varchar(255), car_storage_limit integer, creation_date timestamp, name varchar(255), primary key (id))
alter table car add constraint FK1u9tthhrqs56l6cxxcyhulcfy foreign key (garage_id) references garage