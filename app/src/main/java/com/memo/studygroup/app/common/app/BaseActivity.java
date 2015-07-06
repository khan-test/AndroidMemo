package com.memo.studygroup.app.common.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.memo.studygroup.app.common.model.BaseModel;

import java.io.Serializable;

/**
 * Created by KHAN on 2015-07-06.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static final String DATA = "data";

    /**
     * Activity context를 리턴
     * @return activity context
     */
    protected Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _setWidget();
        setData(getIntent());
    }

    private void _setWidget() {
        View rootView = getContentView();
        if (rootView == null) {
            setContentView(getContentViewResId());
        }
        else {
            setContentView(rootView);
        }

        setWidget();
    }

    /**
     * Activity의 메인이 되는 ContentView를 리턴한다.
     * ResourceId로 리턴할 시 null로 리턴.
     * @return Activity content view.
     */
    protected View getContentView() {
        return null;
    }

    /**
     * Activity의 메인이 되는 ContentView의 Resource ID를 리턴한다.
     *
     * @return Activity content view resource id.
     */
    protected abstract int getContentViewResId();

    /**
     * 각종 Widget들을 세팅한다.
     */
    protected abstract void setWidget();

    /**
     * Intent로 넘어오는 데이터들에 대한 처리를 담당한다.
     * @param intent
     */
    protected abstract void setData(Intent intent);

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        setData(intent);
    }

    // View 관련
    /**
     * findViewById 대체용.
     *
     * @param view 매핑할 뷰
     * @param resId 리소스 id
     * @return 생성된 뷰
     * @author khan
     * @since 2013. 12. 29.
     */
    protected <V extends View> V setView(V view, int resId) {
        return setView(null, view, resId);
    }

    /**
     * findViewById 대체용.
     *
     * @param rootView 매핑할 뷰의 상위 뷰
     * @param view 매핑할 뷰
     * @param resId 리소스 id
     * @return 생성된 뷰
     * @author khan
     * @since 2013. 12. 29.
     */
    @SuppressWarnings("unchecked")
    protected <V extends View> V setView(View rootView, V view, int resId) {
        try {
            if (rootView != null) {
                return (V) rootView.findViewById(resId);
            }
            else {
                return (V) findViewById(resId);
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    public <C, T> void moveActivity(Class<C> target, T item) {
        moveActivityForResult(-1, target, item);
    }

    public <C, T> void moveActivityForResult(int requestCode, Class<C> target, T item) {
        Intent intent = new Intent(this, target);

        if (item != null && item instanceof BaseModel) {
            intent.putExtra(DATA, ((BaseModel) item).toContentValues());
        }
        else if (item != null && item instanceof Parcelable) {
            intent.putExtra(DATA, ((Parcelable) item));
        }
        else if (item != null && item instanceof Serializable) {
            intent.putExtra(DATA, ((Serializable) item));
        }

        startActivityForResult(intent, requestCode);
    }
}
