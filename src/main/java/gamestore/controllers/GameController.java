package gamestore.controllers;

import gamestore.dao.GameDAO;
import gamestore.dao.GenreDAO;
import gamestore.models.Game;
import gamestore.util.GameValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/gamestore/game")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameController {
    private final GameDAO gameDAO;
    private final GenreDAO genreDAO;
    private final GameValidator gameValidator;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("games", gameDAO.index());
        return "gamestore/game/index";
    }

    @GetMapping("/{name}")
    public String show(Model model,
                       @PathVariable("name") String name){
        model.addAttribute("game", gameDAO.show(name));
        return "gamestore/game/show";
    }

    @GetMapping("/new")
    public String sendGameCreation(@ModelAttribute("game") Game game,
                                   Model model){
        model.addAttribute("genres", genreDAO.index());
        return "gamestore/game/new";
    }

    @GetMapping("/{name}/edit")
    public String sendGameUpdate(Model model,
                                 @PathVariable("name") String name){
        model.addAttribute("game", gameDAO.show(name));
        model.addAttribute("genres", genreDAO.index());
        return "gamestore/game/edit";
    }

    @PostMapping()
    public String save(@ModelAttribute("game") @Valid Game game,
                       BindingResult bindingResult){
        gameValidator.validate(game, bindingResult);

        if (bindingResult.hasErrors())
            return "gamestore/game/new";

        gameDAO.save(game);
        return "redirect:/gamestore/game";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("game") @Valid Game updatedGame,
                         BindingResult bindingResult,
                         @PathVariable("name") String previousName){
        if (updatedGame.getName().equals(previousName))
            gameValidator.validateIfUpdatedNameEqualsPrevious(updatedGame, bindingResult);
        else
            gameValidator.validate(updatedGame, bindingResult);

        if (bindingResult.hasErrors()) {
            updatedGame.setName(previousName);
            return "gamestore/game/edit";
        }

        gameDAO.update(updatedGame, previousName);
        return String.format("redirect:/gamestore/game/%s", updatedGame.getName());
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name){
        gameDAO.delete(name);
        return "redirect:/gamestore/game";
    }
}
