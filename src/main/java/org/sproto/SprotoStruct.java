package org.sproto;

import java.util.ArrayList;
import java.util.List;

public class SprotoStruct {

    private String name;

    private List<SprotoField> fields = new ArrayList<>();

    public SprotoStruct(String name) {

        this.name = name;

    }

    public String getName() {

        return name;
    }

    public void addField(SprotoField field) {
        this.fields.add(field);
    }

    public List<SprotoField> getFields() {

        return fields;
    }

}
