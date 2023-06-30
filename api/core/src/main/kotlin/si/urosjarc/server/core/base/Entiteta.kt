package si.urosjarc.server.core.base

import io.github.serpro69.kfaker.Faker
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.extend.danes
import si.urosjarc.server.core.extend.zdaj

val fake = Faker()
var counters = mutableMapOf<String, Int>()

@Serializable
abstract class Entiteta<T> {
    abstract val id: Id<T>

    fun enak(entiteta: Entiteta<T>): Boolean = this.id == entiteta.id

    companion object {

        inline fun <reified T : Any> nakljucni(): T {
            val obj = fake.randomProvider.randomClassInstance<T> {
                this.typeGenerator<Id<T>> { pInfo ->
                    val value = counters.getOrDefault(pInfo.name, -1) + 1
                    counters[pInfo.name] = value
                    Id(value=value)
                }
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
