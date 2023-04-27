package gamestore.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gamestore")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameStoreController {
    @GetMapping()
    public String sendGameStorePage(){
        return "gamestore/index";
    }
}
