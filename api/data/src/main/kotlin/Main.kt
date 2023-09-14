import data.resize
import data.show
import data.zip_iterator
import java.io.File

fun main() {
    val omega11 = File("src/main/resources/Omega11.zip")
    for(image in zip_iterator(file=omega11, skip=7, end=8)){

        image.resize(image.width/3, image.height/3).show()
        Thread.sleep(1000)

    }
}
