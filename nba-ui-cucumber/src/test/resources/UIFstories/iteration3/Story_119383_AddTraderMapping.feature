#Story 119383 As a middle office, I want to add reuters and wss trader id mapping on reference data UI so that I don't need to insert data to DB directly.
#AC1 > Given middle office has navigated to "Create GmbH Trader ID Mapping" screen, When he inputs valid GmbH WSS Trader ID, GmbH Reuters Trader ID, SSBT WSS Trader ID, SSBT Reuters Trader ID, City and click save, Then the Reuters trader id and WSS trader id mapping has been created successfully.
#AC2 > Given middle office has navigated to "Create GmbH Trader ID Mapping" screen, When any of GmbH WSS Trader ID, GmbH Reuters Trader ID, SSBT WSS Trader ID, SSBT Reuters Trader ID, City is missing from his input and click save, Then there is error message indicates the missing field is required.
#AC3 > Given middle office has navigated to "Create GmbH Trader ID Mapping" screen, When he inputs valid GmbH WSS Trader ID, GmbH Reuters Trader ID, SSBT WSS Trader ID, SSBT Reuters Trader ID, City and "GmbH Reuters Trader ID, SSBT Reuters Trader ID" exists and click save, Then there is error message indicates this mapping is duplicated in database.
#AC4 > Given middle office has navigated to "Create GmbH Trader ID Mapping" screen, When he inputs valid GmbH WSS Trader ID, GmbH Reuters Trader ID, SSBT WSS Trader ID, SSBT Reuters Trader ID, City and the existing "WSS ID" is the same with the one based on input "GMBH Reuters ID and GMBH WSS ID", click save, Then the Reuters trader id and WSS trader id mapping has been created successfully.
#AC5 > Given middle office has navigated to "Create GmbH Trader ID Mapping" screen, When he inputs valid GmbH WSS Trader ID, GmbH Reuters Trader ID, SSBT WSS Trader ID, SSBT Reuters Trader ID, City and the existing "WSS ID" is the same with the one based on input "SSBT Reuters ID and SSBT WSS ID", click save, Then the Reuters trader id and WSS trader id mapping has been created successfully.
#AC6 > Given middle office has navigated to "Create GmbH Trader ID Mapping" screen, When he inputs invalid GmbH WSS Trader ID, GmbH Reuters Trader ID, SSBT WSS Trader ID, SSBT Reuters Trader ID, City and click save, Then there is error message indicates the fields are invalid.
#AC7 > Given middle office has login into system, When he open up left navigation panel, Then there is the menu about "GMBH trader mapping"
#AC8 > Given IT support has login into system, When he open up left navigation panel, Then there is the menu about "GMBH trader mapping"
#AC9 > Given IT support has navigated to "Create GmbH Trader ID Mapping" screen, When he inputs valid GmbH WSS Trader ID, GmbH Reuters Trader ID, SSBT WSS Trader ID, SSBT Reuters Trader ID, City and click save, Then there is error message indicates the user has no privilege to do this action
@Regression
Feature: Story_119383 GmbH reference data config - Add reuters trader id and wss trader id mapping

  Background: Login to Reference data configuration
   Given user login to Reference data config page
   And Navigate to Trader Mapping Page
   And user open Add panel
  
  @quick
  Scenario Outline: AC1 input valid data, add trader mapping successful
    When User Prepare Add Trader Id mapping data
      | GMBH_REUTERS_ID | <GMBH_REUTERS_ID> |
      | SSBT_REUTERS_ID | <SSBT_REUTERS_ID> |
      | GMBH_WSS_ID     | <GMBH_WSS_ID>     |
      | SSBT_WSS_ID     | <SSBT_WSS_ID>     |
      | CITY            | <CITY>            |
    And there is no same trader mapping in database
    Then user can get Add result after click Save
      | Result | <Result> |

    @CI @DEV
    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY | Result                                       |
      | GSA         | GBA             | SSA         | SBA             | HON  | GMBH trader mapping is created successfully. |

    @QA @UAT
    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY | Result                                       |
      | GSA         | GBA             | SSA         | SBA             | HON  | GMBH trader mapping is created successfully. |

  Scenario Outline: AC2 any of the inputs is missed, add trader mapping should get error msg
    When User Prepare Add Trader Id mapping data
      | GMBH_REUTERS_ID | <GMBH_REUTERS_ID> |
      | SSBT_REUTERS_ID | <SSBT_REUTERS_ID> |
      | GMBH_WSS_ID     | <GMBH_WSS_ID>     |
      | SSBT_WSS_ID     | <SSBT_WSS_ID>     |
      | CITY            | <CITY>            |
    Then user can get Add result after click Save
      | Result | <Error> |

    @CI @DEV @QA @UAT
    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY  | Error                          |
      | @Null       | GBA             | SSA         | SBA             | HON   | Error!- Form field is invalid. |
      | GSA         | @Null           | SSA         | SBA             | HON   | Error!- Form field is invalid. |
      | GSA         | GBA             | @Null       | SBA             | HON   | Error!- Form field is invalid. |
      | GSA         | GBA             | SSA         | @Null           | HON   | Error!- Form field is invalid. |
      | GSA         | GBA             | SSA         | @Null           | @Null | Error!- Form field is invalid. |

  @quick
  Scenario Outline: AC3 input data GMBH_REUTERS_ID and SSBT_REUTERS_ID pair are exist, add trader mapping failed for duplication
    When User Prepare Add Trader Id mapping data
      | GMBH_REUTERS_ID | <GMBH_REUTERS_ID> |
      | SSBT_REUTERS_ID | <SSBT_REUTERS_ID> |
      | GMBH_WSS_ID     | <GMBH_WSS_ID>     |
      | SSBT_WSS_ID     | <SSBT_WSS_ID>     |
      | CITY            | <CITY>            |
    #And this trader mapping already exists
    Then user can get Add result after click Save
      | Result | <Error> |

    @CI
    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY | Error                                                    |
      | HCL         | HC              | ALC         | AC              | LON  | Error!- Internal Service Error: Duplicated record exists |

    @DEV
    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY | Error                                                    |
      | GSA         | GBA             | SSA         | SBA             | HON  | Error!- Internal Service Error: Duplicated record exists |

    @UAT @QA
    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY | Error                                                    |
      | BMI         | MT1             | LMR         | LT1             | LON  | Error!- Internal Service Error: Duplicated record exists |

  @quick
  Scenario Outline: AC4 input data GMBH_REUTERS_ID is exist and GMBH_WSS_ID is different, add trader mapping failed for duplication
    When User Prepare Add Trader Id mapping data
      | GMBH_REUTERS_ID | <GMBH_REUTERS_ID> |
      | SSBT_REUTERS_ID | <SSBT_REUTERS_ID> |
      | GMBH_WSS_ID     | <GMBH_WSS_ID>     |
      | SSBT_WSS_ID     | <SSBT_WSS_ID>     |
      | CITY            | <CITY>            |
    Then user can get Add result after click Save
      | Result | <Error> |

    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY | Error                                                            |
      | HCQ         | LA              | MKP         | MIK             | LON  | Error!- The GMBH Reuters Id has already mapped to another WSS Id |

    @CI @DEV
    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY | Error                                                            |
      | GSS         | GBA             | VBN         | VBM             | LON  | Error!- The GMBH Reuters Id has already mapped to another WSS Id |

    @UAT @QA
    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY | Error                                                            |
      | BMG         | MT1             | VBN         | VBM             | HON  | Error!- The GMBH Reuters Id has already mapped to another WSS Id |

  Scenario Outline: AC5 input data SSBT_REUTERS_ID is exist and SSBT_WSS_ID is different, add trader mapping failed for duplication
    When User Prepare Add Trader Id mapping data
      | GMBH_REUTERS_ID | <GMBH_REUTERS_ID> |
      | SSBT_REUTERS_ID | <SSBT_REUTERS_ID> |
      | GMBH_WSS_ID     | <GMBH_WSS_ID>     |
      | SSBT_WSS_ID     | <SSBT_WSS_ID>     |
      | CITY            | <CITY>            |
    Then user can get Add result after click Save
      | Result | <Error> |

    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY | Error                                                            |
      | GKP         | GIK             | BIH         | BH              | LON  | Error!- The SSBT Reuters Id has already mapped to another WSS Id |

    @CI @DEV
    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY | Error                                                            |
      | VGN         | VGM             | SSX         | SBA             | LON  | Error!- The SSBT Reuters Id has already mapped to another WSS Id |

    @UAT @QA
    Examples: 
      | GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY | Error                                                            |
      | VGN         | VGM             | LMF         | LT1             | HON  | Error!- The SSBT Reuters Id has already mapped to another WSS Id |
