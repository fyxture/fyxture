package acceptance.steps

import cucumber.api.java.pt.Dado
import cucumber.api.java.pt.Então
import cucumber.api.java.pt.Quando
import org.fyxture.Fyxture

public class InicialSteps {
  final String CONTENT = "conteudo do arquivo simples"

  @Dado('^um arquivo simples$')
  public void um_arquivo_simples() throws Throwable {
    File.create("arquivo-simples.fyxture", CONTENT)
  }

  @Quando('^solicito uma carga$')
  public void solicito_uma_carga() throws Throwable {
    Fyxture.db().load("arquivo-simples")
  }

  @Então('^um novo arquivo com o mesmo conteúdo é gerado$')
  public void um_novo_arquivo_com_o_mesmo_conteúdo_é_gerado() throws Throwable {
    File.findByPreffix("/tmp/fyxture.db.").contains(CONTENT);
  }

  @Dado('^um arquivo que referencia outro$')
  public void um_arquivo_que_referencia_outro() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    //throw new PendingException();
  }

  @Então('^um novo arquivo com o mesmo do que foi referenciado é gerado$')
  public void um_novo_arquivo_com_o_mesmo_do_que_foi_referenciado_é_gerado() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    //throw new PendingException();
  }
}
