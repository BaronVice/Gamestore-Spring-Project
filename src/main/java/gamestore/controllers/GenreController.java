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

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("genre", genreDAO.show(id));
        return "gamestore/genre/show";
    }

    @GetMapping("/new")
    public String sendGenreCreation(@ModelAttribute("genre") Genre genre){
        return "gamestore/genre/new";
    }

    @GetMapping("/{id}/edit")
    public String sendGenreEdit(@PathVariable("id") int id,
                                Model model){
        model.addAttribute("genre", genreDAO.show(id));
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

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id,
                       @ModelAttribute("genre") @Valid Genre updatedGenre,
                       BindingResult bindingResult){
        genreValidator.validate(updatedGenre, bindingResult);

        // TODO: return this thing if updated name is same as old one (in validator as well)
        if (bindingResult.hasErrors())
            return "gamestore/genre/edit";

        genreDAO.update(updatedGenre, id);
        return String.format("redirect:/gamestore/genre/%s", updatedGenre.getId());
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        genreDAO.delete(id);
        return "redirect:/gamestore/genre";
    }
}
