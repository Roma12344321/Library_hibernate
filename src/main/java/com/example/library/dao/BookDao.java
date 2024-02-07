package com.example.library.dao;

import com.example.library.models.Book;
import com.example.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int book_id) {
        return jdbcTemplate.query(
                        "SELECT * FROM book WHERE book_id=?",
                        new Object[]{book_id},
                        new BeanPropertyRowMapper<>(Book.class)
                )
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(name,author,year) values(?,?,?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET name=?,author=?,year=? WHERE book_id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), id);

    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);
    }

    public List<Book> getBookByPersonId(int person_id) {
        return jdbcTemplate.query(
                "SELECT * FROM book WHERE person_id=?",
                new Object[]{person_id},
                new BeanPropertyRowMapper<>(Book.class)
        );
    }

    public Person getPersonByBookId(int book_id) {
        return jdbcTemplate.query(
                "SELECT person.person_id,person.name,person.year FROM person JOIN book ON person.person_id = book.person_id WHERE book.book_id=?",
                new Object[]{book_id},
                new PersonMapper()
        ).stream().findAny().orElse(null);
    }

    public void deleteBookReader(int id) {
        jdbcTemplate.update("update book set person_id=null where book_id=?", id);
    }

    public void addBookReader(int person_id, int book_id) {
        jdbcTemplate.update("update book set person_id=? where book_id=?", person_id, book_id);
    }
}