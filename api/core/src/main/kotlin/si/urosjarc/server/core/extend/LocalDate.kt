package si.urosjarc.server.core.extend

import kotlinx.datetime.*

fun LocalDateTime.Companion.zdaj(): LocalDateTime = Clock.System.now().toLocalDateTime(timeZone = TimeZone.UTC)
fun LocalDate.Companion.danes(): LocalDate = LocalDateTime.zdaj().date
