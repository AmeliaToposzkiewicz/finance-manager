create table if not exists incomes (
id bigint primary key auto_increment,
amount bigint not null,
add_date date not null,
`comment` varchar(70) not null
);