package gamestore.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

// TODO: create id for primary key (hard times with update)

@Entity
@Table(name = "game")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name cannot be empty")
    @Size (min = 1, max = 100, message = "Name length should be between 1 and 100 characters")
    private String name;

    @Column(name = "description")
    @Size (max = 200, message = "Description maximum length is 200 characters")
    private String description;

    @ManyToOne
    @JoinColumn(name = "genre_name", referencedColumnName = "name")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Genre genre;

    @Column(name = "price")
    @NotNull(message = "Price cannot be empty")
    @Min(value = 0, message = "Price cannot be negative")
    @Max(value = 10000, message = "Price cannot be more than 10000")
    private Double price;

    @Column(name = "amount")
    @NotNull(message = "Amount cannot be empty")
    @Min(value = 0, message = "Amount cannot be negative")
    @Max(value = 100000, message = "Amount cannot be more than 100000")
    private Integer amount;
}
