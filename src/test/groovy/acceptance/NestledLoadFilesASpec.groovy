package acceptance

import org.fyxture.*

import spock.lang.*

import static org.mockito.Mockito.*
import static org.mockito.Matchers.*

class NestledLoadFilesASpec extends Specification {
  def "um arquivo embutido em outro deve resultar em um arquivo que possui o conteúdo de ambos"() {
    given:
      def repository = mock(FileRepository.class)
      when(repository.get("limpar")).thenReturn("!limpar/delete")
      when(repository.get("limpar/delete")).thenReturn("DELETE FROM ALUNO;")

      def executor = mock(SQLExecutor.class)

      Db db = new Db(repository, executor)

    when:
      db.load("limpar")

    then:
      def content = """DELETE FROM ALUNO;"""
      verify(executor).go(content)
  }

  def "dois arquivos embutidos em outro deve resultar em um arquivo que possui o conteúdo de todos"() {
    given:
      def repository = mock(FileRepository.class)
      when(repository.get("limpar")).thenReturn("""!limpar/delete-tables
!sequence/drop-sequences""")
      when(repository.get("limpar/delete-tables")).thenReturn("""DELETE FROM ALUNO;
DELETE FROM DISCIPLINA;""")
      when(repository.get("sequence/drop-sequences")).thenReturn("""DROP SEQUENCE SQ_ID_ALUNO;
DROP SEQUENCE SQ_ID_DISCIPLINA;""")

      def executor = mock(SQLExecutor.class)

      Db db = new Db(repository, executor)

    when:
      db.load("limpar")

    then:
      def content = """DELETE FROM ALUNO;
DELETE FROM DISCIPLINA;
DROP SEQUENCE SQ_ID_ALUNO;
DROP SEQUENCE SQ_ID_DISCIPLINA;"""
      verify(executor).go(content)
  }

  def "um arquivo embutido em outro, com um outro já embutido, deve resultar em um arquivo que possui o conteúdo de aninhado"() {
    given:
      def repository = mock(FileRepository.class)
      when(repository.get("limpar")).thenReturn("""!limpar/delete-tables""")
      when(repository.get("limpar/delete-tables")).thenReturn("""!limpar/delete-table""")
      when(repository.get("limpar/delete-table")).thenReturn("""DELETE FROM ALUNO;""")

      def executor = mock(SQLExecutor.class)

      Db db = new Db(repository, executor)

    when:
      db.load("limpar")

    then:
      def content = """DELETE FROM ALUNO;"""
      verify(executor).go(content)
  }
}
