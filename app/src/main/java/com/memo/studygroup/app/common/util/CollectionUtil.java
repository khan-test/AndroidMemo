package com.memo.studygroup.app.common.util;

import java.util.Collection;

/**
 * Created by KHAN on 2015-07-06.
 */
public class CollectionUtil {
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }
    public static boolean notEmpty(Collection<?> collection) {
        return isEmpty(collection);
    }
}
