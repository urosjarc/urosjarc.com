package use_cases

import base.AnyId
import base.Id
import domain.Naloga
import domain.Tematika
import domain.Teorija
import domain.Zvezek
import extend.encrypted
import org.bson.types.ObjectId
import services.DbService
import java.io.File


class Sinhroniziraj_bazo_zvezkov(
    private val db: DbService,
) {
    private fun addId(file: File): ObjectId {
        val idFile = File(file, "_id.txt")
        val id = AnyId().value
        return if (!idFile.exists()) {
            idFile.writeText(id.toHexString())
            id
        } else ObjectId(idFile.readText())
    }

    fun zdaj() {
        val resourceFile = File("../data/src/main/resources")

        val zvezki = mutableListOf<Zvezek>()
        val tematike = mutableListOf<Tematika>()
        val naloge = mutableListOf<Naloga>()
        val teorije = mutableListOf<Teorija>()

        for (zvezek in resourceFile.listFiles()!!) {

            if (!zvezek.isDirectory) continue
            val zvezek_id = Id<Zvezek>(addId(zvezek))
            zvezki.add(Zvezek(_id = zvezek_id, naslov = zvezek.name.encrypted()))

            for (tematika in zvezek.listFiles()!!) {

                if (tematika.name == "resitve") continue
                if (!tematika.isDirectory) continue
                val tematika_id = Id<Tematika>(addId(tematika))
                tematike.add(Tematika(_id = tematika_id, zvezek_id = zvezek_id, naslov = tematika.name.encrypted()))

                println(tematika)

                for (naloga in tematika.listFiles()!!) {


                    if (!naloga.isDirectory) continue
                    val metaVsebina = File(naloga, "vsebina.txt").readText()
                    if (naloga.name.startsWith("teorija")) {
                        val teorija_id = Id<Teorija>(addId(naloga))
                        teorije.add(
                            Teorija(
                                _id = teorija_id,
                                tip = Teorija.Tip.SLIKA, //Todo: Make this better!
                                tematika_id = tematika_id,
                                vsebina = "/${zvezek.name}/${tematika.name}/${naloga.name}/vsebina.png".encrypted(),
                                meta = metaVsebina.encrypted()
                            )
                        )
                    } else {
                        val naloga_id = Id<Naloga>(addId(naloga))
                        naloge.add(
                            Naloga(
                                _id = naloga_id,
                                tip = Naloga.Tip.SLIKA, //Todo: Make this better!
                                tematika_id = tematika_id,
                                vsebina = "/${zvezek.name}/${tematika.name}/${naloga.name}/vsebina.png".encrypted(),
                                resitev = "/${zvezek.name}/resitve/${naloga.name}/resitev.png".encrypted(),
                                meta = metaVsebina.encrypted()
                            )
                        )
                    }
                }
            }
        }

        db.zvezki.drop()
        db.tematike.drop()
        db.naloge.drop()
        db.teorije.drop()

        db.ustvari(zvezki)
        db.ustvari(tematike)
        db.ustvari(naloge)
        db.ustvari(teorije)
    }
}
