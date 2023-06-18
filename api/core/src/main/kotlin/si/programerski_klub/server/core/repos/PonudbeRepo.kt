package si.programerski_klub.server.core.repos

import si.programerski_klub.server.core.domain.placevanje.Ponudba

interface PonudbeRepo {
    fun vse(): List<Ponudba>
    fun shrani(ponudba: Ponudba): DbRezultatShranitve<Ponudba>
}
