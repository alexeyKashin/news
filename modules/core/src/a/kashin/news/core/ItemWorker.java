package a.kashin.news.core;

import a.kashin.news.entity.Item;
import a.kashin.news.entity.Site;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.Metadata;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

@Component
public class ItemWorker {
    @Inject
    private Metadata metadata;
    @Inject
    private Persistence persistence;
    private Logger log = LoggerFactory.getLogger(ItemWorker.class);

    public Item craeteItemByElement(Element element, Site site) {
        try {
            String name = getAttributeFromElement(site.getTitleClass(), site.getTitleTag(), element);

            if (name.isEmpty()) return null;
            if (checkItemDouble(name, site)) return null;

            String description = getAttributeFromElement(site.getDescriptionClass(), site.getDescriptionTag(), element);
            String link = getLink(element, site);

            Item item = metadata.create(Item.class);
            item.setName(name);
            item.setDescription(description);
            item.setLink(link);
            item.setDate(parsePublishDate(element, site));
            item.setSite(site);

            return item;
        } catch (Exception e){
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
       }
       return null;
 }


    private String getLink(Element element, Site site) {
        String link = getAttributeFromElement(site.getLinkClass(), site.getLinkTag(), element);
        if (link.startsWith("http")) return link;

        // Дополнительные способы получения гиперссылки - по аттрибуту элемента
        if (site.getLinkClass() != null) {
            String linkTag = site.getLinkTag();
            if (linkTag == null) linkTag = "href";
            link = element.getElementsByClass(site.getLinkClass()).attr("abs:" + linkTag);
        } else {
            link = element.select("a").first().attr("abs:href");
        }
        return link;
    }

    private String getAttributeFromElement(@Nullable String siteClass, @Nullable String siteTag, Element element) {
        if (siteClass != null && siteTag != null) {
            return element.getElementsByClass(siteClass).tagName(siteTag).text();
        } else if (siteClass != null) {
            return element.getElementsByClass(siteClass).text();
        } else if (siteTag != null) {
            return element.getElementsByTag(siteTag).text();
        }
        return "";
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
        String dateString = getAttributeFromElement(site.getPublishedDateClass(), site.getPublishedDateTag(), element);
        if (dateString.isEmpty()) return new Date();
        try {
            return new SimpleDateFormat(site.getDatePattern(), Locale.ENGLISH).parse(dateString);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return new Date();
    }
}
