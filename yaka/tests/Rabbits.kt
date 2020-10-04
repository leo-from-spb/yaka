package lb.yaka


data class Book (val name: String, val author: String) {

    override fun toString() = """"$name" by $author"""

}


val theBook1 = Book("War and Peace", "Lev Tolstoy")
val theBook2 = Book("Crime and Punishment", "Fyodor Dostoevsky")
val theBook3 = Book("The Adventures of Huckleberry Finn", "Mark Twain")


val theBookList = listOf(theBook1, theBook2, theBook3)

