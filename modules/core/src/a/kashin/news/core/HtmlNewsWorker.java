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
public class HtmlNewsWorker implements NewsWorker {
    @Inject
    private DataManager dataManager;
    @Inject
    private ItemWorker itemWorker;
    private Logger log = LoggerFactory.getLogger(HtmlNewsWorker.class);

    @Override
    public List<Item> getItems(Site site) {
        List<Item> list = new ArrayList<>();
        CommitContext commitContext = new CommitContext();
        int index = 0;

        try {
            Connection.Response connection = Jsoup.connect(site.getUrl()).execute();
            Document document = connection.parse();
            Elements elements = null;

            if (site.getItemClass() != null) {
                elements = document.body().getElementsByClass(site.getItemClass());
            } else if (site.getItemTag() != null){
                elements = document.body().getElementsByTag(site.getItemTag());
            }

            if (elements != null) {
                for (Element element : elements) {
                    Item item = itemWorker.craeteItemByElement(element, site);
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
}
