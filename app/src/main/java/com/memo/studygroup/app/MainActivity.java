package com.memo.studygroup.app;

import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.memo.studygroup.app.adapter.MemoAdapter;
import com.memo.studygroup.app.common.adapter.ItemClickListener;
import com.memo.studygroup.app.common.app.BaseActivity;
import com.memo.studygroup.app.db.MemoDao;
import com.memo.studygroup.app.model.MemoVO;

import java.util.List;

import static com.memo.studygroup.app.Constants.*;


public class MainActivity extends BaseActivity implements ItemClickListener<MemoVO> {

    public static final int REQUEST_MEMO = 10;

    private ListView lstMemo;
    private MemoAdapter adapter;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setWidget() {
        lstMemo = setView(lstMemo, R.id.lstMemo);
    }

    @Override
    protected void setData(Intent intent) {
        MemoDao memoDao = new MemoDao(getApplicationContext());
        List<MemoVO> memoList = memoDao.readAll();
        adapter = new MemoAdapter(getContext(), this, memoList);
        lstMemo.setAdapter(adapter);
    }

    @Override
    public void onClick(int action, MemoVO item) {
        moveActivityForResult(REQUEST_MEMO, MemoDetailActivity.class, item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_memo) {
            moveActivityForResult(REQUEST_MEMO, MemoDetailActivity.class, null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        MemoVO memoVO = null;
        if (data != null && data.hasExtra(DATA)) {
            memoVO = MemoVO.fromContentValues(data.<ContentValues> getParcelableExtra(DATA));
        }

        switch (resultCode) {
            case RESULT_ADD:
                adapter.addItem(memoVO);
                adapter.notifyDataSetChanged();
                break;
            case RESULT_UPDATE:
                adapter.replaceItem(memoVO);
                adapter.notifyDataSetChanged();
                break;
            case RESULT_REMOVE:
                adapter.removeItem(memoVO);
                adapter.notifyDataSetChanged();
                break;
        }

    }
}
