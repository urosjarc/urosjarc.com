package si.urosjarc.server.core.domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import si.urosjarc.server.core.base.Id


@Serializable
data class Test(
    @BsonId @Contextual override var id: Id<Test> = Id(),
    @BsonId @Contextual val oseba_id: Id<Oseba>,
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
) : Entiteta<Test>()

@Serializable
data class Status(
    @BsonId @Contextual override var id: Id<Status> = Id(),
    @BsonId @Contextual val naloga_id: Id<Naloga>,
    @BsonId @Contextual val test_id: Id<Test>,
    val tip: Tip,
    val pojasnilo: String
) : Entiteta<Status>() {
    enum class Tip { USPEH, NEUSPEH }
}
