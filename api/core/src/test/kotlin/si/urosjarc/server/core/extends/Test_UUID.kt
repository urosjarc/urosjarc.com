package si.urosjarc.server.core.base

import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_UUID : KoinTest {

    val log = logger()
    val id = Id<String>()

    @Test
    fun value() {
        assertEquals(22, id.value.length)
        assertFalse(id.value.contains(' '))
    }

    @Test
    fun enakost() {
        log.fatal("FAIL testing")
        val id = Id<String>("1")
        val id2 = Id<String>("1")
        val id3 = Id<String>("2")
        assertTrue(id == id2)
        assertFalse(id == id3)
    }
}