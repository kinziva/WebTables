package TablesHomework;

//1) goto https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8
//2) Create a HashMap
//3) change row number to 100, read all data on first page and put uniquID as a KEY 
//and Applicant info as a Value to a map. applicants.put(29,"Amer, Sal-all@dsfdsf.com-554-434-4324-130000")
//4) Click on next page , repeat step 3
//5) Repeat step 4 for all pages 
//6) print count of items in a map. and assert it is matching with a number at the buttom
//======================================

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class tablesHomework {

	WebDriver driver;
	String url = "https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8";
	Map<Integer, String> allData = new HashMap<>();

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		// 2) Create a HashMap

	}

	@BeforeMethod
	public void navigateHomePage() {
		driver.get(url);
	}

	// 3) change row number to 100,
	@Test
	public void readTable() throws InterruptedException {

		// Set Page Range to 100
		WebElement select100 = driver.findElement(By.xpath("//select[@id='recPerPage']"));
		Select list = new Select(select100);
		list.selectByVisibleText("100");
		Thread.sleep(1000);

		// applicant number
		int count = Integer.parseInt(driver.findElement(By.xpath("//span[@id='total']")).getText());
		System.out.println(" Count" + count);
		// next page arrow element
		WebElement next = driver.findElement(By.xpath("//a[@class='nxtArrow']"));
		// Gets the all rows <tr>
		List<WebElement> Rows100 = driver.findElements(By.xpath("//table[@id='reportTab']/tbody/tr"));

		// 3 - read all data on first page and put uniquID as a KEY  and Applicant info as a Value to a map.applicants.put(29,"Amer,Sal-all@dsfdsf.com-554-434-4324-130000")
		DataOf1Page(Rows100);

		// 5) Repeat step 4 for all pages
		do {
			// 4) Click on next page
			next.click();
			Thread.sleep(500);
			// repeat step 3 with new row of elements
			DataOf1Page(driver.findElements(By.xpath("//table[@id='reportTab']/tbody/tr")));

		} while (!next.getAttribute("class").contains("smallDim"));

		// 6) print count of items in a map
		System.out.println(allData.size());
		// assert it is matching with a number at the buttom
		Assert.assertEquals(allData.size(), count);
	}

	public void DataOf1Page(List<WebElement> rows) {
		// loop each row
		for (int j = 1; j <= rows.size(); j++) {
			// ID by row
			Integer key = Integer.parseInt(driver.findElement(By.xpath("//tbody//tr[" + j + "]/td[1]")).getText());
			String name = driver.findElement(By.xpath("//tbody//tr[" + j + "]/td[2]")).getText();
			String email = driver.findElement(By.xpath("//tbody//tr[" + j + "]/td[3]")).getText();
			String phone = driver.findElement(By.xpath("//tbody//tr[" + j + "]/td[4]")).getText();
			String salary = driver.findElement(By.xpath("//tbody//tr[" + j + "]/td[5]")).getText();
			String value = name + " " + email + " " + phone + " " + salary;
			// Adds key and value to the map
			allData.put(key, value);
		}
	}
}
