package ru.iliamalafeev.techtask1.project1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.iliamalafeev.techtask1.project1.models.Book;
import ru.iliamalafeev.techtask1.project1.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showAllBooks() {
        return jdbcTemplate.query("SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book showBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?",
                        new Object[] {id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book (holder_id, title, author, year_of_publication) VALUES (?, ?, ?, ?)",
                null, book.getTitle(), book.getAuthor(), book.getYearOfPublication());
    }

    public void updateBook(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year_of_publication = ? WHERE id = ?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYearOfPublication(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    // This method is used in PeopleController to get a list of books owned by a selected person
    public List<Book> showBooksByPersonId(int personId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE holder_id = ?", new Object[] {personId},
                new BeanPropertyRowMapper<>(Book.class));
    }

    // This method is used in BookController to check whether the selected book is assigned to any Person or not
    // We join both tables - Book and Person - in database based on Book foreign key and Person primary key equality
    public Optional<Person> getBookHolder(int bookId) {
        return jdbcTemplate.query("SELECT p.* FROM Book b JOIN Person p ON b.holder_id = p.id WHERE b.id = ?",
                        new Object[] {bookId}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    // This method is used in BookController to set Person.id value as foreign key for the selected book
    // This method is called from the book page when selected book is getting "assigned" to some person
    public void assignBook(int bookId, Person person) {
        jdbcTemplate.update("UPDATE Book SET holder_id = ? WHERE id = ?", person.getId(), bookId);
    }

    // This method is used in BookController to set book's foreign key value as null
    // This method is called from the book page when selected book is getting set as "free to take"
    public void unbindBook(int bookId) {
        jdbcTemplate.update("UPDATE Book SET holder_id = null WHERE id = ?", bookId);
    }
}
