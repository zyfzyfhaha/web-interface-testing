#As a consumer, I want to get city detail by province
#AC1> Get city details successfully
#AC2> Get non city detail successfully
#AC3> Get the error message while session timeout
Feature: get the city by province

  Background: 
    #Service available
    Given Test environment is ready for getCityByProvince interface

  @test
  Scenario Outline: AC1 Get city details successfully
    Given The city detail are exist in DB
      | AREA_CODE | <AREA_CODE> |
    When Call the getCityByProvince interface
      | URL | <URL> |
    Then The city detail should be mapping with excepted results properly

    Examples: AC1_Case1: Get city details successfully
      | AREA_CODE | URL                                            |
      |    330000 | http://10.10.13.17:9090/area/getCityByProvince |

  @test
  Scenario Outline: AC2 Get non city detail successfully
    Given The city detail are not exist in DB
      | AREA_CODE | <AREA_CODE> |
    When Call the getCityByProvince interface
      | URL | <URL> |
    Then The city detail should be mapping with excepted results properly

    Examples: AC1_Case1: Get city details successfully
      | AREA_CODE | URL                                            |
      |      1025 | http://10.10.13.17:9090/area/getCityByProvince |

  @test
  Scenario Outline: AC3 Get the error message while session timeout
    Given The city detail are exist in DB
      | AREA_CODE | <AREA_CODE> |
    When Call the getCityByProvince Service without login
      | URL | <URL> |
    Then The city error message should be mapping with excepted results properly
      | ERROR_MESSAGE | <ERROR_MESSAGE> |

    Examples: AC1_Case1: Get the error message while session timeout
      | AREA_CODE | URL                                            | ERROR_MESSAGE                        |
      |    330000 | http://10.10.13.17:9090/area/getCityByProvince | {"code":30000,"message":"SESSION超时"} |
