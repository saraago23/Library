package com.project.library.utils;

import com.project.library.exceptions.GeneralException;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class UserUtils {

    public String getLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public String getLoggedUserRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().orElseThrow(()-> new GeneralException("There is an error with the authority of the user!")).getAuthority();
    }
}
