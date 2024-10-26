package com.example.service.impl;

import com.example.mapper.BookMapper;
import com.example.model.BookModel;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;
import com.example.repository.GenreRepository;
import com.example.repository.entity.AuthorEntity;
import com.example.repository.entity.BookEntity;
import com.example.repository.entity.GenreEntity;
import com.example.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.repository.BookSpecificationProvider.buildSearchCriteria;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;

    @Override
    @Cacheable(
            cacheNames = {"books"},
            key = "#authorName + '_' + #authorSurname + '_' + #genreName"
    )
    public List<BookModel> getBooks(String authorName, String authorSurname, String genreName) {
        AuthorEntity author = null;
        GenreEntity genre = null;
        if (authorName != null && authorSurname != null) {
            author = authorRepository.findByNameAndSurname(authorName, authorSurname).orElseThrow(
                    () -> new RuntimeException(format("Автор %s %s не существует в базе", authorName, authorSurname)));
        }
        if (genreName != null) {
            genre = genreRepository.findByName(genreName).orElseThrow(
                    () -> new RuntimeException(format("Жанр %s не существует в базе", genreName)));
        }
        Specification<BookEntity> specification = buildSearchCriteria(author, genre);
        return bookMapper.toModels(bookRepository.findAll(specification));
    }

    @Override
    public BookModel addBook(BookModel model) {
        BookEntity entity = bookMapper.toEntity(model);
        return bookMapper.toModel(bookRepository.save(entity));
    }

    @Override
    public BookModel updateBook(Long bookId, BookModel model) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(
                () -> new RuntimeException(format("Книга с ID %d не существует", bookId)));
        AuthorEntity author = authorRepository.findByNameAndSurname(model.authorName(), model.authorSurname())
                .orElseThrow(() -> new RuntimeException(format(
                        "Автор %s %s не существует в базе", model.authorName(), model.authorSurname())));
        GenreEntity genre = genreRepository.findByName(model.genre()).orElseThrow(
                () -> new RuntimeException(format("Жанр %s не существует в базе", model.genre())));
        bookEntity.setName(model.name());
        bookEntity.setAuthor(author);
        bookEntity.setGenre(genre);

        bookEntity = bookRepository.save(bookEntity);
        return bookMapper.toModel(bookEntity);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
