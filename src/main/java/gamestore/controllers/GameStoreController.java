package gamestore.controllers;

import gamestore.dao.GenreDAO;
import gamestore.models.Game;
import gamestore.util.GameValidator;
import gamestore.util.GenreValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import gamestore.dao.GameDAO;

import javax.validation.Valid;

@Controller
@RequestMapping("/gamestore")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameStoreController {
    private final GameDAO gameDAO;
    private final GenreDAO genreDAO;
    private final GameValidator gameValidator;
    private final GenreValidator genreValidator;

    @GetMapping()
    public String showAll(Model model){
        model.addAttribute("games", gameDAO.index());
        return "gamestore/index";
    }

    @GetMapping("/{name}")
    public String showSpecific(Model model,
                               @PathVariable("name") String name){
        model.addAttribute("game", gameDAO.show(name));
        return "gamestore/game/show";
    }

    @GetMapping("/new")
    public String sendCreationPage(@ModelAttribute("game") Game game){
        return "gamestore/game/new";
    }

    @GetMapping("/{name}/edit")
    public String sendUpdatePage(Model model,
                                 @PathVariable("name") String name){
        model.addAttribute("game", gameDAO.show(name));
        return "gamestore/game/edit";
    }

    @PostMapping()
    public String save(@ModelAttribute("game") @Valid Game game,
                       BindingResult bindingResult){
        gameValidator.validate(game, bindingResult);

        if (bindingResult.hasErrors())
            return "gamestore/game/new";

        gameDAO.save(game);
        return "redirect:/gamestore";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("game") @Valid Game updatedGame,
                         BindingResult bindingResult,
                         @PathVariable("name") String previousName){
        // Weird validation here
        if (!updatedGame.getName().equals(previousName))
            gameValidator.validate(updatedGame, bindingResult);

        if (bindingResult.hasErrors()) {
            updatedGame.setName(previousName);
            return "gamestore/game/edit";
        }

        gameDAO.update(updatedGame, previousName);
        return String.format("redirect:/gamestore/%s", updatedGame.getName());
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name){
        gameDAO.delete(name);
        return "redirect:/gamestore";
    }
}
