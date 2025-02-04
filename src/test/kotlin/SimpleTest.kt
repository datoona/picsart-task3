package web

import SimplePage
import org.testng.annotations.Test
import org.testng.asserts.SoftAssert

class SimpleTest : BaseTest() {

    @Test
    fun aaaa(){
        val driver = getDriver()
        val softAssert = SoftAssert()
        val simplePage = SimplePage(driver)
        simplePage.selectFilterBtn()
        softAssert.assertTrue(simplePage.isFilterComponentDisplayed(),"Filter component is not displayed")

        println("as")
    }
}