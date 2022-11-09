package dev.wony.macro.list;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class NaverMacroTest {

    private String id = "test";
    private String pw = "test123";
    private String targetUrl = "https://smartstore.naver.com/iksan_nhmill/products/7415045409/";
    private int macroCnt = 1000;
    private int optionIndex = 1;
    private static ChromeDriver driver;
    private static final String NAVER_URL = "https://www.naver.com";
    private static String WEB_DRIVER_ID = "webdriver.chrome.driver"; // 드라이버 ID
    private static String WEB_DRIVER_PATH = "E:\\workspace\\macro\\src\\main\\resources\\chromedriver.exe"; // 드라이버 경로

    @BeforeAll
    static void setting(){
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        // Secret Mod
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
    }
    
    @Test
    @DisplayName("smart store")
    void naverSmartStore2() {
        // Start
        driver.get(NAVER_URL);
        // login form
        WebElement loginBtn = driver.findElement(By.className("link_login"));
        loginBtn.click();

        // login
        login(id, pw, driver);

        // 주문
        int cnt = 1;
        while(!purchase(targetUrl)){
            System.out.println("주문 실패 count " + cnt++);
        }
    }

    private static void login(String id, String pw, WebDriver driver) {
        // ID/PW script로 넣기 (captcha 우회)
        String script =  "(function execute(){document.querySelector('#id').value = '" + id + "';document.querySelector('#pw').value = '" + pw + "';})();";
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript(script);

        // 로그인 버튼 클릭
        driver.findElement(By.id("log.login")).click();

        // 등록 버튼 클릭
        driver.findElement(By.id("new.save")).click();
    }

    private boolean purchase(String targetUrl) {
        try {
            driver.get(targetUrl);
            // 상품 선택 버튼
            driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div[2]/fieldset/div[5]/div/a")).click();
            // 옵션 선택
            driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div[2]/fieldset/div[5]/div/ul/li["+ optionIndex +"]/a")).click();
            // 구매 클릭
            driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div[2]/fieldset/div[8]/div[1]/div/a")).click();
            Thread.sleep(1000);
            // 일반 결제 클릭
            driver.findElement(By.xpath("//*[@id=\"chargePointScrollArea\"]/div[1]/ul[1]/li[4]/div[1]/span[1]/span")).click();
            // 나중에 결제 클릭
            driver.findElement(By.xpath("//*[@id=\"chargePointScrollArea\"]/div[1]/ul[1]/li[4]/ul/li[3]/span[1]/span")).click();
            // 결제 버튼 클릭
            driver.findElement(By.xpath("//*[@id=\"orderForm\"]/div/div[7]/button")).click();
        }catch (Exception e){
            System.out.println("주문 실패");
            return false;
        }
        return true;
    }
}