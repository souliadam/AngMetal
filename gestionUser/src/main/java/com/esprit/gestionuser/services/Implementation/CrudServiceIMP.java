package com.esprit.gestionuser.services.Implementation;
import com.esprit.gestionuser.persistence.entity.User;
import com.esprit.gestionuser.repository.CrudRepository;
import com.esprit.gestionuser.repository.UserRepository;
import com.esprit.gestionuser.services.Interface.CrudService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class CrudServiceIMP<T,ID> implements CrudService<T,ID> {

    @Autowired
    public CrudRepository<T,ID> crudRepository;

    @Autowired
    private UserRepository userDao;
    @Override
    public List<T> retrieveAll() {
        try{
            return  crudRepository.findAll();
        } catch (Exception err) {
            System.out.println("Un erreur est survenue : " + err);
        }
        return null;
    }

    @Override
    public void add(T t) {
        try{
            crudRepository.save(t);
        } catch (Exception err) {
            System.out.println("Un erreur est survenue : " + err);
        }

    }

    @Override
    public void update(T t) {
        try {
            System.out.println("mise a jour avec succes");
            crudRepository.save(t);
        } catch (Exception err) {
            System.out.println("Un erreur est survenue : " + err);
        }

    }

    @Override
    public void remove(ID id) {
        try{
            T t = crudRepository.findById(id).orElse(null);
            crudRepository.delete(t);
        } catch (Exception err) {
            System.out.println("Un erreur est survenue : " + err);
        }
    }

    @Override
    public T retrieve(ID id) {
        try{
            return  crudRepository.findById(id).get();
        } catch (Exception err) {
            System.out.println("Un erreur est survenue : " + err);
        }
        return null;
    }

}



