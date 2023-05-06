package com.rest.api.Services;


import com.rest.api.controllers.BookController;
import com.rest.api.controllers.PersonController;
import com.rest.api.data.dto.v1.BookDTO;
import com.rest.api.data.dto.v1.PersonDTO;
import com.rest.api.exceptions.RequiredObjectIsNullException;
import com.rest.api.exceptions.ResourceNotFoundException;
import com.rest.api.mapper.ModelMapper;
import com.rest.api.model.Book;
import com.rest.api.model.Person;
import com.rest.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private final Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository repository;

    public BookDTO findById(Long id) {
        logger.info("Find one Book by id:" + id);
        Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        BookDTO dto = ModelMapper.parseObject(book, BookDTO.class);
        dto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return dto;
    }

    public List<BookDTO> findAll() {
        logger.info("Find all Books");
        List<BookDTO> books = ModelMapper.parseListObjects(repository.findAll(), BookDTO.class);
        books.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getId())).withSelfRel()));
        return books;
    }

    public BookDTO create(BookDTO bookDto) {
        if (bookDto == null) throw new RequiredObjectIsNullException();

        logger.info("Create a new Book");
        Book book = ModelMapper.parseObject(bookDto, Book.class);
        BookDTO dto = ModelMapper.parseObject(repository.save(book), BookDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }

    public BookDTO update(BookDTO bookDto) {
        if (bookDto == null) throw new RequiredObjectIsNullException();

        logger.info("update a Book");
        Book entity = repository.findById(bookDto.getId()).orElseThrow(() -> new ResourceNotFoundException(" No records found for this ID"));

        entity.setAuthor(bookDto.getAuthor());
        entity.setPrice(bookDto.getPrice());
        entity.setTitle(bookDto.getTitle());
        entity.setLaunch_date(bookDto.getLaunch_date());

        BookDTO dto = ModelMapper.parseObject(repository.save(entity), BookDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }

    public void delete(Long id) {
        logger.info("delete one Book");
        Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(" No records found for this ID"));
        repository.delete(entity);
    }
}
