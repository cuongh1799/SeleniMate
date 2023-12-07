import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;

public class SeleniMate {
    public static String extractCode(String input){
        int start = input.indexOf('=');
        // get video code of the youtube link
        return input.substring(start + 1);
    }

    public static void Playlist(){
        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter the playlist: ");
            String playlistLink = sc.next();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-minimized");
            ChromeDriver driver = new ChromeDriver(options);

            try {
                driver.navigate().to(playlistLink);
                Thread.sleep(2000);

                WebElement elementList = driver.findElement(By.cssSelector("span.style-scope.yt-formatted-string[dir='auto']"));

                // change string to int
                int playlistSize = Integer.parseInt(elementList.getText());
                System.out.println(playlistSize);

                List<String> videohref = new ArrayList<>();
                int count = 0;

                List<WebElement> videoLink = driver.findElements(By.cssSelector("a.yt-simple-endpoint.style-scope.ytd-playlist-video-renderer[id='video-title']"));
                for(WebElement e : videoLink){
                    System.out.println(e.getAttribute("href"));
                    videohref.add(count, e.getAttribute("href"));
                    count++;
                }

                for(String video : videohref){
                    // get the video code
                    String convert = extractCode(video);
                    String fullLink = "https://www.y2mate.com/youtube-mp3/" + convert;

                    driver.navigate().to(fullLink);

                    Thread.sleep(1000);
                    //System.out.println(driver.getPageSource());

                    WebElement element = driver.findElement(By.id("process_mp3"));

                    // Click download
                    element.click();
                    Thread.sleep(3000);

                    // Find all element with tagName
                    while(true){
                        List<WebElement> plants = driver.findElements(By.xpath("//a[@class='btn btn-success btn-file' and @target='_blank' and @rel='nofollow']"));
                        if(plants.isEmpty()){
                            System.out.println("None found, retrying...");
                        }
                        else {
                            System.out.println("Sucess! Found " + plants.size() + " elements.");
                            for(WebElement e : plants) {
                                System.out.println(e + "\n");
                                System.out.println(e.getAttribute("href"));
                                driver.navigate().to(e.getAttribute("href"));
                            }
                            break;
                        }
                    }

                    Thread.sleep(3500);

                    System.out.println("Sucess");
                }
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                driver.quit();
            }

            System.out.println("Continue ? y/n ");
            String ans = sc.next();
            if(ans.equals("n")){
                break;
            }
        }
    }

    public static void Single(){
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
                // If none found, retry again
                while(true){
                    List<WebElement> plants = driver.findElements(By.xpath("//a[@class='btn btn-success btn-file' and @target='_blank' and @rel='nofollow']"));
                    if(plants.isEmpty()){
                        System.out.println("None found, retrying...");
                    }
                    else {
                        System.out.println("Sucess! Found " + plants.size() + " elements.");
                        for(WebElement e : plants) {
                            System.out.println(e + "\n");
                            System.out.println(e.getAttribute("href"));
                            driver.navigate().to(e.getAttribute("href"));
                        }
                        break;
                    }
                }
                Thread.sleep(4000);

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

    public static void main(String[] args) {
        Scanner choice = new Scanner(System.in);
        System.out.print("1.Playlist\n2.Single video\n");
        System.out.print("Please enter your choice: ");
        String choose = choice.next();
        switch(choose){
            case "1":
                Playlist();
                break;
            case "2":
                Single();
                break;
        }
    }
}
