import org.openqa.selenium.By
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

    @FindBy(xpath = "//input[@aria-label='licenses-Personal-checkbox']/parent::*")
    private lateinit var checkbox: WebElement

    fun selectFilterBtn(isFrame: Boolean = true) {
        waitForElementToBeVisible1(frame)                              // (Component/Layout/Widget level)
        isFrame.takeIf { it }?.let { driver.switchTo().frame(frame) } // qani vor iframe shat ka DOM-i mej karox enq qnnarkel ev lucel
        click(filterBtn)
        isFrame.takeIf { it }?.let { driver.switchTo().defaultContent() }
    }

    fun isFilterComponentDisplayed(): Boolean {
        return isDisplayed(filterComponent)
    }

    fun isFilterComponentNotDisplayed(): Boolean {
        return isNotDisplayed(filterComponent)
    }

    // urish motecum | kotlin-@ java-i update version-a ))))
    fun clickOnLicenseCheckbox(checkBox: LicenseType) {
        driver.switchTo().frame(frame)
        when (checkBox) {
            LicenseType.All,
            LicenseType.Commerce,
            LicenseType.Personal
                -> click(By.xpath("//input[@aria-label='licenses-${checkBox}-checkbox']/parent::*")) // xpath we can change

            else -> throw IllegalArgumentException("Invalid checkbox value: $checkBox")
        }

    }

}

/* enumner-ov karox enq hetaqrqir lucum tal
   ays bolor locatorner@elementner@ petq en APPIUM-ov grelu hamar
 */
enum class LicenseType { All, Commerce, Personal }