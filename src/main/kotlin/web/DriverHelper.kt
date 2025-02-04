package org.example.web

import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.MalformedURLException
import java.net.URL

class DriverHelper(private val browser: String, private val remoteBrowser: String) {
    private val width:Int = 1920
    private val height:Int = 1080

    fun getDriver(): WebDriver {
        if (driverThread.get() == null) {
            driverThread.set(createDriver())
        }
        return driverThread.get()
    }

    fun quitDriver(driver: WebDriver) {
        driver.quit()
        driverThread.remove()
    }

    private fun createDriver(): WebDriver {
        // we can add another browser e.g safari
        val driver: WebDriver = when {
            remoteBrowser.toBoolean() -> initRemoteDriver()
            "chrome" == browser -> {
                createChromeDriver()
            }

            else -> createFirefoxDriver()
        }

        driver.manage().deleteAllCookies()
        if (isHeadless()) {
            driver.manage().window().size = Dimension(width, height)
        } else {
            driver.manage().window().size = Dimension(width, height)
        }
        return driver
    }

    private fun createFirefoxDriver(): FirefoxDriver {
        val options = FirefoxOptions();
        if (isHeadless()) {
            // hard code we can change to read system variables
            options.addArguments("--headless")
        }
        return FirefoxDriver(options)
    }

    private fun createChromeDriver(): ChromeDriver {
        val options = ChromeOptions();
        if (isHeadless()) {
            // hard code we can change to read system variables
            options.addArguments("--headless")
        }
        val driver = ChromeDriver()
        return driver
    }

    private fun initRemoteDriver(): RemoteWebDriver {
        return if ("chrome" == browser) {
            initRemoteChromeDriver()
        } else {
            initRemoteFirefoxDriver()
        }
    }

    private fun initRemoteChromeDriver(): RemoteWebDriver {
        val chromeOptions = ChromeOptions()
        try {
            // hard code we can change to read system variables
            chromeOptions.setCapability("browserVersion", "67")
            chromeOptions.setCapability("platformName", "Windows XP")
            return RemoteWebDriver(URL("http://www.example.com"), chromeOptions)
        } catch (e: MalformedURLException) {
            throw RuntimeException("Failed to initialize remote web driver", e)
        }
    }

    private fun initRemoteFirefoxDriver(): RemoteWebDriver {
        val firefoxOptions = FirefoxOptions()
        try {
            // hard code we can change to read system variables
            firefoxOptions.setCapability("browserVersion", "67")
            firefoxOptions.setCapability("platformName", "Windows XP")
            return RemoteWebDriver(URL("http://www.example.com"), firefoxOptions)
        } catch (e: MalformedURLException) {
            throw RuntimeException("Failed to initialize remote web driver", e)
        }
    }

    private fun isHeadless(): Boolean {
        return false // GraphicsEnvironment.isHeadless()
    }


    companion object {
        @JvmStatic
        fun get(browser: String, remoteBrowser: String): DriverHelper {
            return DriverHelper(browser, remoteBrowser)
        }

        fun getDriver(): WebDriver {
            return driverThread.get()
        }

        private val driverThread = ThreadLocal<WebDriver>()
    }
}