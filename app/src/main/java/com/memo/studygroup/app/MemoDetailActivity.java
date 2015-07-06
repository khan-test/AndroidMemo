package com.memo.studygroup.app;

import android.content.ContentValues;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.memo.studygroup.app.common.app.BaseActivity;
import com.memo.studygroup.app.common.util.DateUtil;
import com.memo.studygroup.app.common.util.StringUtil;
import com.memo.studygroup.app.db.MemoDao;
import com.memo.studygroup.app.model.MemoVO;

import java.util.Date;

import static com.memo.studygroup.app.Constants.*;

public class MemoDetailActivity extends BaseActivity {

    private EditText edtMemo;
    private View btnReload;
    private View btnDelete;

    private MemoVO memo;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_memo_detail;
    }

    @Override
    protected void setWidget() {
        edtMemo = setView(edtMemo, R.id.edtMemo);
        btnReload = setView(btnReload, R.id.btnReload);
        btnDelete = setView(btnDelete, R.id.btnDelete);
    }

    @Override
    protected void setData(Intent intent) {
        if (intent.hasExtra(DATA)) {
            memo = MemoVO.fromContentValues(intent.<ContentValues>getParcelableExtra(DATA));
            setMemoString(memo.getMemo());
        }
        btnReload.setVisibility(memo == null ? View.GONE : View.VISIBLE);
        btnDelete.setVisibility(memo == null ? View.GONE : View.VISIBLE);
    }

    void setMemoString(String memo) {
        memo = StringUtil.nvl(memo);
        edtMemo.setText(memo);
        edtMemo.setSelection(memo.length());
    }

    public void saveMemo(View v) {
        MemoDao dao = new MemoDao(getApplicationContext());
        try {
            if (memo == null) {
                String memoStr = edtMemo.getText().toString();
                String dateStr = DateUtil.format(new Date());
                memo = new MemoVO(memoStr, dateStr);

                long id = dao.insert(memo);

                if (id != -1) {
                    memo.setId((int) id);
                    moveList(RESULT_ADD);
                } else {
                    //TODO error handle
                }

            } else {
                memo.setMemo(edtMemo.getText().toString());

                int row = dao.update(memo);

                if (row > 0) {
                    moveList(RESULT_UPDATE);
                } else {
                    //TODO error handle
                }
            }
        }
        catch (Exception e) {
            //TODO error handle...
        }
        finally {
            dao.close();
            dao = null;
        }
    }

    public void reloadMemo(View v) {
        setMemoString(memo.getMemo());
    }

    public void deleteMemo(View v) {
        if (memo != null && memo.getId() != null) {

            MemoDao dao = new MemoDao(getApplicationContext());
            try {
                int row = dao.delete(memo);
                if (row > 0) {
                    moveList(RESULT_REMOVE);
                }
            }
            catch (Exception e) {
                //TODO error handle...
            }
            finally {
                dao.close();
                dao = null;
            }
        }
        else {
            //TODO error handle...
        }
    }

    void moveList(int resultCode) {
        Intent intent = new Intent();
        intent.putExtra(DATA, memo.toContentValues());
        setResult(resultCode, intent);
        finish();
    }
}
