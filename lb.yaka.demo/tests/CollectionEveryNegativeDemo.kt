package lb.yaka.demo

import lb.yaka.base.expectations.contains
import lb.yaka.base.expectations.hasLength
import lb.yaka.base.gears.every
import lb.yaka.base.gears.expect
import org.junit.jupiter.api.Test


class CollectionEveryNegativeDemo : DemoTest {


    @Test
    fun every_ok() {
        expect that theBookList aka "My Books" every {
            property { ::name } contains ' '
            property { ::name } hasLength (12..40)
            property { ::author } hasLength (10..20)
        }
    }

    @Test
    fun every_errors() {
        expect that theBookList aka "My Books" every {
            property { ::name } contains "and"
            property { ::name } hasLength (12..20)
            property { ::author } hasLength (15..25)
        }
    }


}