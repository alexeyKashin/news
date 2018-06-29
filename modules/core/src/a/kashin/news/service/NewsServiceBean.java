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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service(NewsService.NAME)
public class NewsServiceBean implements NewsService {
    @Inject
    private HtmlNewsWorker htmlNewsWorker;
    @Inject
    private XmlNewsWorker xmlNewsWorker;
    private Logger log = LoggerFactory.getLogger(NewsServiceBean.class);

    @Override
    public Integer parseSite(Site site) {
        List<Item> items = new ArrayList<>();
        try {
            if (site.getContentType() == ContentType.html)
                items = htmlNewsWorker.getItems(site);
            else
                items = xmlNewsWorker.getItems(site);
            log.debug("Создано [" + items.size() + "] новостей.");
        } catch (IOException e) {
            log.error("Ошибка при получении данных сайта " + site.getName() + ": " + e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
        return items.size();
    }
}