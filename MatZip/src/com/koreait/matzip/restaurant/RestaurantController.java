package com.koreait.matzip.restaurant;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.CommonDAO;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.UserService;
import com.koreait.matzip.vo.RestaurantDomain;
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

		request.setAttribute("data", service.getRest(param));
		request.setAttribute(Const.TITLE, "디테일");
		request.setAttribute(Const.VIEW, "restaurant/restDetail");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	public String addRecMenusProc(HttpServletRequest request) {
		
		String uploads = request.getRealPath("/res/img");
		MultipartRequest multi = null;
		String strI_rest = null;
		
		String[] menu_nmArr = null;
		String[] menu_priceArr = null;
		
		try {
			multi=new MultipartRequest(request, uploads,5*1024*1024,"UTF-8", new DefaultFileRenamePolicy());
			
			strI_rest = multi.getParameter("i_rest");
			menu_nmArr = multi.getParameterValues("menu_nm");
			menu_priceArr = multi.getParameterValues("menu_price");
		}catch(IOException e) {
			e.printStackTrace();
		}
		if(menu_nmArr != null && menu_priceArr != null) {
			
		}
		
		return "redirect:/restaurant/restDetail?i_rest=" + strI_rest;
	}


}
