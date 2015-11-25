package unit.org.fyxture;

import org.fyxture.*;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

import java.text.MessageFormat;

public class LineUTest {
  @Test public void fill() {
    assertThat(new Line("DELETE FROM ALUNO;").fill(), equalTo("DELETE FROM ALUNO;"));
    assertThat(new Line("DELETE FROM {0};").fill(), equalTo("DELETE FROM {0};"));
    assertThat(new Line("DELETE FROM {0};", "ALUNO").fill(), equalTo("DELETE FROM ALUNO;"));
    assertThat(new Line("DELETE FROM {0} WHERE ID = {1};", "ALUNO", 1).fill(), equalTo("DELETE FROM ALUNO WHERE ID = 1;"));

    assertThat(new Line("!limpar").fill(), equalTo("!limpar:"));
    assertThat(new Line("!limpar:").fill(), equalTo("!limpar:"));
    assertThat(new Line("!limpar: {0}").fill(), equalTo("!limpar: {0}"));
    assertThat(new Line("!limpar: {0}", "ALUNO").fill(), equalTo("!limpar: ALUNO"));

    assertThat(new Line("!limpar/delete").fill(), equalTo("!limpar/delete:"));
    assertThat(new Line("!limpar/delete:").fill(), equalTo("!limpar/delete:"));
    assertThat(new Line("!limpar/delete: {0}").fill(), equalTo("!limpar/delete: {0}"));
    assertThat(new Line("!limpar/delete: {0}", "ALUNO").fill(), equalTo("!limpar/delete: ALUNO"));
  }

  @Test public void literal() {
    assertTrue(new Line("DELETE FROM ALUNO;").literal());
    assertTrue(new Line("DELETE FROM {0};").literal());
    assertFalse(new Line("!limpar").literal());
    assertFalse(new Line("!limpar:").literal());
    assertFalse(new Line("!limpar: {0}").literal());
  }

  @Test public void behave() {
    assertThat(MessageFormat.format("DELETE FROM {0};", "ALUNO"), equalTo("DELETE FROM ALUNO;"));
    assertThat(MessageFormat.format("DELETE FROM {1};", new Object[]{}), equalTo("DELETE FROM {1};"));
  }
}
