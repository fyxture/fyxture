package org.fyxture;

import java.text.MessageFormat;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class Db {
  private Logger logger = Logger.getLogger(Db.class);
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
      Line line = new Line(lines[i], parameters);
      if(line.literal()) {
        String filled = line.fill();
        logger.info(String.format("%s : %s", "literal", filled));
        result += filled;
      }else{
//        parameters = merge(parameters, line);
        String compiled = compile(repository.get(line.file()), parameters);
        logger.info(String.format("%s : %s", "not literal", compiled));
        logger.info(String.format("parameters : %s", Arrays.asList(parameters).toString()));
        result += compiled;
      }
      if(i + 1 < l) {
        result += "\n";
      }
    }
    return result;
  }

  // private Object [] merge(Object [] parameters, Line line) {
  //   Integer p = parameters.length;
  //   Integer l = line.args().size();
  //   Object [] result = new Object[greater(p, l)];
  //   for(int i = 0, s = result.length; i < s; i++) {
  //     if(p > i) {
  //       result[i] = parameters[i];
  //     }
  //     if(l > i) {
  //       result[i] = line.args(i);
  //     }
  //   }
  //   return result;
  // }

  // private Integer greater(Integer a, Integer b) {
  //   return a > b ? a : b;
  // }
}
