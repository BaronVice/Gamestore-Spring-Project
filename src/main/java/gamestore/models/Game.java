package gamestore.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

// TODO: make genre a list box/option box (or how this is called)

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @NotEmpty(message = "Name cannot be empty")
    @Size (min = 1, max = 100, message = "Name length should be between 1 and 100 characters")
    private String name;
    @Size (max = 200, message = "Description maximum length is 200 characters")
    private String description;
    @Size (max = 30, message = "Genre maximum length is 30 characters")
    private String genre;
    @NotNull(message = "Price cannot be empty")
    @Min(value = 0, message = "Price cannot be negative")
    @Max(value = 10000, message = "Price cannot be more than 10000")
    private Double price;
    @NotNull(message = "Amount cannot be empty")
    @Min(value = 0, message = "Amount cannot be negative")
    @Max(value = 100000, message = "Amount cannot be more than 100000")
    private Integer amount;
}
