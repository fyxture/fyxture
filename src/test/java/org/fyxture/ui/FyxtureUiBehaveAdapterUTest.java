package org.fyxture.ui;

import br.gov.frameworkdemoiselle.behave.parser.jbehave.*;
import org.junit.*;
import static org.mockito.Mockito.*;

public class FyxtureUiBehaveAdapterUTest {
  CommonSteps steps = mock(CommonSteps.class);
  FyxtureUiBehaveAdapter adapter = new FyxtureUiBehaveAdapter(steps);

  @Test public void interpret() throws Throwable {
    adapter.interpret("go Autenticação");
    verify(steps).goToWithName("Autenticação");

    adapter.interpret("click Entrar");
    verify(steps).clickButton("Entrar");

    adapter.interpret("type 84571713304 CPF");
    verify(steps).inform("84571713304", "CPF");

    adapter.interpret("on Principal");
    verify(steps).pageWithName("Principal");

    adapter.interpret("visible 'Sua situação acadêmica é: aprovado'");
    verify(steps).textVisible("Sua situação acadêmica é: aprovado");
  }

  @Test public void interpret_many_lines() throws Throwable {
    adapter.interpret("go Autenticação\nclick Entrar\ntype 84571713304 CPF\non Principal\nvisible 'Sua situação acadêmica é: aprovado'");

    verify(steps).goToWithName("Autenticação");
    verify(steps).clickButton("Entrar");
    verify(steps).inform("84571713304", "CPF");
    verify(steps).pageWithName("Principal");
    verify(steps).textVisible("Sua situação acadêmica é: aprovado");
  }
}
