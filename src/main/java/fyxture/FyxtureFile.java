package fyxture;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;


public class FyxtureFile {
  private static Map<String, FyxtureFile> instances = new HashMap<String, FyxtureFile>();
  private String name;
  private File file;
  private String content;
  private InputStream stream;
  private String encoding = "UTF-8";
  private boolean encodingHadChanged = false;

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

  public String content() throws FyxtureException {
    if(content == null) {
      try {
        content = FileUtils.readFileToString(file, "UTF-8");
      } catch(IOException ioe) {
        throw new FyxtureException(ioe);
      }
    }
    return content;
  }

  public String content(String encoding) throws FyxtureException {
    encoding(encoding);
    if(content == null) {
      _content();
    }
    return content;
  }

  public InputStream stream() throws FyxtureException {
    if(stream == null) {
      try {
        stream = new FileInputStream(file);
      } catch(FileNotFoundException fnfe) {
        throw new FyxtureException(fnfe);
      }
    }
    return stream;
  }

  private FyxtureFile encoding(String encoding) {
    if(encoding != null && this.encoding != null && !this.encoding.equals(encoding)) {
      this.encoding = encoding;
      encodingHadChanged(true);
    }
    return this;
  }

  private boolean encodingHadChanged() {
    return this.encodingHadChanged;
  }

  private void encodingHadChanged(boolean encodingHadChanged) {
    this.encodingHadChanged = encodingHadChanged;
  }

  private void _content() throws FyxtureException {
    try {
      content = FileUtils.readFileToString(file, encoding);
    } catch(IOException ioe) {
      throw new FyxtureException(ioe);
    }
    encodingHadChanged(false);
  }
}
