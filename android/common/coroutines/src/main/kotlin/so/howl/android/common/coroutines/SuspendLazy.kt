package so.howl.android.common.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext


interface SuspendLazy<out T> {
    suspend operator fun invoke(): T
}

fun <T> CoroutineScope.suspendLazy(
    context: CoroutineContext = kotlin.coroutines.EmptyCoroutineContext,
    initializer: suspend CoroutineScope.() -> T
): SuspendLazy<T> = RealSuspendLazy(this, context, initializer)

private class RealSuspendLazy<out T>(
    coroutineScope: CoroutineScope,
    context: CoroutineContext,
    initializer: suspend CoroutineScope.() -> T
) : SuspendLazy<T> {
    private val deferred = coroutineScope.async(context, start = CoroutineStart.LAZY, block = initializer)
    override suspend operator fun invoke(): T = deferred.await()
}