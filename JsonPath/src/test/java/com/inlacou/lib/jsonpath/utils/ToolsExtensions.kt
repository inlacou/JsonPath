package com.inlacou.lib.jsonpath.utils

import kotlin.math.max

/**
 * Right now used for debugging, but I think we should find a way to color the Logs we already have, by severity.
 * For example ERROR as red and WARNING as yellow.
 *
 * I don't know you guys, but for me our logs are pretty hard to read.
 *
 * @author Iñigo Lacoume
 */

/**
 * This enum holds the colors available by name and it's associated code.
 */
internal enum class PrintColor(val code: String) {
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    RESET("\u001B[0m");

    override fun toString() = code
}

internal fun printErr(s: String) = common(PrintColor.RED, s)
internal fun printWarn(s: String) = common(PrintColor.YELLOW, s)
internal fun printSuccess(s: String) = common(PrintColor.GREEN, s)
internal fun printDark(s: String) = common(PrintColor.BLUE, s)
internal fun printLight(s: String) = common(PrintColor.CYAN, s)
internal fun printColor(printColor: PrintColor, s: String) = common(printColor, s)

private fun common(color: PrintColor, s: String) = s.lines().forEach { println("${color}$it${PrintColor.RESET}") }

/**
 * Prints two Strings side by side.
 */
internal fun printCompare(
    s1: String,
    s2: String,
    redCallback1: ((String) -> Boolean)? = null,
    redCallback2: ((String) -> Boolean)? = null,
) = printCompare(PrintColor.BLUE, s1, PrintColor.CYAN, s2, redCallback1, redCallback2)

/**
 * Prints two Strings side by side.
 */
internal fun printCompare(
    s1: String,
    s2: String,
    redCallback: ((String) -> Boolean)? = null,
) = printCompare(PrintColor.BLUE, s1, PrintColor.CYAN, s2, redCallback, redCallback)

/**
 * Prints two Strings side by side, and used the colors you want for each of them.
 */
internal fun printCompare(
    color1: PrintColor,
    s1: String,
    color2: PrintColor,
    s2: String,
    redCallback1: ((String) -> Boolean)? = null,
    redCallback2: ((String) -> Boolean)? = null,
) {
    val margin = 2
    val s1Lines = s1.lines()
    val s2Lines = s2.lines()
    val longest = max(s1Lines.maxOf { it.length }, s2Lines.maxOf { it.length }) + margin
    val linesNumber = max(s1Lines.size, s2Lines.size)
    repeat(linesNumber) {
        printCompareLine(color1, s1Lines.getOrNull(it) ?: "", color2, s2Lines.getOrNull(it) ?: "", longest, redCallback1, redCallback2)
    }
}

private fun printCompareLine(
    color1: PrintColor,
    s1: String,
    color2: PrintColor,
    s2: String,
    longest: Int,
    redCallback1: ((String) -> Boolean)? = null,
    redCallback2: ((String) -> Boolean)? = null,
) {
    require(s1.lines().size == 1 || s2.lines().size == 1)
    println(
        "${if (redCallback1?.invoke(s1) == true) PrintColor.RED else color1}${s1.padEnd(longest)}${PrintColor.RESET}" +
            " | ${if (redCallback2?.invoke(s2) == true) PrintColor.RED else color2}${s2}${PrintColor.RESET}"
    )
}

/**
 * [toString] variant that removes the base64 data from the [String] and replaces it with [replacement] [String]
 */
fun Any.toStringRemoveBase64(replacement: String = "_base64_"): String {
    var aux = if (this is String) this else toString()
    while (aux.contains("data:image/")) {
        aux = aux.replace(Regex("\"(data:image/.*)\""), "\"$replacement\"")
    }
    return aux
}
