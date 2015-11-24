package org.fyxture;

import java.text.MessageFormat;

public class Db {
  private FileRepository repository;
  private SQLExecutor executor;

  public Db(FileRepository repository, SQLExecutor executor) {
    this.repository = repository;
    this.executor = executor;
  }

  public void load(String name, Object... parameters) {
    executor.go(compile(repository.get(name), parameters));
  }

  private String compile(String content, Object... parameters) {
    String result = "";
    String [] lines = content.split("\n");
    for(int i = 0, l = lines.length; i < l; i++) {
      Line line = new Line(lines[i]);
      //String line = lines[i];
      if(line.literal()) {
        result += line.fill(parameters);
      }else{
        result += compile(repository.get(line.file()), parameters);
      }
      // if(line.charAt(0) == '!') {
      //   String next = line.substring(1);
      //   result += compile(repository.get(next), parameters);
      // } else {
      //   result += fill(line, parameters);
      // }
      if(i + 1 < l) {
        result += "\n";
      }
    }
    return result;
  }
}
