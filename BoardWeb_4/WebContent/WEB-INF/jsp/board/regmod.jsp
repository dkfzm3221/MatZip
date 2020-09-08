<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${data == null ? '글 등록' : '글 수정' }</title>
</head>
<body>
	<div class="container">
		<form id="frm" action="regmod" method="post">
			<div><input type="hidden" name="i_board" value="${data.i_board }"></div>
			<div><input id="title" type="text" name="title" placeholder="제목을 입력하세요" value="${data.title }"></div>
			<div><textarea name="ctnt" placeholder="내용을 입력하세요">${data.ctnt }</textarea></div>
			<div><button type="submit">${data.i_board == null ? '글등록' : '글수정'}</button></div>
		</form>
	</div>
</body>
</html>