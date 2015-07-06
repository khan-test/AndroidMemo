package com.memo.studygroup.app.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.memo.studygroup.app.common.adapter.BaseListAdapter;
import com.memo.studygroup.app.common.adapter.ItemClickListener;
import com.memo.studygroup.app.model.MemoVO;

import java.util.List;

/**
 * Created by KHAN on 2015-07-06.
 */
public class MemoAdapter extends BaseListAdapter<MemoVO, MemoItemView> {

    private ItemClickListener<MemoVO> listener;

    public MemoAdapter(Context context, ItemClickListener<MemoVO> listener, List<MemoVO> items) {
        super(context, items);
        this.listener = listener;
    }

    @Override
    public MemoItemView generateView(int position, ViewGroup parent) {
        return new MemoItemView(getContext(), listener);
    }
}
