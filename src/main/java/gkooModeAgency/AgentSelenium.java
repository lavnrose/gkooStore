package gkooModeAgency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AgentSelenium {
    private static final int SECOND_2 = 2000;
    private static final int SECOND_5 = 5000;
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/sanghuncho/Programme/chromedriver.exe");
        //System.setProperty("webdriver.gecko.driver", "C:/Users/sanghuncho/Programme/geckodriver.exe");
            
        WebDriver driver = new ChromeDriver();
        //WebDriver driver = new FirefoxDriver();
            
        //driver.get("https://signin.ebay.de/ws/eBayISAPI.dll?SignIn&ru=https%3A%2F%2Fwww.ebay.com%2F");
        driver.get("https://de.maje.com/de/bekleidung/kollektion/tops-und-hemden/");
        //driver.get("https://www.ebay.de/itm/Siemens-6ruf-lsp-14g-Vintage-Lautsprecher-Speaker-Roehrenradio-Selten-/154262452027");
        //By cookies_accept = By.xpath("//*[@title='Accept Cookies']");
        
        waitFor(SECOND_5);
        
        WebElement click = driver.findElement(By.xpath("//*[@id='consentAllowAllCookies']"));
        click.click();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        
        waitFor(SECOND_2);

        //driver.findElement(By.xpath("//img[@ src='/on/demandware.static/Sites-Maje-DE-Site/-/default/dw1ea5bb9d/images/double-arrow-down.png']")).click();
        WebElement btn = driver.findElement(By.xpath("//a[@href='https://de.maje.com/de/bekleidung/kollektion/tops-und-hemden/*']"));
        System.out.println(btn.toString());
        btn.click();
        
    }

    public static void waitFor(int miliseconds) {
        try{
            Thread.sleep(miliseconds);
        } catch(InterruptedException e){ 
            LOGGER.trace("wait for error");
        }
  }
}
