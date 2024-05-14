package com.project.library.service.impl;

import com.project.library.dto.PageDTO;
import com.project.library.dto.UserDTO;
import com.project.library.entity.BorrowingRecordEntity;
import com.project.library.entity.UserEntity;
import com.project.library.entity.enums.Role;
import com.project.library.exceptions.GeneralException;
import com.project.library.repository.BorrowingRecordRepository;
import com.project.library.repository.UserRepository;
import com.project.library.service.UserService;
import com.project.library.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

import static com.project.library.mapper.UserMapper.USER_MAPPER;
import static com.project.library.utils.PageUtils.toPageImpl;


@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepository userRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public PageDTO<UserDTO> findAll(Pageable pageable) {
        return toPageImpl(userRepository.findAllNonDeleted(pageable), USER_MAPPER);
    }

    @Override
    public UserDTO findUserById(Integer id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new GeneralException("User with id: " + id + " was not found"));

        if (userEntity.isDeleted()) {
            throw new GeneralException("No user with id: " + id + " was found");
        }

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(userEntity.getUsername()))) {
            throw new GeneralException("You have no access over this user");
        }

        return USER_MAPPER.toDto(userEntity);

    }

    @Override
    public UserDTO createUser(@Valid UserDTO userDTO) {

        if(userRepository.existsById(userDTO.getId())){
            throw new GeneralException("There already exists a user with this id: " + userDTO.getId());
        }
        UserEntity userEntity = USER_MAPPER.toEntity(userDTO);
        UserDTO userDTO1 = USER_MAPPER.toDto(userEntity);
        userDTO1.setRole(Role.PATRON);
        userDTO1.setFirstName(userDTO.getFirstName());
        userDTO1.setLastName(userDTO.getLastName());
        userDTO1.setEmail(userDTO.getEmail());
        userDTO1.setDateOfBirth(userDTO.getDateOfBirth());
        userDTO1.setUsername(userDTO.getUsername());
        userDTO1.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO1.setCreatedAt(LocalDateTime.now());
        userDTO1.setDeleted(false);

        return USER_MAPPER.toDto(userRepository.save(userEntity));

    }

    @Override
    public UserDTO updateUser(Integer id, UserDTO userDTO) {

        UserEntity userEntity = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("User with id: " + id + " was not found"));

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(userEntity.getUsername()))) {
            throw new GeneralException("You have no access to edit this user");
        }
        UserDTO userDTO1 = USER_MAPPER.toDto(userEntity);

        userDTO1.setFirstName(userDTO.getFirstName());
        userDTO1.setLastName(userDTO.getLastName());
        userDTO1.setDateOfBirth(userDTO.getDateOfBirth());
        userDTO1.setUsername(userDTO.getUsername());
        userDTO1.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO1.setCreatedAt(LocalDateTime.now());

        return USER_MAPPER.toDto(userRepository.save(USER_MAPPER.toEntity(userDTO1)));
    }

    @Override
    public void deleteUser(Integer id) {
        UserEntity userToBeDeleted = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("User with id: " + id + " was not found"));

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(userToBeDeleted.getUsername()))) {
            throw new GeneralException("You have no access over this user");
        }

       BorrowingRecordEntity bookBorrowings = borrowingRecordRepository.findBorrowingRecordEntityByPatronId(id);

        if(bookBorrowings!=null){
            throw new GeneralException("You can not delete a user with an active book borrowing");
        }


        UserDTO userDTO = USER_MAPPER.toDto(userToBeDeleted);

        userDTO.setDeleted(true);
        userRepository.save(USER_MAPPER.toEntity(userDTO));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

}
