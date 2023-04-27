package gamestore.util;

import gamestore.dao.GameDAO;
import gamestore.dao.GenreDAO;
import gamestore.models.Game;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameValidator implements Validator {

    private final GameDAO gameDAO;
    private final GenreDAO genreDAO;

    @Override
    public boolean supports(Class<?> aClass) {
        return Game.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Game game = (Game) o;
        if (gameDAO.show(game.getName()) != null)
            errors.rejectValue("name", "", "Game with this name is already exists");
        if (genreDAO.show(game.getGenre()) == null)
            errors.rejectValue("genre", "", "Chosen genre doesn't exist");
    }

    public void validateIfUpdatedNameEqualsPrevious(Object o, Errors errors) {
        Game game = (Game) o;
        if (genreDAO.show(game.getGenre()) == null)
            errors.rejectValue("genre", "", "Chosen genre doesn't exist");
    }
}
