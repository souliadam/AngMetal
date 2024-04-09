package com.esprit.gestionAuth.rest;

import com.esprit.gestionAuth.persistence.entity.*;
import com.esprit.gestionAuth.repositories.UserRepository;
import com.esprit.gestionAuth.services.Implementation.EmailServiceImpl;
import com.esprit.gestionAuth.services.Implementation.UserService;
import com.esprit.gestionAuth.services.Implementation.VerificationTokenService;
import com.esprit.gestionAuth.util.UserCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userDao;
    @Autowired
    private EmailServiceImpl emailServ;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenService verificationTokenService;


    @GetMapping({"/hello"})
    public String hello() {
        return ("hello auth service");
    }


    @PutMapping("/updateUser/{username}")
    public User updateUser(@PathVariable String username , @RequestBody User userDetails) {

            return userService.updateUser(username,userDetails);

    }

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@Valid @RequestBody User user) {
        //user.setVerificationToken(verificationTokenService.generateVerificationToken());

        User savedUser = userService.registerNewUser(user);
        VerificationToken verificationToken = verificationTokenService.createVerificationToken(user); // création du jeton de vérification
        verificationTokenService.saveVerificationToken(verificationToken);


        // Logging whether a user was found

        return savedUser;
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin() {
        return "This URL is only accessible to the admin";
    }


    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser() {
        return "This URL is only accessible to the user";
    }

    @DeleteMapping({"/delete/{userName}"})
    @PreAuthorize("hasRole('Admin')")
    public void delete(@PathVariable String userName) {
        userService.delete(userName);
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('Admin')")
    public long count() {
        return userService.count();
    }

    @GetMapping("/countoperateur")
    @PreAuthorize("hasRole('Admin')")
    public long countoperateur() {
        return userService.countoperateur();
    }

    @GetMapping("/countadmin")
    @PreAuthorize("hasRole('Admin')")
    public long countadmin() {
        return userService.countadmin();
    }

    @GetMapping("/countusers")
    @PreAuthorize("hasRole('Admin')")
    public long countusers() {
        return userService.countusers();
    }

    @GetMapping({"/sms/{userName}"})
    public void SMS(@PathVariable String userName) {
        userService.sms(userName);
    }

    @PutMapping({"/addRole/{roleName}/{userName}"})
    @PreAuthorize("hasRole('Admin')")
    public void addRoleToUser(@PathVariable String roleName, @PathVariable String userName) {
        userService.addRoleToUser(roleName, userName);


    }
    //activate compte
    /*
    @PutMapping("/activate/{verificationToken}")
    public ResponseEntity<String> activateAccount(@PathVariable String verificationToken) {
        System.out.println("Received token: " + verificationToken); // Logging the received token
        User user = userService.activateUser(verificationToken);
        System.out.println("User found: " + (user != null)); // Logging whether a user was found

        if (user != null) {
            String to = user.getUserEmail();
            String subject = "Account Created";
            String text = "Your account has been created successfully.";
            emailServ.sendEmail(to, subject, text);
            return ResponseEntity.ok("Account activated successfully");
        } else {
            System.out.println("Failed to activate account with token: " + verificationToken); // Logging failure
            return ResponseEntity.badRequest().body("Failed to activate account");
        }
    }
    */
    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam String token) {
        User user = userService.activateUser(token);
        if (user != null) {
            return ResponseEntity.ok("Account activated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid activation token");
        }
    }













    // mail


    // http://localhost:8080/checkEmail
    @PostMapping("/checkEmail")
    public UserAccountResponse resetPasswordEmail(@RequestBody UserResetPassword resetPassword) {
        User user = this.userService.findByUserEmail(resetPassword.getEmail());
        UserAccountResponse accountResponse = new UserAccountResponse();
        if (user != null) {
            String code = UserCode.getCode();
            System.out.println("le code est" + code);
            UserMail mail = new UserMail(resetPassword.getEmail(), code);
            System.out.println("le mail est" + resetPassword.getEmail());
            System.out.println("la variable mail est" + mail);
            emailServ.sendCodeByMail(mail);
            System.out.println("la variable User est" + user);
            user.setUserCode(code);
            userDao.save(user);
            accountResponse.setResult(1);
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;
    }

    // http://localhost:8080/resetPassword
    @PostMapping("/resetPassword")
    public UserAccountResponse resetPassword(@RequestBody UserNewPassword newPassword) {
        User user = this.userService.findByUserEmail(newPassword.getEmail());
        UserAccountResponse accountResponse = new UserAccountResponse();
        if (user != null) {
            if (user.getUserCode().equals(newPassword.getCode())) {
                user.setUserPassword(passwordEncoder.encode(newPassword.getPassword()));
                userDao.save(user);
                accountResponse.setResult(1);
            } else {
                accountResponse.setResult(0);
            }
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;
    }
    @ControllerAdvice
    public class CommandeControllerAdvice {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ResponseBody
        public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return errors;
        }
    }
}
