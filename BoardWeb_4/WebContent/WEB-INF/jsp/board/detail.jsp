<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세페이지</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<style type="text/css">
	.material-icons { color: red; }
	.pointerCursor{cursor : pointer;}
	.marginTop30{
		margin-top:30p;}
	#cmt{
		width : 600px;}
		
	.containerPImg {
		display: inline-block;	
		width: 30px;
		height: 30px;
	    border-radius: 50%;
	    overflow: hidden;
	}
	.pImg {
		 object-fit: cover;
		  max-width:100%;
	}
	.highlight {
		color: red;
		font-weight: bold;
	}

		
	#likeListContainer {
		opacity: 0;
		border: 1px solid #bdc3c7;
		position: absolute;
		left: 0px;
		top: 30px;
		width: 100px;
		height: 130px;
		overflow-y: auto;
		background-color: white;
		transition-duration : 500ms;
		}		
	#id_like { 
		position:relative;
		font-size: 1em;
		 }		
		
	#id_like:hover #likeListContainer {
		opacity: 1;
		}		
	.pImg {
		 object-fit: cover;
		  max-width:100%;
	}
	.highlight{
		color : red;
		font-weight : bold;
}
	#likeListContainer {
			opacity: 0;
			border: 1px solid #bdc3c7;
			position: absolute;
			left: 0px;
			top: 30px;
			width: 100px;
			height: 200px;
			overflow-y: auto;
			background-color: white;
			transition-duration : 500ms;
		}		
		#id_like { 
			position:relative;
			font-size: 1em;
		 }		
		
		#id_like:hover #likeListContainer {
			opacity: 1;
		}	

</style>
</head>
<body>
    <div class="container">
        <table>
            <tr id="title">
                <th>제목</th>
               <th colspan="6" id="elTitle">${data.title}</th>
            </tr>
            <tr class="boardInfo">
                <th id="nm">작성자</th>
                <td id="nm-1">
                	<div class="containerPImg">
						<c:choose>
							<c:when test="${data.profile_img != null}">
								<img class="pImg" src="/img/user/${data.i_user}/${data.profile_img}">
							</c:when>
							<c:otherwise>
								<img class="pImg" src="/img/default_profile.jpg">
							</c:otherwise>
						</c:choose>
					</div>
                	${data.nm }
                </td>
                <th id="date">작성일시</th>
                <td id="date-1"> ${data.r_dt} <small>${data == null ? '' : '수정'}</small> </td>
                <th id="hits">조회수</th>
                <td id="hits-1">${data.hits}</td>                
                <td class="pointerCursor" onclick="toggleLike(${data.yn_like})">
                	
                	<c:if test="${data.yn_like == 0 }">
						<span class="material-icons">favorite_border</span>                	
                	</c:if>
                	<c:if test="${data.yn_like == 1}">
                		<span class="material-icons" style="color: red;">favorite</span>
                	</c:if>
                	<c:if test="${data.like_cnt > 0}">
                		<tr>
                			<td colspan="7">
                				<div id="id_like" class="pointerCursor">좋아요${data.like_cnt}개
                					<!-- 작업즁 -->
                					<div id="likeListContainer">
                					<c:forEach items="${likeList}"  var="item">
                						<div class="likeItemContainer">
                							<div class="profile">
                								<c:choose>
                									<c:when test="${item.profile_img == null }">
                										<img class="pImg" src="/img/default_img.jpg">
                									</c:when>
                									<c:otherwise>
                										<img class="pImg" src="/img/user/${item.profile_img}">
                									</c:otherwise>
                								</c:choose>
                							</div>
                						</div>
                					</c:forEach>
                					</div>
                					
                				</div>
                			</td>
                		</tr>
                	</c:if>
                </td>
            </tr>
        </table>
        <div class="ctnt" id="elCtnt">
            ${data.ctnt}
        </div>
        <div class="btn">
             <button type="button">
             	<a href="/board/list?page=${param.page}&record_cnt=${param.record_cnt}&searchText=${param.searchText}&searchType=${param.searchType}">목록</a>
             </button>
             <c:if test="${loginUser.i_user == data.i_user }">
                <button type="submit">
                    <a href="/board/regmod?i_board=${data.i_board}">수정</a>
                </button>
                <form id="delFrm" action="/board/del" method="post">
                    <input type="hidden" name="i_board" value="${data.i_board}">
                    <button type="submit"><a href="#" onclick="submitDel()">삭제</a></button>
                </form>
            </c:if>
        </div>
        
        <div class="marginTop30">
        	<form id="cmtFrm" action="/board/cmt" method="post">
        		<input type="hidden" name="i_cmt" value="0">
        		<input type="hidden" name="i_board" value="${data.i_board}">
        		<div>
        			<input type="text" id="cmt" name="cmt" placeholder="댓글내용">
        			<input type="submit" id="cmtSubmit" value="전송">
        			<input type="button" value="취소" onclick="clkCmtCancel()">
        		</div>
        	</form>
        </div>
        <div class="marginTop30">
        	<table>
        		<tr>
        			<th>내용</th>
        			<th></th>
        			<th>글쓴이</th>
        			<th>등록일</th>
        			<th>비고</th>
        		</tr>
        		<c:forEach items="${cmtList}" var="item">
        			<tr>
        				<td>${item.cmt}</td>
        				<td>
	        				<div class="containerPImg">
								<c:choose>
									<c:when test="${item.profile_img != null}">
										<img class="pImg" src="/img/user/${item.i_user}/${item.profile_img}">
									</c:when>
									<c:otherwise>
										<img class="pImg" src="/img/default_profile.jpg">
									</c:otherwise>
								</c:choose>
							</div>
        				</td>
        				<td>${item.nm}</td>
        				<td>${item.r_dt}</td>
        				<td>
        					<c:if test="${item.i_user == loginUser.i_user}">
        						<button onclick="clkCmtDel(${item.i_cmt})">삭제</button>
        						<button onclick="clkCmtMod(${item.i_cmt}, '${item.cmt}')">수정</button>
        					</c:if>
        				</td>
        			</tr>
        		</c:forEach>
        	</table>
        </div>
    </div>
    <script>
    	function clkCmtCancel() {
    		cmtFrm.i_cmt.value = 0
    		cmtFrm.cmt.value = ''  //홑따옴표
    		cmtSubmit.value = '전송'
    	}
    
    	function clkCmtDel(i_cmt) {
    		if(confirm('삭제 하시겠습니까?')) {
    			location.href = '/board/cmt?i_board=${data.i_board}&i_cmt=' + i_cmt
    		}
    	}
    	
    	//댓글 수정
    	function clkCmtMod(i_cmt, cmt) {
    		console.log('i_cmt : ' + i_cmt)
    		
    		cmtFrm.i_cmt.value = i_cmt
    		cmtFrm.cmt.value = cmt
    		
    		cmtSubmit.value = '수정'
    	}
    
    	function toggleLike(yn_like) {
    		location.href='/board/toggleLike?page=${param.page}&record_cnt=${param.record_cnt}&searchType=${param.searchType}&searchText=${param.searchText}&i_board=${data.i_board}&yn_like=' + yn_like
    	}
    
        function submitDel() {
            delFrm.submit()
        }
        
        function doHighlight() {
        	var searchText = '${param.searchText}'
        	var searchType = '${param.searchType}'
        	
        	switch(searchType) {
        	case 'a': //제목
        		var txt = elTitle.innerText
        		txt = txt.replace(new RegExp('${param.searchText}','gi'), '<span class="highlight">' + searchText + '</span>')
        		elTitle.innerHTML = txt;
        		break;
        	case 'b': //내용
        		var txt = elCtnt.innerText
        		txt = txt.replace(new RegExp('${param.searchText}','gi'), '<span class="highlight">' + searchText + '</span>')
        		elCtnt.innerHTML = txt
        		
        		break;
        	case 'c': //제목+내용
        		var txt = elTitle.innerText
        		txt = txt.replace(new RegExp('${param.searchText}','gi'), '<span class="highlight">' + searchText + '</span>')
        		elTitle.innerHTML = txt
        		
        		txt = elCtnt.innerText
        		txt = txt.replace(new RegExp('${param.searchText}','gi'), '<span class="highlight">' + searchText + '</span>')
        		elCtnt.innerHTML = txt
        		break;
        	}
        }
        
        doHighlight();
        
        
    </script>
</body>
</html>