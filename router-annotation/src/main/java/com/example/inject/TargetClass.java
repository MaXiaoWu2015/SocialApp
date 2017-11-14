package com.example.inject;

import static javax.lang.model.element.Modifier.PUBLIC;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;


/**
 * Created by matingting on 2017/11/12.
 */

class TargetClass {

    private static final ClassName PARAMETERS_INJECTOR = ClassName.get("","");

    private ArrayList<FieldInJectedEntity> fieldInJectedList = new ArrayList<>();

    private TypeTools mTypeTools;
    
    private TypeName targetTypeName;
    
    private ClassName inJectingClassName;

    public TargetClass(TypeTools typeTools, TypeName targetTypeName,
            ClassName inJectingClassName) {
        mTypeTools = typeTools;
        this.targetTypeName = targetTypeName;
        this.inJectingClassName = inJectingClassName;
    }

    public void addFiledInjected(FieldInJectedEntity entity){
        fieldInJectedList.add(entity);
    }

    public JavaFile brewJava(){
      //为了创建构造方法
        TypeName targetClassName = TypeVariableName.get("T");

        TypeSpec.Builder builder = TypeSpec.classBuilder(inJectingClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(createMethodCode(targetTypeName))
                .addTypeVariable(TypeVariableName.get("T",targetClassName))
                .addSuperinterface(PARAMETERS_INJECTOR);
        return JavaFile.builder(inJectingClassName.packageName(),builder.build())
                .addFileComment("Generated")
                .build();
    }

    private MethodSpec createMethodCode(TypeName typeName){
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(PUBLIC)
                .addParameter(typeName,"target");

        for (FieldInJectedEntity entity:fieldInJectedList){
            CodeBlock codeBlock = CodeBlock.builder()
                    .add("target.$L = ",entity.getName())
                    .add("target.getIntent().")
                    .add(getTypeStatement(mTypeTools.convertType(entity.getTypeMirror()),
                            true))
                    .build();
            builder.addCode(codeBlock);
        }
        return builder.build();
    }

    private String getTypeStatement(int type, boolean isActivity) {
        String statement = "";
        switch (type) {
            case Constants.TYPE_KIND.TYPE_INTEGER:
                statement += (isActivity ? ("getIntExtra($S, 0)") : ("getInt($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_LONG:
                statement += (isActivity ? ("getLongExtra($S, 0)") : ("getLong($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_FLOAT:
                statement += (isActivity ? ("getFloatExtra($S, 0)") : ("getFloat($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_DOUBLE:
                statement += (isActivity ? ("getDoubleExtra($S, 0)") : ("getDouble($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_SHORT:
                statement += (isActivity ? ("getShortExtra($S, (short) 0)") : ("getShort($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_BYTE:
                statement += (isActivity ? ("getByteExtra($S, (byte) 0)") : ("getByte($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_BOOLEAN:
                statement += (isActivity ? ("getBooleanExtra($S, false)") : ("getBoolean($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_STRING:
                statement += (isActivity ? ("getStringExtra($S)") : ("getString($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_CHAR_SEQUENCE:
                statement += (isActivity ? ("getCharSequenceExtra($S)") : ("getCharSequence($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_PARCELABLE:
                statement += (isActivity ? ("getParcelableExtra($S)") : ("getParcelable($S)"));
                break;

            case Constants.TYPE_KIND.TYPE_ARRAY_INT:
                statement += (isActivity ? ("getIntArrayExtra($S)") : ("getIntArray($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_LONG:
                statement += (isActivity ? ("getLongArrayExtra($S)") : ("getLongArray($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_FLOAT:
                statement += (isActivity ? ("getFloatArrayExtra($S)") : ("getFloatArray($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_DOUBLE:
                statement += (isActivity ? ("getDoubleArrayExtra($S)") : ("getDoubleArray($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_SHORT:
                statement += (isActivity ? ("getShortArrayExtra($S)") : ("getShortArray($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_BYTE:
                statement += (isActivity ? ("getByteArrayExtra($S)") : ("getByteArray($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_BOOLEAN:
                statement += (isActivity ? ("getBooleanArrayExtra($S)") : ("getBooleanArray($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_STRING:
                statement += (isActivity ? ("getStringArrayExtra($S)") : ("getStringArray($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_CHAR_SEQUENCE:
                statement += (isActivity ? ("getCharSequenceArrayExtra($S)") : ("getCharSequenceArray($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_PARCELABLE:
                statement += (isActivity ? ("getParcelableArrayExtra($S)") : ("getParcelableArray($S)"));
                break;

            case Constants.TYPE_KIND.TYPE_ARRAY_LIST_INTEGER:
                statement += (isActivity ? ("getIntegerArrayListExtra($S)") : ("getIntArrayList($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_LIST_LONG:
                statement += (isActivity ? ("getLongArrayListExtra($S)") : ("getLongArrayList($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_LIST_FLOAT:
                statement += (isActivity ? ("getFloatArrayListExtra($S)") : ("getFloatArrayList($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_LIST_DOUBLE:
                statement += (isActivity ? ("getDoubleArrayListExtra($S)") : ("getDoubleArrayList($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_LIST_SHORT:
                statement += (isActivity ? ("getShortArrayListExtra($S)") : ("getShortArrayList($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_LIST_BYTE:
                statement += (isActivity ? ("getByteArrayListExtra($S)") : ("getByteArrayList($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_LIST_BOOLEAN:
                statement += (isActivity ? ("getBooleanArrayListExtra($S)") : ("getBooleanArrayList($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_LIST_STRING:
                statement += (isActivity ? ("getStringArrayListExtra($S)") : ("getStringArrayList($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_LIST_CHAR_SEQUENCE:
                statement += (isActivity ? ("getCharSequenceArrayListExtra($S)") : ("getCharSequenceArrayList($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_ARRAY_LIST_PARCELABLE:
                statement += (isActivity ? ("getParcelableArrayListExtra($S)") : ("getParcelableArrayList($S)"));
                break;


            case Constants.TYPE_KIND.TYPE_SERIALIZABLE:
                statement += (isActivity ? ("getSerializableExtra($S)") : ("getSerializable($S)"));
                break;
            case Constants.TYPE_KIND.TYPE_OTHER_OBJECT:
                statement = "$T.parseObject(" + (isActivity ? "substitute.getIntent()." : "getArguments(). ") + "getStringExtra($S), $T.class)";
                break;
            default:break;
        }
        return statement + ";";

    }


}
