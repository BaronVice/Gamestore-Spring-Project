package gamestore.util;

import gamestore.dao.GenreDAO;
import gamestore.models.Genre;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GenreValidator implements Validator {
    private final GenreDAO genreDAO;

    @Override
    public boolean supports(Class<?> aClass) {
        return Genre.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Genre genre = (Genre) o;
        if (genreDAO.show(genre.getName()) != null)
            errors.rejectValue("name", "", "This genre is already exists");
    }

    public void validateIfUpdatedNameEqualsPrevious(Object o, Errors errors) {
        Genre genre = (Genre) o;
    }
}
