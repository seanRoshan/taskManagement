<%@ include file="common/header.jspf"%>
<c:set var="pageNum" value="2"/>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	<H1>Your Todos</H1>
	<div>
		<table class="table table-striped">
			<caption>Your Todos are</caption>

			<thead>
				<tr>
					<th>Description</th>
					<th>Date</th>
					<th>Completed</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${todos}" var="todo">
					<tr>
						<td>${todo.desc}</td>
						<td><fmt:formatDate pattern="dd/MM/yyyy"
								value="${todo.targetDate}" /></td>
						<td>${todo.done}</td>
						<td><a href="/update-todo?id=${todo.id}"
							class="badge badge-danger">Update</a></td>
						<td><a href="/delete-todo?id=${todo.id}"
							class="badge badge-danger">Delete</a></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<a class="badge badge-success" href="/add-todo">Add</a>
</div>

<%@ include file="common/footer.jspf"%>