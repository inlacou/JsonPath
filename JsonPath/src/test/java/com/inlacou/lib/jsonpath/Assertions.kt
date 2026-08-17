package com.inlacou.lib.jsonpath

import kotlinx.serialization.json.JsonObject

inline fun <reified T : Throwable> assertThrows(message: String, block: () -> Unit) {
    try {
        block()
        throw AssertionError(message)
    } catch (e: Throwable) {
        if (e !is T) {
            throw e // rethrow unexpected exception
        }
    }
}

inline fun <reified T : Throwable> assertThrows(block: () -> Unit) {
    try {
        block()
        throw AssertionError("Expected ${T::class.simpleName} but no exception was thrown")
    } catch (e: Throwable) {
        if (e !is T) {
            throw e // rethrow unexpected exception
        }
    }
}

fun assertNull(value: Any?) {
    if(value != null) {
        throw AssertionError("Expected null but got $value")
    }
}

fun assertJsonEquals(json1: JsonObject?, json2: JsonObject?) =
    assertEquals(json1, json2, json1?.compareTo(json2!!))

fun assertJsonEquals(json1: JsonObject?, json2: JsonObject?, message: String) =
    assertEquals(json1, json2, message + "\n" + json1?.compareTo(json2!!))

fun assertEquals(json1: JsonObject?, json2: JsonObject?, message: String? = null) {
    TODO()
}

fun JsonObject.compareTo(jsonObject: JsonObject): String = TODO()