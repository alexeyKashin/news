package a.kashin.news.core;

import a.kashin.news.entity.Item;
import a.kashin.news.entity.Site;
import com.haulmont.cuba.core.Persistence;
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
    @Inject
    private Persistence persistence;
    private Logger log = LoggerFactory.getLogger(ItemWorker.class);

    public Item craeteItemByElement(Element element, Site site) {
        try {

            String name = element.getElementsByClass(site.getTitleClass()).tagName(site.getTitleTag()).text();

            if (name.isEmpty()) return null;
            if (checkItemDouble(name, site)) return null;

            String description = element.getElementsByClass(site.getDescriptionClass()).text();
            String link = null;
            if (site.getLinkClass() != null) {
                link = element.getElementsByClass(site.getLinkClass()).tagName(site.getLinkTag()).text();
            }

            Item item = metadata.create(Item.class);
            item.setName(name);
            item.setDescription(description);
            item.setLink(link);
            item.setDate(parsePublishDate(element, site));


            return item;
        } catch (Exception exc){
            log.error(exc.getMessage());
        }
        return null;
    }

    private boolean checkItemDouble(String name, Site site) {
        Item item = persistence.callInTransaction(em ->
                (Item) em.createQuery("select e from news$Item e where e.name = :name and e.site.id = :siteId")
                .setParameter("name", name)
                .setParameter("siteId", site.getId())
                .getFirstResult());
        if (item != null) {
            log.debug("Обнаружена дублирующая запись [" + name + "]");
            return true;
        }
        return false;
    }

    private Date parsePublishDate(Element element, Site site) {
        if (site.getPublishedDateClass() != null) {
            try {
                return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
                        .parse(element.getElementsByClass(site.getPublishedDateClass()).
                                tagName(site.getPublishedDateTag()).text());

            } catch (ParseException e) {
                log.error(e.getMessage());
            }
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
            // TODO Отладка
            break;
        }
        dataManager.commit(commitContext);
        return items;
    }
}
