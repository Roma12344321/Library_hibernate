package com.example.library.conrollers;

import com.example.library.models.Book;
import com.example.library.models.Person;
import com.example.library.services.BookService;
import com.example.library.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookService.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("formPerson") Person form) {
        model.addAttribute("book", bookService.show(id));
        Person person = bookService.getPersonByBookId(id);
        model.addAttribute("person", person);
        model.addAttribute("people", peopleService.index());
        return "books/show";
    }

    @PatchMapping("/become/{book_id}")
    public String becomeReader(@PathVariable("book_id") int book_id, @ModelAttribute("person") Person selected) {
        bookService.addBookReader(selected.getId(), book_id);
        return "redirect:/book/{book_id}";
    }

    @PatchMapping("/deletereader/{id}")
    public String deleteReader(@PathVariable("id") int id) {
        bookService.deleteBookReader(id);
        return "redirect:/book/{id}";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping("/{id}")
    public String create(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookService.update(id, book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/book";
    }
}