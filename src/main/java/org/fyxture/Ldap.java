package org.fyxture;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.apache.commons.io.*;
import javax.naming.*;
import javax.naming.directory.*;
import org.fyxture.utils.*;

public class Ldap extends Loadable {
  private final String user;
  private final String password;
  private final String host;
  private final String port;
  private final String base;

  public Ldap(Fyxture patern, Properties properties, String name) throws Throwable {
    super(patern);
    this.user = (String)properties.get("fyxture.ldap." + name + ".user");
    this.password = (String)properties.get("fyxture.ldap." + name + ".password");
    this.host = (String)properties.get("fyxture.ldap." + name + ".host");
    this.port = (String)properties.get("fyxture.ldap." + name + ".port");
    this.base = (String)properties.get("fyxture.ldap." + name + ".base");
  }

  public Loadable remove(String... names) throws Throwable {
    if(names.length == 0) {
      for(String name : all()) {
        context().unbind("uid=" + name);
      }
    }
    for(String name : names) {
      context().unbind("uid=" + name);
    }
    return this;
  }

  public Loadable load(String name, Object... parameters) throws Throwable {
    List<String> attributes = FileUtils.readLines(file(name));
    bind(context(), attributes);
    return this;
  }

  private DirContext context() throws Throwable {
    String server = String.format("%s:%s", host, port);
    Properties props = new Properties();
    props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    props.put(Context.PROVIDER_URL, "ldap://" + server + "/" + base);
    props.put(Context.SECURITY_CREDENTIALS, password);
    props.put(Context.SECURITY_PRINCIPAL, user);
    return new InitialDirContext(props);
  }

  private void bind(DirContext context, List<String> attributes) throws Throwable {
    Attributes attrs = new BasicAttributes(true);
    String name = null;
    for(String attribute : attributes) {
      String key = LdifUtils.key(attribute);
      String value = LdifUtils.value(attribute);
      if (key.equals("uid")) {
        name = name(value);
      }
      attrs.put(new BasicAttribute(key, value));
    }
    ((InitialDirContext)context).bind(name, context, attrs);
  }

  private String name(String uid) throws Throwable {
    return "uid=" + uid;
  }

  private List<String> all() throws Throwable {
    List<String> list =  new ArrayList<String>();
    DirContext context = (DirContext)context();
    SearchControls controls  = new SearchControls();
    controls.setReturningAttributes(new String [] {"uid"});
    controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
    NamingEnumeration<SearchResult> answer = context.search("", "(objectClass=person)", controls);
    while(answer.hasMore()){
      list.add((String)((SearchResult) answer.next()).getAttributes().get("uid").get());
    }
    return list;
  }
}
