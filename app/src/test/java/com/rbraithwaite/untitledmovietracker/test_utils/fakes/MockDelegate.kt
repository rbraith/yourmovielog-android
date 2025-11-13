package com.rbraithwaite.untitledmovietracker.test_utils.fakes
import org.mockito.Mockito.mock
import kotlin.reflect.KFunction
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.isAccessible

/**
 * This records mock calls while also delegating to an internal delegate instance.
 * In order for this to work, any class implementing MockDelegate must also implement the interface [T]
 */
abstract class MockDelegate<T : Any>(
    /** Function calls are delegated to this instance */
    private val delegate: T,
    /** The class of the delegate */
    clazz: Class<T>,
) {
    /**
     * A mock of [T].
     * Use this to verify outbound side effect calls.
     */
    val mock: T = mock(clazz)

    /**
     * If true, calls are also sent to [mock].
     * Useful for setup code that you don't want to ruin later verifications.
     */
    var mockEnabled: Boolean = true

    /**
     * Run some work using a temporary state of [mockEnabled], then revert to the previous state.
     */
    suspend fun withMockEnabled(mockEnabled: Boolean, work: suspend T.() -> Unit) {
        val oldMockEnabled = this.mockEnabled
        this.mockEnabled = mockEnabled
        @Suppress("UNCHECKED_CAST")
        (this as T).work()
        this.mockEnabled = oldMockEnabled
    }

    /**
     * All function implementations of a [MockDelegate] should delegate to this function. This handles
     * executing the [delegate] method and optionally the [mock] method
     */
    protected suspend fun <ReturnType> delegate(funcName: String, vararg args: Any?): ReturnType {
        if (mockEnabled) {
            val mockFunc = getFunction(mock, funcName)
            if (mockFunc.isSuspend) {
                mockFunc.callSuspend(mock, *args)
            } else {
                mockFunc.call(mock, *args)
            }
        }
        val delegateFunc = getFunction(delegate, funcName)
        val retVal = if (delegateFunc.isSuspend) {
            delegateFunc.callSuspend(delegate, *args)
        } else {
            delegateFunc.call(delegate, *args)
        }

        @Suppress("UNCHECKED_CAST")
        return retVal as ReturnType
    }

    private fun getFunction(obj: T, funcName: String): KFunction<*> {
        return obj::class.memberFunctions
            .firstOrNull { it.name == funcName }
            ?.apply { isAccessible = true }
            ?: throw IllegalArgumentException("Function $funcName not found")
    }
}