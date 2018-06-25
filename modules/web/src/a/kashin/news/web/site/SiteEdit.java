package a.kashin.news.web.site;

import a.kashin.news.entity.Site;
import a.kashin.news.service.NewsService;
import com.haulmont.cuba.gui.components.AbstractEditor;

import javax.inject.Inject;

public class SiteEdit extends AbstractEditor<Site> {
    @Inject
    private NewsService newsService;
    public void onStart() {
        newsService.getItems(getItem());
    }
}