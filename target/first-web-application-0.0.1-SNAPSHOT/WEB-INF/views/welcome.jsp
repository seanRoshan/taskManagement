<%@ include file="common/header.jspf"%>
<c:set var="pageNum" value="1"/>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	<h1>Welcome ${name}</h1>
	<br>
	You can manage your todo list from <a class="badge badge-default" href="/todo-list">Here</a>
</div>
<%@ include file="common/footer.jspf"%>
