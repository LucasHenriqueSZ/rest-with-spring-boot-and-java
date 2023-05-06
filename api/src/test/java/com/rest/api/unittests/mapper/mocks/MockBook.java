package com.rest.api.unittests.mapper.mocks;

import com.rest.api.data.dto.v1.BookDTO;
import com.rest.api.model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();

        book.setId(number.longValue());
        book.setAuthor("Some Author" + number);
        book.setTitle("Some Title" + number);
        book.setPrice(25D + number.doubleValue());
        book.setLaunch_date(LocalDate.now().plusDays(number));

        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setId(number.longValue());
        book.setAuthor("Some Author" + number);
        book.setTitle("Some Title" + number);
        book.setPrice(25D + number.doubleValue());
        book.setLaunch_date(LocalDate.now().plusDays(number));

        return book;
    }
}
