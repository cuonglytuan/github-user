package com.exercise.githubuser.ext

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.NONE
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

@VisibleForTesting(otherwise = NONE)
@Throws(IOException::class)
fun String.convertInputStreamToString(inputStream: InputStream): String {
    val reader = InputStreamReader(inputStream)
    val builder = StringBuilder()
    val buffer = CharArray(512)
    var read: Int
    do {
        read = reader.read(buffer)
        if (read <= 0) {
            break
        }
        builder.append(buffer, 0, read)

    } while (true)
    return builder.toString()
}