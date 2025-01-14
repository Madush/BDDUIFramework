Feature: HomePage Validations

  Scenario: Verify user direct to the correct page
    Given The user is on the homepage
    Then the user is redirected to the homepage

  Scenario: Verify user direct to the correct page
    Given The user is on the homepage
    Then the user is redirected to the homepage

  Scenario: Verify user direct to the correct page
    Given The user is on the homepage
    Then the user is redirected to the homepage

  Scenario: Verify whether user direct to the 'Datorer och Kontor' page successfully
    Given The user is on the homepage
    Then Click on the Accept cookies option
    Then Click on the "DATORER & KONTOR" button
    Then I should see search results related to "Datorer och Kontor"

  Scenario: Verify whether user direct to the 'VITVAROR' page successfully
    Given The user is on the homepage
    Then Click on the Accept cookies option
    Then Click on the "VITVAROR" button
    Then I should see search results related to "Vitvaror"

  Scenario: Verify whether user direct to the 'HEM, HUSHÅLL & TRÄDGÅRD' page successfully
    Given The user is on the homepage
    Then Click on the Accept cookies option
    Then Click on the "HEM, HUSHÅLL & TRÄDGÅRD" button
    Then I should see search results related to "Hem, Hushåll och Trädgård"

  Scenario: Verify whether user direct to the 'GAMING' page successfully
    Given The user is on the homepage
    Then Click on the Accept cookies option
    Then Click on the "GAMING" button
    Then I should see search results related to "Gaming"

  Scenario: Verify whether user direct to the 'MOBILER, TABLETS & SMARTKLOCKOR' page successfully
    Given The user is on the homepage
    Then Click on the Accept cookies option
    Then Click on the "MOBILER, TABLETS & SMARTKLOCKOR" button
    Then I should see search results related to "Mobiler, Tablets och Smartklockor"
