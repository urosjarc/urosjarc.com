package si.urosjarc.server.core.extend

import kotlinx.datetime.*

fun Long.vLocalDateTime(): LocalDateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
fun Long.vLocalDate(): LocalDate = this.vLocalDateTime().date
