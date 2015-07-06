package com.memo.studygroup.app.common.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.concurrent.Callable;

/**
 * Created by KHAN on 2015-07-06.
 */
public abstract class BaseDao<T, ID> implements CRUDOperations<T, ID> {

    private SQLiteOpenHelper helper;

    private SQLiteDatabase readableDB;
    private SQLiteDatabase writableDB;

    public BaseDao(Context context) {
        helper = getDefaultDBHelper(context);
    }

    protected abstract SQLiteOpenHelper getDefaultDBHelper(Context context);

    protected SQLiteDatabase getReadableDB() {
        if (readableDB == null) {
            readableDB = helper.getReadableDatabase();
        }
        return readableDB;
    }

    protected SQLiteDatabase getWritableDB() {
        if (writableDB == null) {
            writableDB = helper.getWritableDatabase();
        }
        return writableDB;
    }


    public synchronized void close() {
        if (readableDB != null) {
            readableDB.close();
            readableDB = null;
        }

        if (writableDB != null) {
            writableDB.close();
            writableDB = null;
        }

        helper.close();
        helper = null;
    }

    protected T executeWithTransaction(Callable<T> task) {
        SQLiteDatabase db = getWritableDB();
        try {
            db.beginTransaction();

            T obj = task.call();

            db.setTransactionSuccessful();

            return obj;
        }
        catch (SQLException e) {
            Log.w(getClass().getSimpleName(), "sql error....", e);
        }
        catch (Exception e) {
            Log.w(getClass().getSimpleName(), "transaction error....", e);
        }
        finally {
            db.endTransaction();
        }

        return null;
    }
}
