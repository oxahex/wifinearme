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

    /**
     * 특정 ID의 북마크 정보 조회
     * @param targetBookmarkId 조회 하고자 하는 북마크 ID
     * @return 해당 ID와 일치 하는 북마크 객체 반환
     */
    public BookmarkDTO getBookmark(int targetBookmarkId) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        BookmarkDTO bookmark = null;

        try {
            String sql = " select  b.id, b.create_timestamp, bg.name, w.wifi_name   "+"\n"
                        +" from bookmark as b                                       "+"\n"
                        +" inner join bookmark_group as bg                          "+"\n"
                        +" on b.fk_bookmark_group_id = bg.id                        "+"\n"
                        +" inner join wifi as w                                     "+"\n"
                        +" on b.fk_wifi_manage_no = w.manage_no                     "+"\n"
                        +" where b.id = ?                                           ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, targetBookmarkId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int bookmarkId = rs.getInt("id");
                Timestamp createTimestamp = new Timestamp(rs.getLong("create_timestamp"));
                String bookmarkGroupName = rs.getString("name");
                String wifiName = rs.getString("wifi_name");

                bookmark = new BookmarkDTO(bookmarkId, bookmarkGroupName, wifiName, null, createTimestamp);
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

        return bookmark;
    }

    public boolean deleteBookmark(int targetBookmarkId) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        int affected = 0;

        try {
            String sql = " delete from bookmark     "+"\n"
                        +" where id = ?             ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, targetBookmarkId);

            affected = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteBookmark: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }

        return affected == 1;
    }

    /**
     * 북마크 그룹 삭제 시, 연결되어 있던 북마크 컬럼을 전부 삭제
     * @param targetBookmarkGroupId 삭제할 북마크 그룹 ID
     */
    public void clearBookmark(int targetBookmarkGroupId) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;

        try {
            String sql = " delete from bookmark             "+"\n"
                        +" where fk_bookmark_group_id = ?   ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, targetBookmarkGroupId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("clearBookmark: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }
    }
}