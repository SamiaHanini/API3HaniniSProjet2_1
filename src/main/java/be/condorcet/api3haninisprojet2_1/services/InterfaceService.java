package be.condorcet.api3haninisprojet2_1.services;

import java.util.List;

public interface InterfaceService<T> {

    T create(T t) throws Exception;
    T read(Integer id) throws Exception;
    T update(T t) throws Exception;
    void delete(T t) throws Exception;
    List<T> all() throws Exception;
    
}
