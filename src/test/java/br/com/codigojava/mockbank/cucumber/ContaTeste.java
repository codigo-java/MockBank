package br.com.codigojava.mockbank.cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", 
				 tags = "@ContaTeste", 
				 publish = true,
				 glue = "br.com.codigojava.mockbank.cucumber", 
				 monochrome = true, 
				 plugin = { "pretty", "html:target/cucumber-reports/report.html"})
public class ContaTeste {

}
