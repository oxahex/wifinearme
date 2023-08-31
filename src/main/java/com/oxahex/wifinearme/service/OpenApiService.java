package com.oxahex.wifinearme.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oxahex.wifinearme.dto.WifiDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class OpenApiService {
    private int totalDataCount = 0;

    public OpenApiService() {
        this.totalDataCount = preFlight();
    }

    /**
     * 받아 온 Open API Wi-Fi 데이터를 ArrayList로 가공 후 반환
     * @return Wi-Fi 정보를 ArrayList로 반환
     */
    public ArrayList<WifiDTO> getOpenAPI() {
        int start = 0;
        int end = 0;
        int page = 1;
        int limit = this.totalDataCount / 1000 + 1;

        ArrayList<WifiDTO> wifiList = new ArrayList<>();

        try {
            while (page <= limit) {
                start = end + 1;
                if (page != limit) end = page * 1000;
                else end = totalDataCount;

                System.out.println("page" + page + ", start: " + start + ", end: " + end);

                JsonObject json = requestHTTP(start, end);
                JsonArray jsonArray = json.get("row").getAsJsonArray();

                for (JsonElement j : jsonArray) {
                    JsonObject data = j.getAsJsonObject();

                    String manageNo = data.get("X_SWIFI_MGR_NO").getAsString();
                    String district = data.get("X_SWIFI_WRDOFC").getAsString();
                    String wifiName = data.get("X_SWIFI_MAIN_NM").getAsString();
                    String address = data.get("X_SWIFI_ADRES1").getAsString();
                    String addressDetail = data.get("X_SWIFI_ADRES2").getAsString();
                    String installFloor = data.get("X_SWIFI_INSTL_FLOOR").getAsString();
                    String installType = data.get("X_SWIFI_INSTL_TY").getAsString();
                    String installAgency = data.get("X_SWIFI_INSTL_MBY").getAsString();
                    String serviceType = data.get("X_SWIFI_SVC_SE").getAsString();
                    String netType = data.get("X_SWIFI_CMCWR").getAsString();
                    String installYear = data.get("X_SWIFI_CNSTC_YEAR").getAsString();
                    String installPlace = data.get("X_SWIFI_INOUT_DOOR").getAsString();
                    String netEnv = data.get("X_SWIFI_REMARS3").getAsString();
                    double lnt = data.get("LNT").getAsDouble();
                    double lat = data.get("LAT").getAsDouble();
                    String workDate = data.get("WORK_DTTM").getAsString();

                    wifiList.add(new WifiDTO(
                            manageNo,
                            district,
                            wifiName,
                            address,
                            addressDetail,
                            installFloor,
                            installType,
                            installAgency,
                            serviceType,
                            netType,
                            installYear,
                            installPlace,
                            netEnv,
                            lnt,
                            lat,
                            workDate
                    ));
                }
                page++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wifiList;
    }

    /**
     * OpenAPI Open API HTTP 요청 후 HTTP Response 파싱
     * @param start 시작 데이터 번호
     * @param end 마지막 데이터 번호
     * @return  Json Object
     */
    private JsonObject requestHTTP(int start, int end) throws IOException {
        String BASE_URL = "http://openapi.seoul.go.kr:8088";
        String KEY = "594e5a617968796538366669436f59";
        String CONTENT_TYPE = "json";
        String SERVICE_TYPE = "TbPublicWifiInfo";
        String ENC = "UTF-8";

        StringBuilder sb = new StringBuilder(BASE_URL);
        String s = Integer.toString(start);
        String e = Integer.toString(end);

        sb.append("/").append(URLEncoder.encode(KEY, ENC));
        sb.append("/").append(URLEncoder.encode(CONTENT_TYPE, ENC));
        sb.append("/").append(URLEncoder.encode(SERVICE_TYPE, ENC));
        sb.append("/").append(URLEncoder.encode(s, ENC));
        sb.append("/").append(URLEncoder.encode(e, ENC));

        URL url = new URL(sb.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-type", "application/json");

        BufferedReader br;
        int resCode = connection.getResponseCode();
        if (resCode >= 200 && resCode <= 300) {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        connection.disconnect();

        return JsonParser.parseString(sb.toString()).getAsJsonObject().get(SERVICE_TYPE).getAsJsonObject();
    }

    /**
     * Open API 전체 데이터 개수를 미리 얻어옴
     * @return Open API 전체 데이터 개수 반환
     */
    private int preFlight() {
        System.out.println("preFlight 실행");
        int totalCount = 0;
        try {
            JsonObject json = requestHTTP(1, 1);
            totalCount = Integer.parseInt(json.get("list_total_count").getAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return totalCount;
    }
}
