package dev.wony.macro;

import dev.wony.macro.list.NaverMacro;

public class MacroApplication {

    private static String WEB_DRIVER_ID = "webdriver.chrome.driver"; // 드라이버 ID
    private static String WEB_DRIVER_PATH = "E:\\workspace\\macro\\src\\main\\resources\\chromedriver.exe"; // 드라이버 경로

    static {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
    }

    public static void main(String[] args) {
        String naverId = "";
        String naverPw = "";
        String targetUrl = "";
        int optionIndex = 1;

        NaverMacro macro = new NaverMacro(naverId, naverPw, targetUrl);
        macro.start(optionIndex);
    }
}
