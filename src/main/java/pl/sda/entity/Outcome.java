package pl.sda.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "outcomes")
@NoArgsConstructor
@Getter
@Setter
public class Outcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @Column(name = "add_date")
    private LocalDate addDate;

    private String comment;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Outcome(Long amount, LocalDate addDate, String comment, Category category) {
        this.amount = amount;
        this.addDate = addDate;
        this.comment = comment;
        this.category = category;
    }
}
