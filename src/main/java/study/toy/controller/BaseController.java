package study.toy.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import study.toy.dto.Hello;
import study.toy.dto.World;

@RestController
public class BaseController{
    @GetMapping("/hello")
    public Hello getHello(){
        return new Hello("hello to controller");
    }
    @GetMapping("/world")
    public World getWorld(){
        return new World("hello to added object");
    }
}