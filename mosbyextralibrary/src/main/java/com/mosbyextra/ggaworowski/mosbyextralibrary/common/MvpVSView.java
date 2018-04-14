package com.mosbyextra.ggaworowski.mosbyextralibrary.common;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by GG on 10.03.2018.
 */

public interface MvpVSView<VS extends ViewState> extends MvpView {
    void render(VS vs);
}
