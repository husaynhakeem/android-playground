package com.husaynhakeem.daggersample.base

abstract class Presenter<V : View> {

    protected var view: V? = null

    fun bind(view: V) {
        this.view = view
    }

    fun unbind() {
        this.view = null
    }
}