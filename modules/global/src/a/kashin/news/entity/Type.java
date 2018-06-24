package a.kashin.news.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Type implements EnumClass<Integer> {

    auto(10),
    user(20);

    private Integer id;

    Type(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static Type fromId(Integer id) {
        for (Type at : Type.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}