<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="navigation">
	<h2>카테고리</h2>
	<ul>
		<!-- 카테고리 번호 URL 추가 필요 -->
		<c:forEach items="${categoryList }" var="cvo">
		<li><a href="${pageContext.request.contextPath}/${userId}/${cvo.no}">${cvo.subject }</a></li>
		</c:forEach>
	</ul>
</div>