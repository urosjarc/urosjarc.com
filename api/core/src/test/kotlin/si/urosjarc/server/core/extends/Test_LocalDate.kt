package si.urosjarc.server.core.base

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import si.urosjarc.server.core.extends.mladoletnik
import si.urosjarc.server.core.extends.now
import si.urosjarc.server.core.extends.starost
import si.urosjarc.server.core.extends.today
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_LocalDate : KoinTest {

    val localDate = LocalDate(year = 2000, month = Month(10), dayOfMonth = 1)
    val today = LocalDate.today()

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
        println(LocalDate.today())
    }

    @Test
    fun now() {
        println(LocalDateTime.now())
    }
}
