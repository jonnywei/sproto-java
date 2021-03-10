package org.sproto;

public class SprotoProtocol {

    private String name;

    private Integer tag;

    private String requestName;

    private SprotoStruct request;

    private String responseName;

    private SprotoStruct response;

    private boolean confirm;

    public SprotoProtocol(String name, Integer tag) {

        this.name = name;
        this.tag = tag;
    }

    public SprotoProtocol(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Integer getTag() {

        return tag;
    }

    public void setTag(Integer tag) {

        this.tag = tag;
    }

    public SprotoStruct getRequest() {

        return request;
    }

    public void setRequest(SprotoStruct request) {

        this.request = request;
    }

    public SprotoStruct getResponse() {

        return response;
    }

    public void setResponse(SprotoStruct response) {

        this.response = response;
    }

    public boolean isConfirm() {

        return confirm;
    }

    public void setConfirm(boolean confirm) {

        this.confirm = confirm;
    }

    public String getRequestName() {

        return requestName;
    }

    public void setRequestName(String requestName) {

        this.requestName = requestName;
    }

    public String getResponseName() {

        return responseName;
    }

    public void setResponseName(String responseName) {

        this.responseName = responseName;
    }

    @Override public String toString() {

        return "SprotoProtocol{" + "name='" + name + '\'' + ", tag=" + tag + ", requestName='" + requestName + '\''
                + ", request=" + request + ", responseName='" + responseName + '\'' + ", response=" + response
                + ", confirm=" + confirm + '}';
    }
}
