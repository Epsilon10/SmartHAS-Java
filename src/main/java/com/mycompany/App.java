package com.mycompany;

import com.typesafe.config.Config;
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

import javax.sql.DataSource;
import java.sql.Connection;
import com.typesafe.config.Config;

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

        User user = require(DBI.class).inTransaction((handle,status) -> {
            UserDB userDB = handle.attach(UserDB.class);
            return userDB.getUser(loginForm.getUsername());
        });

        return (user != null) ? Results.redirect("/home") : Results.redirect("/login");


    });




  }


  public static void main(final String[] args) {
    run(App::new, args);
  }



}
