package org.sproto;

import java.util.ArrayList;
import java.util.List;

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
}
