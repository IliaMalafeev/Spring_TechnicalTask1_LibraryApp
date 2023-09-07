package ru.iliamalafeev.techtask1.project1.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.iliamalafeev.techtask1.project1.dao.BookDAO;
import ru.iliamalafeev.techtask1.project1.dao.PersonDAO;
import ru.iliamalafeev.techtask1.project1.models.Book;
import ru.iliamalafeev.techtask1.project1.models.Person;
import ru.iliamalafeev.techtask1.project1.utils.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookDAO.showAllBooks());
        return "books/all_books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.showBook(id));
        if (bookDAO.getBookHolder(id).isPresent()) {
            Person holder = bookDAO.getBookHolder(id).get();
            model.addAttribute("holder", holder);
        } else {
            model.addAttribute("people", personDAO.showAllPeople());
        }
        return "books/book";
    }

    @GetMapping("/new")
    public String showNewBookForm(@ModelAttribute("book") Book book) {
        return "books/new_book_form";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/new_book_form";
        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showBookUpdatePage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.showBook(id));
        return "books/edit_book_form";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult,
                             @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/edit_book_form";
        bookDAO.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    // We receive a person object with only field id set, just enough to use it as foreign key for the selected book
    @PatchMapping("/{id}/assign")
    public String assignBook(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        bookDAO.assignBook(id, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/unbind")
    public String unbindBook(@PathVariable("id") int id) {
        bookDAO.unbindBook(id);
        return "redirect:/books/" + id;
    }
}
