package gamestore.dao;

import gamestore.models.Genre;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GenreDAO {
    private final JdbcTemplate jdbcTemplate;

    public List<Genre> index(){
        return jdbcTemplate.query(
                "SELECT * FROM genre",
                new BeanPropertyRowMapper<>(Genre.class)
        );
    }

    // TODO Optional here (to make possible validation in controller if not exists)
    public Genre show(String name){
        return jdbcTemplate.query(
                "SELECT * FROM genre where name=?",
                new Object[]{name},
                new BeanPropertyRowMapper<>(Genre.class)
        ).stream().findAny().orElse(null);
    }

    public void save(Genre genre){
        jdbcTemplate.update(
                "INSERT INTO genre VALUES(?, ?)",
                genre.getName(),
                genre.getDescription()
        );
    }

    public void update(Genre updatedGenre, String previousName){
        jdbcTemplate.update(
                "UPDATE genre SET name=?, description=? where name=?",
                updatedGenre.getName(),
                updatedGenre.getDescription(),
                previousName
        );
    }

    public void delete(String name){
        jdbcTemplate.update(
                "DELETE FROM genre WHERE name=?",
                name
        );
    }

}
