package data.use_cases

import data.domain.ZipSlika
import java.io.File
import java.util.zip.ZipFile
import javax.imageio.ImageIO

class Najdi_vse_zip_slike {

    fun zdaj(file: File, start: Int = 0, end: Int? = null): Sequence<ZipSlika> {

        return sequence {
            val zipFile = ZipFile(file.absolutePath)
            val entries = zipFile.entries().toList()
            val endSafe = listOf(end ?: entries.size, entries.size).min()

            for (i in start - 1 until endSafe) {
                if (i < start) continue

                val inputStream = zipFile.getInputStream(entries[i])
                val bufferedImage = ImageIO.read(inputStream)

                yield(ZipSlika(img = bufferedImage, i, entries.size))
            }
            zipFile.close()
        }

    }
}
