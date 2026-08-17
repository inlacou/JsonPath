package com.inlacou.lib.jsonpath

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonPathTest {

    @Test
    fun `JsonPath init with valid string`() {
        val path = JsonPath("$.store.book[0].title")
        assertEquals("$.store.book[0].title", path.toString())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `JsonPath init with invalid string throws exception`() {
        JsonPath("invalid.path")
    }

    @Test
    fun `JsonPath init with list of strings`() {
        val path = JsonPath(listOf("store", "book", "title"))
        assertEquals("$.store.book.title", path.toString())
    }

    @Test
    fun `isEmpty returns true for root paths`() {
        assertTrue(JsonPath("$").isEmpty())
        assertTrue(JsonPath("$.").isEmpty())
    }

    @Test
    fun `mergePaths merges correctly`() {
        val root = JsonPath("$.store")
        val child = JsonPath("$.book")
        val merged = root.mergePaths(child)
        assertEquals("$.store.book", merged.toString())
    }

    @Test
    fun `plus operator merges correctly`() {
        val root = JsonPath("$.store")
        val child = JsonPath("$.book")
        val merged = root + child
        assertEquals("$.store.book", merged.toString())
    }

    @Test
    fun `pathElements splits path into components`() {
        val path = JsonPath("$.store.book[0].title")
        val elements = path.pathElements()
        assertEquals(listOf("store", "book[0]", "title"), elements)
    }

    @Test
    fun `compareTo correctly orders paths`() {
        val path1 = JsonPath("$.a[1]")
        val path2 = JsonPath("$.a[2]")
        val path3 = JsonPath("$.a[10]")

        assertTrue(path1 < path2)
        assertTrue(path2 < path3)
        assertTrue(path1 < path3)
    }
}
