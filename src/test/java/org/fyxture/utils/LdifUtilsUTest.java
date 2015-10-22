package org.fyxture.utils;

import org.junit.*;

public class LdifUtilsUTest {
  @Test public void key() {
    Assert.assertEquals("uid", LdifUtils.key("uid=84571713304"));
  }

  @Test public void value() {
    Assert.assertEquals("84571713304", LdifUtils.value("uid=84571713304"));
  }
}
