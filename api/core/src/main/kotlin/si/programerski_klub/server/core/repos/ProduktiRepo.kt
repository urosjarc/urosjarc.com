package si.programerski_klub.server.core.repos

import org.litote.kmongo.Id
import si.programerski_klub.server.core.domain.placevanje.Ponudba
import si.programerski_klub.server.core.domain.placevanje.Produkt

interface ProduktiRepo {
    fun en(id: Id<Produkt>): DbRezultatId<Produkt>
    fun vsi(id: Id<Ponudba>): DbRezultatId<List<Produkt>>
    fun vsi(tip: Produkt.Tip): List<Produkt>
    fun shrani(produkt: Produkt): DbRezultatShranitve<Produkt>
}
