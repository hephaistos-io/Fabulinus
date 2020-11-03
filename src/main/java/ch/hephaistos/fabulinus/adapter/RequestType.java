package ch.hephaistos.fabulinus.adapter;

import ch.hephaistos.fabulinus.annotations.AnnotationStrategy;
import ch.hephaistos.fabulinus.annotations.get.GetAnnotationStrategy;
import ch.hephaistos.fabulinus.annotations.post.PostAnnotationStrategy;

public enum RequestType {

    GET("GET", new GetAnnotationStrategy()),

    POST("POST", new PostAnnotationStrategy());

    private String name;
    private AnnotationStrategy annotationStrategy;

    private RequestType(String name, AnnotationStrategy annotationStrategy){
        this.name = name;
        this.annotationStrategy = annotationStrategy;
    }

    public String getName(){
        return name;
    }

    public AnnotationStrategy getAnnotationStrategy(){
        return annotationStrategy;
    }
}
