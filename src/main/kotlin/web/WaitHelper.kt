package web

import web.DriverHelper.Companion.getDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

object WaitHelper {

    // its example of waiting | Chisht waiter kazmakerpel Picsart specific Component
    private val timeout = Duration.ofSeconds(10)

    fun waitForElementToBeVisible(location: By) {
        WebDriverWait(getDriver(), timeout).until(ExpectedConditions.visibilityOfElementLocated(location))
    }

    fun waitForElementToBeClickable(location: By) {
        WebDriverWait(getDriver(), timeout).until(ExpectedConditions.elementToBeClickable(location))
    }

    fun waitForElementToBeInVisible(location: By) {
        WebDriverWait(getDriver(), timeout)
            .until(ExpectedConditions.invisibilityOfElementLocated(location))
    }

    fun waitForElementToBeVisible(element: WebElement) {
        WebDriverWait(getDriver(), timeout)
            .until(ExpectedConditions.visibilityOf(element))
    }

    fun waitForElementToBeVisible1(element: WebElement) {
        WebDriverWait(getDriver(), timeout)
            .until(ExpectedConditions.visibilityOf(element))
    }

    fun waitForElementToBeInVisible(element: WebElement) {
        WebDriverWait(getDriver(), timeout)
            .until(ExpectedConditions.invisibilityOf(element))
    }

}