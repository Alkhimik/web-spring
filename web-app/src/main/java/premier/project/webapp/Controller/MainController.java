package premier.project.webapp.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import premier.project.webapp.Repositories.UserRepository;
import premier.project.webapp.Models.Post;
import premier.project.webapp.Models.projectUser;
import premier.project.webapp.Repositories.PostRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

@Controller
public class MainController {
    private static boolean checkedIn = false;
    private static Long userId = -1L;

    private char[]login;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    //starter page
    @GetMapping("/")
    public String homePage(Model model) {
        if(!checkedIn){
            return "signin";
        }
        model.addAttribute("name", String.valueOf(login));
        return "mainPage";
    }
  //logout reference
    @GetMapping("/logout")
    public String signIn(Model model) {
        checkedIn = false;
        userId = -1L;
        return "signin";
    }
    //SIGN UP
    @GetMapping("/signup")
    public String homeSignUp(Model model) {
        return "signup";
    }

    @GetMapping("/signin")
    public String homeSignIp(Model model) {
        return "signin";
    }

    @PostMapping("/signin")
    public String confirmUser(@RequestParam char[]login, @RequestParam char[]password, Model model){
      if(findUser(login,password) != -1){
          this.login = login;
          return "mainPage";
      }
       return "signin";
    }

    @PostMapping("/signup")
    public String createUser(@RequestParam char[]login, @RequestParam char[]password, Model model){
     if(login.length <= 3 || password.length <= 3){
         return "signup";
     }
        if(findUser(login,password) == -1){
            userRepository.save(new projectUser(login,password));
            findUser(login,password);
            this.login = login;
            return "mainPage";
        };
        return "signin";
    }

    private long findUser(char[]login, char[]password){
        Iterable<projectUser> user = userRepository.findAll();
        projectUser checkUser = new projectUser(login,password);

        Iterator<projectUser> iter = user.iterator();
        while(iter.hasNext()){
            projectUser us = iter.next();
            if(us.equals(checkUser)){
                checkedIn = true;
                userId = us.getId();
                return userId;
            }
        }
        return -1;
    }

    //MAIN PROJECTS LIST PAGE
    @GetMapping("/projects")
    public String projectMain(Model model){
        if(!checkedIn){
            return "signin";
        }
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("name", String.valueOf(login));
        model.addAttribute("posts", posts);
        return "project-main";
    }
    //ADD NEW PROJECT
    @GetMapping("/project/add")
    public String addProject(Model model){
        if(!checkedIn){
            return "signin";
        }
        model.addAttribute("name", String.valueOf(login));
        return "project-add";
    }

    //CREATE NEW PROJECT AND GO BACK TO MAIN PROJECTS LIST PAGE
    @PostMapping("/project/add")
    public String saveProject(@RequestParam String title,@RequestParam String anons,
                              @RequestParam String full_text, Model model){
        if(!checkedIn){
            return "signin";
        }
        Post post = new Post(title,anons,full_text, String.valueOf(login), userId);
        postRepository.save(post);
        return "redirect:/projects";
    }

    //GET TO CERTAIN PROJECT DETAILS
    @GetMapping("/project/{id}")
    public String viewProjectDetails(@PathVariable(value = "id")long id, Model model){
        if(!checkedIn){
            return "signin";
        }
        if(!postRepository.existsById(id)){return "redirect:/projects";}

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();

        post.ifPresent(res::add);
        Post post1 = post.get();
        post1.setViews(post1.getViews()+1);

        postRepository.save(post1);
        model.addAttribute("post",res);
        model.addAttribute("name", String.valueOf(login));

        if(!Objects.equals(userId, post.get().getAuthorId()) && userId != 0L){
            return "guest-article";
        }
        return "project-details";
    }

    @GetMapping("/project/{id}/remove")
    public String removeProject(@PathVariable(value = "id")long id, Model model){
        if(!checkedIn){
            return "signin";
        }
        if(!postRepository.existsById(id)){
            return "redirect:/projects";
        }
        postRepository.deleteById(id);
        return "redirect:/projects";
    }

    @GetMapping("/project/{id}/edit")
    public String editProject(@PathVariable(value = "id") long id, Model model){
        if(!checkedIn){
            return "signin";
        }
        if(!postRepository.existsById(id)){
            return "redirect:/projects";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        model.addAttribute("name", String.valueOf(login));
        return "project-edit";
    }

    //edit post
    @PostMapping("/project/{id}/edit")
    public String editUpdateProject(@PathVariable(value = "id")long id,@RequestParam String title,@RequestParam String anons,
                              @RequestParam String full_text, Model model){
        if(!checkedIn){
            return "signin";
        }
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/projects";
    }
}