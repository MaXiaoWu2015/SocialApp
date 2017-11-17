package com.example.entity;

import java.lang.annotation.Annotation;

import javax.lang.model.type.TypeMirror;

/**
 * Created by matingting on 2017/11/12.
 */

public class FieldInJectedEntity {

    private String name;

    private String paramKey;

    private Class<? extends Annotation> annotationClass;

    private TypeMirror typeMirror;

    public FieldInJectedEntity(String name, String paramKey,
            Class<? extends Annotation> annotationClass, TypeMirror typeMirror) {
        this.name = name;
        this.paramKey = paramKey;
        this.annotationClass = annotationClass;
        this.typeMirror = typeMirror;
    }

    public String getName() {
        return name;
    }

    public String getParamKey() {
        return paramKey;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }
}
