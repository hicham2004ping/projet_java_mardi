package ma.prodenta.mvc.controllers.modules.auth;

import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.service.modules.auth.AuthService;

/*
   GET  /login  affiche login page
   POST /login  traite login
   GET  /logout logout
 */
@Controller
public class AuthController {

    private final AuthService authService = new AuthService();

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String login,
                          @RequestParam String motdepasse,
                          Model model) {

        Utilisateur user = authService.login(login,motdepasse);
        if (user == null) {
            model.addAttribute("error", "Nom d'utilisateur ou mot de passe invalide");
            return "login";
        }

        // stocker utilisateur en session
        session.setAttribute("currentUser", user);

        // rediriger selon role
        if ("ADMIN".equalsIgnoreCase(user.getIdRole())) {
            return "redirect:/admin";
        } else {
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
