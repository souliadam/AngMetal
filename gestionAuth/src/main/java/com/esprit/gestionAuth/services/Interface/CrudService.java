package com.esprit.gestionAuth.services.Interface;

public interface CrudService<T,ID>{
    void add(T t);
    void update(T t);
    void remove(ID id);
    T retrieve(ID id);
}

