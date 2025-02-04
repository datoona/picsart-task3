import web.BasePage
import web.WaitHelper.waitForElementToBeVisible1
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class SearchPage(driver: WebDriver) : BasePage(driver) {

    // it's classic solution PageFactory
    @FindBy(xpath = "//button[@data-testid='search-header-filter']")
    private lateinit var filterBtn: WebElement

    @FindBy(xpath = "//iframe[@data-testid='com.picsart.social.search']")
    private lateinit var frame: WebElement

    @FindBy(xpath = "//div[contains(@class, 'search-filter-root') and contains(@class, 'hide')]")
    private lateinit var filterComponent: WebElement

    fun selectFilterBtn(isFrame: Boolean = true) {
        waitForElementToBeVisible1(frame)
        isFrame.takeIf { it }?.let { driver.switchTo().frame(frame) }
        click(filterBtn)
        isFrame.takeIf { it }?.let { driver.switchTo().defaultContent() }
    }

    fun isFilterComponentDisplayed(): Boolean {
        return isDisplayed(filterComponent)
    }

    fun isFilterComponentNotDisplayed(): Boolean {
        return isNotDisplayed(filterComponent)
    }


}