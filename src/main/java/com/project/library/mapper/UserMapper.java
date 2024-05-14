package com.project.library.mapper;

import com.project.library.dto.UserDTO;
import com.project.library.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity, UserDTO>{

    UserMapper USER_MAPPER= Mappers.getMapper(UserMapper.class);
}
