package org.fyxture.utils;

import org.junit.*;
import java.util.*;

public class ActionCommandsUtilsUTest {
  @Test public void command() throws Throwable {
    Assert.assertEquals("go", ActionCommandsUtils.command("go Autenticação"));
    Assert.assertEquals("click", ActionCommandsUtils.command("click Entrar"));
    Assert.assertEquals("type", ActionCommandsUtils.command("type 84571713304 CPF"));
    Assert.assertEquals("on", ActionCommandsUtils.command("on Principal"));
    Assert.assertEquals("click", ActionCommandsUtils.command("click \"Solicitar Situação Acadêmica\""));
    Assert.assertEquals("visible", ActionCommandsUtils.command("visible 'Sua situação acadêmica é: aprovado'"));
  }

  @Test public void args() throws Throwable {
    Assert.assertEquals(Arrays.asList("Autenticação"), ActionCommandsUtils.args("go Autenticação"));
    Assert.assertEquals(Arrays.asList("Entrar"), ActionCommandsUtils.args("click Entrar"));
    Assert.assertEquals(Arrays.asList("84571713304", "CPF"), ActionCommandsUtils.args("type 84571713304 CPF"));
    Assert.assertEquals(Arrays.asList("Principal"), ActionCommandsUtils.args("on Principal"));
    Assert.assertEquals(Arrays.asList("Solicitar Situação Acadêmica"), ActionCommandsUtils.args("click \"Solicitar Situação Acadêmica\""));
    Assert.assertEquals(Arrays.asList("Sua situação acadêmica é: aprovado"), ActionCommandsUtils.args("visible 'Sua situação acadêmica é: aprovado'"));
  }
}
