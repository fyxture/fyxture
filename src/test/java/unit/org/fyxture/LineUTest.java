package unit.org.fyxture;

import org.fyxture.*;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

public class LineUTest {
  @Test public void fill() {
    assertThat(new Line("DELETE FROM ALUNO;").fill(), equalTo("DELETE FROM ALUNO;"));
    assertThat(new Line("DELETE FROM {0};").fill(), equalTo("DELETE FROM {0};"));
    assertThat(new Line("DELETE FROM {0};").fill("ALUNO"), equalTo("DELETE FROM ALUNO;"));
    assertThat(new Line("DELETE FROM {0} WHERE ID = {1};").fill("ALUNO", 1), equalTo("DELETE FROM ALUNO WHERE ID = 1;"));

    assertThat(new Line("!limpar").fill(), equalTo("!limpar"));
    assertThat(new Line("!limpar:").fill(), equalTo("!limpar:"));
    assertThat(new Line("!limpar: {0}").fill(), equalTo("!limpar: {0}"));
    assertThat(new Line("!limpar: {0}").fill("ALUNO"), equalTo("!limpar: ALUNO"));

    // assertThat(new Line("!limpar/delete").fill(), equalTo("!limpar/delete"));
    // assertThat(new Line("!limpar/delete:").fill(), equalTo("!limpar/delete"));
    // assertThat(new Line("!limpar/delete: {0}").fill(), equalTo("!limpar/delete: {0}"));
    // assertThat(new Line("!limpar/delete: {0}").fill("ALUNO"), equalTo("!limpar/delete: ALUNO"));
  }

  @Test public void literal() {
    assertTrue(new Line("DELETE FROM ALUNO;").literal());
    assertTrue(new Line("DELETE FROM {0};").literal());
    assertFalse(new Line("!limpar").literal());
    assertFalse(new Line("!limpar:").literal());
    assertFalse(new Line("!limpar: {0}").literal());
  }
}
