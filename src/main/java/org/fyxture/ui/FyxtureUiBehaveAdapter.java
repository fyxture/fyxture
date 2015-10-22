package org.fyxture.ui;

import br.gov.frameworkdemoiselle.behave.parser.jbehave.*;
import org.fyxture.utils.*;
import org.fyxture.exception.*;
import java.util.*;

public class FyxtureUiBehaveAdapter {
  private CommonSteps steps;

  public FyxtureUiBehaveAdapter(CommonSteps steps) {
    this.steps = steps;
  }

  public void interpret(String lines) throws QuotedArgBadFormatedException {
    for(String line : lines.split("\n")){
      String command = ActionCommandsUtils.command(line);
      List<String> args = ActionCommandsUtils.args(line);
      switch(command) {
        case "go":
          steps.goToWithName(args.get(0));
          break;
        case "click":
          steps.clickButton(args.get(0));
          break;
        case "type":
          steps.inform(args.get(0), args.get(1));
          break;
        case "on":
          steps.pageWithName(args.get(0));
          break;
        case "visible":
          steps.textVisible(args.get(0));
          break;
        default:
          throw new UnsupportedOperationException(command);
      }
    }
  }
}
