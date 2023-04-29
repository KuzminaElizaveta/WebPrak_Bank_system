package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "acc_type")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Account_type implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "acc_type_id")
    private Long id;

    @Column(nullable = false, name = "acc_type")
    @NonNull
    private String type;

    @OneToMany(mappedBy = "acc_type_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Accounts> accounts = new ArrayList<>();
}