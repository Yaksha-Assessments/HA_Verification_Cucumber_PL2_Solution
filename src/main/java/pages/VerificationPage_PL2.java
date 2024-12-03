package pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VerificationPage_PL2 extends StartupPage {

	public VerificationPage_PL2(WebDriver driver) {
		super(driver);
	}

	/*
	 * --------------------------- Locators
	 * -------------------------------------------
	 */

	private By emailInput = By.cssSelector("input#username_id");
	private By passwordInput = By.cssSelector("#password");
	private By signInButton = By.cssSelector("#login");
	
	public By getAnchorTagLocatorByText(String anchorTagName) {
		return By.xpath("//a[contains(text(),'" + anchorTagName + "')]");
	}

	public By getInventoryLocator() {
		return By.xpath("//a[@href='#/Inventory']");
	}
	
	public By searchBarId() {
		return By.id("quickFilterInput");
	}

	public By getInventoryPageBarFixedLocator(String navBarName) {
		return By.xpath("//ul[contains(@class,'page-breadcrumb')]/li/a[@href='#/Inventory/" + navBarName + "']");
	}

	public By getSubNavTabLocator(String subNavName) {
		return By.xpath("//div[@class=\"sub-navtab\"]/ul/li/a[text()='" + subNavName + "']");
	}

	public By getButtonLocatorsBytext(String buttonName) {
		return By.xpath("//button[contains(text(),'" + buttonName + "')]");
	}

	public By getInputFieldLocator(String inputFieldName) {
		return By.cssSelector("input[display-property-name='" + inputFieldName + "']");
	}

	public By getLocatorById(String idName) {
		return By.id(idName);
	}

	public By getPopUpMessageText(String msgStatus, String messageText) {
		return By.xpath("//p[text()=' " + msgStatus + " ']/../p[contains(@class,'main-message') or contains(text(),'"
				+ messageText + "')]");
	}

	public By getVerificationLocator() {
		return By.xpath("//a[@href='#/Verification']");
	}

	public By getPageBarFixedLocator(String navBarName) {
		return By.xpath("//ul[@class='page-breadcrumb']/li/a[@href='#/Verification/" + navBarName + "']");
	}

	public By getPurchaseRequestStatus(String companyName, String status) {
		return By.xpath("(//div[@col-id='VendorName' and text()='" + companyName
				+ "']/../div[@col-id='VerificationStatus' and text()='" + status + "'])[1]");
	}

	public By getOkButtonLocator() {
		return By.xpath("//button[@class='btn green btn-success']");
	}

	public By getPurchaseRequestViewButton(String status) {
		return By.xpath("(//div[@col-id='VerificationStatus' and text()='" + status
				+ "']/../div/a[@danphe-grid-action='view'])[1]");
	}

	public By getRadioButtonsLocator(String radioButtonName) {
		return By.xpath("//input[@value='" + radioButtonName + "']/../span");
	}

	public By getVerificationRemarks() {
		return By.cssSelector("textarea[name='VerificationRemarks']");
	}
	
	public By getDateRangeButton() {
		return By.cssSelector("td [data-hover='dropdown']");
	}

	public By calendarFromDropdown() {
		return By.xpath("(//input[@id='date'])[1]");
	}

	public By calendarToDropdown() {
		return By.xpath("(//input[@id='date'])[2]");
	}
	
	public By getStarIconLocator() {
		return By.xpath("//i[contains(@class,'icon-favourite')]/..");
	}
	
	public By getActualRequestedOnDates() {
		return By.xpath("//div[@col-id=\"RequisitionDate\"]/span[not(contains(@class,'hidden'))]");
	}
	
	public By favouriteOrStarIcon() {
		return By.xpath("//i[contains(@class,'icon-favourite')]");
	}
	
	/*
	 * --------------------------- Methods
	 * -------------------------------------------
	 */

	public void login() {
		driver.findElement(emailInput).sendKeys("admin");
		driver.findElement(passwordInput).sendKeys("pass123");
		driver.findElement(signInButton).click();

	}

	public void clickOnInventory() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement inventoryTab = commonEvents.findElement(getInventoryLocator());
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", inventoryTab);
		commonEvents.highlight(inventoryTab);
		commonEvents.click(inventoryTab);

		WebElement internalTab = commonEvents.findElement(getInventoryPageBarFixedLocator("InternalMain"));
		commonEvents.highlight(internalTab).click(internalTab);
	}

	public void clickOnPurchaseRequest() {
		WebElement purchaseRequestTab = commonEvents.findElement(getSubNavTabLocator("Purchase Request"));
		commonEvents.highlight(purchaseRequestTab).click(purchaseRequestTab);
	}

	public void clickOnCreatePurchaseRequestButton() {
		WebElement purchaseRequestButton = commonEvents.findElement(getButtonLocatorsBytext("Create Purchase Request"));
		commonEvents.highlight(purchaseRequestButton).click(purchaseRequestButton);
	}

	public void fillTheRequiredFields(Map<String, String> data) throws InterruptedException {
		// fill mandatory details to create Purhcase Request
		commonEvents.click(getInputFieldLocator("VendorName")).sendKeys(getInputFieldLocator("VendorName"),
				data.get("vendorName"));
		commonEvents.click(getInputFieldLocator("ItemName")).sendKeys(getInputFieldLocator("ItemName"),
				data.get("itemName"));
		Thread.sleep(2000);
		commonEvents.sendKeys(getInputFieldLocator("ItemName"), Keys.ENTER);
		commonEvents.sendKeys(getLocatorById("remarks"), data.get("remarks"));
	}

	public void clickOnRequestButton() {
		WebElement requestPORequisition = commonEvents.findElement(getLocatorById("RequestPORequisition"));
		commonEvents.highlight(requestPORequisition).click(requestPORequisition);
	}

	public String verifyPurchaseRequestSuccessMsg(Map<String, String> data) {
		String successMessageText = "";
		try {

			WebElement successMessageElement = commonEvents
					.findElement(getPopUpMessageText("success", data.get("purchaseRequestCreationMessage")));
			System.out.println("Success message text : " + successMessageElement.getText());
			successMessageText = successMessageElement.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return successMessageText;
	}

	public void clickVerificationMenu() {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			WebElement verificationTab = driver.findElement(getVerificationLocator());
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);", verificationTab);
			verificationTab.click();

			// Wait for the URL to contain "Verification/Inventory"
			commonEvents.waitForUrlContains("Verification/Inventory", 10);

			WebElement inventorySubModule = commonEvents.findElement(getPageBarFixedLocator("Inventory"));
			commonEvents.highlight(inventorySubModule).click(inventorySubModule);

		} catch (Exception e) {
			throw e;
		}
	}

	public String verifyAddedPurchaseRequestStatus(Map<String, String> data) {
		String purchaseRequestStatus = "";
		try {

			WebElement okButton = commonEvents.findElement(getOkButtonLocator());
			// Highlight and click the OK button
			commonEvents.highlight(okButton).click(okButton);

			WebElement purchaseReqElement = commonEvents
					.findElement(getPurchaseRequestStatus(data.get("vendorName"), data.get("status_1")));
			purchaseRequestStatus = purchaseReqElement.getText();

			System.out.println("purchaseRequestStatus : " + purchaseRequestStatus);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return purchaseRequestStatus;
	}

	public void clickOnViewRequestWithStatusPending() {
		WebElement viewButton = commonEvents.findElement(getPurchaseRequestViewButton("pending"));
		commonEvents.highlight(viewButton).click(viewButton);
	}

	public void approveThePurchaseRequest() {
		WebElement approveButton = commonEvents.findElement(getButtonLocatorsBytext("Approve"));
		commonEvents.highlight(approveButton).click(approveButton);
	}

	public String verifyApprovalSuccessMessage(Map<String, String> data) {
		String successMessageText = "";
		try {
			WebElement successMessageElement = commonEvents
					.findElement(getPopUpMessageText("Success", data.get("purchaseRequestApproveMessage")));
			System.out.println("Success message text : " + successMessageElement.getText() + " Expected : "
					+ data.get("purchaseRequestApproveMessage"));
			successMessageText = successMessageElement.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return successMessageText;
	}

	public void clickOnApprovedRadioButton() {
		WebElement approvedRadioButton = commonEvents.findElement(getRadioButtonsLocator("approved"));
		commonEvents.highlight(approvedRadioButton).click(approvedRadioButton);
	}

	public void clickOnRejectedRadioButton() {
		WebElement approvedRadioButton = commonEvents.findElement(getRadioButtonsLocator("rejected"));
		commonEvents.highlight(approvedRadioButton).click(approvedRadioButton);
	}

	public String verifyPurchaseRequestStatusInTable(Map<String, String> data) {
		String purchaseRequestStatus = "";
		try {
			WebElement purchaseReqElement = commonEvents
					.findElement(getPurchaseRequestStatus(data.get("vendorName"), "approved"));
			purchaseRequestStatus = purchaseReqElement.getText();
			System.out.println("purchaseRequestStatus : " + purchaseRequestStatus);

			return purchaseRequestStatus;
		} catch (Exception e) {
			throw e;
		}
	}

	public String verifyPurchaseRequestStatusInRejectedTable(Map<String, String> data) {
		String purchaseRequestStatus = "";
		try {
			WebElement purchaseReqElement = commonEvents
					.findElement(getPurchaseRequestStatus(data.get("vendorName"), "rejected"));
			purchaseRequestStatus = purchaseReqElement.getText();
			System.out.println("purchaseRequestStatus : " + purchaseRequestStatus);

			return purchaseRequestStatus;
		} catch (Exception e) {
			throw e;
		}
	}

	public void clickOnRejectAllButton() {
		WebElement rejectAllButton = commonEvents.findElement(getButtonLocatorsBytext("Reject All "));
		commonEvents.highlight(rejectAllButton).click(rejectAllButton);
	}

	public void clickOnRejectAllButtonWithRemarks(Map<String, String> verificationExpectedData) {
		commonEvents.sendKeys(getVerificationRemarks(), verificationExpectedData.get("remarks"));

		WebElement rejectAllButton = commonEvents.findElement(getButtonLocatorsBytext("Reject All "));
		commonEvents.highlight(rejectAllButton).click(rejectAllButton);
	}

	public String rejectAPurchaseRequestAndVerifyThePopUpMessage(Map<String, String> verificationExpectedData) {
		String failedMessageText = "";
		try {
			WebElement successMessageElement = commonEvents.findElement(
					getPopUpMessageText("failed", verificationExpectedData.get("remarksCompulsaryForCancellation")));
			System.out.println("Failed message text : " + successMessageElement.getText() + " Expected : "
					+ verificationExpectedData.get("remarksCompulsaryForCancellation"));
			failedMessageText = successMessageElement.getText();

			return failedMessageText;

		} catch (Exception e) {
			throw e;
		}
	}

	public void clickOnViewRequestWithStatusApproved() {
		WebElement viewButton = commonEvents.findElement(getPurchaseRequestViewButton("approved"));
		commonEvents.highlight(viewButton).click(viewButton);
	}

	public Boolean takingScreenshotOfTheCurrentPage() throws Exception {
		boolean isDisplayed = false;
		try {
			commonEvents.takeScreenshot("Verification-Purchase Request");
			isDisplayed = true;

		} catch (Exception e) {
			throw e;
		}
		return isDisplayed;
	}

	public void openVerificationMenu() {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			WebElement verificationTab = driver.findElement(getVerificationLocator());
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);", verificationTab);
			verificationTab.click();

		} catch (Exception e) {
			throw e;
		}
	}

	public void successfullNavigation(String module) throws InterruptedException {
		// Get the current URL
		Thread.sleep(3000);
		String currentURL = driver.getCurrentUrl();

		// Substring to verify
		String expectedSubstring = module;
		System.out.println("this is the current URL: " + currentURL);

		// Verify the substring
		if (currentURL.contains(expectedSubstring)) {
			System.out.println("URL contains the expected substring: " + expectedSubstring);
		} else {
			Assert.fail("URL does not contains the expected substring: " + expectedSubstring);
		}
	}

	public void verifyButtonPresentWithText(String text) {
		WebElement element = driver.findElement(getAnchorTagLocatorByText(text));
		Boolean elementISDisplayed = element.isDisplayed();

		if (!elementISDisplayed) {
			Assert.fail("element with text:" + text + " is not visible");
		}
	}

	public void clickOnButtonByText(String text) {
		WebElement element = driver.findElement(getAnchorTagLocatorByText(text));
		element.click();
	}

	public boolean verifyVerificationComponentsAreVisible() throws Exception {

		boolean areAllFieldsDisplayed = false;
		try {

			WebElement firstButton = commonEvents.findElement(getButtonLocatorsBytext("First"));
			WebElement previousButton = commonEvents.findElement(getButtonLocatorsBytext("Previous"));
			WebElement nextButton = commonEvents.findElement(getButtonLocatorsBytext("Next"));
			WebElement lastButton = commonEvents.findElement(getButtonLocatorsBytext("Last"));
			WebElement searchBarId = commonEvents.findElement(searchBarId());
			WebElement getDateRangeButton = commonEvents.findElement(getDateRangeButton());
			WebElement calendarFromDropdown = commonEvents.findElement(calendarFromDropdown());
			WebElement calendarToDropdown = commonEvents.findElement(calendarToDropdown());
			WebElement starIconLocator = commonEvents.findElement(getStarIconLocator());
			WebElement requisition = commonEvents.findElement(getSubNavTabLocator("Requisition"));
			WebElement purchaseRequest = commonEvents.findElement(getSubNavTabLocator("Purchase Request"));
			WebElement purchaseOrder = commonEvents.findElement(getSubNavTabLocator("Purchase Order"));
			WebElement GRQualityInspection = commonEvents.findElement(getSubNavTabLocator("GR Quality Inspection"));

			List<WebElement> options = Arrays.asList(requisition, purchaseRequest, purchaseOrder, GRQualityInspection,
					firstButton, previousButton, nextButton, lastButton, searchBarId, getDateRangeButton,
					calendarFromDropdown, calendarToDropdown, starIconLocator);

			for (int i = 0; i < options.size(); i++) {
				WebElement option = options.get(i);
				commonEvents.highlight(option);
				System.out.println("is " + i + 1 + " displayed? " + option.isDisplayed());
				if (!option.isDisplayed()) {
					areAllFieldsDisplayed = false;
					throw new Exception("Visibility check failed for: " + option.getText());
				}
			}
			areAllFieldsDisplayed = true;
		} catch (Exception e) {
			// Throw an exception with a meaningful message if any UI component is not found
			throw new Exception("Failed to verify if all fields are displayed!", e);
		}
		// Return the result of the visibility check
		return areAllFieldsDisplayed;
	}

	public void clickOkButton() {

		WebElement okButton = driver.findElement(getOkButtonLocator());
		okButton.click();
	}
	
	public void verifyDateRange(String fromDate, String toDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<WebElement> actualDatesAfterFilterApplied = driver.findElements(getActualRequestedOnDates());
		LocalDate from = LocalDate.parse(fromDate, formatter);
		LocalDate to = LocalDate.parse(toDate, formatter);

		LocalDate date;
		LocalDate newDate = null;

		for (WebElement dateElement : actualDatesAfterFilterApplied) {

			String dateText = dateElement.getText();

			try {
				date = LocalDate.parse(dateText, inputFormatter);
				newDate = LocalDate.parse(date.format(formatter), formatter);

			} catch (Exception e) {
				Assert.fail("Date parsing failed for: " + dateText);

			}

			if (newDate.isBefore(from) || newDate.isAfter(to)) {
				Assert.fail("Date parsing failed for: " + dateText);
			} else {
				System.out.println("the from date is: " + from + " the to date is: " + to);
			}
		}
	}
	
	public boolean selectRadioButton(String radioButtonText) {
		try {
			WebElement radioButtonToClick = driver.findElement(getRadioButtonsLocator(radioButtonText));
			radioButtonToClick.click();
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	public void chooseDate(String fromDate, String toDate) {
		// Split the fromDate and toDate into day, month, and year components

		System.out.println("From Date : " + fromDate + ", To Date : " + toDate);
		String[] fromDateComponents = fromDate.split("-");
		String fromDay = fromDateComponents[0];
		String fromMonth = fromDateComponents[1];
		String fromYear = fromDateComponents[2];

		String[] toDateComponents = toDate.split("-");
		String toDay = toDateComponents[0];
		String toMonth = toDateComponents[1];
		String toYear = toDateComponents[2];

		// Locate the date dropdowns and OK button
		WebElement fromDateDropdown = driver.findElement(calendarFromDropdown());
		WebElement toDateDropdown = driver.findElement(calendarToDropdown());
		WebElement okButton = driver.findElement(getOkButtonLocator());

		// Highlight and set the "from" date
		fromDateDropdown.sendKeys(fromDay);
		fromDateDropdown.sendKeys(fromMonth);
		fromDateDropdown.sendKeys(fromYear);

		// Highlight and set the "to" date
		toDateDropdown.sendKeys(toDay);
		toDateDropdown.sendKeys(toMonth);
		toDateDropdown.sendKeys(toYear);
	}

	public void clickTooltip() {
		WebElement toolTip = driver.findElement(getStarIconLocator());
		toolTip.click();
	}
	
	public void verifyDateRangeforReq(String fromDate, String toDate) {
		System.out.println("From Date : " + fromDate + ", To Date : " + toDate);
		String[] fromDateComponents = fromDate.split("-");
		String fromDay = fromDateComponents[0];
		String fromMonth = fromDateComponents[1];
		String fromYear = fromDateComponents[2];

		String[] toDateComponents = toDate.split("-");
		String toDay = toDateComponents[0];
		String toMonth = toDateComponents[1];
		String toYear = toDateComponents[2];
		String actualFromDate = fromDay + "-" + fromMonth + "-" + fromYear;
		String actualToDate = toDay + "-" + toMonth + "-" + toYear;

		System.out.println("Actual from date : " + actualFromDate);
		System.out.println("Actual to date : " + actualToDate);

		// Verify if the remembered dates match the expected dates
		if (actualFromDate.equals(fromDate) && actualToDate.equals(toDate)) {
			System.out.println("Returned true");
		} else {
			Assert.fail("The date does not lies in range of :" + fromDate + " to date: " + toDate);
		}

	}
	
	public void chooseDateRange(String range) {
		WebElement dateRangeButton = driver.findElement(getDateRangeButton());
		dateRangeButton.click();
		WebElement valueToSelectElement = driver.findElement(getAnchorTagLocatorByText(range));
		valueToSelectElement.click();

		dateRangeButton.click();
		valueToSelectElement.click();

	}
	
	public String verifyToolTipText() {
		String toolTipValue = "";
		try {
			WebElement toolTip = commonEvents.findElement(favouriteOrStarIcon());
			toolTipValue = commonEvents.highlight(toolTip).getAttribute(toolTip, "title");
			System.out.println("Tool tip title : " + toolTipValue);
		} catch (Exception e) {
			throw e;
		}
		return toolTipValue;
	}

}
