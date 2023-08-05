drop table if exists item;
create table item(
                     id bigint auto_increment,
                     item_name varchar(10),
                     price INTEGER,
                     quantity INTEGER,
                     primary key (id)
);