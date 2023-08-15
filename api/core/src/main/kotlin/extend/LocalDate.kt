package extend

import base.Encrypted
import base.encryptor
import kotlinx.datetime.*
import org.bson.BsonBinary

fun LocalDateTime.Companion.zdaj(dDni: Int = 0): LocalDateTime {
    val dateTime = Clock.System.now().toLocalDateTime(timeZone = TimeZone.UTC)
    val date = dateTime.date.plus(period = DatePeriod(days = dDni))
    return LocalDateTime(date = date, time = dateTime.time)
}

fun LocalDate.Companion.danes(dDni: Int = 0): LocalDate {
    return LocalDateTime.zdaj().date.plus(period = DatePeriod(days = dDni))
}
