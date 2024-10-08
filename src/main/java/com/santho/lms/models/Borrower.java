package com.santho.lms.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Borrower {
    @Id
    private String username; //Email
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "borrower", cascade = CascadeType.REMOVE)
    private List<BorrowerDetails> borrowed;
}
