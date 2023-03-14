package samples

import kotlinx.coroutines.*


fun main() = runBlocking {
    println("Start - ${coroutineContext[Job]}")
    println("Start - ${coroutineContext.job}")

    launch {
        println("Launch Start - ${coroutineContext.job}")
        delay(1000)
        println("Launch End - ${coroutineContext.job}")
    }

    println("End - ${coroutineContext.job}")
}
