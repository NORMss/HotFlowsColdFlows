package com.norm.myhotflowscoldflows

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun flowsDemo() {
    val flow = flow<Int> {
        repeat(10) {
            emit(it)
            println("Emitted value $it")
            delay(1000L)
        }
    }

//    flow.launchIn(GlobalScope)
    GlobalScope.launch {
        flow.collect {
            println(it.toString())
        }
    }
}