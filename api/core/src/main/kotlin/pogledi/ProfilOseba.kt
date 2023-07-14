//package pogledi
//
//import data.OsebaData
//import domain.Audit
//import extends.danes
//import kotlinx.datetime.LocalDate
//import kotlinx.datetime.daysUntil
//import kotlinx.datetime.toLocalDateTime
//import kotlinx.serialization.Serializable
//
//@Serializable
//data class ProfilOseba(
//    val key: String, val value: String
//) {
//    companion object {
//        fun new(osebaData: OsebaData): List<ProfilOseba> {
//            val oseba = osebaData.oseba
//            return mutableListOf<ProfilOseba>().apply {
//                this.add(ProfilOseba("Oseba", oseba.tip.name))
//                this.add(ProfilOseba("Naziv", "${oseba.ime} ${oseba.priimek}"))
//                osebaData.naslov_refs.forEachIndexed { index, naslov ->
//                    this.add(ProfilOseba("${index + 1}. Naslov", "${naslov.ulica} ${naslov.zip} ${naslov.mesto}"))
//                }
//                osebaData.kontakt_refs.forEachIndexed { index, kontaktData ->
//                    this.add(ProfilOseba("${index + 1}. ${kontaktData.kontakt.tip}", kontaktData.kontakt.data))
//                }
//            }
//        }
//
//    }
//}
//
//@Serializable
//data class ProfilOsebaAudits(
//    val datum: String,
//    val dni_nazaj: Int,
//    var st_aktivnosti: Int,
//    var skupno_trajanje: Int
//) {
//    companion object {
//        fun decode(audits: List<Audit>): List<ProfilOsebaAudits> {
//            return mutableMapOf<LocalDate, ProfilOsebaAudits>().also { map ->
//                val danes = LocalDate.danes()
//
//                audits.forEach { audit ->
//
//                    val ustvarjen = audit.ustvarjeno.toString().toLocalDateTime().date
//
//                    map.getOrPut(ustvarjen) {
//                        ProfilOsebaAudits(
//                            datum = ustvarjen.toString(),
//                            dni_nazaj = danes.daysUntil(ustvarjen),
//                            st_aktivnosti = 0,
//                            skupno_trajanje = 0
//                        )
//                    }.apply {
//                        this.st_aktivnosti++
//                        this.skupno_trajanje += audit.trajanje
//                    }
//
//                }
//            }.values.toTypedArray()
//        }
//
//    }
//}
