package lb.yaka.gears

import lb.yaka.AbstractUnitTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class EntityIdTest : AbstractUnitTest {


    @Test
    fun `simpleName basic`() {
        val id = EntityId("TheType", "TheName")
        Assertions.assertEquals("TheType TheName", id.simpleName)
    }

    @Test
    fun `simpleName basic alias`() {
        val id = EntityId("TheType", "TheName").aka("TheAlias")
        Assertions.assertEquals("TheType TheAlias", id.simpleName)
    }

    @Test
    fun `simpleName basic no name`() {
        val id = EntityId("TheType", null)
        Assertions.assertEquals("TheType", id.simpleName)
    }

    @Test
    fun `simpleName chained`() {
        val id1 = EntityId("ParentType", "ParentName")
        val id2 = id1.inner("ChildType", "ChildName")
        Assertions.assertEquals("ChildType ChildName", id2.simpleName)
    }

    @Test
    fun `fullName chained`() {
        val id1 = EntityId("ParentType", "ParentName")
        val id2 = id1.inner("ChildType", "ChildName")
        Assertions.assertEquals("ChildType ChildName of ParentType ParentName", id2.fullName)
    }

}