package gamestore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "genre")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre implements Serializable {
    @Id
    @Column(name = "name")
    @NotEmpty(message = "Name cannot be empty")
    @Size (min = 1, max = 30, message = "Name length should be between 5 and 30 characters")
    private String name;

    @Column(name = "description")
    @Size (max = 200, message = "Description maximum length is 200 characters")
    private String description;

    @OneToMany(mappedBy = "genre")
    private List<Game> games;
}
