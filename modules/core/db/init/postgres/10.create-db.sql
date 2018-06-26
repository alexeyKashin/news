-- begin NEWS_SITE
create table NEWS_SITE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    DATE_PATTERN varchar(255),
    URL varchar(255),
    CONTENT_TYPE integer,
    ITEM_CLASS varchar(255),
    ITEM_TAG varchar(255),
    TITLE_CLASS varchar(255),
    TITLE_TAG varchar(255),
    DESCRIPTION_CLASS varchar(255),
    DESCRIPTION_TAG varchar(255),
    PUBLISHED_DATE_CLASS varchar(255),
    PUBLISHED_DATE_TAG varchar(255),
    LINK_CLASS varchar(255),
    LINK_TAG varchar(255),
    --
    primary key (ID)
)^
-- end NEWS_SITE
-- begin NEWS_ITEM
create table NEWS_ITEM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    DATE_ date,
    DESCRIPTION text,
    LINK varchar(255),
    SITE_ID uuid,
    --
    primary key (ID)
)^
-- end NEWS_ITEM
