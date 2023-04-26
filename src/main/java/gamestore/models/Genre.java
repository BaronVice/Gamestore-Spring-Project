package gamestore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @NotEmpty(message = "Name cannot be empty")
    @Size (min = 5, max = 30, message = "Name length should be between 5 and 30 characters")
    private String name;

    @Size (max = 200, message = "Description maximum length is 200 characters")
    private String description;
}
