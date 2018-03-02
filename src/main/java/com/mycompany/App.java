package com.mycompany;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.Optional;
import org.jooby.Jooby;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.jdbc.Jdbc;
import org.jooby.jdbi.Jdbi;
import org.jooby.jedis.Redis;
import org.jooby.jedis.RedisSessionStore;
import org.jooby.json.Jackson;
import org.jooby.pebble.Pebble;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import java.util.*;

import javax.sql.DataSource;
import javax.swing.text.html.Option;
import java.sql.Connection;
import java.util.HashMap;


import redis.clients.jedis.Jedis;

/**
 * @author jooby generator
 */
public class App extends Jooby {
    Config conf;
  {

    assets("/assets/**");
    use(new Jackson());
    use(new Pebble());
    use(new Jdbc());
    use(new Jdbi("db"));


    use(new Redis("redis_db"));
    session(RedisSessionStore.class);
    //DataSource db = require(DataSource.class);
    get("/", () -> "Hello World!");
    get("/login", req -> Results.html("login"));

    post("/", req -> Results.html("home"));
    post("/login", req -> {
        LoginForm loginForm = req.form(LoginForm.class);

        User user = require(DBI.class).inTransaction((handle, status) -> {
            UserDB userDB = handle.attach(UserDB.class);

            return userDB.getUser(loginForm.getUsername());
        });

        if (user != null) {
            if(user.getPassword().equals(loginForm.getPassword())) {
                ObjectMapper oMapper = new ObjectMapper();
                Map<String, User> userMap = oMapper.convertValue(user,Map.class);
                req.session().set("user", userMap.toString());
                return Results.redirect("/home");
            }

            return Results.html("login").put("error", "Your password was incorrect");
        }

        return Results.html("login").put("error", "An account does not exist with this username");

    });

    get("/logout", req -> {
        req.session().destroy();
        return Results.json("d");
    });




  }


  public static void main(final String[] args) {
    run(App::new, args);
  }



}
