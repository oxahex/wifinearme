package com.oxahex.wifinearme.service;

import com.oxahex.wifinearme.db.DBManager;
import com.oxahex.wifinearme.dto.WifiDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class WifiService {

    /**
     * Wi-Fi 정보 여러 개 등록
     * @param wifiList Wi-Fi 정보 List
     * @return 성공 여부
     */
    public int registerWifi(ArrayList<WifiDTO> wifiList) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        int affected = 0;

        try {
            String sql = "insert into wifi (                                  "+"\n"
                    + "  manage_no,                                           "+"\n"
                    + "  district,                                            "+"\n"
                    + "  wifi_name,                                           "+"\n"
                    + "  address,                                             "+"\n"
                    + "  address_detail,                                      "+"\n"
                    + "  install_floor,                                       "+"\n"
                    + "  install_type,                                        "+"\n"
                    + "  install_agency,                                      "+"\n"
                    + "  service_type,                                        "+"\n"
                    + "  net_type,                                            "+"\n"
                    + "  install_year,                                        "+"\n"
                    + "  install_place,                                       "+"\n"
                    + "  net_env,                                             "+"\n"
                    + "  lnt,                                                 "+"\n"
                    + "  lat,                                                 "+"\n"
                    + "  work_date                                            "+"\n"
                    + ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

            for (WifiDTO wifi : wifiList) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, wifi.getManageNo());
                pstmt.setString(2, wifi.getDistrict());
                pstmt.setString(3, wifi.getWifiName());
                pstmt.setString(4, wifi.getAddress());
                pstmt.setString(5, wifi.getAddressDetail());
                pstmt.setString(6, wifi.getInstallFloor());
                pstmt.setString(7, wifi.getInstallType());
                pstmt.setString(8, wifi.getInstallAgency());
                pstmt.setString(9, wifi.getServiceType());
                pstmt.setString(10, wifi.getNetType());
                pstmt.setString(11, wifi.getInstallYear());
                pstmt.setString(12, wifi.getInstallPlace());
                pstmt.setString(13, wifi.getNetEnv());
                pstmt.setDouble(14, wifi.getLnt());
                pstmt.setDouble(15, wifi.getLat());
                pstmt.setString(16, wifi.getWorkDate());

                affected += Objects.requireNonNull(pstmt).executeUpdate();
            }
            System.out.println("affected: " + affected);
        } catch (SQLException e) {
            System.out.println("registerWifi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }
        return affected;
    }

    /**
     * 특정 Wi-Fi 정보를 가져옴
     * @param userManageNo Wi-Fi 관리 번호
     * @return 해당 Wi-Fi 정보
     */
    public WifiDTO getWifi(String userManageNo) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        WifiDTO wifi = null;

        try {
            String sql = " select * from wifi where manage_no = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userManageNo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                wifi = new WifiDTO(
                        null,
                        rs.getString("manage_no"),
                        rs.getString("district"),
                        rs.getString("wifi_name"),
                        rs.getString("address"),
                        rs.getString("address_detail"),
                        rs.getString("install_floor"),
                        rs.getString("install_type"),
                        rs.getString("install_agency"),
                        rs.getString("service_type"),
                        rs.getString("net_type"),
                        rs.getString("install_year"),
                        rs.getString("install_place"),
                        rs.getString("net_env"),
                        rs.getDouble("lnt"),
                        rs.getDouble("lat"),
                        rs.getString("work_date")
                );
            }
        } catch (SQLException e) {
            System.out.println("getWifi: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(rs);
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(conn);
        }

        return wifi;
    }

    /**
     * limit 개의 Wi-Fi 정보를 사용자 현 위치 기준 가까운 순으로 정렬해 가져옴
     * @param limit 가져올 Wi-Fi 개수 최댓값
     * @param userLat 사용자 입력 위도
     * @param userLnt 사용자 입력 경도
     * @return lnt, lat를 기준으로 가장 가까운 Wi-Fi 정보를 최대 limit 개 반환
     */
    public ArrayList<WifiDTO> getWifi(int limit, double userLat, double userLnt) {
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<WifiDTO> wifiList = new ArrayList<>();

        try {
            String sql = " select *,                                     "+"\n"
                       + " (                                             "+"\n"
                       + "   6371 *                                      "+"\n"
                       + "     acos(                                     "+"\n"
                       + "      cos(radians(?)) *                        "+"\n"
                       + "      cos(radians(lat)) *                      "+"\n"
                       + "      cos(radians(lnt) - radians(?)) +         "+"\n"
                       + "      sin(radians(?)) *                        "+"\n"
                       + "      sin(radians(lat))                        "+"\n"
                       + "    )                                          "+"\n"
                       + " ) as distance                                 "+"\n"
                       + " from wifi                                     "+"\n"
                       + " order by distance                             "+"\n"
                       + " limit ?                                       ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Double.toString(userLat));
            pstmt.setString(2, Double.toString(userLnt));
            pstmt.setString(3, Double.toString(userLat));
            pstmt.setString(4, Integer.toString(limit));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                double distance = Math.round(rs.getDouble("distance") * 10000) / 10000.0;
                String manageNo = rs.getString("manage_no");
                String district = rs.getString("district");
                String wifiName = rs.getString("wifi_name");
                String address = rs.getString("address");
                String addressDetail = rs.getString("address_detail");
                String installFloor = rs.getString("install_floor");
                String installType = rs.getString("install_type");
                String installAgency = rs.getString("install_agency");
                String serviceType = rs.getString("service_type");
                String netType = rs.getString("net_type");
                String installYear = rs.getString("install_year");
                String installPlace = rs.getString("install_place");
                String netEnv = rs.getString("net_env");
                double lnt = rs.getDouble("lnt");
                double lat = rs.getDouble("lat");
                String workDate = rs.getString("work_date");

                WifiDTO wifi = new WifiDTO(
                        distance, manageNo, district, wifiName, address, addressDetail,
                        installFloor, installType, installAgency, serviceType,
                        netType, installYear, installPlace, netEnv, lnt, lat, workDate
                );


                wifiList.add(wifi);
            }
        } catch (SQLException e) {
            System.out.println("getWifi: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBManager.closeConnection(pstmt);
            DBManager.closeConnection(rs);
            DBManager.closeConnection(conn);
        }

        return wifiList;
    }
}