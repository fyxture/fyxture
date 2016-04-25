package acceptance;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
  plugin = {"pretty"},
  features = {"classpath:acceptance/features"}
)
public class CucumberATest {
}
