package a.kashin.news.service;

import a.kashin.news.entity.Site;

public interface NewsService {
    String NAME = "news_NewsService";
    Integer parseSite(Site site);
}