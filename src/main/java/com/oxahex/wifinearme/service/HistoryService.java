package com.oxahex.wifinearme.service;

import com.oxahex.wifinearme.db.DBManager;
import com.oxahex.wifinearme.dto.HistoryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HistoryService {

    /**
     * 근처 Wi-Fi 조회 시, 조회 이력 저장
     * @param history 유저 조회 history 객체
     * @return 저장 성공 여부 반환
     */
    public boolean registerHistory(HistoryDTO history) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        int affected = 0;

        try {
            String sql = " insert into history ( lat, lnt, view_datetime )"+"\n"
                       + " values ( ?, ?, ? )                             ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, history.getLat());
            pstmt.setDouble(2, history.getLnt());
            pstmt.setString(3, history.getViewDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            affected = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("registerHistory: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return affected == 0;
    }

    /**
     * 유저의 모든 조회 내역을 가져옴
     * @return 조회 내역을 ArrayList로 반환
     */
    public ArrayList<HistoryDTO> getHistory() {
        ArrayList<HistoryDTO> historyList = new ArrayList<>();

        return historyList;
    }

    /**
     * 특정 조회 내역 삭제
     * @param userHistoryId 삭제하고자 하는 조회 내역의 id
     * @return 삭제 성공 여부를 반환
     */
    public boolean deleteHistory(int userHistoryId) {

        return false;
    }
}
