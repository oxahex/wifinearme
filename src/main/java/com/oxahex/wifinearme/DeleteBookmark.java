package com.oxahex.wifinearme;

import com.oxahex.wifinearme.service.BookmarkService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DeleteBookmark", value = "/delete-bookmark")
public class DeleteBookmark extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        int bookmarkId = Integer.parseInt(request.getParameter("id"));

        System.out.println("id: " + bookmarkId);

        BookmarkService bookmarkService = new BookmarkService();
        boolean isSuccess = bookmarkService.deleteBookmark(bookmarkId);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        if (isSuccess) {
            out.println("<script>alert('북마크 정보를 삭제했습니다.'); location.href='"+ "/bookmark-list.jsp" +"';</script>");
        } else {
            out.println("<script>alert('북마크 정보를 삭제하지 못했습니다. 다시 시도해주세요.'); location.href='"+ "/bookmark-list.jsp" +"';</script>");
        };

        out.close();

    }
}