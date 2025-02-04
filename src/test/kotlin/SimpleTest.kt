package web

import SimplePage
import org.testng.annotations.Test
import org.testng.asserts.SoftAssert

class SimpleTest : BaseTest() {
    private val searchPath: String = "/search/all/"

    @Test
    fun verifySearchPage(){
        val driver = getDriver(urlPath = searchPath)
        val softAssert = SoftAssert()
        val simplePage = SimplePage(driver)
        simplePage.selectFilterBtn()
        softAssert.assertTrue(simplePage.isFilterComponentDisplayed(),"Filter component hide state  should be displayed")
        simplePage.selectFilterBtn()
        softAssert.assertFalse(simplePage.isFilterComponentNotDisplayed(),"Filter component hide state should not be displayed")
        // I can continue and add actions.
    }
}