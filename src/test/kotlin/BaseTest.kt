package web

import io.qameta.allure.Attachment
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.logging.Logger

open class BaseTest {
    private val logger: Logger = Logger.getLogger(javaClass.simpleName)
    private val baseUrl: String = System.getProperty("app-url")
    private val defUrl: String = "https://picsart.com"

    private val BROWSER: String = "firefox"  // we can add system variable
    private val SELENIUM_REMOTE: String = "false"  // we can add system variable

    private fun isWebUrlNullable(webUrl: String = defUrl): String {
        return baseUrl.ifEmpty {
            webUrl
        }
    }


    /* avelacnenq mez petkakan baner
     chat notification/report
     TM tool
     */
    @AfterMethod
    @Attachment(value = "Failure in method {0}", type = "image/png")
    fun tearDown(result: ITestResult): ByteArray? {
        val driver = DriverHelper.get(BROWSER, SELENIUM_REMOTE).getDriver()
        var output: ByteArray? = null
        if (result.status == ITestResult.FAILURE) {
            output = takeScreenshot(driver as TakesScreenshot, result.method.qualifiedName)
        }
        DriverHelper.get(BROWSER, SELENIUM_REMOTE).quitDriver(driver)
        return output
    }

    protected fun getDriver(urlPath: String): WebDriver {
        val driver = DriverHelper.get(BROWSER, SELENIUM_REMOTE).getDriver()
        driver.get(getPageUrl(urlPath))
        return driver
    }

    private fun getPageUrl(urlPath: String): String {
        return "${isWebUrlNullable()}${urlPath}"
    }

    // screenshot ev video | chisht klini retry implementation-ic heto
    @Attachment(value = "Failure in method {0}", type = "image/png")
    fun takeScreenshot(driver: TakesScreenshot, methodName: String): ByteArray {
        val currentDate = LocalDateTime.now();
        val formatDate = DateTimeFormatter.ofPattern("HHmmssSSS")
        val formattedDate = currentDate.format(formatDate)
        val screenshot: File = driver.getScreenshotAs(OutputType.FILE);
        "/tmp/gh/$methodName$formattedDate.png".also { path ->
            logger.severe("Screenshot taken => ${path}");
        }
        return driver.getScreenshotAs(OutputType.BYTES);
    }
}