package si.urosjarc.server.core.extend

import kotlinx.datetime.*

fun LocalDate.starost(): Float {
    val diff = (LocalDate.danes() - this)
    return diff.years + diff.days / 365.25f
}
fun LocalDate.mladoletnik(): Boolean = this.starost() < 18.0f
fun LocalDateTime.Companion.zdaj(): LocalDateTime = Clock.System.now().toLocalDateTime(timeZone = TimeZone.UTC)
fun LocalDate.Companion.danes(): LocalDate = LocalDateTime.zdaj().date
