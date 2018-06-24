package a.kashin.news.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ContentType implements EnumClass<Integer> {

    html(10),
    xml(20);

    private Integer id;

    ContentType(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static ContentType fromId(Integer id) {
        for (ContentType at : ContentType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}