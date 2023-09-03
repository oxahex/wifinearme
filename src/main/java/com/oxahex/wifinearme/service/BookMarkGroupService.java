package com.oxahex.wifinearme.service;

import com.oxahex.wifinearme.db.DBManager;
import com.oxahex.wifinearme.dto.BookmarkGroupDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}