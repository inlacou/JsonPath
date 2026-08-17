package com.inlacou.lib.jsonpath

object Constants {
    /**
     * Regex to identify a UUID
     */
    const val UUID_REGEX =
        "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"

    const val UUID_EXTENSION_REGEX =
        "$UUID_REGEX\\.(\\w+)"

}