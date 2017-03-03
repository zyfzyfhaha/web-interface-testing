#Story 129749 As a middle office , I want to update the tolerance so that I can correct the setup
#AC1> Given middle office or risk manager opens the detailed tolerance view screen, When user clicks "Edit" button, Then tolerance range, factor are enabled for edit
#AC2> Given tier range are updated, When user clicks save button, Then the new tolerance is saved.
#AC3> Given user edit tier range, When click Edit button, Then the first row of tiers and the end range of last row can't be edit.
#AC4> Given nothing is updated, When user clicks save button, Then nothing is saved.
#AC5> Given tier range is not valid, When user clicks save button., Then error message should be popped up
#AC6> Given user opens detailed tolerance view screen or update the range start/end, When range start/end is larger than 1000, Then value are having the thousand separator.
#AC7> Given a user who does not have Middle Office or risk manager Role to modify tier range, When click save button, Then error message popped up indicating no access to modify.
#Story 129750 As a middle office, I want to edit minimum sizes for currencies to that I can correct the minimum sizes
#AC1> Given middle office or risk manager opens the detailed tolerance view screen, When user clicks "Edit" button, Then minimum size for each currency is enabled for edit
#AC2> Given the minimum size is updated, When user clicks save button, Then the new minimum size is saved.
#AC3> Given nothing is updated, When user clicks save button, Then nothing is saved.
#AC4> Given minimum size is not valid, When user clicks save button, Then error message should be popped up
#AC5> Given user opens detailed tolerance view screen or update the minimum size, When minimum sizes are larger than 1000, Then minimum sizes are having the thousand separator.
#AC6> Given a user who does not have Middle Office or risk manager Role to modify minimum size, When click save button, Then error message popped up indicating no access to modify.
@Regression
Feature: Story_129749_129750 Order Size Tolerance Configuration - Update detailed tolerance and minimum size setup

  Background: Login to Reference data configuration
    Given user login to Reference data config page
    Then Navigate to Order Size Tolerance Page

  @CI @DEV @QA @UAT @quick
  Scenario: Prepare data
    When user open Add panel
    And user select and input Pricing Service and currency
      | Pricing service | HPS |
      | CCY1            | CHF |
      | CCY2            | AUD |
    And tier is setup for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.2    |
    And tier is setup for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.1    |
    And there is no same record in database
    Then user can get Add result after click Save
      | Result | Order size tolerance is created successfully. |

  @CI @DEV @QA @UAT
  Scenario: AC1 Check edit button
    Given user prepare order tolerance query data
      | Pricing Service | HPS |
      | CCY1            | CHF |
      | CCY2            | AUD |
    When User click search to query
    And User click view detail for first record
    And click Edit button for detail panel
    Then the below cell are editable
      | CCY1 Minimum size | enabled |
      | CCY1 tolerance    | enabled |
      | CCY2 Minimum size | enabled |
      | CCY2 tolerance    | enabled |

  @CI @DEV @QA @UAT @quick
  Scenario: AC2 Update minimum size and tiers should be successful
    Given user prepare order tolerance query data
      | Pricing Service | HPS |
      | CCY1            | CHF |
      | CCY2            | AUD |
    And User click search to query
    And User click view detail for first record
    And click Edit button for detail panel
    When user update minimum size value
      | CCY1 Minimum size | 0 |
      | CCY2 Minimum size | 0 |
    And user update tiers for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | MAX       | 1.1    |
    And user update tiers for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | 500       | 1.5    |
      | 500         | MAX       | 1.3    |
    Then save the tolerance should be ok
    And User click view detail for first record
    And the value refreshed should reflect the update

  @CI @DEV @QA @UAT @quick
  Scenario: AC2-2 Update minimum size and tiers should be successful
    Given user prepare order tolerance query data
      | Pricing Service | HPS |
      | CCY1            | CHF |
      | CCY2            | AUD |
    And User click search to query
    And User click view detail for first record
    And click Edit button for detail panel
    When user update minimum size value
      | CCY1 Minimum size | 5,000     |
      | CCY2 Minimum size | 1,000,000 |
    And user update tiers for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | 1,000     | 1      |
      | 1,000       | MAX       | 1.2    |
    And user update tiers for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | MAX       | 1.1    |
    Then save the tolerance should be ok
    And User click view detail for first record
    And the value refreshed should reflect the update

  @CI @DEV @QA @UAT
  Scenario: AC3 Nothing changes update should be successful
    Given user prepare order tolerance query data
      | Pricing Service | HPS |
      | CCY1            | CHF |
      | CCY2            | AUD |
    And User click search to query
    And User click view detail for first record
    When click Edit button for detail panel
    Then save the tolerance should be ok
    And User click view detail for target record
    And the value refreshed should not be changed

  @CI @DEV @QA @UAT
  Scenario Outline: AC4 Invalid tiers update should be failed
    Given user prepare order tolerance query data
      | Pricing Service | HPS |
      | CCY1            | CHF |
      | CCY2            | AUD |
    And User click search to query
    And User click view detail for first record
    And click Edit button for detail panel
    When user update minimum size value
      | CCY1 Minimum size | <Min1> |
      | CCY2 Minimum size | <Min2> |
    And user update tiers for CCY1
      | Range Start  | Range End  | Factor |
      | 0            | 0          | 0      |
      | 0            | <RangeEnd> | 1      |
      | <RangeStart> | MAX        | 1.2    |
    And user update tiers for CCY2
      | Range Start | Range End | Factor   |
      | 0           | 0         | 0        |
      | 0           | MAX       | <Factor> |
    Then save the tolerance should get error
      | result | <Error> |

    Examples: 
      | Min1     | Min2      | RangeEnd | RangeStart | Factor | Error                                       |
      | -+12\#$% | $%&*()+33 | 100      | 100        | 1.1    | Error!- Invalid value. Please verify again. |
      | 500      | 0         | 100      | 500        | 1.1    | Error!- Form field is invalid.              |
      | 0        | 0         | 100      | 100        | -1.1   | Error!- Invalid value. Please verify again. |
