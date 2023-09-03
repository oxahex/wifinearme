package com.oxahex.wifinearme.service;

import com.oxahex.wifinearme.db.DBManager;
import com.oxahex.wifinearme.dto.BookmarkGroupDTO;

import java.sql.*;
import java.util.ArrayList;

public class BookMarkGroupService {

    /**
     * 북마크 그룹 생성
     * @param bookmarkGroup 북마크 그룹 클래스
     * @return 북마크 그룹 생성 여부 반환
     */
    public boolean registerBookmarkGroup(BookmarkGroupDTO bookmarkGroup) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        int affected = 0;

        try {
            String sql = " insert into bookmark_group           "+"\n"
                        +" (name, view_order, create_timestamp)    "+"\n"
                        +" values (?, ?, ?)                        ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookmarkGroup.getName());
            pstmt.setInt(2, bookmarkGroup.getOrder());
            pstmt.setTimestamp(3, bookmarkGroup.getCreateTimestamp());

            affected = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("registerBookmarkGroup: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }

        return affected == 1;
    }

    /**
     * 저장되어 있는 북마크 그룹 리스트를 가져옴
     * @return 북마크 그룹 객체 리스트
     */
    public ArrayList<BookmarkGroupDTO> getBookmarkGroup() {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<BookmarkGroupDTO> bookmarkGroupList = new ArrayList<>();

        try {
            String sql = " select * from bookmark_group     "+"\n"
                        +" order by view_order              ";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int order = rs.getInt("view_order");
                Timestamp createTimestamp = new Timestamp(rs.getLong("create_timestamp"));
                Timestamp updateTimestamp = new Timestamp(rs.getLong("update_timestamp"));

                BookmarkGroupDTO bookmarkGroup = new BookmarkGroupDTO(id, name, order, createTimestamp, updateTimestamp);
                bookmarkGroupList.add(bookmarkGroup);
            }
        } catch (SQLException e) {
            System.out.println("getBookmarkGroup: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(rs);
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }

        return bookmarkGroupList;
    }

}