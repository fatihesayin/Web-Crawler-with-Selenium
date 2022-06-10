package sampleapp;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class DemoAutomation {
    public static void main(String[] args) throws Exception{

        File file = new File("dosya.txt");
        if (!file.exists()){
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file, false);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Adisyo\\Downloads\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.ilan.gov.tr/ilan/kategori/9/ihale-duyurulari");
        driver.manage().window().maximize();

        String ilanXpath = "/html/body/igt-root/main/igt-ad-list/div/div/div[2]/section/div[2]/div[2]/div/igt-ad-single-list/ng-component//a";
        String currentUrl;

        List<WebElement> ilanlar;
        List<WebElement> ilanAciklamalari;
        List<String> strDescriptions = new ArrayList<>();

        Thread.sleep(2000);
        for (int i = 1; i <= 5;i++){

            bWriter.write(i+". Sayfa");
            bWriter.newLine();
            if (i>1){
                driver.findElement(By.xpath(
                        "/html/body/igt-root/main/igt-ad-list/div/div/div[2]/section/igt-pagination/pagination-template/div/ul/li/a//span[normalize-space()='"+i+"']"))
                        .click();
            }
            Thread.sleep(2000);
            ilanlar = driver.findElements(By.xpath(ilanXpath));//LİNKLERİ ÇEKME
            for (int l=1; l<=ilanlar.size();l++){
                currentUrl = driver.getCurrentUrl();
                driver.findElement(By.xpath("/html/body/igt-root/main/igt-ad-list/div/div/div[2]/section/div[2]/div[2]/div/igt-ad-single-list["+l+"]/ng-component")).click();
                Thread.sleep(2000);

                ilanAciklamalari = driver.findElements(By.xpath("//div//div//div//ul//li//div[2]"));
                for (WebElement descriptions : ilanAciklamalari){
                    strDescriptions.add(descriptions.getText());
                }

                driver.get(currentUrl);
                Thread.sleep(2000);
            }
            int len=2;
            int flag =0;
            bWriter.write("1.İlan ");
            for (String string : strDescriptions){
                bWriter.write(string+ " ");
                if (string.contains(":")){
                    flag++;
                    if (flag%2==0 && (len%13)!=0) {
                        bWriter.newLine();
                        bWriter.write(len + ".İlan ");
                        len++;
                    }
                }
            }
            bWriter.newLine();
            strDescriptions = new ArrayList<>();
        }
        bWriter.close();
        driver.quit();
    }
}