package si.urosjarc.server.core.extends

import kotlinx.datetime.*

fun LocalDate.starost(): Float {
    val diff = (LocalDate.today() - this)
    return diff.years + diff.days / 365.25f
}
fun LocalDate.mladoletnik(): Boolean = this.starost() < 18.0f
fun LocalDateTime.Companion.now(): LocalDateTime = Clock.System.now().toLocalDateTime(timeZone = TimeZone.UTC)
fun LocalDate.Companion.today(): LocalDate = LocalDateTime.now().date
