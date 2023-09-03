package com.oxahex.wifinearme;

import com.oxahex.wifinearme.service.BookMarkGroupService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

@WebServlet(name = "EditBookmarkGroup", value = "/edit-bookmark-group")
public class EditBookmarkGroup extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int order = Integer.parseInt(request.getParameter("order"));
        Timestamp updateTimestamp = new Timestamp(System.currentTimeMillis());


        BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
        boolean isSuccess = bookMarkGroupService.updateBookmarkGroup(id, name, order, updateTimestamp);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        if (isSuccess) {
            out.println("<script>alert('북마크 그룹 정보를 수정했습니다.'); location.href='"+ "/bookmark-group.jsp" +"';</script>");
        } else {
            out.println("<script>alert('북마크 그룹 정보를 수정하지 못했습니다. 다시 시도해주세요.'); location.href='"+ "/bookmark-group.jsp" +"';</script>");
        };

        out.close();

    }

}