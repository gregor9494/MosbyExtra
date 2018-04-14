package com.mosbyextra.ggaworowski.mosbyextralibrary.common;

/**
 * Created by GG on 07.03.2018.
 */

public abstract class BaseVSPCPresenter<V extends MvpVSView<VS>, VS extends ViewState,PC extends PartialChanges> extends BaseVSPresenter<V,VS> {

    public BaseVSPCPresenter(IPComunicator ipComunicator) {
        super(ipComunicator);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
    }

    public abstract void setupViewState(VS viewState, PC partialChanges);


}
