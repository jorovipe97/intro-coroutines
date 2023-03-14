package samples

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val request = launch {
        launch {
            println("Coroutine 1 start")
            delay(1000)
            println("Coroutine 1 end")
        }
        launch {
            println("Coroutine 2 start")
            delay(2000)
            println("Coroutine 2 end")
        }

        println("request: I'm done and I don't explicitly join my children that are still active")
    }

    request.join() // wait for completion of the request, including all its children
    println("Now processing of the request is complete")
}
