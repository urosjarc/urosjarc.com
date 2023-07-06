package si.urosjarc.server.core.repos

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.domain.*

@Serializable
data class OsebaData(
    val oseba: Oseba,
    val naslov_refs: List<Naslov> = listOf(),
    val ucenje_ucitelj_refs: List<UcenjeData> = listOf(),
    val kontakt_refs: List<KontaktData> = listOf(),
    val test_refs: List<TestData> = listOf()
)


@Serializable
data class TestData(
    val test: Test,
    val status_refs: List<Status> = listOf()
) {
    fun opravljeno(): Float {
        var count = 0f
        for (status in status_refs) {
            if (status.tip == Status.Tip.USPEH) {
                count += 1
            }
        }
        return count / status_refs.size
    }
}

@Serializable
data class KontaktData(
    val kontakt: Kontakt,
    val oseba_refs: List<Oseba> = listOf(),
    val sporocilo_prejemnik_refs: List<SporociloData> = listOf(),
    val sporocilo_posiljatelj_refs: List<SporociloData> = listOf()
)

@Serializable
data class SporociloData(
    val sporocilo: Sporocilo,
    val kontakt_refs: List<KontaktData> = listOf()
)

@Serializable
data class UcenjeData(
    val ucenje: Ucenje,
    val oseba_refs: List<Oseba> = listOf(),
    val ucenje_ucenec_refs: List<UcenjeData> = listOf(),
)
