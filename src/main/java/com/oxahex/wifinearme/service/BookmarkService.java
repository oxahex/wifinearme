package com.oxahex.wifinearme.service;

import com.oxahex.wifinearme.db.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BookmarkService {

    /**
     * 북마크 생성
     * @param bookmarkGroupId 북마크 그룹 ID
     * @param wifiManageNo 북마크하려는 와이파이 관리 번호
     * @return 북마크 생성 여부 반환
     */
    public boolean registerBookmark(int bookmarkGroupId, String wifiManageNo) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        int affected = 0;

        try {
            String sql = " insert into bookmark                                         "+"\n"
                        +" (create_timestamp, fk_wifi_manage_no, fk_bookmark_group_id)  "+"\n"
                        +" values (?, ?, ?)                                             ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(2, wifiManageNo);
            pstmt.setInt(3, bookmarkGroupId);

            affected = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("registerBookmark: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }

        return affected == 1;
    }
}