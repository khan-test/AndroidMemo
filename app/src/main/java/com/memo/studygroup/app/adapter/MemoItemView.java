package com.memo.studygroup.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.memo.studygroup.app.R;
import com.memo.studygroup.app.common.adapter.AbsListItemView;
import com.memo.studygroup.app.common.adapter.ItemClickListener;
import com.memo.studygroup.app.model.MemoVO;

/**
 * Created by KHAN on 2015-07-06.
 */
public class MemoItemView extends AbsListItemView<MemoVO> {

    private ItemClickListener<MemoVO> listener;

    private TextView txtId;
    private TextView txtMemo;
    private TextView txtRegDate;

    public MemoItemView(Context context, ItemClickListener<MemoVO> listener) {
        super(context);
        setListener(listener);
    }

    public void setListener(ItemClickListener<MemoVO> listener) {
        this.listener = listener;
    }

    @Override
    protected int getViewResourceId() {
        return R.layout.memo_item;
    }

    @Override
    protected void setSubWidget(View mainView) {
        txtId = setView(txtId, R.id.txtId);
        txtMemo = setView(txtMemo, R.id.txtMemo);
        txtRegDate = setView(txtRegDate, R.id.txtRegDate);
    }

    @Override
    protected void setEvent() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(0, getItem());
                }
            }
        });
    }

    @Override
    protected void bindData(MemoVO data) {
        txtId.setText(String.valueOf(data.getId()));
        txtMemo.setText(data.getMemo());
        txtRegDate.setText(data.getRegDate());
    }
}
