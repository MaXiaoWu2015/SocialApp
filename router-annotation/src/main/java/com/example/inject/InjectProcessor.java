package com.example.inject;


import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by maxiaowu on 2017/10/29.
 */
@AutoService(Processor.class)
public class InjectProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotationElement : annotations){

            //getElementsAnnotatedWith的返回值是Set<? extends Element>,如果elements定义成Set<Element>,会提示类型转换异常的错误
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotationElement);
            if (annotationElement.getSimpleName().equals(Inject.class.getSimpleName())){

                for (Element element:elements){

                    parseInjectParam(element);


                }


            }

        }


        return false;
    }

    private void parseInjectParam(Element element) {

        //1.验证被注解的元素是否合法
        boolean isValid = isInaccessiableViaGenerateCode(element) || isInJectInWrongPackage(element);


    }

    private boolean isInJectInWrongPackage(Element element) {

        return false;

    }

    private boolean isInaccessiableViaGenerateCode(Element element) {

        //被注解的元素以及该元素所在的类都必须是public的

        TypeElement typeElement = (TypeElement) element.getEnclosingElement();


        if (typeElement.getKind() != ElementKind.CLASS){
            return true;
        }

        //TODO:为什么STATIC的不可以
        if (element.getModifiers().contains(Modifier.PRIVATE ) || element.getModifiers().contains(Modifier.STATIC)){
            return true;
        }


        if(element.getModifiers().contains(Modifier.PRIVATE ) || element.getModifiers().contains(Modifier.STATIC)){
            return true;
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
