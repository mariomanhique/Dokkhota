package com.mariomanhique.dokkhota.di

import javax.inject.Qualifier

object Anotations {
    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Dispatcher(val niaDispatcher: NiaDispatchers)

    enum class NiaDispatchers {
        Default,
        IO,
    }
}