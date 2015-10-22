package org.fyxture.ui;

import br.gov.frameworkdemoiselle.behave.parser.jbehave.*;

public class FyxtureUiBehaveAdapterFactory {
  private static FyxtureUiBehaveAdapter instance;

  public static FyxtureUiBehaveAdapter create() {
    if (instance == null) {
      instance = new FyxtureUiBehaveAdapter(new CommonSteps());
    }
    return instance;
  }
}
