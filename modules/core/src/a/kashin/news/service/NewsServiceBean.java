package a.kashin.news.service;

import a.kashin.news.entity.Item;
import a.kashin.news.entity.Site;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service(NewsService.NAME)
public class NewsServiceBean implements NewsService {
    @Inject
    private Metadata metadata;
    @Inject
    private DataManager dataManager;
    private Logger log = LoggerFactory.getLogger(NewsServiceBean.class);

    @Override
    public List<Item> getItems(Site site) {
        List<Item> list = new ArrayList<>();
        CommitContext commitContext = new CommitContext();
        int index = 0;

        try {
            Connection.Response connection = Jsoup.connect(site.getUrl()).execute();
            Document document = connection.parse();
            Elements elements = null;
            
            if (site.getFeedClass() != null) {
                elements = document.body().getElementsByClass(site.getFeedClass());
            } else if (site.getFeedTag() != null){
                elements = document.body().getElementsByTag(site.getFeedTag());
            } else if (site.getItemClass() != null) {
                elements = document.body().getElementsByClass(site.getItemClass());
            } else if (site.getItemTag() != null){
                elements = document.body().getElementsByTag(site.getItemTag());
            }
            
            if (elements != null) {
                for (Element element : elements) {
                    Item item = craeteItemByElement(element, site);
                    if (item != null) {
                        list.add(item);
                        commitContext.addInstanceToCommit(item);
                    }
                    index++;
                    if (index > 0) break;
                }
            }
        } catch (IOException e) {
            log.error("Ошибка при получении данных сайта " + site.getName() + ": " + e.getMessage());;
        }
        dataManager.commit(commitContext);
        return list;
    }

    private Item craeteItemByElement(Element element, Site site) {
        try {
            String name = element.getElementsByClass(site.getTitleClass()).tagName(site.getTitleTag()).text();
            String description = element.getElementsByClass(site.getDescriptionClass()).text();
            if (name.equals("") || description.equals(""))
                return null;
            Item item = metadata.create(Item.class);
            item.setName(name);
            item.setDescription(description);
            if (!site.getLinkClass().equals("")) {
                item.setLink(element.getElementsByClass(site.getLinkClass()).tagName(site.getLinkTag()).text());
            }
            if (!site.getPublishedDateClass().equals("")) {
                try {
                    Date pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
                            .parse(element.getElementsByClass(site.getPublishedDateClass()).
                                    tagName(site.getPublishedDateTag()).text());
                    item.setDate(pubDate);
                } catch (ParseException pe) {
                    log.error(pe.getMessage());
                    item.setDate(new Date());
                }
            } else {
                item.setDate(new Date());
            }
            return item;
        } catch (Exception exc){
            log.error(exc.getMessage());
        }
        return null;
    }
}