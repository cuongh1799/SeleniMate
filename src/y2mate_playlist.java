import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;

public class y2mate_playlist {
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

    public static void main(String[] args){

        boolean run = true;
        while(run){
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter the playlist: ");
            String playlistLink = sc.next();

            ChromeDriver driver = new ChromeDriver();

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
                run = false;
                break;
            }
        }
    }
}
