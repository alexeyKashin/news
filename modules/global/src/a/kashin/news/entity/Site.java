package a.kashin.news.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "NEWS_SITE")
@Entity(name = "news$Site")
public class Site extends StandardEntity {
    private static final long serialVersionUID = 4993102540774541504L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "DATE_PATTERN")
    protected String datePattern;

    @Column(name = "URL")
    protected String url;

    @Column(name = "CONTENT_TYPE")
    protected Integer contentType;

    @Column(name = "ITEM_CLASS")
    protected String itemClass;

    @Column(name = "ITEM_TAG")
    protected String itemTag;

    @Column(name = "TITLE_CLASS")
    protected String titleClass;

    @Column(name = "TITLE_TAG")
    protected String titleTag;

    @Column(name = "DESCRIPTION_CLASS")
    protected String descriptionClass;

    @Column(name = "DESCRIPTION_TAG")
    protected String descriptionTag;

    @Column(name = "PUBLISHED_DATE_CLASS")
    protected String publishedDateClass;

    @Column(name = "PUBLISHED_DATE_TAG")
    protected String publishedDateTag;

    @Column(name = "LINK_CLASS")
    protected String linkClass;

    @Column(name = "LINK_TAG")
    protected String linkTag;

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public String getDatePattern() {
        return datePattern;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType == null ? null : contentType.getId();
    }

    public ContentType getContentType() {
        return contentType == null ? null : ContentType.fromId(contentType);
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    public String getItemClass() {
        return itemClass;
    }

    public void setItemTag(String itemTag) {
        this.itemTag = itemTag;
    }

    public String getItemTag() {
        return itemTag;
    }

    public void setTitleClass(String titleClass) {
        this.titleClass = titleClass;
    }

    public String getTitleClass() {
        return titleClass;
    }

    public void setTitleTag(String titleTag) {
        this.titleTag = titleTag;
    }

    public String getTitleTag() {
        return titleTag;
    }

    public void setDescriptionClass(String descriptionClass) {
        this.descriptionClass = descriptionClass;
    }

    public String getDescriptionClass() {
        return descriptionClass;
    }

    public void setDescriptionTag(String descriptionTag) {
        this.descriptionTag = descriptionTag;
    }

    public String getDescriptionTag() {
        return descriptionTag;
    }

    public void setPublishedDateClass(String publishedDateClass) {
        this.publishedDateClass = publishedDateClass;
    }

    public String getPublishedDateClass() {
        return publishedDateClass;
    }

    public void setPublishedDateTag(String publishedDateTag) {
        this.publishedDateTag = publishedDateTag;
    }

    public String getPublishedDateTag() {
        return publishedDateTag;
    }

    public void setLinkClass(String linkClass) {
        this.linkClass = linkClass;
    }

    public String getLinkClass() {
        return linkClass;
    }

    public void setLinkTag(String linkTag) {
        this.linkTag = linkTag;
    }

    public String getLinkTag() {
        return linkTag;
    }


}