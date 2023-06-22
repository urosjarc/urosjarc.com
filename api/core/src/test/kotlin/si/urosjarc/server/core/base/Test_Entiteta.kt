package si.urosjarc.server.core.base

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class FakeEntity(
    override val id: Id<FakeEntity> = Id(),
    val date: LocalDate,
    val time: LocalDateTime,
    val name: String,
    val spisek: MutableSet<String>
) : Entiteta<FakeEntity>()

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_Entiteta : KoinTest {

    val entiteta = Entiteta.random<FakeEntity>()
    val entiteta2 = Entiteta.random<FakeEntity>()

    @Test
    fun enak() {
        assertFalse(entiteta.enak(entiteta2))
        assertFalse(entiteta2.enak(entiteta))
        assertTrue(entiteta.enak(entiteta))
        assertTrue(entiteta2.enak(entiteta2))
    }

    @Test
    fun random() {
        val list = entiteta.name.split('_')
        assertEquals("name", list.first())
        assertNotEquals(entiteta2.name, entiteta.name)
        assertEquals(0, entiteta.spisek.size)
    }

}
