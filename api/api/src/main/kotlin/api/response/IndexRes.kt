package api.response

import api.request.NapakaReq
import data.*
import domain.*
import kotlinx.serialization.Serializable
import si.urosjarc.server.api.response.*

@Serializable
data class IndexRes(
    val a1: KontaktObrazecReq,
    val a2: NapakaReq,
    val a3: PrijavaReq,
    val a4: StatusUpdateReq,
    val a5: TestUpdateReq,
    val a6: ErrorRes,
    val a7: KontaktObrazecRes,

    val a8: PrijavaRes,
    val a9: KontaktData,
    val b1: NalogaData,
    val b2: OsebaData,
    val b3: SporociloData,
    val b4: StatusData,
    val b5: TestData,
    val b6: UcenecData,
    val b7: AdminData,
    val b8: UcenjeData,

    val b9: Audit,
    val c1: Entiteta<String>,
    val c2: Kontakt,
    val c3: Naloga,
    val c4: Napaka,
    val c5: Naslov,
    val c7: Oseba,
    val c8: Sporocilo,
    val c9: Status,
    val d1: Tematika,
    val d2: Test,
    val d3: Ucenje,
    val d4: Zvezek
)
