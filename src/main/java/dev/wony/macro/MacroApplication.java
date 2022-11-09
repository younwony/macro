package dev.wony.macro;

import dev.wony.macro.list.NaverMacro;

public class MacroApplication {

    public static void main(String[] args) {
        String naverId = "";
        String naverPw = "";
        String targetUrl = "";

        NaverMacro macro = new NaverMacro(naverId, naverPw, targetUrl);
        macro.start(1);
    }
}
