package data

import com.recognition.software.jdeskew.ImageDeskew
import net.sourceforge.tess4j.util.ImageHelper
import java.awt.BorderLayout
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel

fun BufferedImage.show() {
    val frame = JFrame().apply {
        this.title = "stained_image"
        this.setSize(this.width, this.height)
        this.defaultCloseOperation = javax.swing.WindowConstants.EXIT_ON_CLOSE
    }
    val label = JLabel().apply {
        this.icon = ImageIcon(this@show)
    }
    frame.apply {
        this.contentPane.add(label, BorderLayout.CENTER)
        this.setLocationRelativeTo(null)
        this.pack()
        this.isVisible = true
    }

    label.icon = ImageIcon(this)
}

fun BufferedImage.save(file: File) {
    ImageIO.write(this, "png", file)
}

fun BufferedImage.deskew(): BufferedImage {
    val imgdeskew = ImageDeskew(this) // BufferedImage img
    println("Image rotated: ${imgdeskew.skewAngle} deg")
    return ImageHelper.rotateImage(this, -imgdeskew.skewAngle) // rotateImage static method
}
