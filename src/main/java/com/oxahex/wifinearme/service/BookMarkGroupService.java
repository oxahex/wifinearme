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

        String name = bookmarkGroup.getName();
        int order = bookmarkGroup.getOrder();
        Timestamp createTimestamp = bookmarkGroup.getCreateTimestamp();

        try {
            if (isOrderDuplicated(order)) updateOrder(order);

            String sql = " insert into bookmark_group           "+"\n"
                        +" (name, view_order, create_timestamp)    "+"\n"
                        +" values (?, ?, ?)                        ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, order);
            pstmt.setTimestamp(3, createTimestamp);

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

    public BookmarkGroupDTO getBookmarkGroup(int targetId) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        BookmarkGroupDTO bookmarkGroup = null;

        try {
            String sql = " select *                "+"\n"
                        +" from bookmark_group     "+"\n"
                        +" where id = ?            ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, targetId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int order = rs.getInt("view_order");
                Timestamp createTimestamp = new Timestamp(rs.getLong("create_timestamp"));
                long updateTime = rs.getLong("update_timestamp");
                Timestamp updateTimestamp = updateTime > 0 ? new Timestamp(updateTime) : null;

                bookmarkGroup = new BookmarkGroupDTO(id, name, order, createTimestamp, updateTimestamp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(rs);
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }

        return bookmarkGroup;
    }

    /**
     * 저장되어 있는 북마크 그룹 리스트 전체를 가져옴
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
                long updateTime = rs.getLong("update_timestamp");
                Timestamp updateTimestamp = updateTime > 0 ? new Timestamp(updateTime) : null;

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

    public boolean updateBookmarkGroup(int targetId, String targetName, int targetOrder, Timestamp targetTime) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        int affected = 0;

        try {
            if (isOrderDuplicated(targetId)) updateOrder(targetId);

            String sql = " update bookmark_group                                "+"\n"
                    +" set name = ?, view_order = ?, update_timestamp = ?       "+"\n"
                    +" where id = ?                                             ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, targetName);
            pstmt.setInt(2, targetOrder);
            pstmt.setTimestamp(3, targetTime);
            pstmt.setInt(4, targetId);

            affected = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateBookmarkGroup: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }

        return affected == 1;
    }

    public boolean deleteBookmarkGroup(BookmarkGroupDTO bookmarkGroup) {

        return false;
    }

    /**
     * 유저가 입력한 순서와 같거나 큰 행의 view_order + 1
     * @param userOrder 유저 입력 순서
     * @return 순서 업데이트 수행 결과 반환
     */
    private boolean updateOrder(int targetOrder) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        int affected = 0;

        try {
            String sql = " update bookmark_group                "+"\n"
                        +" set view_order = view_order + 1      "+"\n"
                        +" where view_order >= ?                ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, targetOrder);
            affected = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("updateBookmarkGroupOrder: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }

        return affected == 1;
    }

    /**
     * DB에 유저가 생성하고자 하는 북마크 그룹 순서와 동일한 순서가 있는지 확인
     * @param userOrder 생성하려는 북마크 그룹의 순서
     * @return 동일한 값이 존재하면 true, 없으면 false
     */
    private boolean isOrderDuplicated(int targetOrder) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = " select exists (           "+"\n"
                        +"   select *               "+"\n"
                        +"   from bookmark_group    "+"\n"
                        +"   where view_order = ?   "+"\n"
                        +" ) as row_exist           ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, targetOrder);

            rs = pstmt.executeQuery();
            if (rs.next() && rs.getBoolean("row_exist")) return true;

        } catch (SQLException e) {
            System.out.println("isBookmarkGroupOrderDuplicated: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(rs);
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }

        return false;
    }
}