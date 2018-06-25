package a.kashin.news.service;

import a.kashin.news.core.HtmlNewsWorker;
import a.kashin.news.core.XmlNewsWorker;
import a.kashin.news.entity.ContentType;
import a.kashin.news.entity.Item;
import a.kashin.news.entity.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service(NewsService.NAME)
public class NewsServiceBean implements NewsService {
    @Inject
    private HtmlNewsWorker htmlNewsWorker;
    @Inject
    private XmlNewsWorker xmlNewsWorker;
    private Logger logger = LoggerFactory.getLogger(NewsServiceBean.class);

    @Override
    public List<Item> getItems(Site site) {
        List<Item> items;
        if (site.getContentType() == ContentType.html)
            items = htmlNewsWorker.getItems(site);
        else
            items = xmlNewsWorker.getItems(site);

        logger.debug("Создано [" + items.size() + "] новостей.");
        return items;
    }
}