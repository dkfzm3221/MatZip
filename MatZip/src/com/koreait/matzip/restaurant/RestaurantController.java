package com.koreait.matzip.restaurant;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.CommonDAO;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.UserService;
import com.koreait.matzip.vo.RestaurantDomain;
import com.koreait.matzip.vo.RestaurantRecommendMenuVO;
import com.koreait.matzip.vo.RestaurantVO;
import com.koreait.matzip.vo.UserVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class RestaurantController {
	
	private  RestaurantService service;
	
	public RestaurantController() {
		service = new RestaurantService();
	}
	
	public String restMap(HttpServletRequest request) {
		
		request.setAttribute(Const.TITLE, "map" );
		request.setAttribute(Const.VIEW, "restaurant/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}
	public String restReg(HttpServletRequest request) {
		final int I_M = 1; //카테고리 코드
		request.setAttribute("categoryList", CommonDAO.selCodeList(I_M));
		
		request.setAttribute(Const.TITLE, "가게등록");
		request.setAttribute(Const.VIEW, "restaurant/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	public String restRegProc(HttpServletRequest request) {
		UserVO loginUser = SecurityUtils.getLoginUser(request);
		int i_user = loginUser.getI_user();
		String nm = request.getParameter("nm");
		String addr = request.getParameter("addr");
		String strLat = request.getParameter("lat");
		String strLng = request.getParameter("lng");
		String strCd_category = request.getParameter("cd_category");
		
		double lat = CommonUtils.parseStringToDouble(strLat);
		double lng = CommonUtils.parseStringToDouble(strLng);
		int cd_category = CommonUtils.parseStringToInt(strCd_category);
		
		RestaurantVO param = new RestaurantVO();
		
		param.setI_user(i_user);
		param.setNm(nm);
		param.setAddr(addr);
		param.setLat(lat);
		param.setLng(lng);
		param.setCd_category(cd_category);
		
		int result = service.restReg(param);
		return "redirect:/restaurant/restMap";
	}
	
	public String ajaxGetList(HttpServletRequest request) {
		
		return "ajax:" + service.getRestList();
	}
	
	public String restDetail(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter("i_rest", request);

		
		RestaurantVO param = new RestaurantVO();
		param.setI_rest(i_rest);
		
		request.setAttribute("css", new String[] {"restaurant"});
		
		request.setAttribute("recommendMenuList", service.getRecommendMenuList(i_rest));
		request.setAttribute("data", service.getRest(param));
		request.setAttribute(Const.TITLE, "디테일");
		request.setAttribute(Const.VIEW, "restaurant/restDetail");
		request.setAttribute("menuList", service.getSelMenuList(i_rest));
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	public String addRecMenusProc(HttpServletRequest request) { //추천 메뉴
		int i_rest = service.addRecMenus(request);
		
		return "redirect:/restaurant/restDetail?i_rest=" + i_rest;
		
	}
	
	public String addMenusProc(HttpServletRequest request) { //메뉴
		int i_rest = service.addMenus(request);
		return "redirect:/restaurant/restDetail?i_rest=" + i_rest;
	}
	
	public String ajaxDelRecMenu(HttpServletRequest request) { //메뉴 삭제
		int i_rest = CommonUtils.getIntParameter("i_rest", request);
		int seq = CommonUtils.getIntParameter("seq", request);
		int i_user = SecurityUtils.getLogunUserPk(request);
		
		RestaurantRecommendMenuVO param = new RestaurantRecommendMenuVO();
		param.setI_rest(i_rest);
		param.setSeq(seq);
		param.setI_user(i_user);
		
		int result = service.delRecMenu(param);
		
		return "ajax:" + result;
	}

	
	
	
	
	
	
	
	
	
}
