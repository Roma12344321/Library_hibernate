package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.models.Person;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> index() {
        return bookRepository.findAll();
    }

    public Book show(int book_id) {
        return bookRepository.findById(book_id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book book = bookRepository.findById(id).orElse(null);
        book.setAuthor(updatedBook.getAuthor());
        book.setName(updatedBook.getName());
        book.setYear(updatedBook.getYear());
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.delete(Objects.requireNonNull(bookRepository.findById(id).orElse(null)));
    }

    public List<Book> getBookByPersonId(int person_id) {
        Person person = personRepository.findById(person_id).orElse(null);
        Hibernate.initialize(person.getBookList());
        return person.getBookList();
    }

    public Person getPersonByBookId(int book_id) {
        Book book = bookRepository.findById(book_id).orElse(null);
        Hibernate.initialize(book.getOwner());
        return book.getOwner();
    }

    @Transactional
    public void deleteBookReader(int book_id) {
        Book book = bookRepository.findById(book_id).orElse(null);
        if (book.getOwner() != null) {
            book.getOwner().getBookList().remove(book);
        }
        book.setOwner(null);
    }

    @Transactional
    public void addBookReader(int person_id, int book_id) {
        Person person = personRepository.findById(person_id).orElse(null);
        Book book = bookRepository.findById(book_id).orElse(null);
        if (book.getOwner() != null) {
            book.getOwner().getBookList().remove(book);
        }
        person.getBookList().add(book);
        book.setOwner(person);
    }
}
