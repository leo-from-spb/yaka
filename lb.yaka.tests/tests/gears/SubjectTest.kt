package lb.yaka.tests.gears

import lb.yaka.expectations.*
import lb.yaka.gears.*
import lb.yaka.tests.AbstractUnitTest
import lb.yaka.tests.rabbits.Book
import lb.yaka.tests.rabbits.theBook1
import org.junit.jupiter.api.Test


class SubjectTest : AbstractUnitTest {


    @Test
    fun property_byGetter() {
        expect that theBook1 property {::name}  equalsTo "War and Peace"
        expect that theBook1 property {::author}  equalsTo "Lev Tolstoy"
    }

    @Test
    fun property_byRefernce() {
        expect that theBook1 property (Book::name)  equalsTo "War and Peace"
        expect that theBook1 property (Book::author)  equalsTo "Lev Tolstoy"
    }


}