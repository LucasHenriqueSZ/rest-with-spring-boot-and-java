package com.rest.api.data.dto.v1;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String author;

    private LocalDate launch_date;

    private double price;

    private String title;

    public BookDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(LocalDate launch_date) {
        this.launch_date = launch_date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof com.rest.api.model.Book book)) return false;
        return Double.compare(book.getPrice(), getPrice()) == 0 && Objects.equals(getId(), book.getId()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getLaunch_date(), book.getLaunch_date()) && Objects.equals(getTitle(), book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getLaunch_date(), getPrice(), getTitle());
    }
}
