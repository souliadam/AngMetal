package com.esprit.gestionAuth.repositories;

import com.esprit.gestionAuth.persistence.entity.UserMail;

public interface IUserEmailRepository {
    public void sendCodeByMail(UserMail mail);
}
