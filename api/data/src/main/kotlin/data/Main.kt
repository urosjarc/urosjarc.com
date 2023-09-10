package data

import data.books.Omega

fun main() {
    val omega = Omega(resourceZipPath = "Omega12.zip", startingTeorijaName = "resitve")
//    omega.naloge(start = 65, end = 108, debug = false)
    omega.resitve(start = 186, end = 190, debug = false)
}
