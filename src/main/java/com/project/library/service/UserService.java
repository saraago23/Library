package com.project.library.service;

import com.project.library.dto.PageDTO;
import com.project.library.dto.UserDTO;
import org.springframework.data.domain.Pageable;

public interface UserService {

    PageDTO<UserDTO> findAll(Pageable pageable);

    UserDTO findUserById(Integer id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Integer id, UserDTO userDTO);

    void deleteUser(Integer id);

}
