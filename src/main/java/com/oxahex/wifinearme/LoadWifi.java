package com.oxahex.wifinearme;

import com.oxahex.wifinearme.dto.WifiDTO;
import com.oxahex.wifinearme.service.OpenApiService;
import com.oxahex.wifinearme.service.WifiService;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "LoadWifi", value = "/load-wifi")
public class LoadWifi extends HttpServlet {
    private int totalDataCount = 0;

    public void init() {
        OpenApiService openApiService = new OpenApiService();
        ArrayList<WifiDTO> wifiList = openApiService.getOpenAPI();

        WifiService wifiService = new WifiService();
        int affected = wifiService.registerWifi(wifiList);

        this.totalDataCount = affected == wifiList.size() ? wifiList.size() : 0;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html><body style=\"text-align: center\">");
        if (this.totalDataCount == 0) {
            out.println("<h1>이미 와이파이 정보가 저장되어 있거나 와이파이 정보를 저장하지 못했습니다. 다시 시도해주세요.</h1>");
        } else {
            out.println("<h1>" + this.totalDataCount + "개의 와이파이 정보를 성공적으로 저장했습니다.</h1>");
        }
        out.println("<a href=\"/\">홈 으로 가기</a>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}