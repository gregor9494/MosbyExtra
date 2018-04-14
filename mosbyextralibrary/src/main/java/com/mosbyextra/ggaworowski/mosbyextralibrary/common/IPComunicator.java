package com.mosbyextra.ggaworowski.mosbyextralibrary.common;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Grzegorz Gaworowski company Graphicbox on 2017-11-09.
 */

public class IPComunicator {


    private List<BasePresenter> presenters = new CopyOnWriteArrayList<>();
    private PublishSubject<PresenterState> synchronizer = PublishSubject.create();

    public IPComunicator() {
        synchronizer
                .subscribeOn(Schedulers.computation())
                .subscribe(this::refreshPresenterState);
    }

    public void register(BasePresenter presenter) {
        synchronizer.onNext(new PresenterState(presenter, true));
    }

    public void unregister(BasePresenter presenter) {
        synchronizer.onNext(new PresenterState(presenter, false));
    }

    private void refreshPresenterState(PresenterState presenterState) {
        if (presenterState.isAttached()) {
            presenters.add(presenterState.getPresenter());
        } else {
            presenters.remove(presenterState.getPresenter());
        }
    }

    public <PresenterType extends BasePresenter> Observable<PresenterType> getPresenters(
            final Class<PresenterType> presenterTypeClass) {
        return Observable.fromIterable(presenters)
                .filter(presenterTypeClass::isInstance)
                .map(presenterTypeClass::cast)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    class PresenterState {
        BasePresenter presenter;
        boolean attached;

        public PresenterState(BasePresenter presenter, boolean attached) {
            this.presenter = presenter;
            this.attached = attached;
        }

        public BasePresenter getPresenter() {
            return presenter;
        }

        public void setPresenter(BasePresenter presenter) {
            this.presenter = presenter;
        }

        public boolean isAttached() {
            return attached;
        }

        public void setAttached(boolean attached) {
            this.attached = attached;
        }
    }

}
