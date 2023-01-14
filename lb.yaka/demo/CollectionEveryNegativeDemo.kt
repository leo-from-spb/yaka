package lb.yaka.demo

import lb.yaka.expectations.*
import lb.yaka.gears.*
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