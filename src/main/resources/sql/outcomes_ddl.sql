create table if not exists outcomes (
id bigint primary key auto_increment,
amount bigint not null,
add_date date not null,
`comment` varchar(70) not null,
category_id bigint not null,
constraint outcomes_to_categories_fk foreign key (category_id) references categories(id)
);