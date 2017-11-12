package com.example.inject;

import java.lang.annotation.Annotation;

import javax.lang.model.type.TypeMirror;

/**
 * Created by matingting on 2017/11/12.
 */

class FieldInJectedEntity {

    private String name;

    private String paramKet;

    private Class<? extends Annotation> annotationClass;

    private TypeMirror typeMirror;

    public FieldInJectedEntity(String name, String paramKet,
            Class<? extends Annotation> annotationClass, TypeMirror typeMirror) {
        this.name = name;
        this.paramKet = paramKet;
        this.annotationClass = annotationClass;
        this.typeMirror = typeMirror;
    }

    public String getName() {
        return name;
    }

    public String getParamKet() {
        return paramKet;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }
}
