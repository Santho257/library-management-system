package com.santho.lms.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String title;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BorrowerDetails> borrowers;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
