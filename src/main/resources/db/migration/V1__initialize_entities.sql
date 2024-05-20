create table osm_content.users
(
    id             bigserial           not null
        constraint users_seq primary key,
    uid            varchar(255) unique not null,
    created_at     timestamp           not null,
    last_update_at timestamp           not null,
    username       varchar(50) unique  not null
);

create table osm_content.media_frames
(
    id             bigserial           not null
        constraint followership_seq primary key,
    uid            varchar(255) unique not null,
    created_at     timestamp           not null,
    last_update_at timestamp           not null,
    user_id        bigint              not null,
    size           bigint              not null,
    name           varchar(255)        not null,
    bucket         varchar(255)        not null,
    constraint media_frame_user_fk foreign key (user_id) references osm_content.users
);