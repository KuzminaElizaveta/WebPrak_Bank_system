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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final Account_type other = (Account_type) obj;
        return  (this.id == other.id) &&
                (this.type.equals(other.type));
    }
}