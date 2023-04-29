package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Type")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Client_type implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "type_id")
    private Long id;

    @Column(nullable = false, name = "type")
    @NonNull
    private String type;

    @OneToMany(mappedBy = "type_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Clients> clients = new ArrayList<>();
}