package ru.iliamalafeev.techtask1.project1.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.iliamalafeev.techtask1.project1.models.Book;

@Component
public class BookValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book validatedBook = (Book) target;
        if (validatedBook.getTitle().equals("") || !Character.isUpperCase(validatedBook.getTitle().codePointAt(0))) {
            errors.rejectValue("title", "", "Title should start with a capital letter");
        }

        if (validatedBook.getAuthor().equals("") || !Character.isUpperCase(validatedBook.getAuthor().codePointAt(0))) {
            errors.rejectValue("author", "", "Author name should start with a capital letter");
        }
    }
}
