package org.sproto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SprotoStruct {

    private String name;

    private String dotName; //spb use

    private List<SprotoField> fields = new ArrayList<>();

    public SprotoStruct(String name) {

        this.name = name;
        this.dotName = name;
    }

    public SprotoStruct(String name,String dotName) {

        this.name = name;
        this.dotName = dotName;
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

    @Override public String toString() {

        return "SprotoStruct{" + "name='" + name + '\'' + ", fields=" + fields + '}';
    }


    public Map<String,Object> toMap( Map<String, Integer> typePositionMap){
        Map<String,Object> map = new HashMap<>();
        map.put("name",this.dotName);
        List<Map<String,Object>> fieldMaps = new ArrayList<>();
        for(SprotoField field : this.fields){
            fieldMaps.add(field.toMap( typePositionMap));
        }
        map.put("fields",fieldMaps);
        return map;
    }
}
