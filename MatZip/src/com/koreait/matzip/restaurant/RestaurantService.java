package com.koreait.matzip.restaurant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.vo.RestaurantDomain;
import com.koreait.matzip.vo.RestaurantRecommendMenuVO;
import com.koreait.matzip.vo.RestaurantVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class RestaurantService {
	private RestaurantDAO dao;
	
	public RestaurantService() {
		dao = new RestaurantDAO();
	}
	
	public int restReg(RestaurantVO param) {
		return dao.insRest(param);
	}
	public String getRestList() {
		List<RestaurantDomain> list = dao.selRestList();
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	public RestaurantDomain getRest(RestaurantVO param) {
		return dao.selRest(param);
	}
	public int addMenus(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter("i_rest", request);
		String savePath = request.getServletContext().getRealPath("/res/img/restaurant"); //임시이미지 보관
		String targetPath = savePath + "/" + i_rest + "/menu"; //임시
		FileUtils.makeFolder(targetPath); //경로에 폴더가 없으면 폴더 만들어줌
		RestaurantRecommendMenuVO vo = new RestaurantRecommendMenuVO();
		vo.setI_rest(i_rest);
		
		try {
			for(Part part : request.getParts()) {
				String fileName = part.getSubmittedFileName();
				if(fileName != null) {
					String ext = FileUtils.getExt(fileName);
					String saveFileNm = UUID.randomUUID() + ext;
					part.write(targetPath + "/" + saveFileNm); //파일저장
					
					vo.setMenu_pic(saveFileNm);
					dao.insMenu(vo);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}


		return i_rest;
	}
	
	//추천메뉴
	public int addRecMenus(HttpServletRequest request) {
		
		String savePath = request.getServletContext().getRealPath("/res/img/restaurant"); //임시이미지 보관
		String tempPath = savePath + "/temp"; //임시
		FileUtils.makeFolder(tempPath); //경로에 폴더가 없으면 폴더 만들어줌
		
		int maxFileSize = 10_485_760;
		MultipartRequest multi = null;
		int i_rest = 0;
		String[] menu_nmArr = null;
		String[] menu_priceArr = null;
		List<RestaurantRecommendMenuVO> list = null;
		try {
			multi = new MultipartRequest(request, tempPath, maxFileSize, "UTF-8", new DefaultFileRenamePolicy()); //중복된파일명 바꿔줌
			
			i_rest = CommonUtils.getIntParameter("i_rest", multi);
			
			System.out.println("i_rest : " + i_rest);
			menu_nmArr = multi.getParameterValues("menu_nm");
			menu_priceArr = multi.getParameterValues("menu_price");
			
			if(menu_nmArr == null || menu_priceArr == null) {
				return i_rest;
			}
			
			//메뉴 객체 생성
			list = new ArrayList();
		
				for(int i=0; i<menu_nmArr.length; i++) {
					RestaurantRecommendMenuVO vo = new RestaurantRecommendMenuVO();
					vo.setI_rest(i_rest);
					vo.setMenu_nm(menu_nmArr[i]);
					vo.setMenu_price(CommonUtils.parseStringToInt(menu_priceArr[i]));
					list.add(vo);
				}
			
			String targetPath = savePath + "/" + i_rest;
			FileUtils.makeFolder(targetPath);
			
			String originFileNm = "";
			Enumeration files = multi.getFileNames();
			while(files.hasMoreElements()) {		
				String key = (String)files.nextElement(); //다음 키값을 준다
				System.out.println("key : " + key);
				originFileNm = multi.getFilesystemName(key);
				System.out.println("fileNm : " + originFileNm);
				
				if(originFileNm != null) { //파일 선택을 안 했으면 null이 넘어옴
					String ext = FileUtils.getExt(originFileNm);
					String saveFileNm = UUID.randomUUID() + ext;
					
					System.out.println("saveFileNm : " + saveFileNm);				
					File oldFile = new File(tempPath + "/" + originFileNm);
				    File newFile = new File(targetPath + "/" + saveFileNm);
				    oldFile.renameTo(newFile);	
				    
				    int idx = CommonUtils.parseStringToInt(key.substring(key.lastIndexOf("_") + 1));
				    RestaurantRecommendMenuVO vo = list.get(idx);
				    vo.setMenu_pic(saveFileNm);
				}
				}
		}catch(IOException e) {
			e.printStackTrace();
		}
		if(list != null) {
			for(RestaurantRecommendMenuVO vo : list) {
				dao.insRecommendMenu(vo);
			}
		}
		return i_rest;
	
	}
	
	
	public List<RestaurantRecommendMenuVO> getRecommendMenuList(int i_rest){
		return dao.selRecommendMenuList(i_rest);
	}
	
	public int delRecMenu(RestaurantRecommendMenuVO param) {
		return dao.delRecommendMenu(param);
	}
	
	public List<RestaurantRecommendMenuVO> getSelMenuList(int i_rest){
		return dao.selMenuList(i_rest);
	}
	
	
	
	
	
}