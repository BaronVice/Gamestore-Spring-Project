package gamestore.controllers;

import gamestore.dao.GenreDAO;
import gamestore.models.Genre;
import gamestore.util.GenreValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/gamestore/genre")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GenreController {
    private final GenreDAO genreDAO;
    private final GenreValidator genreValidator;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("genres", genreDAO.index());
        return "gamestore/genre/index";
    }

    @GetMapping("/{name}")
    public String show(@PathVariable("name") int id,
                       Model model){
        model.addAttribute("genre", genreDAO.show(id));
        return "gamestore/genre/show";
    }

    @GetMapping("/new")
    public String sendGenreCreation(@ModelAttribute("genre") Genre genre){
        return "gamestore/genre/new";
    }

    @GetMapping("/{name}/edit")
    public String sendGenreEdit(@PathVariable("name") String genreName,
                                Model model){
        model.addAttribute("genre", genreDAO.show(genreName));
        return "gamestore/genre/edit";
    }

    @PostMapping()
    public String save(@ModelAttribute("genre") @Valid Genre genre,
                       BindingResult bindingResult){
        genreValidator.validate(genre, bindingResult);

        if (bindingResult.hasErrors())
            return "/gamestore/genre/new";

        genreDAO.save(genre);
        return "redirect:/gamestore/genre";
    }

    @PatchMapping("/{name}")
    public String edit(@PathVariable("name") String previousName,
                       @ModelAttribute("genre") @Valid Genre updatedGenre,
                       BindingResult bindingResult){
        if (!previousName.equals(updatedGenre.getName()))
            genreValidator.validate(updatedGenre, bindingResult);
        else
            genreValidator.validateIfUpdatedNameEqualsPrevious(updatedGenre, bindingResult);

        if (bindingResult.hasErrors()){
            updatedGenre.setName(previousName);
            return "gamestore/genre/edit";
        }

        genreDAO.update(updatedGenre, previousName);
        return String.format("redirect:/gamestore/genre/%s", updatedGenre.getName());
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String genreName){
        genreDAO.delete(genreName);
        return "redirect:/gamestore/genre";
    }
}
