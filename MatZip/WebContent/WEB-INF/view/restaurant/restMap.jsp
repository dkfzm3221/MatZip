<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sectionContainerCenter">
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=249fd926a9b341971398e3cab160d42b"></script>
	<div id="mapContainer" style="width:100%; height:100%;"></div>
	<script type="text/javascript">
		const options = { //지도를 생성할 때 필요한 기본 옵션
			center: new kakao.maps.LatLng(35.8650788, 128.5947286), //지도의 중심좌표.
			level: 5 //지도의 레벨(확대, 축소 정도)
		};
	
		var map = new kakao.maps.Map(mapContainer, options); //지도 생성 및 객체 리턴
	</script>
</div>