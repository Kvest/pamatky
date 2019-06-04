package com.kvest.pamatky.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(): T {
    return ViewModelProviders.of(this)[T::class.java]
}

inline fun <reified T : ViewModel> AppCompatActivity.withViewModel(body: T.() -> Unit): T {
    val vm = getViewModel<T>()
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> AppCompatActivity.withViewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val vm = getViewModel<T>(factory)
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.getViewModel(): T {
    return ViewModelProviders.of(this)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.withViewModel(body: T.() -> Unit): T {
    val vm = getViewModel<T>()
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.withViewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val vm = getViewModel<T>(factory)
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}