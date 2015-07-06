package com.memo.studygroup.app.model;

import android.content.ContentValues;
import com.memo.studygroup.app.common.model.BaseModel;

/**
 * Created by KHAN on 2015-07-06.
 */
public class MemoVO implements BaseModel {

    // Table name
    public static final String TABLE_NAME = "memoTable";

    public static final String FIELD_ID = "id";
    public static final String FIELD_MEMO = "memo";
    public static final String FIELD_REG_DATE = "regDate";

    private Integer id;
    private String memo;
    private String regDate;

    public MemoVO() {
    }

    public MemoVO(String memo, String regDate) {
        this.memo = memo;
        this.regDate = regDate;
    }

    public MemoVO(Integer id, String memo, String regDate) {
        this.id = id;
        this.memo = memo;
        this.regDate = regDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemoVO memo = (MemoVO) o;

        return !(id != null ? !id.equals(memo.id) : memo.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(FIELD_ID, id);
        values.put(FIELD_MEMO, memo);
        values.put(FIELD_REG_DATE, regDate);
        return values;
    }

    public static MemoVO fromContentValues(ContentValues values) {
        if (values != null) {
            return new MemoVO(values.getAsInteger(FIELD_ID), values.getAsString(FIELD_MEMO), values.getAsString(FIELD_REG_DATE));
        }
        return null;
    }
}
