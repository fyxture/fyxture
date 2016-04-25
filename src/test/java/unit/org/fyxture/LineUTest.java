package unit.org.fyxture;

import org.fyxture.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.assertj.core.api.Assertions.*;

import java.text.MessageFormat;

public class LineUTest {
  @Test public void fill() {
    assertThat(Line.create("DELETE FROM ALUNO;").fill()).isEqualTo("DELETE FROM ALUNO;");
    assertThat(Line.create("DELETE FROM {0};").fill()).isEqualTo("DELETE FROM {0};");
    assertThat(Line.create("DELETE FROM {0};", "ALUNO").fill()).isEqualTo("DELETE FROM ALUNO;");
    assertThat(Line.create("DELETE FROM {0} WHERE ID = {1};", "ALUNO", 1).fill()).isEqualTo("DELETE FROM ALUNO WHERE ID = 1;");

    assertThat(Line.create("!limpar").fill()).isEqualTo("!limpar:");
    assertThat(Line.create("!limpar:").fill()).isEqualTo("!limpar:");
    assertThat(Line.create("!limpar: {0}").fill()).isEqualTo("!limpar: {0}");
    assertThat(Line.create("!limpar: {0}", "ALUNO").fill()).isEqualTo("!limpar: ALUNO");

    assertThat(Line.create("!limpar/delete").fill()).isEqualTo("!limpar/delete:");
    assertThat(Line.create("!limpar/delete:").fill()).isEqualTo("!limpar/delete:");
    assertThat(Line.create("!limpar/delete: {0}").fill()).isEqualTo("!limpar/delete: {0}");
    assertThat(Line.create("!limpar/delete: {0}", "ALUNO").fill()).isEqualTo("!limpar/delete: ALUNO");
  }

  @Test public void literal() {
    assertTrue(Line.create("DELETE FROM ALUNO;").literal());
    assertTrue(Line.create("DELETE FROM {0};").literal());
    assertFalse(Line.create("!limpar").literal());
    assertFalse(Line.create("!limpar:").literal());
    assertFalse(Line.create("!limpar: {0}").literal());
  }

  @Test public void behave() {
    assertThat(MessageFormat.format("DELETE FROM {0};", "ALUNO")).isEqualTo("DELETE FROM ALUNO;");
    assertThat(MessageFormat.format("DELETE FROM {1};", new Object[]{})).isEqualTo("DELETE FROM {1};");
  }
}
