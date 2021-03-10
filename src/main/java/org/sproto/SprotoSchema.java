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

    public List<SprotoProtocol> getProtocols() {

        return protocols;
    }

    public void addProtocol(SprotoProtocol protocol) {

        this.protocols.add(protocol);
    }


}
