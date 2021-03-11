package org.sproto;

import java.util.HashMap;
import java.util.Map;

/**
 * Sproto Rpc 实现
 */
public class SprotoRpc {

    private SprotoSchema schema;

    private SprotoStruct defaultPackage = buildDefaultPackageSchema();

    private Map<Long,SprotoProtocol> sessionMap = new HashMap<>();
    private static SprotoStruct buildDefaultPackageSchema() {
        SprotoStruct packag = new SprotoStruct("package");
        packag.addField(new SprotoField("type",0, SprotoType.INTEGER));
        packag.addField(new SprotoField("session",1, SprotoType.INTEGER));
        return packag;
    }

    public SprotoRpc(String packageName,SprotoSchema sprotoSchema){
        this.defaultPackage = sprotoSchema.getType(packageName);
        this.schema = sprotoSchema;
    }

    public  SprotoRpc(SprotoSchema sprotoSchema){

        this.schema = sprotoSchema;
        this.schema.addType(defaultPackage);
    }


    public   byte[] request(String protocolName, Object reqObj,Long sessionId){

        SprotoProtocol protocol = this.schema.getProtocol(protocolName);
        Map<String,Object> packageObj = new HashMap<>();
        packageObj.put("type",protocol.getTag());
        packageObj.put("session",sessionId
        );
        this.setSprotoProtocolBySessionId(sessionId, protocol);

        byte[] header = SprotoEncoder.encodeStruct(defaultPackage, packageObj);

        byte[] request =SprotoEncoder.encodeStruct(protocol.getRequest(), reqObj);

        byte[] newArray = new byte[header.length + request.length];

        System.arraycopy(header,0, newArray,0, header.length);
        System.arraycopy(request,0, newArray,header.length, request.length);
        return newArray;

    }

    public RpcMessage dispatch(byte[] data){

        RpcMessage message = new RpcMessage();
        SprotoByteBuffer sprotoByteBuffer = SprotoByteBuffer.wrap(data);

        Map<String,Object> packageObj = ( Map<String,Object>) SprotoDecoder.decodeStruct(defaultPackage,sprotoByteBuffer);
        Long sessionId = (Long) packageObj.get("session");
        message.sessionId = sessionId;

        if(packageObj.containsKey("type")){
            Integer protoTag = (Integer) packageObj.get("type");
            SprotoProtocol protocol = this.schema.getProtocol(protoTag);
            Map<String,Object> requestObj = ( Map<String,Object>) SprotoDecoder.decodeStruct(protocol.getRequest(),sprotoByteBuffer);
            message.request = requestObj;
            message.isRequest = true;
            message.protocol = protocol;
            message.protocolName = protocol.getName();
        }else {
            message.isRequest = false;
            SprotoProtocol protocol = this.getSprotoProtocolBySessionId(sessionId);
            Map<String,Object> responseObj = ( Map<String,Object>) SprotoDecoder.decodeStruct(protocol.getResponse(),sprotoByteBuffer);
            message.response = responseObj;
        }
        return message;
    }


    public  class RpcMessage{

        private boolean isRequest;

        private String protocolName;

        private Object request;

        private Object response;

        private Long sessionId;

        private  SprotoProtocol protocol;

        public byte[] geneResponse(Object responseObj){
            this.response = responseObj;
            Map<String,Object> packageObj = new HashMap<>();
            packageObj.put("type",protocol.getTag());
            packageObj.put("session",sessionId);
            byte[] header = SprotoEncoder.encodeStruct(defaultPackage, packageObj);
            byte[] request =SprotoEncoder.encodeStruct(protocol.getRequest(), responseObj);
            byte[] newArray = new byte[header.length + request.length];
            System.arraycopy(header,0, newArray,0, header.length);
            System.arraycopy(request,0, newArray,header.length, request.length);
            return newArray;

        }

        @Override public String toString() {

            return "RpcMessage{" + "isRequest=" + isRequest + ", protocolName='" + protocolName + '\'' + ", request="
                    + request + ", response=" + response + ", sessionId=" + sessionId + ", protocol=" + protocol + '}';
        }
    }




    public void setSprotoProtocolBySessionId(Long sessionId,SprotoProtocol protocol){

        sessionMap.put(sessionId, protocol);

    }

    public SprotoProtocol getSprotoProtocolBySessionId(Long sessionId){

        return sessionMap.get(sessionId);
    }

}
