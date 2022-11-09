package dev.wony.macro.list;

import lombok.AccessLevel;
import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class NaverMacro {
    private String id;
    private String pw;
    private String targetUrl;
    /**
     * Chrome Version : 107.0.5304.88
     * Chrome Version 에 맞는 Driver 설치
     * https://chromedriver.chromium.org/downloads
     */
    private ChromeDriver driver;

    private static final String NAVER_URL = "https://www.naver.com";

    public NaverMacro(String id, String pw, String targetUrl) {
        this.id = id;
        this.pw = pw;
        this.targetUrl = targetUrl;
        this.driver = setDriver();
    }

    public ChromeDriver setDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        return new ChromeDriver(options);
    }

    /**
     * 구매할 옵션 번호
     * ex) 1번 옵션 -> optionIndex = 1
     */
    public void start(int optionIndex){
        start();
        login();
        while(!purchase(optionIndex)){
        }
    }

    private void start() {
        driver.get(NAVER_URL);
        WebElement loginBtn = driver.findElement(By.className("link_login"));
        loginBtn.click();
    }

    private void login(){
        // ID/PW script로 넣기 (captcha 우회)
        String script =  "(function execute(){document.querySelector('#id').value = '" + id + "';document.querySelector('#pw').value = '" + pw + "';})();";
        JavascriptExecutor javascriptExecutor = driver;
        javascriptExecutor.executeScript(script);

        // 로그인 버튼 클릭
        driver.findElement(By.id("log.login")).click();

        // 등록 버튼 클릭
        driver.findElement(By.id("new.save")).click();
    }

    private boolean purchase(int optionIndex) {
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
