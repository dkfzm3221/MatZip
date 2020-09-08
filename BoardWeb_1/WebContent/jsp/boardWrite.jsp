<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 
	String err = request.getParameter("err");
	
	String msg = "";
	if(err!=null){
		switch(err){
		case "10":
			msg = "등록할 수 없습니다.";
			break;
		case "20":
			msg = "DB 에러 발생";
			break;
		}
			
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<style>
	#msg{
		color : red;
	} 	
</style>
</head>
<body>
	<div id = "msg"><%=msg %></div>
	<div>
		<form id = "frm" action = "/jsp/boardWriteProc.jsp" method="post" onsubmit="return chk()"> <% //form태그로 페이지 이동 할때 값을 전달할때  post = 캡슐화/내용이길거나 보안이필요하면%>
			<div><label>제목 : <input type = "text" name="title"></label></div> <%//name = [name]만 가능 서버에게 값을 보내기 위한 키값 id는 유일해야함  %>
			<div><label>내용 : <textarea name = "ctnt"></textarea></label></div> <%//class는 같은 이름을 여러개 주고싶을 때  ex)class = jj bb cc%>
			<div><label>작성자 : <input type = "text" name="i_student"></label></div>
			<div><input type = "submit" value = "글등록"></div>
		</form>	
	</div>
	<script>
		function eleValid(ele, nm){
			if(ele.value.length == 0){
				alert(nm+'을(를) 입력해주세요')
				ele.focus()
				return true
			}
		}
		function chk(){
			//console.log(`title : \${frm.title.value}`)
			
			if(eleValid(frm.title, '제목')){
				return false
			}else if(eleValid(frm.ctnt,'내용')){
				return false
			}else if(eleValid(frm.i_student,'작성자')){
				return false
			}
			
			/*
			if(frm.title.value == ''){
				alert('제목을 입력해 주세요')
				frm.title.focus()
				return false
			}else if(frm.ctnt.value.length == 0){
				alert('내용을 입력해주세요.')
				frm.ctnt.focus()
				return false
			}else if(frm.i_student.value.length == 0){
				alert('작성자를 입력해주세요')
				frm.i_student.focus()
				return false
			}
	
			*/
		}
	</script>
</body>
</html>