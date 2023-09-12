package data

import data.books.Omega


fun process() {
    val omega = Omega(resourceZipPath = "Omega21.zip", startingTeorijaName = "empty")
    omega.naloge(start = 7, end = 114, debug = false)
    omega.resitve(start = 116, end = 174, debug = false)
}

fun main() {
    process()
}
