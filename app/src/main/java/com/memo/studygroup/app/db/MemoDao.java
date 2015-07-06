package com.memo.studygroup.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.memo.studygroup.app.common.db.BaseDao;
import com.memo.studygroup.app.common.util.DateUtil;
import com.memo.studygroup.app.model.MemoVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.memo.studygroup.app.model.MemoVO.*;

/**
 * Created by KHAN on 2015-07-06.
 */
public class MemoDao extends BaseDao<MemoVO, Integer> {

    private static final String LOG_TAG = "MEMO_DAO";

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "memoDb";

    public MemoDao(Context context) {
        super(context);
    }

    @Override
    protected SQLiteOpenHelper getDefaultDBHelper(Context context) {
        return new SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                //DB를 새로 만든다.
                String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                        + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + FIELD_MEMO + " TEXT,"
                        + FIELD_REG_DATE + " TEXT" + ");";
                db.execSQL(CREATE_TABLE);
                initValue(db);
            }

            private void initValue(SQLiteDatabase db) {
                List<MemoVO> list = new ArrayList<>();
                String today = DateUtil.format(new Date());
                list.add(new MemoVO("test4", today));
                list.add(new MemoVO("test3", today));
                list.add(new MemoVO("test2", today));
                list.add(new MemoVO("test1", today));

                db.beginTransaction();
                for (MemoVO item : list) {
                    db.insert(TABLE_NAME, null, item.toContentValues());
                }
                db.setTransactionSuccessful();
                db.endTransaction();
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // DB를 지우고 다시 쓴다.
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                this.onCreate(db);
            }
        };
    }

    @Override
    public long insert(MemoVO memo) {
        return getWritableDB().insert(TABLE_NAME, null, memo.toContentValues());
    }

    @Override
    public int update(MemoVO memo) {
        return getWritableDB().update(TABLE_NAME,
                memo.toContentValues(),
                FIELD_ID + "=?",
                new String[] { String.valueOf(memo.getId()) });
    }

    @Override
    public int delete(MemoVO memo) {
        return getWritableDB().delete(TABLE_NAME, FIELD_ID + "=?", new String[]{String.valueOf(memo.getId())});
    }

    @Override
    public MemoVO read(Integer id) {
        SQLiteDatabase db = getReadableDB();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME, null, FIELD_ID + "=?", new String[]{String.valueOf(id)}, null, null, FIELD_ID, null);
            if (cursor != null && cursor.moveToFirst()) {
                return getMemo(cursor);
            }
        }
        catch (Exception e) {
            Log.w(LOG_TAG, "read error", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            cursor = null;
        }
        return null;
    }

    @Override
    public List<MemoVO> readAll() {
        SQLiteDatabase db = getReadableDB();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, FIELD_ID, null);
            if (cursor != null && cursor.moveToFirst()) {
                List<MemoVO> result = new ArrayList<MemoVO>();
                do {
                    result.add(getMemo(cursor));
                } while(cursor.moveToNext());

                return result;
            }
        }
        catch (Exception e) {
            Log.w(LOG_TAG, "read error", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private MemoVO getMemo(Cursor cursor) {
        MemoVO memo = new MemoVO();
        memo.setId(cursor.getInt(cursor.getColumnIndex(FIELD_ID)));
        memo.setMemo(cursor.getString(cursor.getColumnIndex(FIELD_MEMO)));
        memo.setRegDate(cursor.getString(cursor.getColumnIndex(FIELD_REG_DATE)));
        return memo;
    }
}
