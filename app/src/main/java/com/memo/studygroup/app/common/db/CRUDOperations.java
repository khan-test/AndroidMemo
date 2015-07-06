package com.memo.studygroup.app.common.db;

import java.util.List;

/**
 * Created by KHAN on 2015-07-06.
 */
public interface CRUDOperations<T, ID> {
    long insert(T object);
    int update(T object);
    int delete(T object);
    T read(ID id);
    List<T> readAll();
}
