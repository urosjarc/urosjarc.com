package si.urosjarc.server.core.base

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import si.urosjarc.server.core.extend.mladoletnik
import si.urosjarc.server.core.extend.zdaj
import si.urosjarc.server.core.extend.starost
import si.urosjarc.server.core.extend.danes
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_LocalDate : KoinTest {

    val localDate = LocalDate(year = 2000, month = Month(10), dayOfMonth = 1)
    val today = LocalDate.danes()

    @Test
    fun starost() {
        val starost = localDate.starost()
        println(starost)
        assertTrue( starost >= 20)
    }

    @Test
    fun mladoletnik() {
        assertFalse(localDate.mladoletnik())
        assertTrue(today.mladoletnik())
    }

    @Test
    fun today() {
        println(LocalDate.danes())
    }

    @Test
    fun now() {
        println(LocalDateTime.zdaj())
    }
}
