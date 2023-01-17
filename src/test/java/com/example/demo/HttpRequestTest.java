package com.example.demo;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private final Book book = new Book("TheReturnOfTheKing", "JRR Tolkien", 1955);
    private final Book newBook = new Book("Some Title", "Writer", 2012);

    @Test
    public void bookGetRequestShouldReturnBooks() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:8080/books", String.class)).contains("JRRTolkien", "TheFellowshipOfTheRing", "\"id\":2");
    }

    @Test
    public void addingBooksToRepositoryShouldSave() throws Exception {
        this.restTemplate.postForEntity("http://localhost:8080/books", book, String.class);
        assertThat(this.restTemplate.getForObject("http://localhost:8080/books", String.class)).contains("TheReturnOfTheKing");
    }

    @Test
    public void addingIncorrectTypeReturnsUnsupportedMediaTypeError() throws Exception {
        assertThat(this.restTemplate.postForEntity("http://localhost:8080/books", "BookTitle", String.class).getStatusCode()).isEqualTo(HttpStatusCode.valueOf(415));
    }

    @Test
    public void accessingIncorrectMethodReturnsNotFound() throws Exception {
        assertThat(this.restTemplate.postForEntity("http://localhost:8080/book", book, String.class).getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));

    }

    @Test
    public void replacingBooksReturnsOK() {
        this.restTemplate.put("http://localhost:8080/books/2", newBook);
        assertThat(this.restTemplate.getForObject("http://localhost:8080/books", String.class)).contains("Writer");
        assertThat(this.restTemplate.getForObject("http://localhost:8080/books", String.class)).doesNotContain("TheTwoTowers");
    }
}