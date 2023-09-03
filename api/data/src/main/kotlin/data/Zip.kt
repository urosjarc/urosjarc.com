import data.Piksel
import data.extends.dobiPrvega
import data.extends.dobiZadnjega
import java.awt.BorderLayout
import java.awt.image.BufferedImage
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.WindowConstants

data class ZipPart(
    val slika: BufferedImage,
    val tip: Tip
) {

    enum class Tip { NASLOV, TEORIJA, NALOGA }
}

class Zip {
    val parts = mutableListOf<ZipPart>()


    fun init(file: File) {
        val zipFile = ZipFile(file.absolutePath)
        val entries = zipFile.entries()

        while (entries.hasMoreElements()) {
            val zipEntry: ZipEntry = entries.nextElement()
            val inputStream = zipFile.getInputStream(zipEntry)
            val bufferedImage = ImageIO.read(inputStream)
            this.parse(bufferedImage)
            break
        }
        zipFile.close()
    }

    fun parse(slika: BufferedImage) {
        var y = 0
        var y_active = -1
        while (y < slika.height - 1) {
            val prvi_temno_rdeci = slika.dobiPrvega(y, 300, Piksel::is_red_dark)
            val prvi_svetlo_rdeci = slika.dobiPrvega(y, 300, Piksel::is_red_light)
            val zadnji_temno_rdeci = slika.dobiZadnjega(y, 300, Piksel::is_red_dark)
            val zadnji_svetlo_rdeci = slika.dobiZadnjega(y, 300, Piksel::is_red_light)

            //Preverjanje za tematika naslov
            y_active = -1
            while (prvi_temno_rdeci in 50..200 && zadnji_temno_rdeci in 1600..1750) y_active++
            if (y_active > 0) {
                parts.add(ZipPart(slika.getSubimage(0, y, slika.width, y_active), tip=ZipPart.Tip.NASLOV))
                y += y_active
            }

            //Preverjanje za tematika naslov
            y_active = -1
            while (prvi_svetlo_rdeci in 50..200 && zadnji_svetlo_rdeci in 1600..1750) y_active++
            if (y_active > 0) {
                parts.add(ZipPart(slika.getSubimage(0, y, slika.width, y_active), tip=ZipPart.Tip.TEORIJA))
                y += y_active
            }

            //TODO: Preverjanje za nalogo...


        }
    }

    fun display(image: BufferedImage) {
        val frame = JFrame().apply {
            this.setTitle("stained_image")
            this.setSize(image.getWidth(), image.getHeight())
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        }
        val label = JLabel().apply {
            this.setIcon(ImageIcon(image))
        }
        frame.apply {
            this.getContentPane().add(label, BorderLayout.CENTER)
            this.setLocationRelativeTo(null)
            this.pack()
            this.setVisible(true)
        }

        label.setIcon(ImageIcon(image))
    }
}
