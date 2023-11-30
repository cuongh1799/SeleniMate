import java.net.*;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.chrome.*;

public class UrlConnectionReader  {
    public static void main(String[] args)
    {

        // get YT video code
        Scanner sc = new Scanner(System.in);
        System.out.print("Please paste in the link: ");
        String link = sc.next();

        int start = 0;
        int end = 0;

        for(char c : link.toCharArray()){
            if(c != '='){
                start++;
            }
            else break;
        }

        for(char c : link.toCharArray()){
            if(c != '&'){
                end++;
            }
            else break;
        }

        // get video code of the youtube link
        String videoCode = link.substring(start + 1, end);

        System.out.println( "Converted link: " + "https://www.y2mate.com/youtube-mp3/" + videoCode);
        String fullLink = "https://www.y2mate.com/youtube-mp3/" + videoCode;
        ChromeDriver driver = new ChromeDriver();

        // Open the webpage
        driver.get(fullLink);
        try {
            driver.navigate().to(fullLink);
            Thread.sleep(450);
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
                for(WebElement e : plants) {
                    System.out.println(e + "\n");
                    System.out.println(e.getAttribute("href"));
                    driver.navigate().to(e.getAttribute("href"));
                }
            }

            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            driver.quit();
        }

    }

    // copy from https://www.javatpoint.com/java-get-data-from-url
    // this prints out the 'inspect element HTML of the page


//    private static String getUrlContents(String theUrl)
//    {
//        StringBuilder content = new StringBuilder();
//        // Use try and catch to avoid the exceptions
//        try
//        {
//            URL url = new URL(theUrl); // creating a url object
//            URLConnection urlConnection = url.openConnection(); // creating a urlconnection object
//
//            // wrapping the urlconnection in a bufferedreader
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            String line;
//            // reading from the urlconnection using the bufferedreader
//            while ((line = bufferedReader.readLine()) != null)
//            {
//                content.append(line + "\n");
//            }
//            bufferedReader.close();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return content.toString();
    }
