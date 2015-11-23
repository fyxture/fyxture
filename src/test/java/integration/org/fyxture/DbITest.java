package integration.org.fyxture;

import org.fyxture.*;
import org.junit.*;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

public class DbITest {
  @Test public void trivial() {
    given:;
      FileRepository repository = mock(FileRepository.class);
      when(repository.get("limpar")).thenReturn("DELETE FROM ALUNO;");

      SQLExecutor executor = mock(SQLExecutor.class);

      Db db = new Db(repository, executor);

    when:;
      db.load("limpar");

    then:;
      String content = "DELETE FROM ALUNO;";
      verify(executor).go(content);
  }

  // @Test public void com_parâmetros() {
  //   given:;
  //     FileRepository repository = mock(FileRepository.class);
  //     when(repository.get("limpar")).thenReturn("DELETE FROM {0};");

  //     SQLExecutor executor = mock(SQLExecutor.class);

  //     Db db = new Db(repository, executor);

  //   when:;
  //     db.load("limpar", "ALUNO");

  //   then:;
  //     String content = "DELETE FROM ALUNO;";
  //     verify(executor).go(content);
  // }

  // @Test public void com_um_nível_de_referência() {
  //   given:;
  //     FileRepository repository = mock(FileRepository.class);
  //     when(repository.get("limpar")).thenReturn("!limpar/delete");
  //     when(repository.get("limpar/delete")).thenReturn("DELETE FROM ALUNO;");

  //     SQLExecutor executor = mock(SQLExecutor.class);

  //     Db db = new Db(repository, executor);

  //   when:;
  //     db.load("limpar");

  //   then:;
  //     String content = "DELETE FROM ALUNO;";
  //     verify(executor).go(content);
  // }

  // @Test public void com_vários_elementos_e_um_nível_de_referência() {
  //   given:;
  //     FileRepository repository = mock(FileRepository.class);
  //     when(repository.get("limpar")).thenReturn(
  //       "!limpar/delete-tables\n" +
  //       "!sequence/drop-sequences"
  //     );
  //     when(repository.get("limpar/delete-tables")).thenReturn(
  //       "DELETE FROM ALUNO;\n" +
  //       "DELETE FROM DISCIPLINA;"
  //     );
  //     when(repository.get("sequence/drop-sequences")).thenReturn(
  //       "DROP SEQUENCE SQ_ID_ALUNO;\n" +
  //       "DROP SEQUENCE SQ_ID_DISCIPLINA;"
  //     );

  //     SQLExecutor executor = mock(SQLExecutor.class);

  //     Db db = new Db(repository, executor);

  //   when:;
  //     db.load("limpar");

  //   then:;
  //     String content =
  //       "DELETE FROM ALUNO;\n" +
  //       "DELETE FROM DISCIPLINA;\n" +
  //       "DROP SEQUENCE SQ_ID_ALUNO;\n" +
  //       "DROP SEQUENCE SQ_ID_DISCIPLINA;";
  //     verify(executor).go(content);
  // }

  // @Test public void com_dois_níveis_de_referência() {
  //   given:;
  //     FileRepository repository = mock(FileRepository.class);
  //     when(repository.get("limpar")).thenReturn("!limpar/delete-tables");
  //     when(repository.get("limpar/delete-tables")).thenReturn("!limpar/delete-table");
  //     when(repository.get("limpar/delete-table")).thenReturn("DELETE FROM ALUNO;");

  //     SQLExecutor executor = mock(SQLExecutor.class);

  //     Db db = new Db(repository, executor);

  //   when:;
  //     db.load("limpar");

  //   then:;
  //     String content = "DELETE FROM ALUNO;";
  //     verify(executor).go(content);
  // }

  // @Test public void passando_parâmetros_em_uma_referência() {
  //   given:;
  //     FileRepository repository = mock(FileRepository.class);
  //     when(repository.get("limpar")).thenReturn(File.create("limpar").line("!limpar/delete-tables"));
  //     when(repository.get("limpar/delete-tables")).thenReturn(File.create("limpar/delete-tables").line("!limpar/delete-table: ALUNO"));
  //     when(repository.get("limpar/delete-table")).thenReturn(File.create("limpar/delete-table").line("DELETE FROM {0};"));

  //     SQLExecutor executor = mock(SQLExecutor.class);

  //     Db db = new Db(repository, executor);

  //   when:;
  //     db.load("limpar");

  //   then:;
  //     String content = "DELETE FROM ALUNO;";
  //     verify(executor).go(content);
  // }
}
