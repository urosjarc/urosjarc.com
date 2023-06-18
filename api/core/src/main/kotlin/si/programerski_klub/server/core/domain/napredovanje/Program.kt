package si.programerski_klub.server.core.domain.napredovanje

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta
import si.programerski_klub.server.core.domain.placevanje.Produkt
import si.programerski_klub.server.core.domain.uprava.Oseba

@Serializable
data class Program(
    @SerialName("_id")
    @Contextual override val id: Id<Program> = newId(),
    @Contextual val produkt: Id<Produkt>,
    @Contextual val uporabnik: Id<Oseba>,
    @Contextual val zacetek: Id<Postaja>,
    @Contextual val checkpoint: Id<Postaja>,
    val opravljeno: MutableSet<@Contextual Id<Postaja>> = mutableSetOf()
) : UnikatnaEntiteta<Program>()
