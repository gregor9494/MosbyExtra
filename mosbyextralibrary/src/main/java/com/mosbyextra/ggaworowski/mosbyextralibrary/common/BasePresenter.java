package com.mosbyextra.ggaworowski.mosbyextralibrary.common;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import ggaworowski.worktime.screens.common.PresenterParser;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Grzegorz Gaworowski company Graphicbox on 2017-11-03.
 */

public abstract class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {

    private IPComunicator ipComunicator;

    public BasePresenter(IPComunicator ipComunicator) {
        this.ipComunicator = ipComunicator;
    }

    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Add next disposable that need to be disposed after onDetach
     *
     * @param disposable
     */
    public void addSubscription(Disposable disposable) {
        this.disposable.add(disposable);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        ipComunicator.register(this);
        PresenterParser.parse(this);
    }

    @Override
    public void detachView() {
        ipComunicator.unregister(this);
        disposable.clear();
        super.detachView();
    }

    protected IPComunicator getIpComunicator() {
        return ipComunicator;
    }
}
