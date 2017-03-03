#Story 116535: As a middle office, I want to view the detailed tolerance setup so that I can update the tolerance
#AC1> Given a list of currency pairs is searched out
#When click "Tolerance Details" button of a record
#Then the detailed tolerance setup for the currency pair is displayed on UI including minimum sizes
@Regression
Feature: Story_116535 Order Size Tolerance Configuration - View detailed tolerance setup

  Background: Login to Reference data configuration
    Given user login to Reference data config page
    Then Navigate to Order Size Tolerance Page

  @CI @QA @quick
  Scenario: check detail data
    Given user prepare order tolerance query data
      | Pricing Service | SSH |
      | CCY1            | MXN |
      | CCY2            | RUB |
    When User click search to query
    And User click view detail for first record
    Then the value in detail panel is correct
      | Title | SSH : MXN/RUB |
      | CCY1  | MXN           |
      | CCY2  | RUB           |
    And the tire for CCY1 in detail panel is correct
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | 1,000     | 1      |
      | 1,000       | 2,000     | 2      |
      | 2,000       | MAX       | 1.2    |
    And the tire for CCY2 in detail panel is correct
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | MAX       | 1.1    |

  @DEV
  Scenario: check detail data
    Given user prepare order tolerance query data
      | Pricing Service | SSH |
      | CCY1            | MXN |
      | CCY2            | RUB |
    When User click search to query
    And User click view detail for first record
    Then the value in detail panel is correct
      | Title | SSH : RUB/MXN |
      | CCY1  | RUB           |
      | CCY2  | MXN           |
    And the tire for CCY1 in detail panel is correct
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | MAX       | 1.1    |
    And the tire for CCY2 in detail panel is correct
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | 1,000     | 1      |
      | 1,000       | 2,000     | 2      |
      | 2,000       | MAX       | 1.2    |