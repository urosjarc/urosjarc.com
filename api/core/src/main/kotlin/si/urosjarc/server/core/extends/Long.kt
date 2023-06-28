package si.urosjarc.server.core.extends

import kotlinx.datetime.*

fun Long.toLocalDateTime(): LocalDateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
fun Long.toLocalDate(): LocalDate = this.toLocalDateTime().date
