package com.example.takhfifdar.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object Navigator {
    private val _sharedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun navigateTo(navTarget: NavTarget, args: String = "") {
        if (args.isEmpty()) {
            _sharedFlow.tryEmit(navTarget.label)
        } else {
            _sharedFlow.tryEmit(navTarget.label + "/" + args)
        }
    }
}

