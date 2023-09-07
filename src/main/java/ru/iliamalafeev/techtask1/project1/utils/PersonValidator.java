package ru.iliamalafeev.techtask1.project1.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.iliamalafeev.techtask1.project1.dao.PersonDAO;
import ru.iliamalafeev.techtask1.project1.models.Person;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person validatedPerson = (Person) target;
        Optional<Person> existingPerson = personDAO.showPerson(validatedPerson.getFullName());

        if (existingPerson.isPresent() && existingPerson.get().getId() != validatedPerson.getId()) {
            errors.rejectValue("fullName", "", "Person with this full name is already registered");
        }
    }
}
