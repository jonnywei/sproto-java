package org.sproto;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SprotoSchema {

    private List<SprotoStruct> types = new ArrayList<>();


    private List<SprotoProtocol> protocols = new ArrayList<>();

    public List<SprotoStruct> getTypes() {

        return types;
    }

    public void addType(SprotoStruct type) {

        this.types.add(type);
    }




    public SprotoStruct getType(String typeName) {

        for(SprotoStruct type : this.types){
            if(type.getName().equals(typeName)){
                return type;
            }
        }
        return null;
    }


    public List<SprotoProtocol> getProtocols() {

        return protocols;
    }

    public void addProtocol(SprotoProtocol protocol) {

        this.protocols.add(protocol);
    }



    public SprotoProtocol getProtocol(String protocolName) {

        for(SprotoProtocol protocol : this.protocols){
            if(protocol.getName().equals(protocolName)){
                return protocol;
            }
        }
        return null;
    }



    public SprotoProtocol getProtocol(Integer tag) {

        for(SprotoProtocol protocol : this.protocols){
            if(protocol.getTag().equals(tag)){
                return protocol;
            }
        }
        return null;
    }

    public Map<String ,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        this.types.sort(Comparator.comparing(SprotoStruct::getName));
        AtomicInteger index = new AtomicInteger(0);

        Map<String, Integer> typePositionMap = new HashMap<>();
        for(SprotoStruct struct : this.types){
            typePositionMap.put(struct.getName(),index.getAndIncrement());
        }

        List<Map<String,Object>> typeMaps = new ArrayList<>();
        for(SprotoStruct type : this.types){
            typeMaps.add(type.toMap(typePositionMap));
        }
        map.put("type",typeMaps);
//        map.put("protocol",this.protocols);
        return map;
    }
}
