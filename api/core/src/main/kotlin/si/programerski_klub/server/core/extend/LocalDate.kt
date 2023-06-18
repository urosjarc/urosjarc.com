package si.programerski_klub.server.core.extend

import kotlinx.datetime.*

fun LocalDate.starost(): Float = (this - LocalDate.today()).days / 365.25f
fun LocalDate.mladoletnik(): Boolean = this.starost() < 18.0f
fun LocalDateTime.Companion.now(): LocalDateTime = Clock.System.now().toLocalDateTime(timeZone = TimeZone.UTC)
fun LocalDate.Companion.today(): LocalDate = LocalDateTime.now().date
