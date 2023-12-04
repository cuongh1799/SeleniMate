import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;

public class y2mate_single {

    public static String extractCode(String input){
        int start = 0;
        int end = 0;

        for(char c : input.toCharArray()){
            if(c != '='){
                start++;
            }
            else break;
        }

        for(char c : input.toCharArray()){
            if(c != '&'){
                end++;
            }
            else break;
        }

        // get video code of the youtube link
        String videoCode = input.substring(start + 1, end);

        return videoCode;
    }

    public static void main(String[] args)
    {
        while(true){
            // get YT video code
            Scanner sc = new Scanner(System.in);
            System.out.print("Please paste in the link: ");
            String link = sc.next();

            // get video code of the youtube link
            String videoCode = extractCode(link);

            System.out.println( "Converted link: " + "https://www.y2mate.com/youtube-mp3/" + videoCode);
            String fullLink = "https://www.y2mate.com/youtube-mp3/" + videoCode;
            ChromeDriver driver = new ChromeDriver();


            // headless % Experimental
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");
//        options.addArguments("--window-size=1920,1080");
//        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//        options.setExperimentalOption("useAutomationExtension", false);
//        options.addArguments("--disable-blink-features=AutomationControlled");
//        WebDriver driver = new ChromeDriver(options);
//        driver = new ChromeDriver(options);

            // Open the webpage
            driver.get(fullLink);
            try {
                driver.navigate().to(fullLink);
                Thread.sleep(1000);
                //System.out.println(driver.getPageSource());

                WebElement element = driver.findElement(By.id("process_mp3"));

                // Click download
                element.click();
                Thread.sleep(3000);

                // Find all element with tagName
                List<WebElement> plants = driver.findElements(By.xpath("//a[@class='btn btn-success btn-file' and @target='_blank' and @rel='nofollow']"));

                if(plants.size() == 0){
                    System.out.println("None found");
                }
                else {
                    System.out.println("Found " + plants.size() + " elements.");
                    for(WebElement e : plants) {
                        System.out.println(e + "\n");
                        System.out.println(e.getAttribute("href"));
                        driver.navigate().to(e.getAttribute("href"));
                    }
                }

                Thread.sleep(2000);

                System.out.println("Sucess");
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            catch (NoSuchElementException e){
                System.out.println("No element found");
                driver.quit();
            }
            finally {
                driver.quit();
            }

            System.out.println("Continue? y/n ");
            String ans = sc.next();
            if(ans.equals("n")){
                break;
            }
        }
    }
}
