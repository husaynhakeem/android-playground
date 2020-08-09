package com.husaynhakeem.daggersample.presenter

import com.husaynhakeem.daggersample.base.Presenter
import com.husaynhakeem.daggersample.view.AllNewsView

abstract class AllNewsPresenter : Presenter<AllNewsView>() {

    abstract fun getAllNews()
}