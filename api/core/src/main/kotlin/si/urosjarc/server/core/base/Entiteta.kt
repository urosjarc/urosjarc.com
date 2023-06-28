package si.urosjarc.server.core.base

import io.github.serpro69.kfaker.Faker
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.extends.name
import si.urosjarc.server.core.extends.now
import si.urosjarc.server.core.extends.today

val fake = Faker()
var counters = mutableMapOf<String, Int>()

@Serializable
abstract class Entiteta<T> {
    abstract val id: Id<T>
    val _otroci = mutableListOf<String>()

    fun enak(entiteta: Entiteta<T>): Boolean = this.id == entiteta.id

    inline fun <reified T : Any> dodaj_otroka(id: Id<T>) {
        this._otroci.add("${name<T>()}_${id.value}")
    }

    inline fun <reified T : Any> otroci(): List<Id<T>> = this._otroci
        .filter { it.startsWith("${name<T>()}_") }
        .map { Id(it.split("_").last()) }


    companion object {

        inline fun <reified T : Any> random(): T {
            val obj = fake.randomProvider.randomClassInstance<T> {
                this.typeGenerator<Id<T>> { Id.new() }
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
