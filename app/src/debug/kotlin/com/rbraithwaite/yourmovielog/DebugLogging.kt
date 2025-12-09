package com.rbraithwaite.yourmovielog

import timber.log.Timber

// REFACTOR [23-12-19 6:08p.m.] -- copied from old task app codebase, make this a lib.
object DebugLogging {
    const val TAG = "(o_o)"

    fun log(obj: Any?) {
        log(obj.toString())
    }

    fun log(message: String) {
        log { text(message) }
    }

    fun logValue(name: String, value: Any?) {
        log { value(name, value) }
    }

    fun logValues(values: List<Pair<String, Any?>>) {
        log {
            for ((name, value) in values) {
                value(name, value)
            }
        }
    }

    fun log(logBlock: LogBuilder.() -> Unit) {
        Timber.e(LogBuilder().apply(logBlock).build())
    }

    class LogBuilder {
        private val stringBuilder = StringBuilder().apply { append("$TAG ") }

        fun title(title: String, lineChar: Char = '*') {
            val padding = 10
            val line = lineChar.toString().repeat(padding)
            stringBuilder.appendLine("$line $title $line")
        }

        fun classNameOf(thing: Any?) {
            thing?.let {
                stringBuilder.appendLine(it.javaClass.simpleName)
            } ?: stringBuilder.appendLine("null (no class name)")
        }

        fun method(obj: Any, methodName: String) {
            stringBuilder.appendLine(methodStr(obj, methodName))
        }

        fun methodStr(obj: Any, methodName: String): String {
            return "${obj.javaClass.simpleName}.$methodName()"
        }

        fun methodTitle(obj: Any, methodName: String, lineChar: Char = '*') {
            title(methodStr(obj, methodName), lineChar)
        }

        fun separator(sep: Char = '*', width: Int = 10) {
            stringBuilder.appendLine(sep.toString().repeat(width))
        }

        fun value(name: String, value: Any?) {
            stringBuilder.appendLine("$name: $value")
        }

        fun text(value: String) {
            stringBuilder.appendLine(value)
        }

        fun build(): String {
            return stringBuilder.toString()
        }
    }
}