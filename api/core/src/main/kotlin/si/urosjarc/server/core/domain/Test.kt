package si.urosjarc.server.core.domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


data class Test(
    @BsonId
    override var id: ObjectId? = null,
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
    val oseba_id: String,
) : Entiteta<Test>()

data class Status(
    @BsonId
    override var id: ObjectId? = null,
    val tip: Tip,
    val naloga_id: String,
    val test_id: String,
    val pojasnilo: String
) : Entiteta<Status>() {
    enum class Tip { USPEH, NEUSPEH }
}
