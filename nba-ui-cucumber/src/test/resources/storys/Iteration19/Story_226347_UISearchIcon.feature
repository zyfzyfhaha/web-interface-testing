#Story 226347 As a risk manager, I want to search IM from the Order_Normal_Size page
#AC1 > Query a single IM
#AC2 > There is no record found for IM
#AC3 > Query single fund when IM is selected
#AC4 > There is no record found for Fund
#AC5 > Query multiple fund when IM is selected
#AC6 > Query single fund when IM is NOT selected
#AC7 > Return an error message when IM and Fund not selected either
#AC8 > Clear fund when re-query IM
#AC9 > Dropdown list disappear
Feature: Story_226347 search IM from the Order_Normal_Size page

  Background: 
    Given user login to Reference data config page
    And navigate to Order Normal Size Page
    And navigate to Bulk Update Page
    And select SFX pricing service

  Scenario Outline: AC1 Query a single IM
    When Input a valid IM name which exsit in DB and click the IM search button
      | IM_NAME | <IM_NAME> |
    Then IM search results should be listed in dropdown list
      | IM_NAME | <IM_NAME> |

    @CI @DEV @QA @UAT
    Examples: valid IM name which is START WITH
      | IM_NAME                      |
      | STATE STREET GLOBAL ADVISORS |

    @CI @DEV @QA @UAT
    Examples: valid IM name which is EXACT
      | IM_NAME                             |
      | STATE STREET GLOBAL ADVISORS BOSTON |

    @CI @DEV @QA @UAT
    Examples: valid IM name which with special character
      | IM_NAME                         |
      | MERRILL LYNCH MGMT (HK) LTD -HK |

    @CI @DEV @QA @UAT
    Examples: space & lower case
      | IM_NAME                             |
      | state street GLOBAL ADVISORS BOSTON |

  Scenario Outline: AC2 There is no record found for IM
    When Input a valid IM name which not exsit in DB click the IM search button
      | IM_NAME | <IM_NAME> |
    Then No IM search results, no drop downlist, nothing happened

    @CI @DEV @QA @UAT
    Examples: valid IM name which not exsit in DB
      | IM_NAME |
      | ABC123  |

  Scenario Outline: AC3 Query fund when IM is selected
    Given Select a valid IM
      | IM_NAME | <IM_NAME> |
    When Input a valid Fund name which exsit in DB and click the Fund search button
      | FUND_NAME | <FUND_NAME> |
    Then Fund search results should be listed in dropdown list
      | FUND_NAME | <FUND_NAME> |
      | IM_ID     | <IM_ID>     |

    @CI @DEV @QA @UAT
    Examples: valid Fund name which is START WITH
      | IM_ID   | IM_NAME                             | FUND_NAME |
      | 9100054 | STATE STREET GLOBAL ADVISORS BOSTON | 2D0       |

    @CI @DEV @QA @UAT
    Examples: valid Fund name which is EXACT
      | IM_ID   | IM_NAME                             | FUND_NAME |
      | 9100054 | STATE STREET GLOBAL ADVISORS BOSTON | 2D08      |

    @CI @DEV @QA @UAT
    Examples: space & lower case
      | IM_ID   | IM_NAME                             | FUND_NAME |
      | 9100054 | STATE STREET GLOBAL ADVISORS BOSTON | 2d08      |

  Scenario Outline: AC4 There is no record found for Fund
    Given Select a valid IM
      | IM_NAME | <IM_NAME> |
    When Input a valid Fund name which NOT exsit in DB and click the Fund search button
      | FUND_NAME | <FUND_NAME> |
    Then No fund search results, no drop downlist, nothing happened

    @CI @DEV @QA @UAT
    Examples: valid Fund name which not exsit in DB
      | IM_NAME                             | FUND_NAME |
      | STATE STREET GLOBAL ADVISORS BOSTON | ABC123    |

  @zyf
  Scenario Outline: AC5 Select fund when IM is selected
    Given Select valid IM and click the Fund search button
      | IM_NAME   | <IM_NAME>   |
      | FUND_NAME | <FUND_NAME> |
    When User clicked some fund in dropdown list
    Then Fund name should be displayed properly in the textbox
    When Selected the fund already exist in textbox
    Then No fund added to textbox
    When Delete the fund in textbox
    Then The fund should be deleted

    @CI @DEV @QA
    Examples: select multiple fund
      | IM_ID   | IM_NAME                             | FUND_NAME |
      | 9100054 | STATE STREET GLOBAL ADVISORS BOSTON | D00       |

    @CI @DEV @QA
    Examples: select single fund
      | IM_ID   | IM_NAME                             | FUND_NAME |
      | 9100054 | STATE STREET GLOBAL ADVISORS BOSTON | D001      |

  Scenario Outline: AC6 Query single fund when IM is NOT selected
    When Input a valid Fund name which exsit in DB and click the Fund search button
      | FUND_NAME | <FUND_NAME> |
    Then Fund search results should be listed in dropdown list
      | IM_ID     | <IM_ID>     |
      | FUND_NAME | <FUND_NAME> |

    @CI @DEV @QA
    Examples: select multiple fund
      | IM_ID   | FUND_NAME |
      | 9100054 | D001      |

  Scenario Outline: AC7 Return an error message when IM and Fund not selected either
    When Click the Fund search button
    Then Return an error message

  Scenario Outline: AC8 Clear fund when re-query IM
    Given Select a valid IM and valid Fund
      | IM_NAME_1 | <IM_NAME_1> |
      | FUND_NAME | <FUND_NAME> |
    Then Select another valid IM
      | IM_NAME_2 | <IM_NAME_2> |
    Then The selected fund in textbox should be cleared

    @CI @DEV @QA @UAT
    Examples: Clear fund when re-query IM
      | IM_NAME_1                           | IM_NAME_2                           | FUND_NAME |
      | STATE STREET GLOBAL ADVISORS BOSTON | STATE STREET GLOBAL ADVISORS FRANCE | D001      |

  Scenario Outline: AC9 Dropdown list disappear
    Given Select a valid IM and valid Fund
      | IM_NAME   | <IM_NAME>   |
      | FUND_NAME | <FUND_NAME> |
    When Click ohter place
    Then Fund dropdown list should be disappeared
