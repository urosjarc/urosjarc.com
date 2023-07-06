@file:OptIn(ExperimentalJsExport::class)

package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class Oseba(
    @Contextual override var _id: String? = null,
    val ime: String,
    val priimek: String,
    val username: String,
    val tip: Tip,
) : Entiteta {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN }
}

@JsExport
@Serializable
data class Kontakt(
    @Contextual override var _id: String? = null,
    @Contextual var oseba_id: String? = null,
    val data: String,
    val tip: Tip
) : Entiteta {
    enum class Tip { EMAIL, TELEFON }
}

@JsExport
@Serializable
data class Naslov(
    @Contextual override var _id: String? = null,
    @Contextual var oseba_id: String? = null,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta

@JsExport
@Serializable
data class Sporocilo(
    @Contextual override var _id: String? = null,
    @Contextual var kontakt_posiljatelj_id: String? = null,
    @Contextual var kontakt_prejemnik_id: String? = null,
    val vsebina: String,
) : Entiteta
