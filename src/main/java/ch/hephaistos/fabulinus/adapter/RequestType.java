package ch.hephaistos.fabulinus.adapter;

public enum RequestType {

    GET("GET"),

    POST("POST");

    private String name;

    private RequestType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
