@Role @QA @UAT @New 
Feature: Role authorization - access control for UI menu

  Scenario:
    When user login to Reference data page
      | RoleType | Middle_office |
    Then user view feature boxes
      | feature_box          | view |
      | Order Size Tolerance | can  |
      | Normal Order Size    | can  |
      | Trader Mapping       | can  |
      | CCY Pair Deadline	 | can  |

  Scenario:
    When user login to Reference data page
      | RoleType | Risk_manager |
    Then user view feature boxes
      | feature_box          | view   |
      | Order Size Tolerance | can    |
      | Normal Order Size    | can    |
      | Trader Mapping       | cannot |
      | CCY Pair Deadline	 | cannot |

  Scenario:
    When user login to Reference data page
      | RoleType | IT_support |
    Then user view feature boxes
      | feature_box          | view |
      | Order Size Tolerance | can  |
      | Normal Order Size    | can  |
      | Trader Mapping       | can  |
      | CCY Pair Deadline	 | can  |

  Scenario: 
    When user login to Reference data page
      | RoleType | Sales_trader |
    Then user view feature boxes
      | feature_box          | view   |
      | Order Size Tolerance | can    |
      | Normal Order Size    | can    |
      | Trader Mapping       | cannot |
      | CCY Pair Deadline	 | can    |
    
  Scenario:
    When user login to Reference data page
      | RoleType | Admin |
    Then user view feature boxes
      | feature_box          | view   |
      | Order Size Tolerance | can    |
      | Normal Order Size    | can    |
      | Trader Mapping       | can 	  |
      | CCY Pair Deadline	 | can    | 
     #| UI Module Configure	 | can    |   
           