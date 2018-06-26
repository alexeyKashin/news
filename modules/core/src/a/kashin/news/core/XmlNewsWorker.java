package a.kashin.news.core;

import a.kashin.news.entity.Item;
import a.kashin.news.entity.Site;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class XmlNewsWorker implements NewsWorker {
    @Inject
    private DataManager dataManager;
    @Inject
    private ItemWorker itemWorker;
    private Logger log = LoggerFactory.getLogger(XmlNewsWorker.class);

    @Override
    public List<Item> getItems(Site site) {
        List<Item> items = new ArrayList<>();
        CommitContext commitContext = new CommitContext();
        if (site.getItemTag() == null) {
            log.error("Не заполнено поле \"itemTag\" для получения новостей " + site.getName());
            return null;
        }

        try {
            Connection.Response connection = Jsoup.connect(site.getUrl()).execute();
            Document document = connection.parse();
            Elements elements = document.getElementsByTag(site.getItemTag());

            if (elements != null) {
                for (Element element : elements) {
                    Item item = itemWorker.craeteItemByElement(element, site);
                    if (item != null) {
                        items.add(item);
                        commitContext.addInstanceToCommit(item);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Ошибка при получении данных сайта " + site.getName() + ": " + e.getMessage());;
        }
        dataManager.commit(commitContext);
        return items;
    }
}
