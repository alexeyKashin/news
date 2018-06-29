package a.kashin.news.core;

import a.kashin.news.entity.Item;
import a.kashin.news.entity.Site;

import java.io.IOException;
import java.util.List;

public interface NewsWorker {
    List<Item> getItems(Site site) throws IOException;
}
