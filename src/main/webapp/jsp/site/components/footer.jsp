<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<fmt:setLocale value="${locale}"  scope="session"/>
<fmt:setBundle basename="pagesCont.pagecontent" var="rb" />
	
<footer class="bs-docs-footer">
	<div class="container">
		<div class="row">
			<div class="col-sm-6">
				<address>
				<fmt:message key="company.name" bundle="${ rb }" var="companyName" />
				<fmt:message key="company.street" bundle="${ rb }" var="companyStreet" />
				<fmt:message key="company.city" bundle="${ rb }" var="companyCity" />
				<fmt:message key="company.phone" bundle="${ rb }" var="companyPhone" />
					<strong>${companyName}</strong>
					<br> ${companyStreet}
					<br> ${companyCity}
					<br> <abbr title="${companyPhone}">P:</abbr> (093) 774-5975
				</address>
			</div>
			<div class="col-sm-6 text-right">
				<div class="form-search">
				<form name="ProductListForm" method="POST" action="controller">
					<input type="hidden" name="command" value="footer" />
					<fmt:message key="button.search" bundle="${ rb }" var="buttonSearch" />
					<fmt:message key="placeholder.search" bundle="${ rb }" var="Search" />
					<input type="text" name="searchRequest" class="input-large search-query" placeholder="${Search}">
					<button type="submit" name="searchButton" class="btn" >${buttonSearch}</button> 
				</form>
				</div>
			</div>
		</div>
	</div>
</footer>

