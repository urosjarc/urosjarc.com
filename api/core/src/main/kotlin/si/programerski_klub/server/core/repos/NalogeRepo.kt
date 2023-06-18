package si.programerski_klub.server.core.repos

import si.programerski_klub.server.core.domain.napredovanje.Naloga


interface NalogeRepo {
    fun shrani(naloga: Naloga): DbRezultatShranitve<Naloga>
}
