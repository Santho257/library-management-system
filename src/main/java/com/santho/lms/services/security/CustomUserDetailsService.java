package com.santho.lms.services.security;

import com.santho.lms.daos.BorrowerDao;
import com.santho.lms.models.Borrower;
import com.santho.lms.services.BorrowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final BorrowerService borrowerService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Borrower borrower = borrowerService.get(username);
        return User
                .withUsername(borrower.getUsername())
                .password(borrower.getPassword())
                .roles(borrower.getRole().toString())
                .build();
    }
}
