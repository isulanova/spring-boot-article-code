package ru.auchan.backend.dto;

import org.mapstruct.Mapper;
import ru.auchan.backend.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AuthResponse toResponse(User user);
}
