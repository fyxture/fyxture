package org.fyxture;

import java.util.*;
import java.io.*;

public class Fyxture {
  private static Fyxture instance;
  private static Type type = Type.DB;
  private static Loadable loadable;
  private static Properties properties = new Properties();

  private Fyxture() throws Throwable {
    properties.load(Fyxture.class.getClassLoader().getResource("fyxture.properties").openStream());
  }

  private static Fyxture instance() throws Throwable {
    if (instance == null) {
      instance = new Fyxture();
    }
    return instance;
  }

  public static Db db(String name) throws Throwable {
    return new Db(instance(), properties, name);
  }

  public static Db db() throws Throwable {
    return db("default");
  }

  public static Ldap ldap(String name) throws Throwable {
    return new Ldap(instance(), properties, name);
  }

  public static Ldap ldap() throws Throwable {
    return ldap("default");
  }

  public static Ui ui(String name) throws Throwable {
    return new Ui(instance(), properties, name);
  }

  public static Ui ui() throws Throwable {
    return ui("default");
  }

  public static Fyxture load(String name, Object... parameters) throws Throwable {
    return load(type, name, parameters);
  }

  public static Fyxture load(Type typeOf, String name, Object... parameters) throws Throwable {
    switch(type = typeOf) {
      case DB:
        loadable = db();
        break;
      case LDAP:
        loadable = ldap();
        break;
      case UI:
        loadable = ui();
        break;
      default:
        throw new IllegalArgumentException(type + " não reconhecido!");
    }
    loadable.load(name, parameters);
    return instance();
  }
}
