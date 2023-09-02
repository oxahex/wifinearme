package com.oxahex.wifinearme.util;

public class EarthDistanceCalculator {
    /**
     * 위도와 경도로 정의한 두 위치 값 사이의 거리를 구해 km로 반환
     * @param fromLat 기준 위치 위도
     * @param fromLnt 기준 위치 경도
     * @param toLat 타겟 위치 위도
     * @param toLnt 타겟 위치 경도
     * @return 두 위치 사이의 거리를 소수점 4자리 까지 반환
     */
    public double calculateDistance(double fromLat, double fromLnt, double toLat, double toLnt) {
        double earthRadiusKm = 6371.0;
        double fromLatRad = Math.toRadians(fromLat);
        double fromLntRad = Math.toRadians(fromLnt);
        double toLatRad = Math.toRadians(toLat);
        double toLntRad = Math.toRadians(toLnt);

        double distanceLat = toLatRad - fromLatRad;
        double distanceLnt = toLntRad - fromLntRad;

        double x = Math.sin(distanceLat / 2) * Math.sin(distanceLat / 2)
                + Math.cos(fromLatRad) * Math.cos(toLatRad)
                * Math.sin(distanceLnt / 2) * Math.sin(distanceLnt / 2);

        double dist = earthRadiusKm * 2 * Math.atan2(Math.sqrt(x), Math.sqrt(1 - x));

        return Math.round(dist * 10000) / 10000.0;
    }
}