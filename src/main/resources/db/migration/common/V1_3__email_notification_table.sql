create table "email_notification"
(
    "id"           uuid                                   not null,
    "created_date" timestamp(6) default current_timestamp not null,
    "deleted_date" timestamp(6),
    "status"       varchar(255),
    "updated_date" timestamp(6) default current_timestamp not null,
    "version"      bigint       default 0,
    "to"           varchar(500),
    "template"     varchar(255),
    "subject"      varchar(255),
    "body"         varchar(5000),
    "result"       varchar(5000),
    primary key ("id")
);

