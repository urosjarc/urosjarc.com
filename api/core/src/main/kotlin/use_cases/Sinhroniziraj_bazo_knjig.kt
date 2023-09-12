package use_cases

import domain.Naloga
import domain.Tematika
import domain.Zvezek
import extend.encrypted
import services.DbService
import services.FtpService


class Sinhroniziraj_bazo_knjig(
    private val db: DbService,
    private val ftp: FtpService
) {

    fun zdaj() {
        val zvezki = mutableMapOf<String, MutableMap<String, MutableSet<Int>>>()
        for (file in this.ftp.walk()) {
            val fileInfo = file.split('/')
            if (fileInfo.size != 5) continue
            val zvezekNaslov = fileInfo[1]
            val tematikaNaslov = fileInfo[2]
            val stevilka = fileInfo[3].toInt()

            zvezki.putIfAbsent(zvezekNaslov, mutableMapOf())
            zvezki[zvezekNaslov]!!.putIfAbsent(tematikaNaslov, mutableSetOf())
            zvezki[zvezekNaslov]!![tematikaNaslov]!!.add(stevilka)
        }

        val zvezkiObj = mutableListOf<Zvezek>()
        val tematikeObj = mutableListOf<Tematika>()
        val nalogeObj = mutableListOf<Naloga>()

        for ((zvezekNaslov, tematikaMap) in zvezki.entries) {
            val zvezek = Zvezek(tip = Zvezek.Tip.DELOVNI, naslov = zvezekNaslov.encrypted())
            println("Zvezek: $zvezekNaslov")
            zvezkiObj.add(zvezek)
            for ((tematikaNaslov, nalogaMap) in tematikaMap.entries) {
                val tematika = Tematika(naslov = zvezekNaslov.encrypted(), zvezek_id = zvezek._id)
                println("Tematika: $tematikaNaslov")
                tematikeObj.add(tematika)
                for (nalogaSt in nalogaMap) {
                    val naloga = Naloga(
                        tematika_id = tematika._id,
                        resitev_img = "/$zvezekNaslov/resitev/$nalogaSt/resitev.png".encrypted(),
                        resitev_txt = "".encrypted(),
                        vsebina_img = "/$zvezekNaslov/$tematikaNaslov/$nalogaSt/vsebina.png".encrypted(),
                        vsebina_txt = "".encrypted(),
                    )
                    nalogeObj.add(naloga)
                }
            }
        }

        println("Seed zvezki...")
        db.ustvari(zvezkiObj)

        println("Seed tematike...")
        db.ustvari(tematikeObj)

        println("Seed naloge...")
        db.ustvari(nalogeObj)

    }
}
