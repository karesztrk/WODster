# --- !Ups

create table box (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  photo                     varchar(255),
  phone                     varchar(255),
  website                   varchar(255),
  email                     varchar(255),
  zip                       varchar(255),
  country                   varchar(255),
  city                      varchar(255),
  street                    varchar(255),
  longitude                 float8,
  latitude                  float8,
  constraint pk_box primary key (id))
;

create table "user" (
    email varchar(255) not null,
    name varchar(255) not null,
    password varchar(255) not null,
    constraint pk_user primary key (email)
);

create table administrator (
    email varchar(255) not null,
    constraint pk_administrator primary key (email)
);

create sequence box_seq;

create sequence user_seq;

create sequence workout_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists box;

drop table if exists user;

drop table if exists workout;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists box_seq;

drop sequence if exists user_seq;

drop sequence if exists workout_seq;

