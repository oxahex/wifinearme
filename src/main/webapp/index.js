
window.addEventListener("DOMContentLoaded", () => {
    const btn = document.querySelector("#getLocationBtn");
    if (btn) {
        btn.addEventListener('click', (btn) => getUserLocation(btn));
    }
})

const getUserLocation = (btn) => {
    window.navigator.geolocation.getCurrentPosition(position => {
            const LAT = position.coords.latitude;   // 위도
            const LNT = position.coords.longitude;  // 경도

            const latInput = document.querySelector("#lat");
            const lntInput = document.querySelector("#lnt");
            if (latInput && lntInput) {
                latInput.setAttribute("value", LAT.toString());
                lntInput.setAttribute("value", LNT.toString());
            }
        },
        positionError => {
            switch (positionError) {
                case positionError.PERMISSION_DENIED:
                    alert("PERMISSION_DENIED: 위치 정보 사용 요청을 허용해주세요.");
                    break;
                case positionError.POSITION_UNAVAILABLE:
                    alert("POSITION_UNAVAILABLE: 현재 위치 정보를 사용할 수 없습니다.");
                    break;
                case positionError.TIMEOUT:
                    alert("TIMEOUT: 위치 정보가 응답 하지 않습니다.");
                    break;
                default:
                    alert("알 수 없는 오류가 발생했습니다.");
                    break;
            }
        })
}