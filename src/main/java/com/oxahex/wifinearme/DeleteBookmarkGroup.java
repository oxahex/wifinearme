package com.oxahex.wifinearme;

import com.oxahex.wifinearme.service.BookMarkGroupService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DeleteBookmarkGroup", value = "/delete-bookmark-group")
public class DeleteBookmarkGroup extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        int id = Integer.parseInt(request.getParameter("id"));

        BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
        boolean isSuccess = bookMarkGroupService.deleteBookmarkGroup(id);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        if (isSuccess) {
            out.println("<script>alert('북마크 그룹 정보를 삭제했습니다.'); location.href='"+ "/bookmark-group.jsp" +"';</script>");
        } else {
            out.println("<script>alert('북마크 그룹 정보를 삭제하지 못했습니다. 다시 시도해주세요.'); location.href='"+ "/bookmark-group.jsp" +"';</script>");
        };

        out.close();

    }
}