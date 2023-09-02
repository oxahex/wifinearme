package com.oxahex.wifinearme;

import com.oxahex.wifinearme.service.HistoryService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DeleteHistory", value = "/delete-history")
public class DeleteHistory extends HttpServlet {
    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int historyId = Integer.parseInt(request.getParameter("id"));

        HistoryService historyService = new HistoryService();
        boolean historyDeleted = historyService.deleteHistory(historyId);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();

        if (historyDeleted) {
            writer.println("<script>alert('조회 내역을 성공적으로 삭제했습니다.'); location.href='"+ "/history.jsp" +"';</script>");
        } else {
            writer.println("<script>alert('조회 내역을 삭제하지 못했습니다.'); location.href='"+ "/history.jsp" +"';</script>");
        };

        writer.close();
    }
}