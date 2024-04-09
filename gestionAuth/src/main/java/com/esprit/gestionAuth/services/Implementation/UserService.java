package com.esprit.gestionAuth.services.Implementation;

import com.esprit.gestionAuth.persistence.entity.Role;
import com.esprit.gestionAuth.persistence.entity.User;
import com.esprit.gestionAuth.repositories.RoleRepository;
import com.esprit.gestionAuth.repositories.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userDao;

    @Autowired
    private RoleRepository roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailServiceImpl emailServ;

    public User updateUser(String userName, User updatedUser) {
        // Find the existing user by their username
        User existingUser = userDao.findById(userName).orElse(null);
        Set<Role> userRoles = existingUser.getRole();
        if (existingUser != null) {
            // Update user properties with the values from updatedUser
            updatedUser.setUserName(existingUser.getUserName());
            updatedUser.setUserEmail(existingUser.getUserEmail());
            updatedUser.setUserPassword(existingUser.getUserPassword());
            // Save the updated user
            updatedUser.setRole(userRoles);
            // As
            return userDao.save(updatedUser);
        }

        return null; // User with the given username not found
    }




    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("adminBizmatch");
        adminUser.setUserPassword(getEncodedPassword("adminadmin"));
        adminUser.setUserFirstName("Bizmatch");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);



        Role EntreprisesRole = new Role();
        EntreprisesRole.setRoleName("Entreprises");
        EntreprisesRole.setRoleDescription("Entreprises role");
        roleDao.save(EntreprisesRole);


        Role ReprésentantRole = new Role();
        ReprésentantRole.setRoleName("Représentant");
        ReprésentantRole.setRoleDescription("Operateur role");
        roleDao.save(ReprésentantRole);

        Role entrepriseRole = new Role();
        entrepriseRole.setRoleName("entreprise");
        entrepriseRole.setRoleDescription("entreprise role");
        roleDao.save(entrepriseRole);

        Role VisiteurRole = new Role();
        VisiteurRole.setRoleName("Visiteur");
        VisiteurRole.setRoleDescription("Visiteur role");
        roleDao.save(VisiteurRole);

        Role InvestisseurRole = new Role();
        InvestisseurRole.setRoleName("Investisseur");
        InvestisseurRole.setRoleDescription("Investisseur role");
        roleDao.save(InvestisseurRole);

        Role CollaborateurRole = new Role();
        CollaborateurRole.setRoleName("Collaborateur");
        CollaborateurRole.setRoleDescription("Collaborateur role");
        roleDao.save(CollaborateurRole);

        Role EntrepreneurRole = new Role();
        EntrepreneurRole.setRoleName("Entrepreneur");
        EntrepreneurRole.setRoleDescription("Entrepreneur role");
        roleDao.save(CollaborateurRole);




    }

    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        user.setIsverified(0);
        emailServ.sendVerificationEmail(user);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        userRoles.add(role);

        user.setRole(userRoles);
        user.setRoleDemander(user.getRoleDemander());
        return userDao.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }


    public void delete(String userName){
        User u= userDao.findById(userName).orElse(null);
        u.getRole().clear();
        userDao.delete(u);
    }

    public void addRoleToUser(String roleName, String user)
    {
        Role r = roleDao.findById(roleName).orElse(null);
        User u= userDao.findById(user).orElse(null);
        Set<Role> userRoles = u.getRole();
        userRoles.add(r);
        u.setRole(userRoles);
        userDao.save(u);
    }

    public long count(){
      long count=userDao.count();
      return count;
    }
    public long countoperateur(){
        long countoperateur=0;
        List<User> users=userDao.findAll();
        for(User user:users) {

        Set<Role> roles=user.getRole();
        Role role= roles.iterator().next();
        String rolename = role.getRoleName();
                if(rolename.equals("User")){
                    countoperateur+=1;
                    }
        }
            return countoperateur;
    }
    public long countadmin(){
        long countadmin=0;
        List<User> users=userDao.findAll();
        for(User user:users) {

            Set<Role> roles=user.getRole();
            Role role= roles.iterator().next();
            String rolename = role.getRoleName();
            if(rolename.equals("Admin")){
                countadmin+=1;
            }
        }
        return countadmin;
    }
    public long countusers(){
        long countusers=0;
        List<User> users=userDao.findAll();
        for(User user:users) {

            Set<Role> roles=user.getRole();
            Role role= roles.iterator().next();
            String rolename = role.getRoleName();
            if(rolename.equals("User")){
                countusers +=1;
            }
        }
        return countusers;
    }


    public static final String ACCOUNT_SID = "ACa9b8ab43f7a7e83f03838cc677d4c33b";
    public static final String AUTH_TOKEN = "41d34221dc8ac054654f2da256dbe99a";

    public static final String sender_number ="+15673131960";

    public void sms(String userName){
        User user = userDao.findById(userName).orElse(null);
        // Find your Account Sid and Token at twilio.com/user/account
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber("+216"+ user.getUserNumber()),
                new PhoneNumber(sender_number),
                "This is the ship that made the Kessel Run in fourteen parsecs?").create();

        System.out.println(message.getSid());
    }

    // Reset Password:
    public boolean ifEmailExist(String UserEmail){
        return userDao.existsByUserEmail(UserEmail);
    }

    @Transactional
    public String getPasswordByUserEmail(String userEmail){
        return userDao.getPasswordByUserEmail(userEmail);
    }

    public User findByUserEmail(String UserEmail)
    {
        return this.userDao.findByUserEmail(UserEmail);
    }

    public void editUser(User user){
        this.userDao.save(user);
    }

    public User activateUser(String token) {
        User user = userDao.findByVerificationToken(token);
        if (user != null) {
            user.setIsverified(1);
            user.setVerificationToken(null);
            userDao.save(user);
        }
        return user;
    }


    public User retrieve(String username) {
        try{
            return  userDao.findById(username).get();
        } catch (Exception err) {
            System.out.println("Un erreur est survenue : " + err);
        }
        return null;
    }


}
