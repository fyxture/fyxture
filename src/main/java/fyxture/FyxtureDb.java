package fyxture;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;


public class FyxtureDb {
  private static Map<String, FyxtureDb> dbs = new HashMap<String, FyxtureDb>();

  private String name;
  private Map <String, Liquibase> files;

  public FyxtureDb(String name) {
    this.name =  name;
    this.files = new HashMap<String, Liquibase>();
  }

  public static FyxtureDb db(String name) throws Throwable {
    if(!dbs.containsKey(name)) {
      dbs.put(name, new FyxtureDb(name));
    }
    return dbs.get(name);
  }

  public FyxtureDb go(String file) throws Throwable {
    liquibase(file).update(contexts("test"));
    return this;
  }

  private Contexts contexts(String... context) {
    return new Contexts(context);
  }

  private Connection connection() throws Throwable {
    Class.forName(property("driver"));
    return DriverManager.getConnection(property("url"), property("user"), property("password"));
  }

  private String property(String key) throws Throwable {
    return Fyxture.properties(name).get(key);
  }

  private Database database() throws Throwable {
    return (Database) DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection()));
  }

  private Liquibase liquibase(String file) throws Throwable {
    if(!files.containsKey(file)) {
      Liquibase lb = new Liquibase(file, new ClassLoaderResourceAccessor(), database());
      lb.clearCheckSums();
      files.put(file, lb);
    }
    return files.get(file);
  }

  public static void main(String... args) throws Throwable {
    db(System.getProperty("fyxture.db.name"))
    .go(System.getProperty("fyxture.db.go.file"));
  }
}
