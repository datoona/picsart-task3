package web

import org.example.web.WaitHelper.waitForElementToBeVisible
import org.openqa.selenium.*
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.Select
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger
import kotlin.streams.toList

abstract class BasePage(protected val driver: WebDriver) {

    private val logger: Logger = Logger.getLogger(javaClass.simpleName)

    init {
        PageFactory.initElements(driver, this)
    }

    fun type(element: WebElement, text: String) {
        logger.info("-*- Driver is filling in $element via type method  with $text")
        waitForElementToBeVisible(element)
        element.clear()
        element.sendKeys(text)
    }

    fun find(location: By): WebElement {
        logger.info("-*- Driver is finding element by $location")
        return driver.findElement(location)
    }

    fun isDisplayed(element: WebElement): Boolean = try {
        element.isDisplayed
        logger.info("-*- $element is displayed ")
        true
    } catch (e: NoSuchElementException) {
        logger.info("-*- $element is not displayed $e")
        false
    }

    fun isNotDisplayed(element: WebElement): Boolean {
        return try {
            element.isDisplayed
            logger.info("-*- $element is not displayed ")
            false
        } catch (e: NoSuchElementException) {
            logger.info("-*- $element is displayed $e")
            true
        }
    }

    fun isDisplayed(location: By): Boolean {
        return try {
            find(location).isDisplayed
        } catch (e: NoSuchElementException) {
            false
        }
    }

    fun isElementVisible(element: WebElement): Boolean {
        return try {
            element.isDisplayed
        } catch (e: NoSuchElementException) {
            false
        }
    }

    fun driverSleep(time: Int) {
        logger.info("-*- Driver is sleeping with: $time")
        try {
            Thread.sleep(time.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun getElementText(element: WebElement): String {
        waitForElementToBeVisible(element)
        logger.info("-*- Driver is getting Texts: " + element.text)
        return element.text
    }


    fun getElementCssValue(element: WebElement, value: String): String {
        waitForElementToBeVisible(element)
        return element.getCssValue(value)
    }

    fun scrollDown(vertical: Int) {
        logger.info("-*- Driver is scrolling down with: $vertical")
        val js = driver as JavascriptExecutor?
        js!!.executeScript("window.scrollBy(0,$vertical)")
    }

    fun scrollUp(vertical: Int) {
        logger.info("-*- Driver is scrolling up with: $vertical")
        val js = driver as JavascriptExecutor?
        js!!.executeScript("window.scrollBy(0,$vertical)")
    }

    fun scrollUntilElement(element: WebElement) {
        logger.info("-*- Driver is scrolling until: $element")
        (driver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true);", element)
    }

    fun openNewTab(url: String) {
        logger.info("-*- Driver is opening new tab with: $url URL")
        (driver as JavascriptExecutor).executeScript("window.open('$url','_blank')")
    }

    fun changeTab(index: Int) {
        logger.info("-*- Driver is changing tab with: $index")
        val windows: Array<Any> = driver.windowHandles.toTypedArray()
        driver.switchTo().window(windows[index] as String).toString()
    }

    fun currentData(): String {
        val formatter = SimpleDateFormat("ddHHmmss")
        val date = Date()
        return formatter.format(date)
    }

    val actions: Actions
        get() = Actions(driver)

    fun clickDropDownItemByName(element: WebElement, name: String) {
        Select(element).selectByVisibleText(name)
    }

    fun click(element: WebElement) {
        logger.info("-*- Driver is clicking on $element")
        waitForElementToBeVisible(element)
        element.click()
    }

    fun clickJS(element: WebElement) {
        waitForElementToBeVisible(element)
        val js = driver as JavascriptExecutor
        js.executeScript("arguments[0].click();", element)
    }

    fun clickViaTextOfList(elements: List<WebElement>, elementText: String) {
        elements
            .stream()
            .filter { element: WebElement -> getElementText(element) == elementText }
            .findFirst()
            .map { click(it) }
            .orElseThrow { NoSuchElementException("No element found in the list for the provided text - $elementText") }
    }

    fun elementExistsForText(elements: List<WebElement>, elementText: String): Boolean {
        return elements
            .stream()
            .filter { element: WebElement -> getElementText(element).contains(elementText) }
            .toList()
            .isNotEmpty()
    }
}