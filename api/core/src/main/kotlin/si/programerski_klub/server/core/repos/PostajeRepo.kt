package si.programerski_klub.server.core.repos

import org.litote.kmongo.Id
import si.programerski_klub.server.core.domain.napredovanje.Postaja

interface PostajeRepo {
    fun shrani(postaja: Postaja): DbRezultatShranitve<Postaja>
    fun shrani(postaje: List<Postaja>): DbRezultatShranitve<List<Postaja>>
    fun zacetne(): List<Postaja>
    fun dedici(id: Id<Postaja>): DbRezultatIskanja<List<Postaja>>
    fun vse(ids: Set<Id<Postaja>>): DbRezultatId<List<Postaja>>
    fun ena(id: Id<Postaja>): DbRezultatId<Postaja>
    fun naslednje(id: Id<Postaja>): DbRezultatId<List<Postaja>>
    fun izbrisi(ids: List<Id<Postaja>>): DbRezultatIzbrisa
}
