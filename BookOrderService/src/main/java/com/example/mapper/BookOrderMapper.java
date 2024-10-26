package com.example.mapper;

import com.example.controller.request.BookOrderCreateRequest;
import com.example.controller.request.BookOrderUpdateRequest;
import com.example.model.BookOrderModel;
import com.example.repository.entity.BookOrderEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BookOrderMapper {

    BookOrderEntity toEntity(BookOrderModel model);

    List<BookOrderEntity> toEntities(List<BookOrderModel> models);

    BookOrderModel toModel(BookOrderEntity entity);

    List<BookOrderModel> toModels(List<BookOrderEntity> models);

    BookOrderModel toModel(BookOrderCreateRequest createRequest);

    BookOrderModel toModel(BookOrderUpdateRequest updateRequest);
}
