package com.WebPrak.demo.tables;

import jdk.dynalink.Operation;
import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Op_type")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor

public class Operation_type implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "op_type_id")
    private Long id;

    @Column(nullable = false, name = "op_type")
    @NonNull
    private String type;

    @OneToMany(mappedBy = "op_type_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Operations> operations = new ArrayList<>();
}