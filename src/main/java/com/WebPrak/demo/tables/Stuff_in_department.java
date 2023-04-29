package com.WebPrak.demo.tables;

import lombok.*;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Stuff_in_department")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Stuff_in_department implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    @NonNull
    private Departments department_id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "stuff_id")
    @ToString.Exclude
    @NonNull
    private Stuff stuff_id;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final Stuff_in_department other = (Stuff_in_department) obj;
        return  (this.id == other.id) &&
                (this.department_id.equals(other.department_id)) &&
                (this.stuff_id.equals(other.stuff_id));
    }

}