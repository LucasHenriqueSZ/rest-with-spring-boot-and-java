package com.rest.api.unittests.mockito.services;

import com.rest.api.Services.BookService;
import com.rest.api.data.dto.v1.BookDTO;
import com.rest.api.exceptions.RequiredObjectIsNullException;
import com.rest.api.model.Book;
import com.rest.api.repository.BookRepository;
import com.rest.api.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFinById() {
        Book entity = input.mockEntity(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        BookDTO result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/v1/book/1>;rel=\"self\"]"));
        assertEquals("Some Author1", result.getAuthor());
        assertEquals("Some Title1", result.getTitle());
        assertEquals(26D, result.getPrice());
        assertNotNull(result.getLaunch_date());
        assertEquals(LocalDate.now().plusDays(1), result.getLaunch_date());
    }

    @Test
    void testFindAll() {
        List<Book> bookList = input.mockEntityList();

        when(repository.findAll()).thenReturn(bookList);

        List<BookDTO> result = service.findAll();
        assertNotNull(result);
        assertEquals(14, result.size());

        BookDTO book1 = result.get(1);
        assertNotNull(book1);
        assertNotNull(book1.getId());
        assertNotNull(book1.getLinks());
        assertTrue(book1.toString().contains("links: [</api/v1/book/1>;rel=\"self\"]"));
        assertEquals("Some Author1", book1.getAuthor());
        assertEquals("Some Title1", book1.getTitle());
        assertEquals(26D, book1.getPrice());
        assertNotNull(book1.getLaunch_date());
        assertEquals(LocalDate.now().plusDays(1), book1.getLaunch_date());

        BookDTO book6 = result.get(6);
        assertNotNull(book6);
        assertNotNull(book6.getId());
        assertNotNull(book6.getLinks());
        assertTrue(book6.toString().contains("links: [</api/v1/book/6>;rel=\"self\"]"));
        assertEquals("Some Author6", book6.getAuthor());
        assertEquals("Some Title6", book6.getTitle());
        assertEquals(31D, book6.getPrice());
        assertNotNull(book6.getLaunch_date());
        assertEquals(LocalDate.now().plusDays(6), book6.getLaunch_date());

        BookDTO book13 = result.get(13);
        assertNotNull(book13);
        assertNotNull(book13.getId());
        assertNotNull(book13.getLinks());
        assertTrue(book13.toString().contains("links: [</api/v1/book/13>;rel=\"self\"]"));
        assertEquals("Some Author13", book13.getAuthor());
        assertEquals("Some Title13", book13.getTitle());
        assertEquals(38D, book13.getPrice());
        assertNotNull(book13.getLaunch_date());
        assertEquals(LocalDate.now().plusDays(13), book13.getLaunch_date());
    }

    @Test
    void testCreate() {
        Book entity = input.mockEntity(1);

        BookDTO dto = input.mockDTO(1);

        when(repository.save(entity)).thenReturn(entity);

        var result = service.create(dto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/v1/person/1>;rel=\"self\"]"));
        assertEquals("Some Author1", result.getAuthor());
        assertEquals("Some Title1", result.getTitle());
        assertEquals(26D, result.getPrice());
        assertNotNull(result.getLaunch_date());
        assertEquals(LocalDate.now().plusDays(1), result.getLaunch_date());
    }

    @Test
    void testUpdate() {
        Book entity = input.mockEntity(1);
        BookDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        BookDTO result = service.update(dto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());


        assertTrue(result.toString().contains("links: [</api/v1/person/1>;rel=\"self\"]"));
        assertEquals("Some Author1", result.getAuthor());
        assertEquals("Some Title1", result.getTitle());
        assertEquals(26D, result.getPrice());
        assertNotNull(result.getLaunch_date());
        assertEquals(LocalDate.now().plusDays(1), result.getLaunch_date());
    }

    @Test
    void testDelete() {
        Book entity = input.mockEntity(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }

    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
