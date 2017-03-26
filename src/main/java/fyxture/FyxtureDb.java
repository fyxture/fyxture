package fyxture;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import liquibase.Contexts;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.Liquibase;
import liquibase.resource.ClassLoaderResourceAccessor;


public class FyxtureDb {
  private static Map<String, FyxtureDb> dbs = new HashMap<String, FyxtureDb>();

  private String name;
  private Map <String, Liquibase> files;

  public FyxtureDb(String name) {
    this.name =  name;
    this.files = new HashMap<String, Liquibase>();
  }

  public FyxtureDb go(String file) throws FyxtureException {
    //TODO: trocar referencia inline por configuracao em arquivo de propriedades padrão
    try {
      liquibase(file).update(contexts("test"));
    } catch(LiquibaseException le) {
      throw new FyxtureException(le);
    }
    return this;
  }

  private Contexts contexts(String... context) {
    return new Contexts(context);
  }

  private Connection connection() throws FyxtureException {
    //TODO: trocar referencia inline por configuracao em arquivo de propriedades padrão
    try {
      Class.forName(property("driver"));
      return DriverManager.getConnection(property("url"), property("username"), property("password"));
    } catch(ClassNotFoundException cnfe) {
      throw new FyxtureException(cnfe);
    } catch(SQLException sqle) {
      throw new FyxtureException(sqle);
    }
  }

  private String property(String key) throws FyxtureException {
    return Fyxture.properties(name).get(key);
  }

  private Database database() throws FyxtureException {
    try {
      return DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection()));
    } catch(DatabaseException de) {
      throw new FyxtureException(de);
    }
    // } catch(LiquibaseException le) {
    //   throw new FyxtureException(le);
    // }
  }

  private Liquibase liquibase(String file) throws FyxtureException {
    if(!files.containsKey(file)) {
      Liquibase lb;
      try {
        lb = new Liquibase(file, new ClassLoaderResourceAccessor(), database());
        lb.clearCheckSums();
      } catch(LiquibaseException le) {
        throw new FyxtureException(le);
      }
      files.put(file, lb);
    }
    return files.get(file);
  }

  public static FyxtureDb db(String name) throws FyxtureException {
    if(!dbs.containsKey(name)) {
      dbs.put(name, new FyxtureDb(name));
    }
    return dbs.get(name);
  }

  public static void main(String... args) throws FyxtureException {
    db(System.getProperty("fyxture.db.name"))
    .go(System.getProperty("fyxture.db.go.file"));
  }
}
