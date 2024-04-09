package com.esprit.gestionuser.persistence.entity;

import com.esprit.gestionuser.persistence.enumeration.Domaines;
import com.esprit.gestionuser.persistence.enumeration.RoleDemander;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @NotBlank(message = "Ce champ est obligatoire")
    @Size(min = 3, message = "Ce champs doit contenir au moins 3 caractères")
    private String userName;
    @NotBlank(message = "Ce champ est obligatoire")
    @Size(min = 3, message = "Ce champs doit contenir au moins 3 caractères")
    @NotNull
    private String userFirstName;
    @NotBlank(message = "Ce champ est obligatoire")
    @Size(min = 3, message = "Ce champs doit contenir au moins 3 caractères")
    @NotNull
    private String userLastName;
    private String userPassword;
    @Pattern(regexp = "[0-9]{8}", message = "Le numéro doit être composé de 8 chiffres")
    private String userNumber;
    private String userCode;
    @Email
    private String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getIsverified() {
        return isverified;
    }

    public void setIsverified(int isverified) {
        this.isverified = isverified;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    private int isverified;

    private String verificationToken;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }



    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    @Enumerated(EnumType.STRING)
    private RoleDemander roleDemander;

    public RoleDemander getRoleDemander() {
        return roleDemander;
    }

    public void setRoleDemander(RoleDemander roleDemander) {
        this.roleDemander = roleDemander;
    }



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public com.esprit.gestionuser.persistence.enumeration.Domaines getDomaines() {
        return Domaines;
    }

    public void setDomaines(com.esprit.gestionuser.persistence.enumeration.Domaines domaines) {
        Domaines = domaines;
    }

    private String location;
    private String aboutMe;
    private String siteWeb;
    private String facebook;
    private String linkedIn;
    @Enumerated(EnumType.STRING)
    private Domaines Domaines;
}
