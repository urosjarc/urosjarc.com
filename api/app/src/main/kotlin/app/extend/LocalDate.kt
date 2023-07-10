package app.extend

import kotlinx.datetime.*

fun LocalDateTime.Companion.zdaj(): LocalDateTime = Clock.System.now().toLocalDateTime(timeZone = TimeZone.UTC)
fun LocalDate.Companion.danes(dDni: Int = 0): LocalDate {
    return LocalDateTime.zdaj().date.plus(period = DatePeriod(days = dDni))
}
