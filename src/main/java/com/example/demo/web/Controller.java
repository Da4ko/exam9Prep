package com.example.demo.web;

import com.example.demo.model.binding.SongAddBindingModel;
import com.example.demo.model.binding.UserLoginBindingModel;
import com.example.demo.model.binding.UserRegisterBindingModel;
import com.example.demo.model.entity.enums.StyleName;
import com.example.demo.model.service.SongServiceModel;
import com.example.demo.model.service.UserServiceModel;
import com.example.demo.service.SongService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
public class Controller {
    UserServiceModel userServiceModel;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SongService songService;

    public Controller(UserService userService, ModelMapper modelMapper, SongService songService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.songService = songService;
    }

    @GetMapping("/")
    public String index(HttpSession httpSession, Model model){
        if(httpSession.getAttribute("user") == null){
            return "index";
        }
        model.addAttribute("popSongs", songService.findByStyleName(StyleName.POP));
        model.addAttribute("rockSongs", songService.findByStyleName(StyleName.ROCK));
        model.addAttribute("jazzSongs", songService.findByStyleName(StyleName.JAZZ));
        model.addAttribute("playlist", songService.findUserPlaylist(userServiceModel));
        model.addAttribute("playlistDuration", songService.findUserPlaylistDuration(userServiceModel));
        return "home";
    }



    @GetMapping("/users/register")
    public String register(Model model){
        if(!model.containsAttribute("userRegisterBindingModel")){
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";
    }

    @PostMapping("/users/register")
    public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:register";
        }
        userService.register(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        return "redirect:login";

    }
    @GetMapping("users/login")
    public String login(Model model){
        if(!model.containsAttribute("userLoginBindingModel")){
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
            model.addAttribute("NotFound", false);
        }
        return "login";
    }

    @PostMapping("users/login")
    public String loginConfirm(@Valid UserLoginBindingModel userLoginBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               HttpSession httpSession){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userLoginBindingModel", new UserLoginBindingModel());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:login";
        }
        userServiceModel = userService.findUserByNameAndPassword(userLoginBindingModel.getUsername(), userLoginBindingModel.getPassword());
        if(userServiceModel == null){
            redirectAttributes.addFlashAttribute("userLoginBindingModel", new UserLoginBindingModel());
            redirectAttributes.addFlashAttribute("NotFound", true);
            return "redirect:login";
        }

        httpSession.setAttribute("user", userServiceModel);

        return "redirect:/";

    }
    @GetMapping("users/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "index";
    }
    @GetMapping("/songs/add")
    public String add(Model model){
       if(!model.containsAttribute("songAddBindingModel")){
            model.addAttribute("songAddBindingModel", new SongAddBindingModel());
        }
        return "song-add";
    }
    @PostMapping("/songs/add")
    public String addConfirm(@Valid SongAddBindingModel songAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("songAddBindingModel", songAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.songAddBindingModel", bindingResult);
            return "redirect:add";
        }

        songService.add(modelMapper.map(songAddBindingModel, SongServiceModel.class), userServiceModel);

        return "redirect:/";

    }
    @GetMapping("song/add/{id}")
    public String likePost(@PathVariable String id){
        songService.addToPlaylist(id, userServiceModel);
        return "redirect:/";
    }
    @GetMapping("/songs/remove/all")
    public String removeAllSongs(){
        userService.clearUserPlaylist(userServiceModel);
        return "redirect:/";
    }

}
