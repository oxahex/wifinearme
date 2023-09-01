package com.oxahex.wifinearme;

import com.oxahex.wifinearme.dto.HistoryDTO;
import com.oxahex.wifinearme.service.HistoryService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "SaveHistory", value = "/save-history")
public class SaveHistory extends HttpServlet {
    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        double lat = Double.parseDouble(request.getParameter("lat"));
        double lnt = Double.parseDouble(request.getParameter("lnt"));
        LocalDateTime now = LocalDateTime.now();

        HistoryDTO history = new HistoryDTO(null, lat, lnt, now);

        HistoryService historyService = new HistoryService();
        boolean historyUpdated = historyService.registerHistory(history);

        if (historyUpdated) System.out.println("History 저장 성공");
        else System.out.println("History 저장 실패");

        response.sendRedirect("/?lat=" + lat + "&lnt=" + lnt);
    }
}
