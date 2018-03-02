package com.mycompany;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.helpers.MapResultAsBean;

import java.util.Optional;

public interface UserDB {

    @SqlUpdate("INSERT INTO user_details (:username, :password)")
    void insertUser(@BindBean User user);

    @SqlQuery("SELECT username, password FROM user_details WHERE username=:username")
    @MapResultAsBean
    User getUser(@Bind("username") String username);



}
