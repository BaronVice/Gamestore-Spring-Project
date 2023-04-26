package gamestore.dao;

import gamestore.models.Game;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameDAO {
    private final JdbcTemplate jdbcTemplate;

    public List<Game> index(){
        return jdbcTemplate.query(
                "SELECT * FROM game",
                new BeanPropertyRowMapper<>(Game.class)
        );
    }

    public Game show(String name){
        return jdbcTemplate.query(
                "SELECT * FROM game WHERE name=?",
                new Object[]{name},
                new BeanPropertyRowMapper<>(Game.class)
        ).stream().findAny().orElse(null);
    }

    public void save(Game game){
        jdbcTemplate.update(
                "INSERT INTO game VALUES(?, ?, ?, ?, ?)",
                game.getName(),
                game.getDescription(),
                game.getGenre(),
                game.getPrice(),
                game.getAmount()
        );
    }

    public void update(Game updatedGame, String previousName){
        jdbcTemplate.update(
                "UPDATE game SET name=?, description=?, genre=?, price=?, amount=? WHERE name=?",
                updatedGame.getName(),
                updatedGame.getDescription(),
                updatedGame.getGenre(),
                updatedGame.getPrice(),
                updatedGame.getAmount(),
                previousName
        );
    }

    public void delete(String name){
        jdbcTemplate.update(
                "DELETE FROM game WHERE name=?",
                name
        );
    }
}
