import data.books.Omega

fun main() {
    val omega11 = Omega(resourceZipPath = "Omega11.zip", startingTeorijaName = "empty")
    omega11.naloge(start=7, end=107, debug = false)
    omega11.resitve(start=110, end=250, debug = false)
}
