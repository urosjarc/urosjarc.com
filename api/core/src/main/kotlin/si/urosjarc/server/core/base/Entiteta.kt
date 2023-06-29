package si.urosjarc.server.core.base

import io.github.serpro69.kfaker.Faker
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.extend.zdaj
import si.urosjarc.server.core.extend.danes

val fake = Faker()
var counters = mutableMapOf<String, Int>()

@Serializable
abstract class Entiteta<T> {
    abstract val id: Id<T>
    val _otroci = mutableListOf<String>()

    fun enak(entiteta: Entiteta<T>): Boolean = this.id == entiteta.id

    inline fun <reified T : Any> dodaj_otroka(id: Id<T>) {
        this._otroci.add("${ime<T>()}_${id.value}")
    }

    inline fun <reified T : Any> otroci(): List<Id<T>> = this._otroci
        .filter { it.startsWith("${ime<T>()}_") }
        .map { Id(it.split("_").last()) }


    companion object {

        inline fun <reified T : Any> nakljucni(): T {
            val obj = fake.randomProvider.randomClassInstance<T> {
                this.typeGenerator<Id<T>> { Id.new() }
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
