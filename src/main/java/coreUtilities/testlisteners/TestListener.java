package coreUtilities.testlisteners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import coreUtilities.utils.ExtentReportManager;
import static coreUtilities.testutils.TestUtils.businessTestFile;
import static coreUtilities.testutils.TestUtils.currentTest;
import static coreUtilities.testutils.TestUtils.yakshaAssert;

import java.io.IOException;



public class TestListener implements ITestListener
{

	//Extent Report Declarations
    private static ExtentReports extent = ExtentReportManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
 
    public synchronized void onStart(ITestContext context) {
        System.out.println("Extent Reports Version 3 Test Suite started!");
    }
 
    public synchronized void onFinish(ITestContext context) {
        System.out.println(("Extent Reports Version 3  Test Suite is ending!"));
        extent.flush();
    }
 
    public synchronized void onTestStart(ITestResult result) {
    	//String scenarioName = (String) result.getAttribute("scenarioName");
        System.out.println((result.getMethod().getMethodName()+ " started!"));
        //scenarioName.replaceAll(" ", "");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        test.set(extentTest);
    }
 
    public synchronized void onTestSuccess(ITestResult result) {
    	String scenarioName = (String) result.getAttribute("scenarioName");
    	String name = scenarioName.replaceAll(" ", "");
       System.out.println((name + " passed!"));
      // System.out.println("Scenario Name: " + scenarioName);
       test.get().pass("Test passed");
        try {
        	//System.out.println(result.getTestContext().getName());
        	
            yakshaAssert(name, true, businessTestFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    public synchronized void onTestFailure(ITestResult result) {
    	String scenarioName = (String) result.getAttribute("scenarioName");
    	String name = scenarioName.replaceAll(" ", "");
       System.out.println((name + " failed"));
        System.out.println(result.getThrowable().toString());
        try {
            yakshaAssert(name, false, businessTestFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        test.get().fail(result.getThrowable());
    }
 
    public synchronized void onTestSkipped(ITestResult result) {
    	String scenarioName = (String) result.getAttribute("scenarioName");
    	String name = scenarioName.replaceAll(" ", "");
       System.out.println((name + " skipped!"));
        
        try {
            yakshaAssert(name, false, businessTestFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        test.get().skip(result.getThrowable());
    }
 
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    	String scenarioName = (String) result.getAttribute("scenarioName");
    	String  name = scenarioName.replaceAll(" ", "");
       
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + name));
    }	

}