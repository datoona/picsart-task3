package web

import SearchPage
import org.testng.annotations.Test
import org.testng.asserts.SoftAssert

class SearchPageTest : BaseTest() {
    private val searchPath: String = "/search/all/"
//    Data provider for resolutions using into threads | in progress

    @Test
    fun verifySearchPage(){
        val driver = getDriver(urlPath = searchPath)
        val softAssert = SoftAssert()
        val searchPage = SearchPage(driver)
        searchPage.selectFilterBtn()
        softAssert.assertTrue(searchPage.isFilterComponentDisplayed(),"Filter component hide state  should be displayed")
        searchPage.selectFilterBtn()
        softAssert.assertFalse(searchPage.isFilterComponentNotDisplayed(),"Filter component hide state should not be displayed")
        searchPage.clickOnLicenseCheckbox(LicenseType.Personal)
        // stex actionner ev assertion.
        // voroshel asserion-i Type ev dnel TestCase/TestScenario
        // I can continue and add actions.

    }
}