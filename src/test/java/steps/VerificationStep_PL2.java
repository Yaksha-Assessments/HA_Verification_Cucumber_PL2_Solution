
package steps;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.testng.Assert;

import coreUtilities.testbase.TestBase;
import coreUtilities.utils.FileOperations;
import io.cucumber.java.en.*;
import pages.VerificationPage_PL2;

public class VerificationStep_PL2 extends TestBase {

	String requisitionNumber = "";

	LocalDate currentDate = LocalDate.now();
	LocalDate date7DaysAgo = currentDate.minusDays(90);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	String toDate = currentDate.format(formatter);
	String fromDate = date7DaysAgo.format(formatter);

	VerificationPage_PL2 pl2 = new VerificationPage_PL2(driver);

	private final String EXCEL_FILE_PATH = "src/main/resources/config.xlsx"; // Path to the Excel file
	private final String SHEET_NAME = "ExpectedData"; // Sheet name in the Excel file

	@When("Login in the healthapp application")
	public void login_in_the_healthapp_application() {
		pl2.login();
	}
	
	@Given("Scroll & click till {string} menu on the side bar.")
	public void scroll_click_till_menu_on_the_side_bar(String string) {
		pl2.openVerificationMenu();
	}

	@Then("Verify that the user is able to navigate to {string} section")
	public void verify_that_the_user_is_able_to_navigate_to_section(String module) throws InterruptedException {
		pl2.successfullNavigation(module);
	}

	@Then("Verify that {string} button is visible.")
	public void verify_that_buttons_are_visible(String text) {
		pl2.verifyButtonPresentWithText(text);
	}

	@Then("Click on {string} under {string}")
	public void click_on_under(String subModule, String module) {
		pl2.clickOnButtonByText(module);
		pl2.clickOnButtonByText(subModule);
	}
	
	@Then("Verify these elements are visible on the page Requisition, Purchase Request, Purchase Order, GR Quality Inspection, Ok, print, First, Previous, Next, Last, view, search bar, Requisition Status, Date range, Pending, Approved, Rejected")
	public void verifyElementsArePresentOnThePage() throws Exception {
		Assert.assertTrue(pl2.verifyVerificationComponentsAreVisible());
	}
	
	@When("Click on OK button")
	public void click_ok() {
		pl2.clickOkButton();
	}
	
	@Then("Click on {string} tab")
	public void click_on_tab(String text) {
		pl2.clickOnButtonByText(text);
	}
	
	@Then("Verify that all the dates present inside the requested date are within the range")
	public void verify_that_all_the_dates_present_inside_the_requested_date_are_within_the_range()
			throws InterruptedException {
		LocalDate currentDate = LocalDate.now();
		LocalDate date7DaysAgo = currentDate.minusDays(90);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String toDate = currentDate.format(formatter);
		String fromDate = date7DaysAgo.format(formatter);
		Thread.sleep(3000); // Waiting for data to load
		pl2.verifyDateRange(fromDate, toDate);
	}
	
	@Then("Click on the {string} Radio button from List by Verification Status")
	public void click_on_the_radio_button_from_list_by_verification_status(String radioButtonText) {
		Assert.assertTrue(pl2.selectRadioButton(radioButtonText));
	}
	
	@When("Choose the date from Jan 2020 to March 2024")
	public void choose_date() {

		pl2.chooseDate(fromDate, toDate);
	}
	
	@When("Click on tooltip button")
	public void click_tooltip() {
		pl2.clickTooltip();
	}
	
	@Then("Verify that the date range remains the same")
	public void verify_desired_date_range() {
		pl2.verifyDateRangeforReq(fromDate, toDate);
	}
	
	@Given("Navigate to the Internal section under Inventory.")
	public void navigate_to_the_section_under_inventory() {
		pl2.clickOnInventory();
	}

	@When("Click on the Request button.")
	public void click_on_the_Request_button() {
		pl2.clickOnRequestButton();
	}

	@When("Click on the Create Purchase Request button.")
	public void click_on_the_button_create_purchase_request() {
		pl2.clickOnCreatePurchaseRequestButton();
	}
	
	@When("Select {string} option from the date range dropdown")
	public void date_range(String range) {
		pl2.chooseDateRange(range);
	}
	
	@Then("Hover on the star and Verify tooltip text as {string}")
	public void hover_on_the_star_and_verify_tooltip_text_as_remember_this_date(String tooltipText) {
		Assert.assertEquals(pl2.verifyToolTipText(), tooltipText);
	}

	@When("Fill in the required fields to create a Purchase Request.")
	public void fill_in_the_required_fields_to_create_a_purchase_request() throws Exception {
		Map<String, String> data = new FileOperations().readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);
		System.out.println("File path --> " + EXCEL_FILE_PATH);

		pl2.fillTheRequiredFields(data);
	}

	@Then("Verify that the Purchase Request has been successfully created.")
	public void verify_that_the_purchase_request_has_been_successfully_created() throws Exception {
		Map<String, String> data = new FileOperations().readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);
		Assert.assertEquals(pl2.verifyPurchaseRequestSuccessMsg(data), data.get("purchaseRequestCreationMessage"),
				"Actual and Expected are not equal");
	}

	@Given("Navigate to the {string} section under Verification.")
	public void navigate_to_the_inventory_section_under_verification(String tabName) {
		pl2.clickVerificationMenu();
	}

	@Then("Verify that the status of the Purchase Request is {string} in the list.")
	public void verify_that_the_status_of_the_purchase_request_is_pending_in_the_list(String string) throws Exception {
		Map<String, String> data = new FileOperations().readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);
		Assert.assertEquals(pl2.verifyAddedPurchaseRequestStatus(data), data.get("status_1"));
	}

	@When("Click on the View button under the action column for the Purchase Request.")
	public void click_on_the_view_button_under_the_action_column_for_the_purchase_request() {
		pl2.clickOnViewRequestWithStatusPending();
	}

	@When("Approve the Purchase Request.")
	public void approve_the_purchase_request() {
		pl2.approveThePurchaseRequest();
	}

	@Then("Verify the success message confirming the approval.")
	public void verify_the_success_message_confirming_the_approval() throws Exception {
		Map<String, String> data = new FileOperations().readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);
		Assert.assertTrue(pl2.verifyApprovalSuccessMessage(data).contains("is approved successfully"));
	}

	@When("Click on the Approved radio button.")
	public void click_on_the_approved_radio_button() {
		pl2.clickOnApprovedRadioButton();
	}

	@Then("Verify that the status of the Purchase Request has changed to Approved.")
	public void verify_that_the_status_of_the_purchase_request_has_changed_to_approved() throws Exception {
		Map<String, String> data = new FileOperations().readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);
		Assert.assertEquals(pl2.verifyPurchaseRequestStatusInTable(data), data.get("status_2"));
	}

	@When("Click on Reject All button.")
	public void click_on_reject_all_button() {
		pl2.clickOnRejectAllButton();
	}

	@Then("Verify a popup message should appear stating Remarks is compulsory for cancellation.")
	public void verify_a_popup_message_should_appear_stating_Remarks_is_compulsory_for_cancellation() throws Exception {
		Map<String, String> verificationExpectedData = new FileOperations().readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);
		Assert.assertTrue(pl2.rejectAPurchaseRequestAndVerifyThePopUpMessage(verificationExpectedData)
				.contains(verificationExpectedData.get("remarksCompulsaryForCancellation")));
	}

	@When("Click on the View button under the action column with status approved for the Purchase Request.")
	public void click_on_the_view_button_under_the_action_column_with_status_approved_for_the_purchase_request() {
		pl2.clickOnViewRequestWithStatusApproved();
	}

	@When("Click on the Rejected radio button.")
	public void click_on_the_rejected_radio_button() {
		pl2.clickOnRejectedRadioButton();
	}

	@Then("Verify that the status of the Purchase Request has changed to Rejected.")
	public void verify_that_the_status_of_the_purchase_request_has_changed_to_rejected() throws Exception {
		Map<String, String> data = new FileOperations().readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);
		Assert.assertEquals(pl2.verifyPurchaseRequestStatusInRejectedTable(data), data.get("status_3"));
	}

	@When("Click on Reject All button with remarks.")
	public void click_on_reject_all_button_with_remarks() throws Exception {
		Map<String, String> data = new FileOperations().readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);
		pl2.clickOnRejectAllButtonWithRemarks(data);
	}

	@Then("Take screenshot of the current page.")
	public void take_screenshot_of_the_current_page() throws Exception {
		Assert.assertTrue(pl2.takingScreenshotOfTheCurrentPage());
	}
	
	@When("Click on Purchase Request.")
	public void click_on_purchase_request() {
		pl2.clickOnPurchaseRequest();
	}
}
