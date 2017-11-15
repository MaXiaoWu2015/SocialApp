package com.example.inject;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by maxiaowu on 2017/10/29.
 */
@AutoService(Processor.class)
public class InjectProcessor extends AbstractProcessor {

    private TypeTools mTypeTools;
    private Types types;
    private Elements elements;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        filer = processingEnv.getFiler();

         types = processingEnv.getTypeUtils();

         elements = processingEnv.getElementUtils();

        mTypeTools = new TypeTools(types,elements);

        messager = processingEnv.getMessager();

        messager.printMessage(Diagnostic.Kind.NOTE,"processor init ");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE,"processor process ");
        Map<TypeElement,TargetClass> targetClassMap = new LinkedHashMap<>();

        for (TypeElement annotationElement : annotations){

            //TODO:getElementsAnnotatedWith的返回值是Set<? extends Element>,如果elements定义成Set<Element>,会提示类型转换异常的错误
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotationElement);

//            if (annotationElement.getSimpleName().equals(Inject.class.getSimpleName())){

                for (Element element:elements){
                    //1.检验被注解的元素的合法性
                    boolean isInValid = isInValidAnnotation(element);
                    messager.printMessage(Diagnostic.Kind.NOTE,"processor : "+isInValid);

                    if (!isInValid){
                         getAndParseTargetMap(targetClassMap,element);
                    }
                }


            }

//        }

        try {
            for (Map.Entry<TypeElement,TargetClass> entry: targetClassMap.entrySet()){
                JavaFile javaFile = entry.getValue().brewJava();
                javaFile.writeTo(filer);

            }
        }catch (IOException e){

        }


        return false;
    }

    private void getAndParseTargetMap(Map<TypeElement, TargetClass> targetClassMap,
            Element element) {

        TypeElement enclosingElemnt = (TypeElement) element.getEnclosingElement();

        TypeName targetTypeName = TypeName.get(enclosingElemnt.asType());

        String packageName = getPackageName(enclosingElemnt);

        String className = getClassName(enclosingElemnt,packageName);
        ClassName bindClassName = ClassName.get(packageName,className+"_RouterInjecting");
        TargetClass targetClass = new TargetClass(mTypeTools,targetTypeName,bindClassName);

        String paramKey = element.getAnnotation(Inject.class).value();

        FieldInJectedEntity entity = new FieldInJectedEntity(element.getSimpleName().toString()
                ,paramKey,Inject.class,element.asType());

        targetClass.addFiledInjected(entity);

        targetClassMap.put(enclosingElemnt,targetClass);

    }



    private String getClassName(TypeElement enclosingElemnt, String packageName) {
        int len = packageName.length()+1;

        return enclosingElemnt.getQualifiedName().toString().substring(len).replace(".","$");

    }

    private String getPackageName(TypeElement enclosingElemnt) {
        return elements.getPackageOf(enclosingElemnt).getQualifiedName().toString();
    }

    private boolean isInValidAnnotation(Element element) {
        //1.被public修饰的元素，才能调用到(为什么不能是static修饰的)
        if (element.getModifiers().contains(Modifier.PRIVATE) || element.getModifiers().contains(Modifier.STATIC)){
            return true;
        }

        //2.必须是成员变量
        if (element.getKind() != ElementKind.FIELD){
            return true;
        }

        if(element.getEnclosingElement().getKind() != ElementKind.CLASS){
            return true;
        }

        if (element.getEnclosingElement().getModifiers().contains(Modifier.PRIVATE)){
            return true;
        }

        return false;
    }



    private static final List<Class<? extends Annotation>> ANNOTATIONS = Arrays.asList(
            Inject.class,InjectParam.class
    );

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Inject.class.getCanonicalName());


//        Set<String> types = new LinkedHashSet<>();



        return set;
    }
    private void error(Element element, String message, Object... args) {
        printMessage(Diagnostic.Kind.ERROR, element, message, args);
    }

    private void printMessage(Diagnostic.Kind kind, Element element, String message, Object[] args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(kind, message, element);
    }
}
