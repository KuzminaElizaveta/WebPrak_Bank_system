package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Client")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Clients implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "client_id")
    private Long id;

    @Column(nullable = false, name = "fullname")
    @NonNull
    private String fullname;

    @Column(name = "address")
    @NonNull
    private String address;

    @Column(name = "phone")
    @NonNull
    private String phone;

    @Column(nullable = false, name = "email")
    @NonNull
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    @ToString.Exclude
    @NonNull
    private Client_type type_id;

    @Column(nullable = false, name = "birthday")
    @NonNull
    private LocalDate birthday;

    @OneToMany(mappedBy = "client_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Accounts> accounts = new ArrayList<>();


    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final Clients other = (Clients) obj;
        return  (this.id.equals(other.id)) &&
                (this.fullname.equals(other.fullname)) &&
                (this.address.equals(other.address)) &&
                (this.phone.equals(other.phone)) &&
                (this.email.equals(other.email)) &&
                (this.type_id.equals(other.type_id)) &&
                (this.birthday.equals(other.birthday))
                ;
    }
}
