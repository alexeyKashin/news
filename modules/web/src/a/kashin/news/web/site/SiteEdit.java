package a.kashin.news.web.site;

import a.kashin.news.entity.Item;
import a.kashin.news.service.NewsService;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.gui.components.AbstractEditor;
import a.kashin.news.entity.Site;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SiteEdit extends AbstractEditor<Site> {
    @Inject
    private NewsService newsService;
    public void onStart() {
        newsService.getItems(getItem());
    }
}