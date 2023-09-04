package com.oxahex.wifinearme;

import com.oxahex.wifinearme.service.BookmarkService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddBookmark", value = "/add-bookmark")
public class AddBookmark extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        int bookmarkGroupId = Integer.parseInt(request.getParameter("bookmark-group-id"));
        String wifiManageNo = request.getParameter("wifi-manage-no");

        BookmarkService bookmarkService = new BookmarkService();
        boolean isSuccess = bookmarkService.registerBookmark(bookmarkGroupId, wifiManageNo);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        if (isSuccess) {
            out.println("<script>alert('북마크 정보를 추가했습니다.'); location.href='"+ "/bookmark-list.jsp" +"';</script>");
        } else {
            out.println("<script>alert('북마크 정보를 추가하지 못했습니다. 다시 시도해주세요.'); location.href='"+ "/detail.jsp?manageNo=" + wifiManageNo +"';</script>");
        };

        out.close();

    }
}