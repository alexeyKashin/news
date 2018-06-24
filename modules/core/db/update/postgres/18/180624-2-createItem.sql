alter table NEWS_ITEM add constraint FK_NEWS_ITEM_ON_SITE foreign key (SITE_ID) references NEWS_SITE(ID);
create index IDX_NEWS_ITEM_ON_SITE on NEWS_ITEM (SITE_ID);
