package com.oxahex.wifinearme;

import com.oxahex.wifinearme.dto.BookmarkGroupDTO;
import com.oxahex.wifinearme.service.BookMarkGroupService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

@WebServlet(name = "AddBookmarkGroup", value = "/add-bookmark-group")
public class AddBookmarkGroup extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        String name = request.getParameter("name");
        int order = Integer.parseInt(request.getParameter("order"));
        Timestamp createTimeStamp = new Timestamp(System.currentTimeMillis());

        BookmarkGroupDTO bookmarkGroup = new BookmarkGroupDTO(null, name, order, createTimeStamp, null);

        BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
        boolean isSuccess = bookMarkGroupService.registerBookmarkGroup(bookmarkGroup);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        if (isSuccess) {
            out.println("<script>alert('북마크 그룹 정보를 추가했습니다.'); location.href='"+ "/bookmark-group.jsp" +"';</script>");
        } else {
            out.println("<script>alert('북마크 그룹 정보를 추가하지 못했습니다. 다시 시도해주세요.'); location.href='"+ "/bookmark-group.jsp" +"';</script>");
        };

        out.close();
    }
}