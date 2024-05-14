package com.project.library.service.impl;

import com.project.library.dto.PageDTO;
import com.project.library.dto.PatronDTO;
import com.project.library.dto.UserDTO;
import com.project.library.entity.PatronEntity;
import com.project.library.entity.UserEntity;
import com.project.library.exceptions.GeneralException;
import com.project.library.repository.PatronRepository;
import com.project.library.repository.UserRepository;
import com.project.library.service.PatronService;
import com.project.library.utils.UserUtils;
import com.project.library.mapper.PatronMapper;
import com.project.library.utils.PageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.project.library.mapper.UserMapper.*;

@Service
public class PatronServiceImpl implements PatronService {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PageDTO<PatronDTO> getAllPatrons(Pageable pageable) {
        return PageUtils.toPageImpl(patronRepository.findAllNonDeleted(pageable), PatronMapper.PATRON_MAPPER);
    }

    @Override
    public PatronDTO getPatronById(Integer id) {
        PatronEntity patronEntity = patronRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Patron with id: " + id + " was not found"));

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(patronEntity.getUser().getUsername()))) {
            throw new GeneralException("You have no access over this patron");
        }

        return PatronMapper.PATRON_MAPPER.toDto(patronEntity);
    }

    @Override
    public PatronDTO addPatron(PatronDTO patron) {
        UserEntity loggedUser = userRepository.findByUsernameAndDeletedFalse(UserUtils.getLoggedUser()).orElseThrow(() -> new GeneralException("User: " + UserUtils.getLoggedUser() + " was not found"));

        if (patronRepository.findByIdAndDeletedFalse(patron.getId()).isPresent()) {
            throw new GeneralException("There already is a patron with this id on the db");
        }

        PatronEntity newPatron = PatronEntity.builder()
                .user(loggedUser)
                .deleted(false)
                .build();

        patronRepository.save(newPatron);

        return PatronMapper.PATRON_MAPPER.toDto(newPatron);
    }

    @Override
    public PatronDTO updatePatron(Integer id,@Valid PatronDTO patron) {
        PatronEntity patronToBeUpdated = patronRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("No patron with id: " + patron.getId() + " was found on the db"));

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(patronToBeUpdated.getUser().getUsername()))) {
            throw new GeneralException("You have no access over this patron");
        }
        PatronDTO updatedPatron = PatronMapper.PATRON_MAPPER.toDto(patronToBeUpdated);

        UserEntity user = USER_MAPPER.toEntity(patron.getUser());
        UserDTO userDTO = USER_MAPPER.toDto(user);

        userDTO.setFirstName(patron.getUser().getFirstName());
        userDTO.setPassword(patron.getUser().getPassword());
        userDTO.setFirstName(patron.getUser().getFirstName());
        userDTO.setLastName(patron.getUser().getLastName());
        userDTO.setCreatedAt(LocalDateTime.now());

        userRepository.save(USER_MAPPER.toEntity(userDTO));

        return PatronMapper.PATRON_MAPPER.toDto(patronRepository.save(PatronMapper.PATRON_MAPPER.toEntity(updatedPatron)));
    }

    @Override
    public void deletePatron(Integer id) {
        PatronEntity patronToBeDeleted = patronRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("No patron with id: " + id + " was found on the db"));

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(patronToBeDeleted.getUser().getUsername()))) {
            throw new GeneralException("You have no access over this patron");
        }

        PatronDTO deletedPatron = PatronMapper.PATRON_MAPPER.toDto(patronToBeDeleted);

        deletedPatron.setDeleted(true);

        patronRepository.save(PatronMapper.PATRON_MAPPER.toEntity(deletedPatron));
    }
}
