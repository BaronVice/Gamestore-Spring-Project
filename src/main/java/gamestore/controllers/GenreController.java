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
        model.addAttribute(genreDAO.index());
        return "gamestore/genre/index";
    }

    @GetMapping("/{genre}")
    public String show(@PathVariable("genre") String genreName,
                       Model model){
        model.addAttribute(genreDAO.show(genreName));
        return "gamestore/genre/show";
    }

    @GetMapping("/new")
    public String sendGenreCreation(@ModelAttribute Genre genre){
        return "gamestore/genre/new";
    }

    @GetMapping("/edit/{genre}")
    public String sendGenreEdit(@PathVariable("genre") String genreName,
                                    Model model){
        model.addAttribute(genreDAO.show(genreName));
        return "gamestore/genre/edit";
    }

    @PostMapping("/new")
    public String save(@ModelAttribute @Valid Genre genre,
                              BindingResult bindingResult){
        genreValidator.validate(genre, bindingResult);

        if (bindingResult.hasErrors())
            return "/gamestore/genre/create";

        genreDAO.save(genre);
        return "redirect:/gamestore/index";
    }

    @PatchMapping("/edit/{genre}")
    public String edit(@PathVariable("genre") String previousName,
                       @ModelAttribute Genre updatedGenre){

    }
}
