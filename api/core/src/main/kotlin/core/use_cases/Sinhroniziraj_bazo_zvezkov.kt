package core.use_cases

import core.base.AnyId
import core.base.Id
import core.domain.Naloga
import core.domain.Tematika
import core.domain.Teorija
import core.domain.Zvezek
import core.extend.encrypted
import core.services.DbService
import org.bson.types.ObjectId
import java.io.File
import java.io.FileFilter


/**
 * /zvezek
 *      /tematika
 *          /naloga0
 *              vsebina.txt
 *              vsebina.{png|webm}
 *          /naloga1
 *          ...
 *          /teorija
 *              vsebina.txt
 *              vsebina.{png|webm}
 *          /teorija_0
 *              vsebina.txt
 *              vsebina.{png|webm}
 *      /resitve
 *          /naloga0
 *              resitev.txt
 *              resitev.png
 *          /naloga1
 *          ...
 */
class Sinhroniziraj_bazo_zvezkov(
    private val db: DbService,
) {

    private val TEORIJA = "teorija"
    private val META_EXT = "txt"
    private val META = "vsebina.$META_EXT"
    private val RESITVE = "resitve"
    private val RESITEV = "resitev.png"

    private fun addId(file: File): ObjectId {
        val idFile = File(file, "_id.txt")
        val id = AnyId().value
        return if (!idFile.exists()) {
            idFile.writeText(id.toHexString())
            id
        } else ObjectId(idFile.readText())
    }

    private fun list(file: File, filter: (file: File) -> Boolean): Array<out File> {
        val files = file.listFiles(FileFilter(filter))
        if (files == null || files.isEmpty()) throw Error("Empty directory: $file")
        return files
    }

    fun zdaj(): Boolean {
        val resourceFile = File("../data/src/main/resources")

        val zvezki = mutableListOf<Zvezek>()
        val tematike = mutableListOf<Tematika>()
        val naloge = mutableListOf<Naloga>()
        val teorije = mutableListOf<Teorija>()

        /**
         * Zvezki loop
         * resitve dir mora obstajati
         */
        for (zvezek in list(resourceFile) { it.isDirectory }) {
            if (!File(zvezek, RESITVE).exists()) return false

            val zvezek_id = Id<Zvezek>(addId(zvezek))
            zvezki.add(Zvezek(_id = zvezek_id, naslov = zvezek.name.encrypted()))

            /**
             * Tematika loop
             * teorija dir mora obstajati
             */
            for (tematika in list(zvezek) { it.name != RESITVE && it.isDirectory }) {
                if (!File(tematika, TEORIJA).exists()) throw Error("Dir $TEORIJA ne obstaja v: $tematika")

                val tematika_id = Id<Tematika>(addId(tematika))
                tematike.add(Tematika(_id = tematika_id, zvezek_id = zvezek_id, naslov = tematika.name.encrypted()))

                /**
                 * Naloga loop
                 * meta file mora obstajati
                 */
                for (naloga in list(tematika) { it.isDirectory }) {

                    val metaVsebina = File(naloga, META).readText().encrypted()

                    /**
                     * Naloga file loop
                     */
                    for (nalogaFile in list(naloga) { it.extension != META_EXT }) {

                        val vsebina = nalogaFile.toRelativeString(resourceFile)
                        val vsebinaEncrypted = vsebina.encrypted()

                        if (naloga.name.startsWith(TEORIJA)) {
                            teorije.add(
                                Teorija(
                                    _id = Id(addId(naloga)),
                                    tematika_id = tematika_id,
                                    vsebina = vsebinaEncrypted,
                                    meta = metaVsebina
                                )
                            )
                        } else {
                            /**
                             * Resitev mora obstajati
                             */
                            val resitevFile = File(zvezek, "$RESITVE/${naloga.name}/$RESITEV")
                            if (!resitevFile.exists()) throw Exception(resitevFile.toString())
                            val resitev = resitevFile.toRelativeString(resourceFile)

                            naloge.add(
                                Naloga(
                                    _id = Id(addId(naloga)),
                                    tematika_id = tematika_id,
                                    vsebina = vsebinaEncrypted,
                                    resitev = resitev.encrypted(),
                                    meta = metaVsebina
                                )
                            )
                        }
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

        return true
    }
}
