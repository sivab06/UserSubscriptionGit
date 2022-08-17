package com.subscription;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GetSubscriptionDetailsFromRMT {
    static WebDriver driver;
    static String File = "./record.txt";
    public static void main(String args[]) throws InterruptedException, IOException {
//        HashMap<String, String> map = new HashMap<>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();

//        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(1));
        driver.get("http://192.168.1.15:96/");
        WebElement User_SubscriptionTab = driver.findElement(By.xpath("//*[@id=\"mat-tab-label-0-4\"]"));
        User_SubscriptionTab.click();
        Thread.sleep(7000);
        WebElement UsersDropDown = driver.findElement(By.xpath("//*[@id=\"mat-select-0\"]/div/div[1]"));
        UsersDropDown.click();
        String Write_User = driver.findElement(By.xpath("//mat-option[contains(@id,'mat-option')][1]/span")).getText();
        Thread.sleep(3000);
        WebElement User =driver.findElement(By.xpath("//*[@id=\"cdk-overlay-0\"]/div/div"));
        List <WebElement> Users = User.findElements(By.tagName("mat-option"));
        System.out.println(Users.size());
        for(int j=1; j<= Users.size();j++){
//            String userID = driver.findElement(By.xpath("//mat-option[@id='mat-option-"+j+"']")).getText();
            try {
                UsersDropDown.click();

            }catch (Exception e){}


            driver.findElement(By.xpath("//mat-option[contains(@id,'mat-option')]["+j+"]/span")).click();
            String userID = driver.findElement(By.xpath("//*[@id=\"mat-select-0\"]/div/div[1]/span/span")).getText();
//            System.out.println(userID);
            String last = readFile(File);
//            System.out.println("Record--->"+last);
//            System.out.println("userid-->"+userID);
            if(last.equalsIgnoreCase(userID)){
                System.out.println("No new users for now");
                break;
            }else{

//                try{
//                    Thread.sleep(4000);
//                    UsersDropDown.click();
//                }catch (Exception e){}

                Thread.sleep(4000);
//                driver.findElement(By.xpath("//mat-option[contains(@id,'mat-option')]["+j+"]")).click();
                Thread.sleep(10000);
                WebElement table =driver.findElement(By.xpath("//*[@id=\"mat-tab-content-0-4\"]/div/div/mat-table"));
                List <WebElement> row = table.findElements(By.tagName("mat-row"));
                System.out.println(row.size());
//                int excelrow = row.size();
                for(int i=1;i<=row.size();i++){

                    String DomainUrl = driver.findElement(By.xpath("//mat-tab-body[contains(@class,'mat-tab-body-active')]//mat-row["+i+"]//mat-cell[3]//input")).getAttribute("ng-reflect-model");
                    System.out.println("Domain-->"+DomainUrl);
                    System.out.println("user ID -->"+userID);
                    values.add(DomainUrl);
//                   map.put(DomainUrl,userID);


//                    XSSFWorkbook workbook=new XSSFWorkbook();
//                    XSSFSheet sheet=workbook.createSheet("sheet1");
//                    XSSFRow row1=sheet.createRow(i);
//                    row1.createCell(0).setCellValue(userID);
//                    row1.createCell(1).setCellValue(DomainUrl);
//                    FileOutputStream file = new FileOutputStream("E:\\User_Subscription.xlsx");
//                    workbook.write(file);
//                    file.close();


                }
                map.put(userID, values);
                values = new ArrayList<>();
            }
//            System.out.println("Data Copied to Excel");
        }
        System.out.println("Total Record"+map);
        int rowNo=0;
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("sheet1");
        XSSFRow row= null;
        for(HashMap.Entry<String,List<String>> entry:map.entrySet()) {
            int n = 0;
//            row.createCell(0).setCellValue(values);
            for (int i = 0; i < entry.getValue().size(); i++) {
                row=sheet.createRow(rowNo++);
                row.createCell(0).setCellValue((String)entry.getKey());
                row.createCell(1).setCellValue(entry.getValue().get(i));
            }
        }

        FileOutputStream file = new FileOutputStream(new File("./reports/User_Subscription.xlsx"));
        workbook.write(file);
        file.close();
        System.out.println("Data Copied to Excel");
        writeFile(File,Write_User);
        driver.quit();

    }
    public static String readFile(String strFile) {
        String strBuffer;
        try (BufferedReader buffRead = new BufferedReader(new FileReader(strFile))) {
            while ((strBuffer = buffRead.readLine()) != null) {
                return strBuffer;
            }

        } catch (IOException e) {
            e.getMessage();
        }
        return "";
    }
    public static void writeFile(String strFile, String strData) {
        try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(strFile))) {
            buffWriter.write(strData);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
