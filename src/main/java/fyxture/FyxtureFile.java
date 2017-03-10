package fyxture;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;


public class FyxtureFile {
  private static Map<String, FyxtureFile> instances = new HashMap<String, FyxtureFile>();
  private String name;
  private File file;
  private String content;
  private InputStream stream;

  public static FyxtureFile file(String name) {
    if(!instances.containsKey(name)) {
      instances.put(name, new FyxtureFile(name));
    }
    return instances.get(name);
  }

  private FyxtureFile(String name) {
    this.name = name;
    this.file = new File(FyxtureFile.class.getClassLoader().getResource(name).getPath());
  }

  public String name() {
    return name;
  }

  public String path() {
    return file.getPath();
  }

  public String content() throws Throwable {
    if(content == null) {
      content = FileUtils.readFileToString(file, "UTF-8");
    }
    return content;
  }

  public InputStream stream() throws Throwable {
    if(stream == null) {
      stream = new FileInputStream(file);
    }
    return stream;
  }
}
