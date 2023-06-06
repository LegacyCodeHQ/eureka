import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.chrome.ChromeDriver

fun main() {
  val errorFilePaths = mutableListOf<String>()
  val driver = ChromeDriver()
  driver.manage().timeouts().pageLoadTimeout
  driver.get("http://localhost:7080")

  val filePathLinks = driver.findElements(By.tagName("a"))
  filePathLinks.forEachIndexed { index, link ->
    println("Opening link ${index + 1} of ${filePathLinks.size}")
    link.click()
    val currentFilePath = link.text
    val last = driver.windowHandles.last()
    try {
      driver.switchTo().window(last)
      driver.findElement(By.tagName("svg"))
    } catch (e: NoSuchElementException) {
      errorFilePaths.add(currentFilePath)
    } finally {
      driver.switchTo().window(last).close()
      driver.switchTo().window(driver.windowHandles.first())
    }
  }
  driver.close()

  if (errorFilePaths.isEmpty()) {
    println("No parse failures")
  } else {
    println(errorFilePaths.joinToString("\n"))
  }
}
