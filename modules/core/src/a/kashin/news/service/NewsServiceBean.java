package a.kashin.news.service;

import a.kashin.news.entity.Item;
import a.kashin.news.entity.Site;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(NewsService.NAME)
public class NewsServiceBean implements NewsService {

    @Override
    public List<Item> getItems(Site site) {
        return null;
    }
}