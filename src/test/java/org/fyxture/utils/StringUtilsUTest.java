package org.fyxture.utils;

import org.junit.*;
import java.util.*;

public class StringUtilsUTest {
  @Test public void s() {
    Assert.assertEquals("Sua situação acadêmica é: aprovado", StringUtils.s("Sua situação acadêmica é: {0}", "aprovado"));
  }
}
