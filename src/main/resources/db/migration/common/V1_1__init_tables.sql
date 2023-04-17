create table "city"
(
    "id"           uuid                              not null,
    "created_date" timestamp(6) default current_timestamp not null,
    "deleted_date" timestamp(6),
    "status"       varchar(255)                           ,
    "updated_date" timestamp(6) default current_timestamp not null,
    "version"      bigint       default 0,
    "code"         varchar(255),
    "name"         varchar(255),
    "country_id"   uuid                                 not null,
    primary key ("id")
);

create table "country"
(
    "id"           uuid                              not null,
    "created_date" timestamp(6) default current_timestamp not null,
    "deleted_date" timestamp(6),
    "status"       varchar(255)                           ,
    "updated_date" timestamp(6) default current_timestamp not null,
    "version"      bigint       default 0,
    "code"         varchar(255),
    "name"         varchar(255)                           not null,
    "phone_code"   varchar(255)                           not null,
    primary key ("id")
);

alter table if exists "city" add constraint "city_country_fk" foreign key ("country_id") references "country";