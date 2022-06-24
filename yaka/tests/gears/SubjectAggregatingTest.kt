package lb.yaka.gears

import lb.yaka.AbstractUnitTest
import lb.yaka.Book
import lb.yaka.expectations.*
import lb.yaka.theBook1
import lb.yaka.theBookList
import org.junit.jupiter.api.Test


class SubjectAggregatingTest : AbstractUnitTest {


    @Test
    fun complies_valueOf_expression_basic() {
        expect that theBook1 aka "The Book" complies {
            valueOf("the name") { name } equalsTo "War and Peace"
            valueOf("the author") { author } equalsTo "Lev Tolstoy"
        }
    }

    @Test
    fun complies_property_inBraces() {
        expect that theBook1 aka "The Book" complies {
            property { ::name } equalsTo "War and Peace"
            property { ::author } equalsTo "Lev Tolstoy"
        }
    }

    @Test
    fun complies_property_inParentheses() {
        expect that theBook1 aka "The Book" complies {
            property(Book::name) equalsTo "War and Peace"
            property(Book::author) equalsTo "Lev Tolstoy"
        }
    }


    @Test
    fun every_ok() {
        expect that theBookList every {
            property {::name} hasLength (12..40)
            property {::author} hasLength (10..20)
        }
    }

    @Test
    fun every_1error_info() {
        expectException<AssertionError> {
            expect that theBookList every {
                property { ::name } hasLength (12..25)
            }
        } where message textBetween ("---+" to "+---") complies {
            //print(x)
            this containsIgnoringSpaces "Element: at 2: name"
            this contains "Actual:" contains "The Adventures"
            this contains "Expected:" contains "12..25"
            this contains "Problem:" contains "too long"
        }
    }

    @Test
    fun every_2errors_info() {
        expectException<AssertionError> {
            expect that theBookList every {
                property { ::name } hasLength (12..18)
            }
        } where message complies {
            //print(x)
            this contains "Element:"
            this contains "Actual:"
            this contains "Expected:"
            this contains "Problem:"
        }
    }


}