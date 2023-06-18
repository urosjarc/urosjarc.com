package si.programerski_klub.server.core.repos

import org.litote.kmongo.Id
import si.programerski_klub.server.core.domain.obvescanje.KontaktniObrazec

interface KontaktniObrazecRepo {
    fun en(id: Id<KontaktniObrazec>): DbRezultatId<KontaktniObrazec>
    fun shrani(kontaktni_obrazec: KontaktniObrazec): DbRezultatShranitve<KontaktniObrazec>
}
