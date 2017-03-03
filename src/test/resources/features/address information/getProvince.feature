#As a consumer, I want to get province detail
#AC1> Get province detail successfully
#AC2> Get the error message while session timeout
Feature: get the province test

  Background: 
    #Service available
    Given Test environment is ready for getProvince

  @test
  Scenario Outline: AC1 Get province detail successfully
    Given The province detail are exist in DB
    When Call the getProvince interface
      | URL | <URL> |
    Then The province detail should be mapping with excepted results properly

    Examples: AC1_Case1: get the user session successfully
      | URL                                      |
      | http://10.10.13.17/area/getProvince |

  @test
  Scenario Outline: AC2 Get the error message while session timeout
    Given The province detail are exist in DB
    When Call the getProvince interface whitout login
      | URL | <URL> |
    Then The province session timeout error message should be mapping with excepted results properly
      | ERROR_MESSAGE | <ERROR_MESSAGE> |

    Examples: AC1_Case1: get the user session successfully
      | URL                                      | ERROR_MESSAGE                        |
      | http://10.10.13.17/area/getProvince | {"code":30000,"message":"SESSION超时"} |
