package data

import data.domain.Annotation
import data.domain.zip_iterator
import data.extend.*
import java.io.File



fun main() {
    val omega = Omega("Omega11.zip", 7, 40)
    omega.parse()
}
