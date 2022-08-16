import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.net.ssl.SSLHandshakeException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.common.value.qual.ArrayLen;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserSubscriptionCore {

    static String emailId = "";
    static String url = "";
    static Map<String,Map<String,String>> map = new LinkedHashMap<String,Map<String,String>>();
    static String responseMsg = "";
    static WebDriver driver=null;
    static WebDriverWait wait = null;
    static WebDriverWait shortWait = null,mediumWait = null;
    static int excelRowCount = 1;
    static int subscribedsuccessfullyCount = 0, unableToFindEmailTextBox = 0;
    static String finalMsg = null;
    static WebElement subscriptionMsgEle = null;
    static Boolean subscriptionFlag = false, captchaFlag = false, invalidUrlFlag = false;
    static File finalMsgFile = null;
    static int unabeToAccessTextbox = 0, textboxFoundAndCanAbleToAccess = 0, activiationLinkCount = 0, captchaCount =0, additionalInfo = 0, alreadySubscribedCount = 0, invalidUrlCount =0;

    static void readExcel() throws IOException, InterruptedException {
//		RMT.main();
//		String path = "C:\\Users\\TGL239\\Downloads\\Book 6.xlsx";
        String path = "E:\\User_Subscription.xlsx";

//		String path = "C:\\Users\\tgl267\\Downloads\\UserSubcription_Latest3.xlsx";
        int excelRowCount = 1;
        FileInputStream fis = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Sheet1");
//      System.out.println(sheet.getRow(2).getCell(2).getNumericCellValue());
        int rowCount = sheet.getLastRowNum();
//        int cellCount = sheet.getRow(0).getLastCellNum();
        Map tempMap = new LinkedHashMap();

        for (int s = 0; s <= rowCount; s++) {
            tempMap = new LinkedHashMap();
            XSSFRow row = (XSSFRow) sheet.getRow(s);
            String emailIdLocal = row.getCell(0).toString();
            String urlLocal = row.getCell(1).toString();
//	          System.out.println("emailId-->"+emailIdLocal+"url-->"+urlLocal);
            emailId = row.getCell(0).toString();
            url = row.getCell(1).toString();
            tempMap.put("syenAppId", row.getCell(0).toString());
            tempMap.put("url", row.getCell(1).toString());
            map.put(String.valueOf(s),tempMap);
//	          for (int a = 0; a < cellCount; a++) {
//	              XSSFCell cell = row.getCell(a);
//	              switch (cell.getCellType()) {
//	                  case STRING:
//	                      System.out.print(cell.getStringCellValue());
//	                      break;
//
//	                  case NUMERIC:
//	                      System.out.print(cell.getNumericCellValue());
//	                      break;
//	              }
//	              System.out.print("|");
//	          }
//	          System.out.println();
        }
    }

    public static void acceptCookies(WebDriver driver, String locator) {
        try {
            List<WebElement> cookiesElements = driver.findElements(By.xpath(locator));
            for (WebElement ele : cookiesElements) {
                if(!ele.getTagName().equalsIgnoreCase("script")) {
                    ele.click();
                }
            }
        }catch (Exception e) {
            // TODO: handle exception

        }
    }

    public static void moveToElement(WebDriver driver, WebElement element) {
        try {
            new Actions(driver).moveToElement(element).perform();
        }catch (Exception e) {
            // TODO: handle exception
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        }

    }

    public static void prssingEscapeKey(WebDriver driver) throws AWTException {
        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
    }

    static boolean checkValidUrl(String url){
        HttpURLConnection huc = null;
        int respCode = 200;
        try {
//            huc = (HttpURLConnection)(new URL(url).openConnection());
//
//            huc.setRequestMethod("HEAD");
//
//            huc.connect();
//
//            respCode = huc.getResponseCode();
//            System.out.println("respCode-->"+respCode);
//            if(respCode >= 400){
//                System.out.println(url+" is a broken link");
//                return false;
//            }
//            else{
//                System.out.println(url+" is a valid link");
//                return true;
//            }

            return true;

//        } catch (MalformedURLException e) {
//            // TODO Auto-generated catch block
//        	System.out.println("MalformedURLException-->");
//        	return false;
////            e.printStackTrace();
//        } catch (SSLHandshakeException ssl) {
//			// TODO: handle exception
//        	System.out.println("SSLHandshakeException-->");
//        	return false;
////        	ssl.printStackTrace();
//		}catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        Long currentTimeInMillis = System.currentTimeMillis();
        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
        String emailTxtBoxSecondTry = "//input[contains(@id,'email') or contains(@id,'Email')]";
        String emailTxtBox = "//input[@type='email' or contains(@value,'Email') or contains(@value,'email') or contains(@placeholder,'email') or contains(@placeholder,'Email') or contains(@placeholder,'Email') or contains(@placeholder,'your email')]";
        String subscriptionMsg = "//*[contains(text(),'Thank you for your subscription.') or contains(text(),'Thank you for subscri') or contains(text(),'Thank you for subscrib') or contains(text(),'Thank you') or contains(text(),'for subscribing') or contains(text(),'successfully') or contains(text(),'welcome email') or contains(text(),'signed up') or contains(text(),'signing up') or contains(text(),'have been subscribed') or contains(text(),'for confirming your email address') or contains(text(),'Thanks for subscri') or contains(text(),'THANKS FOR SUBSCRI') or contains(text(),'THANKS FOR SIGN') or contains(text(),'Check your inbox') or contains(text(),'Check Your Inbox') or contains(text(),'re signed up')]";
        String acceptCookiesEle = "//*[contains(text(),'Accept')]";
        String agreeCheckBox = "//*[contains(text(),'I agree') or contains(text(),'i agree') or contains(text(),'Yes, I agree')]";
        String alreadySubscribedMsg = "//*[contains(text(),'This email address is already subscribed.') or contains(text(),'This email address is already') or contains(text(),'already subscribed') or contains(text(),'This email address is already subscribed')]";
        String captchaCheck = "//*[contains(text(),'robot') or contains(text(),'Recaptcha')]//*[not(@style)]";
        String path = "./out.txt";
        String finalMsgPath = "./FinalMsg.txt";
        String submitBtn = emailTxtBox+"//parent::*//input[@type='submit' or contains(@id,'submit')] | "+emailTxtBox+"//following-sibling::*[@type='submit']";
        String additionalDetailsInfo = "//label[contains(text(),'First Name') or contains(text(),'FirstName')] | //*[contains(text(),'is required')]";
        String activiationLinkInfo = "//*[contains(text(),'activation link')]";
        int iterationCount = 0;
        Boolean emailTxtBoxSecondTryFlag = false, emailTextBoxFlag = false, alreadySubscribedFlag = false, emailTextBoxFlagForFinalMsg = false, additionalDetailsFlag = false, activiationLinkFlag = false;

        readExcel();
        map.forEach((key,value) -> {
            System.out.println(excelRowCount+")"+key+"-->"+value);
            excelRowCount++;
        });

//    	System.setProperty("webdriver.chrome.driver","C:\\MyPlace\\Softwares\\chromedriver_103\\chromedriver.exe");
//    	System.setProperty("webdriver.edge.driver","C:\\MyPlace\\Softwares\\EdgeDriver\\edgedriver_win64\\msedgedriver.exe");


        driver=new ChromeDriver();
//    	driver=new EdgeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(4));
        mediumWait = new WebDriverWait(driver, Duration.ofSeconds(6));
        String userId = null,domain = null;
        int recordCount = 1;
        outer:
        for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
            Long currentTimeInMillisIndividual = System.currentTimeMillis();
            Boolean newWindowOrTabFlag = false;
            finalMsg = "";
            emailTextBoxFlag = false;
            alreadySubscribedFlag = false;
            additionalDetailsFlag = false;
            activiationLinkFlag = false;
            emailTextBoxFlagForFinalMsg = false;
            captchaFlag = false;
            subscriptionFlag = false;
            invalidUrlFlag = false;
            responseMsg = "";
            String url = null;
            String currentWindow = "";
//    		WebElement textBoxEle = null;
            try {
                userId = entry.getValue().get("syenAppId").trim();
                domain = entry.getValue().get("url").trim();
//				System.out.println("userId-->"+userId);
//				System.out.println("URL-->"+domain);
                responseMsg += recordCount + ")-------------------Start---------------------\n";
//		    	System.out.println("url-->"+"https://www."+domain.split("\\.")[0]+".com");
                url = "https://www." + domain.split("\\.")[0] + ".com";
                if (checkValidUrl(url)) {
                    try {
                        driver.navigate().to(url);
                    } catch (WebDriverException we) {
                        // TODO: handle exception
//						we.printStackTrace();
                    }
                    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
                    Thread.sleep(2000);
                    acceptCookies(driver, acceptCookiesEle);
                    prssingEscapeKey(driver);
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("window.scrollBy(0,1000)");
                    JavascriptExecutor j = (JavascriptExecutor) driver;
                    if (j.executeScript("return document.readyState").toString().equals("complete")) {
                        System.out.println("Page has loaded");
                    } else {
                        System.out.println("Page not loaded properly!!");
                    }
                    try {
//			        	moveToElement(driver, driver.findElement(By.xpath(emailTxtBox)));
                        shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(emailTxtBox)));
                    } catch (Exception e) {
                        // TODO: handle exception
//			        	System.out.println("Unable to find email id text box!!");
//			        	e.printStackTrace();
                        try {
                            if (driver.findElement(By.xpath(emailTxtBoxSecondTry)) != null && driver.findElement(By.xpath(emailTxtBoxSecondTry)).isDisplayed()) {
                                emailTxtBoxSecondTryFlag = true;
                            } else {
                                recordCount++;
                                unableToFindEmailTextBox++;
                                responseMsg += "Unable to find email id text box!!\n";
                                responseMsg += "-------------------End---------------------\n";
                                System.out.println("responseMsg:\n" + responseMsg);
//					        	driver.quit();
                                finalMsg = "Unable to find email id text box in " + domain;
                                System.out.println("\n\n\nfinalMsg:\n" + finalMsg + "\n");
                                finalMsgWrite(finalMsgPath, recordCount, (recordCount - 1) + ")" + finalMsg);
                                continue;
                            }
                        } catch (Exception ex) {
                            // TODO: handle exception
                            recordCount++;
                            unableToFindEmailTextBox++;
                            responseMsg += "Unable to find email id text box!!\n";
                            responseMsg += "-------------------End---------------------\n";
                            System.out.println("responseMsg:\n" + responseMsg);
//				        	driver.quit();
                            finalMsg = "Unable to find email id text box in " + domain;
                            System.out.println("\n\n\nfinalMsg:\n" + finalMsg + "\n");
                            finalMsgWrite(finalMsgPath, recordCount, (recordCount - 1) + ")" + finalMsg);
                            continue;
                        }

                    }
                    prssingEscapeKey(driver);
                    js = (JavascriptExecutor) driver;
                    js.executeScript("window.scrollBy(0,1000)");

                    List<WebElement> emailTextBoxList = driver.findElements(By.xpath(emailTxtBox));
                    if (emailTxtBoxSecondTryFlag) {
                        emailTextBoxList = driver.findElements(By.xpath(emailTxtBoxSecondTry));
                    }
                    if (emailTextBoxList.size() > 1) {
//			        	System.out.println("More than one email text box available. Total count is "+emailTextBoxList.size());
                        responseMsg += "More than one email text box available. Total count is " + emailTextBoxList.size() + "\n";
                    }
                    iterationCount = 0;
                    int textBoxCount = 1;
                    inner:
                    for (WebElement emailEle : emailTextBoxList) {
                        emailTextBoxFlag = false;
                        WebElement textBoxEle = null;
                        newWindowOrTabFlag = false;
                        System.out.println("Email text box Count-->" + (iterationCount + 1));
                        responseMsg += "Email text box Count-->" + (iterationCount + 1) + "\n";
//			        	System.out.println(emailEle);
                        try {
//			            	wait.until(ExpectedConditions.presenceOfElementLocated(emailEle.));
//			        		System.out.println("("+emailTxtBox+")["+textBoxCount+"]");
//			        		js = (JavascriptExecutor) driver;
//					        js.executeScript("window.scrollBy(0,1000)");

                            moveToElement(driver, driver.findElement(By.xpath("(" + emailTxtBox + ")[" + textBoxCount + "]")));
                            textBoxEle = driver.findElement(By.xpath("(" + emailTxtBox + ")[" + textBoxCount + "]"));
//			            	textBoxEle.isDisplayed();
                        } catch (Exception e) {
                            // TODO: handle exception
//			            	e.printStackTrace();
                            textBoxEle = driver.findElement(By.xpath(emailTxtBoxSecondTry));

                        }
                        textBoxEle.isDisplayed();
                        Thread.sleep(1000);
                        acceptCookies(driver, acceptCookiesEle);
                        prssingEscapeKey(driver);
                        try {
                            textBoxEle.clear();
                            textBoxEle.sendKeys(userId);
                            emailTextBoxFlag = true;
                            emailTextBoxFlagForFinalMsg = true;
                            Thread.sleep(1000);
                            try {
                                driver.findElement(By.xpath(agreeCheckBox)).click();
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                            prssingEscapeKey(driver);
                            currentWindow = driver.getWindowHandle();
                            textBoxEle.sendKeys(Keys.ENTER);
                            Thread.sleep(2000);
                            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
                            Thread.sleep(1000);
                            Set<String> totalWindows = driver.getWindowHandles();
                            if (totalWindows.size() > 1) {
                                newWindowOrTabFlag = true;
                                List<String> list = new ArrayList<>(totalWindows);
                                driver.switchTo().window(list.get(list.size() - 1));
                            }
//				            JavascriptExecutor j = (JavascriptExecutor)driver;
//				            if (j.executeScript("return document.readyState").toString().equals("complete")){
//				               System.out.println("Page has loaded");
//				            }
                            prssingEscapeKey(driver);
                            try {
                                WebElement alreadySubscribedEle = driver.findElement(By.xpath(alreadySubscribedMsg));
                                if (alreadySubscribedEle != null) {
                                    List<WebElement> elementList = driver.findElements(By.xpath(alreadySubscribedMsg));
                                    for (WebElement ele : elementList) {
                                        System.out.println("ele.getTagName()-->" + ele.getTagName());
                                        if (!ele.getTagName().equalsIgnoreCase("script")) {
                                            if (ele.isDisplayed()) {
                                                moveToElement(driver, ele);
                                                responseMsg += userId + " user already subscribed with " + domain + "\n";
                                                alreadySubscribedFlag = true;
                                                alreadySubscribedCount++;
                                                break inner;
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                            try {
                                WebElement additionalDetailsElement = driver.findElement(By.xpath(additionalDetailsInfo));
                                if (additionalDetailsElement != null) {
                                    List<WebElement> elementList = driver.findElements(By.xpath(additionalDetailsInfo));
                                    for (WebElement ele : elementList) {
                                        if (ele.isDisplayed()) {
                                            responseMsg += "Websites demands additional info for the subscription!!" + "\n";
                                            additionalDetailsFlag = true;
                                            break inner;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                            }

                            try {
                                if (driver.findElement(By.xpath(activiationLinkInfo)).isDisplayed()) {
                                    responseMsg += "Got the info about activation Link!! Hence the user got subscribed" + "\n";
                                    activiationLinkFlag = true;
                                    break inner;
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                            }


                            if (validateCaptcha(captchaCheck)) {
                                iterationCount++;
                                break inner;
                            }

                            if (validateSubscriptionMsg(subscriptionMsg, userId, iterationCount)) {
                                break inner;
                            }
                            try {

//				            	System.out.println(submitBtn);
//				            	System.out.println(driver.findElements(By.xpath(submitBtn)).size());
                                for (WebElement submitElement : driver.findElements(By.xpath(submitBtn))) {
                                    if (submitElement.isDisplayed()) {
                                        moveToElement(driver, submitElement);
                                        prssingEscapeKey(driver);
                                        submitElement.click();
                                        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
                                        responseMsg += "Application works with submit button!!" + "\n";
                                    }
                                    if (validateSubscriptionMsg(subscriptionMsg, userId, iterationCount)) {
                                        break inner;
                                    }
                                    if (totalWindows.size() > 1) {
                                        newWindowOrTabFlag = true;
                                        List<String> list = new ArrayList<>(totalWindows);
                                        driver.switchTo().window(list.get(list.size() - 1));
                                    }
                                }

                            } catch (Exception eb) {
                                // TODO: handle exception
//				            	eb.printStackTrace();
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
//				        	e.printStackTrace();
                            if (emailTextBoxFlag == false) {
                                responseMsg += "Unable to access the email id textbox in webpage. Please check!!\n";
                            }
                        }


//			            }catch (Exception e) {
//			    			// TODO: handle exception
//			            	System.out.println("Unable to find subscribe successful message for iteration number: "+iterationCount);
//			            	responseMsg += "Unable to find subscribe successful message for iteration number: "+iterationCount+"\n";
//			    		}
                        iterationCount++;
                        textBoxCount++;
                        Long afterExecutionInMillis = System.currentTimeMillis() - currentTimeInMillisIndividual;
                        Long timeTakenInMins = (afterExecutionInMillis / 1000) / 60;
                        Long timeTakenInSecs = (afterExecutionInMillis / 1000) % 60;
//			        	System.out.println("\n"+afterExecutionInMillis+"-->"+timeTakenInMins+"-->"+timeTakenInSecs);
                        System.out.println("Total execution time for iteration " + (iterationCount + 1) + " is " + timeTakenInMins + " minutes and "
                                + timeTakenInSecs + " seconds.\n");
                        responseMsg += "Total execution time for iteration count " + (iterationCount + 1) + " is " + timeTakenInMins + " minutes and "
                                + timeTakenInSecs + " seconds.\n";
                    }
                    if (!subscriptionFlag && !alreadySubscribedFlag) {
//		            	System.out.println("Unable to validate success message after subscription for "+userId+". Please check!!");
                        responseMsg += "Unable to validate success message after " + iterationCount + " iteration(s). subscrition not completed for " + userId + ". Please check!!\n";
                        responseMsg += "Record number " + recordCount + " failed!! Please check the user " + userId + "\n";
                    }
                    responseMsg += "-------------------End---------------------\n";
                } else {
                    System.out.println("Invalid url ->" + url);
                    invalidUrlFlag = true;
                    invalidUrlCount++;
                }

            } catch (Exception e) {
                // TODO: handle exception
//				e.printStackTrace();
//				if(emailTextBoxFlag == false) {
//					responseMsg += "Unable to access the email id textbox in webpage. Please check!!\n";
//				}
                responseMsg += "Record number " + recordCount + " failed!! Please check the user " + userId + "\n";
                responseMsg += "-------------------End---------------------\n";
//				System.out.println("Record number "+recordCount +" failed!! Please check the user "+userId);
            }
            recordCount++;
            System.out.println("responseMsg:\n" + responseMsg);
//			driver.quit();
            if (emailTextBoxFlag) {
                System.out.println("emailTextBoxFlag-->Text box found and can able to access!!");
            } else {
                System.out.println("emailTextBoxFlag-->Unabe to access the Text box!!");
            }

            if (subscriptionFlag) {
                System.out.println("subscriptionFlag-->Sucessfully able to subscribe the Retailer with " + userId);
                finalMsg = "Sucessfully able to subscribe the " + domain + " Retailer with " + userId;
            } else {
                if (invalidUrlFlag) {
                    System.out.println("Invalid url ->" + url);
                    finalMsg = "Invalid url ->" + url;
                    System.out.println("-------------------End---------------------\n");
                } else if (alreadySubscribedFlag) {
                    System.out.println("alreadySubscribedFlag-->" + userId + " user already subscribed with " + domain);
                    finalMsg = userId + " user already subscribed with " + domain;
                } else if (activiationLinkFlag) {
//		    		System.out.println("activiationLinkFlag-->"+userId+" user already subscribed with "+domain);
                    finalMsg = "Got the info about activation Link!! Hence the user got subscribed" + "\n";
                    activiationLinkCount++;
                } else if (captchaFlag) {
                    finalMsg = "Due to captcha, unable to subscribe " + userId + " in " + domain + " retailer website!!";
                    captchaCount++;
                } else {
                    if (additionalDetailsFlag) {
                        finalMsg = "Websites demands additional info for the subscription!! Hence unable to subscribe the " + userId + " in " + domain + " Retailer website!!";
                        additionalInfo++;
                    } else {
                        System.out.println("subscriptionFlag-->Unabe to subscribe the Retailer with " + userId);
                        String emailTextBoxStatus = emailTextBoxFlagForFinalMsg ? "Text box found and can able to access. " : "Unable to access the Text box!!";
                        finalMsg = emailTextBoxStatus + " Unable to subscribe the " + domain+ " Retailer with " + userId;
                        if (finalMsg.contains("Unable to access the Text box!!")) {
                            unabeToAccessTextbox++;
                        } else if (finalMsg.contains("Text box found and can able to access")) {
                            textboxFoundAndCanAbleToAccess++;
                        }

                        if (unableToFindEmailTextBox > 0 && textboxFoundAndCanAbleToAccess == 0 && !subscriptionFlag) {
                            finalMsg = "Unable to find email id text box in " + domain + " retailer website!!";
                        } else if (unableToFindEmailTextBox > 0 && textboxFoundAndCanAbleToAccess > 0 && !subscriptionFlag) {
                            finalMsg = "Text box found and can able to access, but unable to verify subscribe Msg in " + domain + " retailer website!!";
                        }
                    }


                }
            }
            if (subscribedsuccessfullyCount > 0) {

            }

            if (!subscriptionFlag) {
                finalMsg = finalMsg;
            }
            System.out.println("\n\n\nfinalMsg:\n" + finalMsg + "\n");
            finalMsgWrite(finalMsgPath, recordCount, (recordCount - 1) + ")" + finalMsg);
            if (newWindowOrTabFlag) {
                driver.quit();
//				driver.switchTo().window(currentWindow);
                driver = new ChromeDriver();
                driver.manage().window().maximize();
            }
        }


        try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(new File(path)))) {
            buffWriter.write(responseMsg);
//            System.out.println("responseMsg-->"+responseMsg);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        String finalSummary = "";
        String lineSeperator = "\n---------------------------------------------------------------\n";
        System.out.println(lineSeperator+"Sucessfully able to subscribe-->"+subscribedsuccessfullyCount);
        finalSummary += lineSeperator+"Successfully able to subscribe-->"+subscribedsuccessfullyCount+"\n";
        System.out.println("Unable to access the Text box-->"+unabeToAccessTextbox);
        finalSummary += "Unable to access the Text box-->"+unabeToAccessTextbox+"\n";
        System.out.println("Text box found and can able to access, but unable to verify subscribe Msg-->"+textboxFoundAndCanAbleToAccess);
        finalSummary += "Text box found and can able to access, but unable to verify subscribe Msg-->"+textboxFoundAndCanAbleToAccess+"\n";
        System.out.println("Unable to subscribe due to captcha-->"+captchaCount);
        finalSummary += "Unable to subscribe due to captcha-->"+captchaCount+"\n";
        System.out.println("Email id Text box not available in website-->"+unableToFindEmailTextBox);
        finalSummary += "Email id Text box not available in website-->"+unableToFindEmailTextBox+"\n";
        System.out.println("Unable to subscribe due to Additional Info-->"+additionalInfo);
        finalSummary += "Unable to subscribe due to Additional Info-->"+additionalInfo+"\n";
        System.out.println("Already subscribed user count-->"+alreadySubscribedCount);
        finalSummary += "Already subscribed user count-->"+alreadySubscribedCount+"\n";
        System.out.println("Invalid Url count-->"+invalidUrlCount);
        finalSummary += "Invalid Url count-->"+invalidUrlCount+"\n";
        System.out.println("Activating account by clicking email link-->"+activiationLinkCount);
        finalSummary += "Activating account by clicking email link-->"+activiationLinkCount+"\n";


        Long afterExecutionInMillis = System.currentTimeMillis() - currentTimeInMillis;
        Long timeTakenInMins = (afterExecutionInMillis / 1000) / 60;
        Long timeTakenInSecs = (afterExecutionInMillis / 1000) % 60;
        System.out.println("\n"+afterExecutionInMillis+"-->"+timeTakenInMins+"-->"+timeTakenInSecs);
        System.out.println("Total execution time for "+map.size()+ " subscriber is: "+timeTakenInMins + " minutes and "
                + timeTakenInSecs + " seconds."+lineSeperator);
        finalSummary += "\nTotal execution time for "+map.size()+ " subscriber is: "+timeTakenInMins + " minutes and "
                + timeTakenInSecs + " seconds."+lineSeperator;
        finalMsgWrite(finalMsgPath, recordCount,finalSummary);
//    	driver.quit();
    }

    static void finalMsgWrite(String finalMsgPath, int recordCount, String messageLog) {
        if(finalMsgFile== null) {
            finalMsgFile = new File(finalMsgPath);
            if(finalMsgFile.exists()) {
                finalMsgFile.delete();
                finalMsgFile = new File(finalMsgPath);
            }
        }
        try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(finalMsgFile,true))) {
            buffWriter.write(messageLog+"\n");
//            System.out.println("responseMsg-->"+responseMsg);
//            buffWriter.close();
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    static Boolean validateCaptcha(String captchaCheck) {
        captchaFlag = false;
        List<WebElement> captchaIframes = driver.findElements(By.xpath("//iframe[contains(@title,'CAPTCHA') or contains(@title,'captcha')]"));
        for (int i = 1; i <= captchaIframes.size(); i++) {
            try {
                WebElement iFrame = driver.findElement(By.xpath("(//iframe[contains(@title,'CAPTCHA') or contains(@title,'captcha')])["+i+"]"));
                driver.switchTo().frame(iFrame);
                WebElement iFrame_checkbox = driver.findElement(By.xpath(captchaCheck));
                moveToElement(driver, iFrame_checkbox);
                if(iFrame_checkbox != null) {
                    responseMsg += "Application expects a captcha to be solved!!"+"\n";
                    captchaFlag = true;
                    break;

                }
            }catch (Exception e) {
                // TODO: handle exception
                continue;
            }
        }
        if(captchaFlag == true) {
            return true;
        }else
            return false;
    }

    static Boolean validateSubscriptionMsg(String subscriptionMsg, String userId, int iterationCount) {
        try{
//    		Thread.sleep(2000);
            shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(subscriptionMsg)));
            moveToElement(driver, driver.findElement(By.xpath(subscriptionMsg)));
            subscriptionMsgEle = driver.findElement(By.xpath(subscriptionMsg));
            if(subscriptionMsgEle != null) {
                List<WebElement> elementList = driver.findElements(By.xpath(subscriptionMsg));
                for (WebElement ele : elementList) {
                    if(!ele.getTagName().equalsIgnoreCase("script")) {
                        if(ele.isDisplayed()) {
//		                	System.out.println(userId+" subscribed successfully!!");
                            subscriptionFlag = true;
                            subscribedsuccessfullyCount++;
                            responseMsg += userId+" subscribed successfully!!\n";
//		                	driver.quit();
                            return true;
                        }
                    }

                }
            }
        }catch (Exception e) {
            // TODO: handle exception
//        	e.printStackTrace();
            responseMsg += "Unable to find successful message for iteration number: "+(iterationCount+1)+"\n";
            return false;
        }
        return false;
    }
}
