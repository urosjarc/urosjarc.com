import data.Piksel
import data.extends.deskew
import data.extends.dobiPrvega
import data.extends.dobiZadnjega
import data.extends.save
import java.awt.image.BufferedImage
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.imageio.ImageIO

data class ZipPart(
    val slika: BufferedImage,
    val tip: Tip
) {

    enum class Tip { NASLOV, TEORIJA, NALOGA }
}

class Zip {
    val images = mutableListOf<BufferedImage>()
    val parts = mutableListOf<ZipPart>()

    fun init(file: File) {
        val zipFile = ZipFile(file.absolutePath)
        val entries = zipFile.entries()

        while (entries.hasMoreElements()) {
            val zipEntry: ZipEntry = entries.nextElement()
            println(zipEntry)
            val inputStream = zipFile.getInputStream(zipEntry)
            val bufferedImage = ImageIO.read(inputStream).deskew()
            images.add(bufferedImage)
        }
        zipFile.close()
    }

    companion object {
        fun loadCheckpoint(file: File): Zip {
            val fos = FileInputStream(file)
            val oos = ObjectInputStream(fos)
            val obj = oos.readObject()

            if (obj is Zip) return obj
            else throw Error("FAIL")
        }
    }

    fun saveCheckpoint(file: File) {
        val fos = FileOutputStream(file)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(this)
    }

    fun process() {
        for (image in this.images) {
            this.parse(image)
        }
    }

    fun saveParts(file: File) {
        var i = 0
        val map = mutableMapOf<ZipPart.Tip, Int>()
        for (part in this.parts) {
            val savePath = File("part_${i++}.png")
            println("$savePath ${part.tip}")
            map[part.tip] = map.getOrDefault(part.tip, 0) + 1
            part.slika.save(savePath)
        }

        println("Report: ")
        for (mutableEntry in map) {
            print("\t> ${mutableEntry.key} = ${mutableEntry.value}")
        }
    }

    fun parse(slika: BufferedImage) {
        var y = 0
        while (y++ < slika.height - 1) {
            y = this.parseNaslov(y, slika)
            y = this.parseTeorija(y, slika)
            y = this.parseNaloga(y, slika)
        }
    }

    fun parseNaslov(y: Int, slika: BufferedImage): Int {

        var height = 0

        while (true) {
            val prvi_temno_rdeci = slika.dobiPrvega(y + height, 0, 300, Piksel::is_red_dark)
            val zadnji_temno_rdeci = slika.dobiZadnjega(y + height, 0, 300, Piksel::is_red_dark)

            if (prvi_temno_rdeci == -1 || zadnji_temno_rdeci == -1)
                break

            height++
            if (y + height >= slika.height) break
        }

        if (height > 40) {
            parts.add(ZipPart(slika.getSubimage(0, y, slika.width, height), tip = ZipPart.Tip.NASLOV))
        }

        return y + height + 10
    }

    fun parseTeorija(y: Int, slika: BufferedImage): Int {

        var height = 0

        while (true) {
            val prvi_light_other = slika.dobiPrvega(y + height, 0, 300, Piksel::is_other_light)
            val zadnji_light_other = slika.dobiZadnjega(y + height, 0, 300, Piksel::is_other_light)

            if (prvi_light_other == -1 || zadnji_light_other == -1)
                break

            height++
            if (y + height >= slika.height) break
        }

        if (height > 50) {
            parts.add(ZipPart(slika.getSubimage(0, y, slika.width, height), tip = ZipPart.Tip.TEORIJA))
        }

        return y + height + 10
    }

    fun parseNaloga(y: Int, slika: BufferedImage): Int {

        var height = 0

        while (true) {
            var prvi_temno_rdeci = slika.dobiPrvega(y + height, 0, 300, Piksel::is_red_dark)
            val zadnji_temno_rdeci = slika.dobiZadnjega(y + height, 0, 300, Piksel::is_red_dark)

            var prvi_crni = slika.dobiPrvega(y + height, 0, 300, Piksel::is_black)

            var prvi_other_dark = slika.dobiPrvega(y + height, 0, 300, Piksel::is_other_dark)
            var zadnji_dark_other = slika.dobiZadnjega(y + height, 0, 300, Piksel::is_other_dark)

            height++

            if (//Ce je konec naloge
                y + height >= slika.height - 1 ||
                //Temno rdeci jenajden in ter da je crni po rdecem in zadnji rdecega ni... ter ostalih ni
                (prvi_temno_rdeci in 50..300 && prvi_crni > prvi_temno_rdeci && zadnji_temno_rdeci == -1 && prvi_other_dark == -1 && zadnji_dark_other == -1)
            ) {
                //Ce je najden prvi pixel skoci naprej za 30px
                if (height == 1) {
                    height += 30
                    if (y + height > slika.height - 1) break
                }
                //Ce je najden drugi enak pixel potem je konec naloge...
                else {
                    parts.add(ZipPart(slika.getSubimage(0, y, slika.width, height), tip = ZipPart.Tip.NALOGA))
                    break
                }
            }

        }

        return y + height
    }

}
