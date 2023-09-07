package ru.iliamalafeev.techtask1.project1.models;

import jakarta.validation.constraints.*;

public class Book {

    private int id;

    @NotEmpty(message = "Cannot be left empty")
    private String title;

    @NotEmpty(message = "Cannot be left empty")
    private String author;

    private int year_of_publication;

    public Book() {
    }

    public Book(String title, String author, int year_of_publication) {
        this.title = title;
        this.author = author;
        this.year_of_publication = year_of_publication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return year_of_publication;
    }

    public void setYearOfPublication(int year_of_publication) {
        this.year_of_publication = year_of_publication;
    }
}
