package si.urosjarc.server.core.domain.delovni_zvezki

data class Tematika(
    val naslov: String,
    val naloge: MutableSet<Naloga> = mutableSetOf()
)
