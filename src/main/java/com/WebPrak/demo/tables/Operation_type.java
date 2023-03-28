package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Op_type")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Operation_type implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "op_type_id")
    private Long id;

    @Column(nullable = false, name = "op_type")
    @NonNull
    private String type;
}