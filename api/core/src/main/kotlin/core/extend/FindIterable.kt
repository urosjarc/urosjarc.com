package core.extend

import com.mongodb.kotlin.client.FindIterable

fun <T : Any> FindIterable<T>.stran(n: Int = 0): FindIterable<T> = this.skip(n * 100).limit(100)
