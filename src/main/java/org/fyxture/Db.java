package org.fyxture;

public class Db {
  private FileRepository repository;
  private SQLExecutor executor;

  public Db(FileRepository repository, SQLExecutor executor) {
    this.repository = repository;
    this.executor = executor;
  }

  public void load(String name) {
    executor.go(compile(repository.get(name)));
  }

  private String compile(String content){
    String result = "";
    String [] lines = content.split("\n");
    for(int i = 0, l = lines.length; i < l; i++){
      String line = lines[i];
      if(line.charAt(0) == '!') {
        String next = line.substring(1);
        result += compile(repository.get(next));
      }
      if(i + 1 < l) {
        result += "\n";
      }
    }
    return result;
  }
}
