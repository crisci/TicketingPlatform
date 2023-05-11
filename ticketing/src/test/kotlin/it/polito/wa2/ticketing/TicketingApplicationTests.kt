package it.polito.wa2.ticketing

import it.polito.wa2.ticketing.integrationTest.IntegrationTest
import it.polito.wa2.ticketing.unitTests.CustomerUnitTest
import it.polito.wa2.ticketing.unitTests.TicketUnitTest
import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName

@Suite
@SelectPackages("it.polito.wa2.ticketing.integrationTest","it.polito.wa2.ticketing.unitTests")
@SuiteDisplayName("Run all the tests")
class TicketingApplicationTests {
}
