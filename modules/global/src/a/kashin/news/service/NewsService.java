package a.kashin.news.service;


import a.kashin.news.entity.Item;
import a.kashin.news.entity.Site;

import java.util.List;

public interface NewsService {
    String NAME = "news_NewsService";
    void parseSite(Site site);
}