package pl.sda.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "incomes")
@NoArgsConstructor
@Getter
@Setter
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @Column(name = "add_date")
    private LocalDate addDate;

    private String comment;

    public Income(Long amount, LocalDate addDate, String comment) {
        this.amount = amount;
        this.addDate = addDate;
        this.comment = comment;
    }
}
