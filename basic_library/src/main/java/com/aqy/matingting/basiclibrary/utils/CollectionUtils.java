package com.aqy.matingting.basiclibrary.utils;

import java.util.List;

/**
 * Created by matingting on 2017/12/13.
 */

public class CollectionUtils {

    public static <E> boolean isEmpty(List<E> list){
        return size(list) <= 0;
    }

    public static <E> int size(List<E> list) {
        return list!=null?list.size():0;
    }

}
