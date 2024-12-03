Feature: Verification Module

  Background: User Signup
    When Login in the healthapp application

  @TC_1
  Scenario: Verify the verfication module is present or not
    Given Scroll & click till "Verification" menu on the side bar.
    Then Verify that the user is able to navigate to "Verification" section

  @TC_2
  Scenario: Verify all sub-modules are displayed correctly after Clicking on the "verification" Module.
    Given Scroll & click till "Verification" menu on the side bar.
    Then Verify that the user is able to navigate to "Verification" section
    Then Verify that "Inventory" button is visible.
    Then Verify that "Pharmacy" button is visible.

  @TC_3
  Scenario: Verify the presence of Requisition tab in inventory section with all fields.
    Given Scroll & click till "Verification" menu on the side bar.
    Then Verify that the user is able to navigate to "Verification" section
    Then Verify that "Inventory" button is visible.
    Then Verify that "Pharmacy" button is visible.
    And Click on "Requisition" under "Inventory"
    Then Verify these elements are visible on the page Requisition, Purchase Request, Purchase Order, GR Quality Inspection, Ok, print, First, Previous, Next, Last, view, search bar, Requisition Status, Date range, Pending, Approved, Rejected

  @TC_4
  Scenario: Verify to navigate on another sub module after open the inventory module
    Given Scroll & click till "Verification" menu on the side bar.
    Then Verify that the user is able to navigate to "Verification" section
    And Click on "Pharmacy" tab
    Then Verify that the user is able to navigate to "Verification/Pharmacy/" section

  @TC_5
  Scenario: Verify to navigate on another sub tab after open the Requisition tab
    Given Scroll & click till "Verification" menu on the side bar.
    Then Verify that the user is able to navigate to "Verification" section
    And Click on "Purchase Order" under "Inventory"
    Then Verify that the user is able to navigate to "Verification/Inventory/PurchaseOrder" section

  @TC_6
  Scenario: Verify to search the data by picking the date filter
    Given Scroll & click till "Verification" menu on the side bar.
    And Choose the date from Jan 2020 to March 2024
    And Click on the "all" Radio button from List by Verification Status
    And Click on OK button
    Then Verify that all the dates present inside the requested date are within the range

  @TC_7
  Scenario: Verify the tooltip and it's text present on hover the mouse on "Star"
    Given Scroll & click till "Verification" menu on the side bar.
    Then Hover on the star and Verify tooltip text as "Remember this Date"

  @TC_8
  Scenario: Verify dates are remembered correclty
    Given Scroll & click till "Verification" menu on the side bar.
    Then Verify that the user is able to navigate to "Verification" section
    And Choose the date from Jan 2020 to March 2024
    And Click on tooltip button
    And Click on OK button
    And Click on "Pharmacy" tab
    And Click on "Inventory" tab
    Then Verify that the date range remains the same

  @TC_9
  Scenario: Verify data range by Select "one week" option from drop down
    Given Scroll & click till "Verification" menu on the side bar.
    Then Verify that the user is able to navigate to "Verification" section
    And Select 'Last 3 Months' option from the date range dropdown
    And Click on OK button
    Then Verify that all the dates present inside the requested date are within the range

  @TC_10
  Scenario: Verify following radio buttons are selectable:Pending, Approved, Rejected & All
    Given Scroll & click till "Verification" menu on the side bar.
    Then Verify that the user is able to navigate to "Verification" section
    And Click on the "pending" Radio button from List by Verification Status
    And Click on the "pending" Radio button from List by Verification Status
    And Click on the "approved" Radio button from List by Verification Status
    And Click on the "approved" Radio button from List by Verification Status
    And Click on the "rejected" Radio button from List by Verification Status
    And Click on the "rejected" Radio button from List by Verification Status
    And Click on the "all" Radio button from List by Verification Status
    And Click on the "all" Radio button from List by Verification Status
    And Click on the "pending" Radio button from List by Verification Status

  @TC_1
  Scenario: Create and Verify a Purchase Request in Inventory
    Given Navigate to the Internal section under Inventory.
    And Click on Purchase Request.
    And Click on the Create Purchase Request button.
    And Fill in the required fields to create a Purchase Request.
    And Click on the Request button.
    Then Verify that the Purchase Request has been successfully created.

  @TC_2
  Scenario: Verify Purchase Request in Verification Module
    Given Navigate to the "Inventory" section under Verification.
    And Click on Purchase Request.
    Then Verify that the status of the Purchase Request is "Pending" in the list.

  @TC_3
  Scenario: Approve the Purchase Request and Verify Status
    Given Navigate to the "Inventory" section under Verification.
    And Click on Purchase Request.
    When Click on the View button under the action column for the Purchase Request.
    And Approve the Purchase Request.
    Then Verify the success message confirming the approval.
    And Click on the Approved radio button.
    Then Verify that the status of the Purchase Request has changed to Approved.

  @TC_4
  Scenario: Verify Reject All button without filling remark field
    Given Navigate to the "Inventory" section under Verification.
    And Click on Purchase Request.
    When Click on the View button under the action column for the Purchase Request.
    And Click on Reject All button.
    Then Verify a popup message should appear stating Remarks is compulsory for cancellation.

  @TC_5
  Scenario: Reject the Approved Purchase Request and Verify Status
    Given Navigate to the "Inventory" section under Verification.
    And Click on Purchase Request.
    And Click on the Approved radio button.
    When Click on the View button under the action column with status approved for the Purchase Request.
    And Click on Reject All button with remarks.
    And Click on the Rejected radio button.
    Then Verify that the status of the Purchase Request has changed to Rejected.

  @TC_6
  Scenario: Take Screenshot of the current page
    Given Navigate to the "Inventory" section under Verification.
    And Click on Purchase Request.
    Then Take screenshot of the current page.
