package web

import SearchPage
import org.testng.annotations.Test
import org.testng.asserts.SoftAssert

class SearchPageTest : BaseTest() {
    private val searchPath: String = "/search/all/"

    @Test
    fun verifySearchPage(){
        val driver = getDriver(urlPath = searchPath)
        val softAssert = SoftAssert()
        val searchPage = SearchPage(driver)
        searchPage.selectFilterBtn()
        softAssert.assertTrue(searchPage.isFilterComponentDisplayed(),"Filter component hide state  should be displayed")
        searchPage.selectFilterBtn()
        softAssert.assertFalse(searchPage.isFilterComponentNotDisplayed(),"Filter component hide state should not be displayed")
        // I can continue and add actions.
    }
}