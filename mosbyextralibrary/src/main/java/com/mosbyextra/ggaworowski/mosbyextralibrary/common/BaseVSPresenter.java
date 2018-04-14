package com.mosbyextra.ggaworowski.mosbyextralibrary.common;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by GG on 07.03.2018.
 */

public abstract class BaseVSPresenter<V extends MvpVSView<VS>, VS extends ViewState> extends BasePresenter<V> {

    protected VS viewState;
    private PublishSubject<VS> publishViewState = PublishSubject.create();

    public BaseVSPresenter(IPComunicator ipComunicator) {
        super(ipComunicator);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        if (viewState == null) {
            viewState = createViewState();
        }
        publishViewState
                .scan(viewState, (vs, vs2) -> vs2)
                .subscribe(view::render, this::handleError);

    }

    protected abstract VS createViewState();

    public VS getViewState() {
        return viewState;
    }

    public void subscribeViewState(Observable<VS> observable, Consumer<Throwable> errorAction) {
        addSubscription(
                observable.subscribe(vs -> ifViewAttached(view -> publishViewState.onNext(vs)), errorAction));
    }

    public void subscribeViewState(Single<VS> observable, Consumer<Throwable> errorAction) {
        addSubscription(
                observable.subscribe(vs -> ifViewAttached(view -> publishViewState.onNext(vs)), errorAction));
    }

    public void subscribeViewState(Observable<VS> observable) {
        addSubscription(
                observable.subscribe(vs -> ifViewAttached(view -> publishViewState.onNext(vs)), this::handleError));
    }

    public void subscribeViewState(Single<VS> observable) {
        addSubscription(
                observable.subscribe(vs -> ifViewAttached(view -> publishViewState.onNext(vs)), this::handleError));
    }

    public void subscribeViewStateIfAttached(Observable<VS> observable, Consumer<Throwable> errorAction) {
        ifViewAttached(view -> {
            addSubscription(
                    observable.subscribe(vs -> ifViewAttached(v -> publishViewState.onNext(vs)), errorAction));
        });
    }

    public void subscribeViewStateIfAttached(Single<VS> observable, Consumer<Throwable> errorAction) {
        ifViewAttached(view -> {
            addSubscription(
                    observable.subscribe(vs -> ifViewAttached(v -> publishViewState.onNext(vs)), errorAction));
        });
    }

    public void subscribeViewStateIfAttached(Observable<VS> observable) {
        ifViewAttached(view -> {
            addSubscription(
                    observable.subscribe(vs -> ifViewAttached(v -> publishViewState.onNext(vs)), this::handleError));
        });
    }

    public void subscribeViewStateIfAttached(Single<VS> observable) {
        ifViewAttached(view -> {
            addSubscription(
                    observable.subscribe(vs -> ifViewAttached(v -> publishViewState.onNext(vs)), this::handleError));
        });
    }

    public void publishViewState(VS vs) {
        publishViewState.onNext(vs);
    }

    protected void handleError(Throwable ex) {
        ex.printStackTrace();
    }
}
