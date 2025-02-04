package web

import io.qameta.allure.Attachment
import org.example.web.DriverHelper
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
    private val BASE_UI_URL = "https://picsart.com"
    private val BROWSER = "firefox"
    private val SELENIUM_REMOTE = "false"


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

    protected fun getDriver(urlPath:String): WebDriver {
        val driver = DriverHelper.get(BROWSER, SELENIUM_REMOTE).getDriver()
        driver.get(getPageUrl(urlPath))
        return driver
    }

    private fun getPageUrl(urlPath:String): String {
        return "${BASE_UI_URL}${urlPath}"
    }

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