package com.example.inject;


import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by maxiaowu on 2017/10/29.
 */
@AutoService(Processor.class)
public class InjectProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotationElement : annotations){

            //TODO:getElementsAnnotatedWith的返回值是Set<? extends Element>,如果elements定义成Set<Element>,会提示类型转换异常的错误
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotationElement);
            if (annotationElement.getSimpleName().equals(Inject.class.getSimpleName())){

                for (Element element:elements){

                }


            }

        }


        return false;
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> set = new HashSet<>();
        set.add(Inject.class.getSimpleName());

        return set;
    }
}
