package a.kashin.news.core;

import a.kashin.news.entity.Item;
import a.kashin.news.entity.Site;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class ItemWorker {
    @Inject
    private Metadata metadata;
    @Inject
    private DataManager dataManager;
    private Logger log = LoggerFactory.getLogger(ItemWorker.class);

    public Item craeteItemByElement(Element element, Site site) {
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

    public List<Item> createItemsByRootElement(Element element, List<Item> items, Site site) {
        CommitContext commitContext = new CommitContext();
        Elements itemElements = element.getElementsByTag(site.getItemTag());
        for (Element itemElement : itemElements) {
            Item item = craeteItemByElement(itemElement, site);
            if (item != null) {
                items.add(item);
                commitContext.addInstanceToCommit(item);
            }
        }
        dataManager.commit(commitContext);
        return items;
    }
}
