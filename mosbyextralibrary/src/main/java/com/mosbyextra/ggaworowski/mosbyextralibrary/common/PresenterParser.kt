package ggaworowski.worktime.screens.common

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.mosbyextra.ggaworowski.mosbyextralibrary.common.BasePresenter
import ggaworowski.worktime.screens.common.annotations.AddDisposableOnAttach
import ggaworowski.worktime.screens.common.annotations.RunOnAttach
import ggaworowski.worktime.screens.common.annotations.SubscribeOnAttach
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable


/**
 * Created by GG on 07.03.2018.
 */
class PresenterParser {
    companion object {
        @JvmStatic
        fun parse(basePresenter: BasePresenter<out MvpView>) {
            basePresenter::class.java.declaredMethods.forEach {
                if (it.annotations.filter { it is AddDisposableOnAttach }.isNotEmpty()) {
                    if (it.returnType == Disposable::class.java) {
                        basePresenter.addSubscription(it.invoke(basePresenter) as Disposable)
                    }
                } else if (it.annotations.filter { it is SubscribeOnAttach }.isNotEmpty()) {
                    if (it.returnType == Observable::class) {
                        basePresenter.addSubscription((it.invoke(basePresenter) as Observable<*>).subscribe())
                    } else if (it.returnType == Single::class) {
                        basePresenter.addSubscription((it.invoke(basePresenter) as Single<*>).subscribe())
                    }
                } else if (it.annotations.filter { it is RunOnAttach }.isNotEmpty()) {
                    it.invoke(basePresenter)
                }
            }
        }
    }
}