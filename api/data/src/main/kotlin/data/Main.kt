package data

import Zip
import java.io.File

fun main() {
    val url = Zip::class.java.getResource("Omega11.zip")
    val file = File(url?.toURI() ?: throw Error("$url not exists!"))
    val zip11 = Zip()
    zip11.init(file)
}
