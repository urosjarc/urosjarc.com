package si.urosjarc.server.core.repos

import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.MongoCollection
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.bson.types.ObjectId
import si.urosjarc.server.core.domain.*
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.serializers.ObjectIdSerializer


class OsebaRepo(val collection: MongoCollection<Oseba>) {

    @Serializable
    data class Profil(
        val kontakti: List<Kontakt>,
        val naslovi: List<Naslov>,
        val ucitelji: List<Ucenje>,
        val ucenci: List<Ucenje>,
    )

    fun profil(id: ObjectId) {
        val resultsFlow = collection.aggregate<Profil>(
            listOf(
                Aggregates.match(Filters.eq("_id", id)),
                Aggregates.lookup(ime<Kontakt>(), "_id", "oseba_id", "kontakti"),
                Aggregates.lookup(ime<Naslov>(), "_id", "oseba_id", "naslovi"),
                Aggregates.lookup(ime<Ucenje>(), "_id", "oseba_ucenec_id", "ucitelji"),
                Aggregates.lookup(ime<Ucenje>(), "_id", "oseba_ucitelj_id", "ucenci"),
//                Aggregates.unwind("\$testi"),
//                Aggregates.lookup(ime<Test>(), "_id", "oseba_id", "testi.test"),
            )
        )
        val json = Json(builderAction = {
            serializersModule = SerializersModule {
                contextual(ObjectIdSerializer)
            }
            this.prettyPrint = true
        })
        resultsFlow.forEach {
            println(json.encodeToString(it))
        }
    }
}
