package integration.org.fyxture;

import org.fyxture.*;
import org.junit.*;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

public class DbITest {
  @InjectMocks Db db;

  @Mock private FileRepository repository;

  @Mock private SQLExecutor executor;

  @Before public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test public void trivial() {
    given:;
      when(repository.get(file("limpar"))).thenReturn(content("DELETE FROM ALUNO;"));

    when:;
      db.load(file("limpar"));

    then:;
      verify(executor).go(result("DELETE FROM ALUNO;"));
  }

  @Test public void com_parâmetros() {
    given:;
      when(repository.get(file("limpar"))).thenReturn(content("DELETE FROM {0};"));

    when:;
      db.load(file("limpar"), param("ALUNO"));

    then:;
      verify(executor).go(result("DELETE FROM ALUNO;"));
  }

  private String file(String value) {
    return value;
  }

  private String content(String value) {
    return value;
  }

  private String result(String value) {
    return value;
  }

  private Object param(Object value) {
    return value;
  }

  @Test public void com_um_nível_de_referência() {
    given:;
      when(repository.get(file("limpar"))).thenReturn(content("!limpar/delete"));
      when(repository.get(file("limpar/delete"))).thenReturn(content("DELETE FROM ALUNO;"));

    when:;
      db.load(file("limpar"));

    then:;
      verify(executor).go(result("DELETE FROM ALUNO;"));
  }

  @Test public void com_vários_elementos_e_um_nível_de_referência() {
    given:;
      when(repository.get(file("limpar"))).thenReturn(
        content(
          "!limpar/delete-tables\n" +
          "!sequence/drop-sequences"
        )
      );
      when(repository.get(file("limpar/delete-tables"))).thenReturn(
        content(
          "DELETE FROM ALUNO;\n" +
          "DELETE FROM DISCIPLINA;"
        )
      );
      when(repository.get(file("sequence/drop-sequences"))).thenReturn(
        content(
          "DROP SEQUENCE SQ_ID_ALUNO;\n" +
          "DROP SEQUENCE SQ_ID_DISCIPLINA;"
        )
      );

    when:;
      db.load(file("limpar"));

    then:;
      verify(executor).go(
        result(
          "DELETE FROM ALUNO;\n" +
          "DELETE FROM DISCIPLINA;\n" +
          "DROP SEQUENCE SQ_ID_ALUNO;\n" +
          "DROP SEQUENCE SQ_ID_DISCIPLINA;"
        )
      );
  }

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
