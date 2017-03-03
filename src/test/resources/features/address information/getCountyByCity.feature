#As a consumer, I want to get county detail by city
#AC1> Get county details successfully
#AC2> Get non county detail successfully
#AC3> Get the error message while session timeout
Feature: get the get county detail by city

  Background: 
    #Service available
    Given Test environment is ready for getCountyByCity interface

  @test
  Scenario Outline: AC1 Get county details successfully
    Given The county detail are exist in DB
      | AREA_CODE | <AREA_CODE> |
    When Call the getCountyByCity interface
      | URL | <URL> |
    Then The county detail should be mapping with excepted results properly

    Examples: AC1_Case1: Get county details successfully
      | AREA_CODE | URL                                          |
      |    330100 | http://10.10.13.17:9090/area/getCountyByCity |

  @test
  Scenario Outline: AC2 Get non county detail successfully
    Given The county detail are not exist in DB
      | AREA_CODE | <AREA_CODE> |
    When Call the getCountyByCity interface
      | URL | <URL> |
    Then The county detail should be mapping with excepted results properly

    Examples: AC1_Case2: Get non county detail successfully
      | AREA_CODE | URL                                          |
      |      1025 | http://10.10.13.17:9090/area/getCountyByCity |

  @test
  Scenario Outline: AC3 Get the error message while session timeout
    Given The county detail are exist in DB
      | AREA_CODE | <AREA_CODE> |
    When Call the getCountyByCity Service without login
      | URL | <URL> |
    Then The county error message should be mapping with excepted results properly
      | ERROR_MESSAGE | <ERROR_MESSAGE> |

    Examples: AC1_Case1: Get the error message while session timeout
      | AREA_CODE | URL                                          | ERROR_MESSAGE                        |
      |    330100 | http://10.10.13.17:9090/area/getCountyByCity | {"code":30000,"message":"SESSION超时"} |
