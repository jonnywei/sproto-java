package org.sproto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SprotoField {

    private String name;

    private int number;

    private SprotoType type;

    private boolean array;

    private boolean map;

    private String mapKey;

    private Integer mapKeyIndex;

    private SprotoStruct sprotoStruct;

    private String structName;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getNumber() {

        return number;
    }

    public void setNumber(int number) {

        this.number = number;
    }

    public SprotoType getType() {

        return type;
    }

    public void setType(SprotoType type) {

        this.type = type;
    }

    public SprotoField(String name, int number, SprotoType type) {

        this.name = name;
        this.number = number;
        this.type = type;
    }

    public SprotoField(String name, int number, SprotoType type, boolean array) {

        this.name = name;
        this.number = number;
        this.type = type;
        this.array = array;
    }

    public SprotoField(String name, int number, SprotoStruct sprotoStruct, boolean array) {

        this.name = name;
        this.number = number;
        this.type = SprotoType.STRUCT;
        this.sprotoStruct = sprotoStruct;
        this.array = array;
    }

    public SprotoStruct getSprotoStruct() {

        return sprotoStruct;
    }

    public void setSprotoStruct(SprotoStruct sprotoStruct) {

        this.sprotoStruct = sprotoStruct;
    }

    public String getStructName() {

        return structName;
    }

    public void setStructName(String structName) {

        this.structName = structName;
    }

    public boolean isArray() {

        return array;
    }

    public void setArray(boolean array) {

        this.array = array;
    }

    public boolean isMap() {

        return map;
    }

    public void setMap(boolean map) {

        this.map = map;
    }

    public String getMapKey() {

        return mapKey;
    }

    public void setMapKey(String mapKey) {

        this.mapKey = mapKey;
    }

    public Integer getMapKeyIndex() {

        return mapKeyIndex;
    }

    public void setMapKeyIndex(Integer mapKeyIndex) {

        this.mapKeyIndex = mapKeyIndex;
    }

    @Override public String toString() {

        return "SprotoField{" + "name='" + name + '\'' + ", number=" + number + ", type=" + type + ", array=" + array
                + ", map=" + map + ", mapKey='" + mapKey + '\'' + ", mapKeyIndex=" + mapKeyIndex + ", sprotoStruct="
                + sprotoStruct + ", structName='" + structName + '\'' + '}';
    }


    public Map<String,Object> toMap( Map<String, Integer> typePositionMap){
        Map<String,Object> map = new HashMap<>();
        map.put("name",this.name);
        map.put("tag",this.number);
        if(this.isArray()){
            map.put("array",true);
            if(this.mapKeyIndex != null){
                map.put("key", this.mapKeyIndex);
            }
        }
        if(this.type != SprotoType.STRUCT){
            map.put("buildin",SprotoType.buildin(this.type));
        }else{
            map.put("type",typePositionMap.get(this.sprotoStruct.getName()));
        }
        return map;
    }
}
