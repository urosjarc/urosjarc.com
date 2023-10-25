package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.domain.Anotacija
import gui.domain.Okvir
import gui.domain.Stran
import gui.extend.okvirji
import gui.extend.vOkvirju
import gui.extend.vektor
import gui.services.OcrService
import gui.use_cases.Anotiraj_omego_stran
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.image.BufferedImage

abstract class Anotiranje_slike_Ui : KoinComponent {
    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    val IMG: ImageView_BufferedImage get() = this.imageView_bufferedImage_Controller
}

open class Anotiranje_slike : Anotiranje_slike_Ui() {
    val anotiraj_omego_stran by this.inject<Anotiraj_omego_stran>()
    val ocrService by this.inject<OcrService>()

    val contextMenu = ContextMenu()
    var dragOkvir = Okvir.PRAZEN
    var dragRectangle: Rectangle = Rectangle()

    lateinit var slika: BufferedImage
    lateinit var anotacije: List<Anotacija>
    var stran = Stran.PRAZNA
    var userOkvirji = listOf<Okvir>()

    @FXML
    fun initialize() {
        println("init Anotiranje_zip_slike")

        // Popravi anotacije ce se slika zoomira
        this.IMG.zoom.opazuj { this.na_novo_narisi_anotacije_v_ozadju() }
        this.IMG.self.setOnMousePressed { this.dragOkvir.start = it.vektor }
        this.IMG.self.setOnMouseReleased { this.onMouseReleased(me = it) }
        this.IMG.self.setOnMouseDragged { this.onMouseDragg(me = it) }
        this.resetirajB.setOnAction { this.anotiraj_stran_in_na_novo_narisi_anotacije() }

        // Dodajanje tipov anotacij v context menu slike
        Anotacija.Tip.entries.forEach {
            val menuItem1 = MenuItem(it.name)
            menuItem1.userData = it
            this.contextMenu.items.add(menuItem1)
        }

        // Ko se izbere context menu shrani izbrane anotacije in jih prikazi na strani.
        this.contextMenu.setOnAction {
            val tip = (it.target as MenuItem).userData as Anotacija.Tip
            val okvir = this.IMG.vOkvir(r = this.dragRectangle)
            when (tip) {
                Anotacija.Tip.NEZNANO -> {
                    this.stran.odstrani(this.userOkvirji)
                    this.stran.dodatno.removeIf { okvir.vsebuje(it) }
                }
                Anotacija.Tip.DODATNO -> this.stran.dodatno.add(okvir)
                else -> this.stran.dodaj(okvirji = this.userOkvirji, tip = tip)
            }
            this.userOkvirji = listOf()
            this.na_novo_narisi_anotacije_v_ozadju()
        }
    }

    fun init(slika: BufferedImage) {
        this.slika = slika
        this.anotacije = this.ocrService.google(image = slika)
        this.IMG.init(slika = slika)
        this.anotiraj_stran_in_na_novo_narisi_anotacije()
    }

    open fun anotiraj_stran_in_na_novo_narisi_anotacije() {
        this.stran = this.anotiraj_omego_stran.zdaj(img = this.slika, anotacije = this.anotacije)
        this.na_novo_narisi_anotacije_v_ozadju()
    }

    fun na_novo_narisi_anotacije_v_ozadju() {
        this.IMG.pobrisiOzadje()
        this.stran.let { stran ->
            stran.noga.forEach { this.IMG.narisi_okvir(it, Color.BLACK) }
            stran.naloge.forEach { this.IMG.narisi_okvir(it, Color.GREEN) }
            stran.podnaloge.forEach { this.IMG.narisi_okvir(it, Color.MAGENTA) }
            stran.naslov.forEach { this.IMG.narisi_okvir(it, Color.BLUE) }
            stran.glava.forEach { this.IMG.narisi_okvir(it, Color.BLACK) }
            stran.teorija.forEach { this.IMG.narisi_okvir(it, Color.RED) }
            stran.dodatno.forEach { this.IMG.narisi_okvir(it, Color.GREEN) }
        }
        this.userOkvirji.forEach { this.IMG.narisi_okvir(it, Color.MAGENTA) }
    }

    private fun onMouseReleased(me: MouseEvent) {
        val dragOkvirSlike = this.IMG.vOkvir(r = this.dragRectangle)
        this.userOkvirji = this.stran.anotacije.vOkvirju(okvir = dragOkvirSlike).okvirji
        this.na_novo_narisi_anotacije_v_ozadju()
        this.IMG.backgroundP.children.add(this.dragRectangle)
        this.contextMenu.show(this.IMG.self, me.screenX, me.screenY)
    }

    private fun onMouseDragg(me: MouseEvent) {
        this.IMG.backgroundP.children.remove(this.dragRectangle)
        this.dragOkvir.end = me.vektor
        this.dragRectangle = this.dragOkvir.vRectangle(color = Color.RED)
        this.IMG.backgroundP.children.add(this.dragRectangle)
    }

}
