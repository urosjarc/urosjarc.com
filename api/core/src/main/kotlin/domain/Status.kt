package domain

import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    override var _id: Id<Status> = Id(),
    var naloga_id: Id<Naloga>,
    var test_id: Id<Test>,
    var oseba_id: Id<Oseba>,
    val tip: Tip,
    val pojasnilo: String
) : Entiteta<Status> {
    enum class Tip { NERESENO, NAPACNO, PRAVILNO }
}
