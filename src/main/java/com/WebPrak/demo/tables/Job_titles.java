package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Job_title")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Job_titles implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "job_title_id")
    private Long id;

    @Column(nullable = false, name = "job_title")
    @NonNull
    private String job_title;
}