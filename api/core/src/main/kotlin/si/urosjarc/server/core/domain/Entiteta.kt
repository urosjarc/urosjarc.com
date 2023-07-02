package si.urosjarc.server.core.domain

import io.github.serpro69.kfaker.Faker
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import si.urosjarc.server.core.extend.danes
import si.urosjarc.server.core.extend.zdaj
import si.urosjarc.server.core.serializers.ObjectIdSerializer

val fake = Faker()
var counters = mutableMapOf<String, Int>()

@Serializable
sealed class Entiteta<T> {
    @Serializable(with = ObjectIdSerializer::class)
    abstract var id: ObjectId?

    companion object {
        inline fun <reified T : Any> nakljucni(): T {
            val obj = fake.randomProvider.randomClassInstance<T> {
                this.typeGenerator<MutableSet<T>> { mutableSetOf() }
                this.typeGenerator<LocalDate> { LocalDate.danes() }
                this.typeGenerator<LocalDateTime> { LocalDateTime.zdaj() }
                this.typeGenerator<String> { pInfo ->
                    val value = counters.getOrDefault(pInfo.name, -1) + 1
                    counters[pInfo.name] = value
                    "${pInfo.name}_${value}"
                }
            }
            return obj
        }

    }
}
