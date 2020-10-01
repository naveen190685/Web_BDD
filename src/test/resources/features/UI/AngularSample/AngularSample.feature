@AngularAutomation
Feature: Showcase automation with NgWebDriver
  I want to use this template for my feature file

  @AngularAutomationSample
  Scenario: Check interaction with angular sample website
    Given user is on hello-angularjs.appspot.com/addtablerow
    And add company name as "nk487444"
    And add employee names as "1200"
    And add Headoffice name as "Australia"
    When submit button to add
    Then new row should be shown with details as "nk487444", "1200" and "Austrailia"

