package a.kashin.news.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamePattern("%s|name")
@Table(name = "NEWS_ITEM")
@Entity(name = "news$Item")
public class Item extends StandardEntity {
    private static final long serialVersionUID = -6133667585391315778L;

    @Column(name = "NAME", length = 2000)
    protected String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    protected Date date;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "LINK", length = 2000)
    protected String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    protected Site site;

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }


}