package lb.yaka.gears

import lb.yaka.AbstractUnitTest
import lb.yaka.Book
import lb.yaka.expectations.*
import lb.yaka.theBook1
import org.junit.jupiter.api.Test


class SubjectTest : AbstractUnitTest() {


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