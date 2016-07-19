package br.com.ciscience.scienceci.model.dao.local;

import br.com.ciscience.scienceci.model.entity.impl.Student;

/**
 * Created by pedrodimoura on 19/07/16.
 */
public interface IUserLocalAPI {

    void setSession(Student student);

    Student getSession();

    void destroySession();

}
