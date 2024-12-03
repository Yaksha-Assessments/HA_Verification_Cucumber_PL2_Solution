package runner;

import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;

import coreUtilities.testbase.TestBase;
import coreUtilities.utils.FileOperations;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/features/Verification_PL2.feature", glue = { "runner", "steps" }, plugin = { "pretty",
		"json:target/cucumber-reports/report2.json", "junit:target/cucumber-reports/cucumber.xml",
		"html:target/cucumber-reports/report.html" }, monochrome = true, publish = true 
)
public class TestRunner1 extends AbstractTestNGCucumberTests {
	String browserName = "chrome";
	TestBase testbase = new TestBase();

	@Before()
	public void beforeClass() throws Exception {
		System.out.println("Inside before");
		Map<String, String> configData;
		String projectBaseDirPath = System.getProperty("user.dir");
		String config_filePath = projectBaseDirPath + "/src/main/resources/config.xlsx";

		System.out.println("File path --> " + config_filePath);
		configData = new FileOperations().readExcelPOI(config_filePath, "prod");
		configData.put("url", configData.get("url").replaceAll("[\\\\]", ""));
		configData.put("browser", "chrome");
		testbase.initialize(configData);
	}
	
	@Before
    public void beforeScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        ITestResult testResult = Reporter.getCurrentTestResult(); // Get the current test result from TestNG
        testResult.setAttribute("scenarioName", scenarioName);
    }

	@After(order = 0)
	public void tearDown() {
		testbase.browserTearDown();
	}
	
}
