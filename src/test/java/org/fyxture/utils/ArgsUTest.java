package org.fyxture.utils;

import org.fyxture.exception.*;
import java.util.*;
import java.util.regex.*;
import org.junit.*;

public class ArgsUTest {
  @Test public void all() throws Throwable {
    Assert.assertEquals(Arrays.asList("84571713304", "CPF"), create("84571713304 CPF").all());
    Assert.assertEquals(Arrays.asList("84571713304 CPF"), create("'84571713304 CPF'").all());
    Assert.assertEquals(Arrays.asList("84571713304 CPF"), create("\"84571713304 CPF\"").all());
    Assert.assertEquals(Arrays.asList("primeiro", "segundo argumento", "mais um argumento"), create("primeiro 'segundo argumento' \"mais um argumento\"").all());
    Assert.assertEquals(Arrays.asList("primeiro", "segundo argumento", "mais um argumento"), create("primeiro       'segundo argumento'         \"mais um argumento\"").all());
    Assert.assertEquals(Arrays.asList("    primeiro", "segundo argumento", "mais um argumento"), create("'    primeiro'       'segundo argumento'         \"mais um argumento\"").all());
  }

  @Test(expected=QuotedArgBadFormatedException.class)
  public void single_quoted_arg_bad_formated() throws Throwable {
    create("'84571713304 CPF");
  }

  @Test(expected=QuotedArgBadFormatedException.class)
  public void double_quoted_arg_bad_formated() throws Throwable {
    create("\"84571713304 CPF");
  }

  private Args create(String path) throws QuotedArgBadFormatedException {
    return new Args(path);
  }
}
