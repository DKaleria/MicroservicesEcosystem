package com.example.mapper;

import com.example.controller.request.BookCreateRequest;
import com.example.controller.request.BookUpdateRequest;
import com.example.model.BookModel;
import com.example.repository.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface BookMapper {

    @Mapping(target = "author.name", source = "authorName")
    @Mapping(target = "author.surname", source = "authorSurname")
    @Mapping(target = "genre.name", source = "genre")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "changed", ignore = true)
    BookEntity toEntity(BookModel model);

    List<BookEntity> toEntities(List<BookModel> models);

    @Mapping(target = "authorName", source = "author.name")
    @Mapping(target = "authorSurname", source = "author.surname")
    @Mapping(target = "genre", source = "genre.name")
    BookModel toModel(BookEntity entity);

    List<BookModel> toModels(List<BookEntity> models);

    BookModel toModel(BookCreateRequest createRequest);

    BookModel toModel(BookUpdateRequest updateRequest);
}
