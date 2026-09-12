package uni.pu.fmi;

import  io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true
        , plugin = {"pretty",
        "html:target/cucumber-report.html"})

public class CucumberStarter {
}


