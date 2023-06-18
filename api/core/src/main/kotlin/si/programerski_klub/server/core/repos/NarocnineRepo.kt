package si.programerski_klub.server.core.repos

import org.litote.kmongo.Id
import si.programerski_klub.server.core.domain.placevanje.Narocnina
import si.programerski_klub.server.core.domain.placevanje.Produkt
import si.programerski_klub.server.core.domain.uprava.Oseba

interface NarocnineRepo {
    fun vse(id: Id<Oseba>): List<Narocnina>
    fun ena(id: Id<Narocnina>): DbRezultatId<Narocnina>
    fun ena(id: Id<Oseba>, tip: Produkt.Tip): DbRezultatIskanja<Narocnina>
    fun shrani(narocnine: Narocnina): DbRezultatShranitve<Narocnina>
}
