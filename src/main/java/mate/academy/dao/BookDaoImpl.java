package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        book.setId(generatedKeys.getLong(1));
                        return book;
                    }
                }
            }
            throw new RuntimeException("Can't save a book ");
        } catch (SQLException e) {
            throw new DataProcessingException("Database error while creating book", e);
        }
    }

    private Book parseBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = parseBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = parseBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Database error while finding all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update book", e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Database error while deleting book", e);
        }
    }
}
