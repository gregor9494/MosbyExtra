package com.mosbyextra.ggaworowski.mosbyextralibrary.common;

import android.os.Bundle;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import dagger.android.AndroidInjection;

/**
 * Created by GG on 10.03.2018.
 */

public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V, P> {
    protected abstract int getLayout();
    protected abstract void setupViews();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(getLayout());
        super.onCreate(savedInstanceState);
        setupViews();
    }

}
