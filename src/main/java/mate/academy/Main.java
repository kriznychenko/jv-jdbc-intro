package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();

        Book book1 = new Book();
        book1.setTitle("The Master and Margarita");
        book1.setPrice(new BigDecimal("250.00"));
        bookDao.create(book1);
        System.out.println("Created Book: " + book1.getId()
                + " - " + book1.getTitle() + " - " + book1.getPrice());

        Book book2 = new Book();
        book2.setTitle("1984");
        book2.setPrice(new BigDecimal("180.00"));
        bookDao.create(book2);
        System.out.println("Created Book: " + book2.getId()
                + " - " + book2.getTitle() + " - " + book2.getPrice());

        Book book3 = new Book();
        book3.setTitle("The Great Gatsby");
        book3.setPrice(new BigDecimal("220.00"));
        bookDao.create(book3);
        System.out.println("Created Book: " + book3.getId()
                + " - " + book3.getTitle() + " - " + book3.getPrice());

        Book book4 = new Book();
        book4.setTitle("Ubik");
        book4.setPrice(new BigDecimal("300.00"));
        bookDao.create(book4);
        System.out.println("Created Book: " + book4.getId()
                + " - " + book4.getTitle() + " - " + book4.getPrice());

        Book book5 = new Book();
        book5.setTitle("Harry Potter and the Philosopher's Stone");
        book5.setPrice(new BigDecimal("350.00"));
        bookDao.create(book5);
        System.out.println("Created Book: " + book5.getId()
                + " - " + book5.getTitle() + " - " + book5.getPrice());

        Optional<Book> foundBook = bookDao.findById(3L);
        foundBook.ifPresent(book -> System.out.println("Found Book: "
                + book.getId() + " - "
                + book.getTitle() + " - " + book.getPrice()));

        List<Book> books = bookDao.findAll();
        System.out.println("\nAll Books:");
        for (Book book : books) {
            System.out.println(book.getId() + " - " + book.getTitle()
                    + " - " + book.getPrice());
        }

        book3.setTitle("The Great Gatsby (Updated)");
        book3.setPrice(new BigDecimal("12.99"));
        Book updatedBook = bookDao.update(book3);
        System.out.println("\nUpdated Book: " + updatedBook.getId() + " - "
                + updatedBook.getTitle() + " - " + updatedBook.getPrice());

        boolean deleted = bookDao.deleteById(10L);
        System.out.println("\nBook Deleted: " + deleted);
        System.out.println("\nAll Books:");
        for (Book book : books) {
            System.out.println(book.getId() + " - " + book.getTitle() + " - " + book.getPrice());
        }
    }
}
