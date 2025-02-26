package eddie.project.cinemabookingsystemgenericdao.dao;

import java.util.List;

public interface GenericDao<T, ID> {
    void insert(T entity);//C

    T findById(ID id);//R

    List<T> findAll();//R

    void update(T entity);//U

    void deleteById(ID id);//D
}
