package com.oxahex.wifinearme.service;

import com.oxahex.wifinearme.db.DBManager;
import com.oxahex.wifinearme.dto.BookmarkDTO;

import java.sql.*;
import java.util.ArrayList;

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

    /**
     * 북마크 목록 가져오기
     * @return 저장된 북마크 목록을 List로 반환
     */
    public ArrayList<BookmarkDTO> getBookmark() {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<BookmarkDTO> bookmarkList = new ArrayList<>();

        try {
            String sql = " select  b.id, b.create_timestamp, bg.name, w.wifi_name, w.manage_no   "+"\n"
                        +" from bookmark as b                                                    "+"\n"
                        +" inner join bookmark_group as bg                                       "+"\n"
                        +" on b.fk_bookmark_group_id = bg.id                                     "+"\n"
                        +" inner join wifi as w                                                  "+"\n"
                        +" on b.fk_wifi_manage_no = w.manage_no                                  ";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int bookmarkId = rs.getInt("id");
                Timestamp createTimestamp = new Timestamp(rs.getLong("create_timestamp"));
                String bookmarkGroupName = rs.getString("name");
                String wifiName = rs.getString("wifi_name");
                String wifiManageNo = rs.getString("manage_no");

                BookmarkDTO bookmark = new BookmarkDTO(bookmarkId, bookmarkGroupName, wifiName, wifiManageNo, createTimestamp);
                bookmarkList.add(bookmark);
            }
        } catch (SQLException e) {
            System.out.println("getBookmark: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(rs);
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }

        return bookmarkList;
    }
}