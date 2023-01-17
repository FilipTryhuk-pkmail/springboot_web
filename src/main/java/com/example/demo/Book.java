package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Book {
    private @Id @GeneratedValue Long id;
    private String title;
    private String author;
    private int publishing_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getPublishing_date() {
        return publishing_date;
    }

    public void setPublishing_date(int publishing_date) {
        this.publishing_date = publishing_date;
    }

    Book() {
    }

    Book(String title, String author, int publishing_date) {
        this.author = author;
        this.title = title;
        this.publishing_date = publishing_date;
    }

    @Override
    public String toString() {
        return "\""+title+"\" by "+author+", "+publishing_date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.author, this.publishing_date);
    }
}
