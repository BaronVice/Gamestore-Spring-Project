package gamestore.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

// TODO: create id for primary key (hard times with update)

@Entity
@Table(name = "genre")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Genre {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name cannot be empty")
    @Size (min = 1, max = 30, message = "Name length should be between 1 and 30 characters")
    @NonNull
    private String name;

    @Column(name = "description")
    @Size (max = 200, message = "Description maximum length is 200 characters")
    @NonNull
    private String description;

    @OneToMany(mappedBy = "genre") // TODO: genre though?
    private List<Game> games;
}
