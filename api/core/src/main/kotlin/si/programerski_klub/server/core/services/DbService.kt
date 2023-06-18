package si.programerski_klub.server.core.services

import si.programerski_klub.server.core.repos.*

interface DbService {
    //ADMIN
    fun izbrisi_vse(): DbRezultatIzbrisa

    val kontaktni_obrazec: KontaktniObrazecRepo
    val naloge: NalogeRepo
    val narocila: NarocilaRepo
    val narocnine: NarocnineRepo
    val osebe: OsebeRepo
    val ponudbe: PonudbeRepo
    val postaje: PostajeRepo
    val produkti: ProduktiRepo
    val programi: ProgramiRepo
    val testi: TestiRepo

}
