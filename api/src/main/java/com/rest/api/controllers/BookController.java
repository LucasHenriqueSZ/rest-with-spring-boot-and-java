package com.rest.api.controllers;

import com.rest.api.Services.BookService;
import com.rest.api.data.dto.v1.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@Tag(name = "Book", description = "Endpoints for Managing Book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Finds a Book", description = "Finds a Book",
            tags = {"Book"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = "Application/json", schema = @Schema(implementation = BookDTO.class))
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public BookDTO findById(@PathVariable(value = "id") Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Find all Books", description = "Find all Books",
            tags = {"Book"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = "Application/json", array = @ArraySchema(schema = @Schema(implementation = BookDTO.class)))
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public List<BookDTO> findAll() {
        return bookService.findAll();
    }

    @PostMapping
    @Operation(summary = "Adds a new Book", description = "Adds a new Book",
            tags = {"Book"}, responses = {
            @ApiResponse(description = "Create", responseCode = "201", content = {
                    @Content(mediaType = "Application/json", schema = @Schema(implementation = BookDTO.class))
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        BookDTO savedBookDTO = bookService.create(bookDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedBookDTO.getId()).toUri();

        return ResponseEntity.created(location).body(savedBookDTO);
    }

    @PutMapping
    @Operation(summary = "Updates a Book", description = "Updates a Book",
            tags = {"Book"}, responses = {
            @ApiResponse(description = "Updated", responseCode = "200", content = {
                    @Content(mediaType = "Application/json", schema = @Schema(implementation = BookDTO.class))
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<?> update(@RequestBody BookDTO bookDTO) {
        BookDTO updateBookDTO = bookService.update(bookDTO);
        return ResponseEntity.ok().body(updateBookDTO);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a Book", description = "Deletes a Book",
            tags = {"Book"}, responses = {
            @ApiResponse(description = "No contend", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
