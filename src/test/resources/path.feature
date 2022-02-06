Feature: 경로 조회 기능

  Scenario: 최단 경로 조회
    Given 지하철 역들이 등록되어 있고
    And 지하철 노선이 등록되어 있고
    And 구간이 등록되어 있고
    When 출발역과 도착역에 대한 최단 경로 조회를 요청하면
    Then 사용자는 최단 경로의 역 정보를 응답받는다