package si.programerski_klub.server.core.base

import io.github.serpro69.kfaker.Faker
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.extend.now
import si.programerski_klub.server.core.extend.today

val fake = Faker()
var counters = mutableMapOf<String, Int>()

interface Entiteta<T> {
    fun enak(entiteta: T): Boolean = this == entiteta

    companion object {

        inline fun <reified T : Any> random(): T {
            val obj = fake.randomProvider.randomClassInstance<T> {
                this.typeGenerator<Id<T>> { newId() }
                this.typeGenerator<List<Id<T>>> { listOf() }
                this.typeGenerator<MutableSet<T>> { mutableSetOf() }
                this.typeGenerator<LocalDate> { LocalDate.today() }
                this.typeGenerator<LocalDateTime> { LocalDateTime.now() }
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
