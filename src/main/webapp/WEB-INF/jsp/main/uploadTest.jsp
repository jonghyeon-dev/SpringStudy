<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../layouts/header.jsp"%>
</head>
<body>
    <div class="container">
        <%@include file="../layouts/top.jsp"%> 
    </div>
    <form id="uploadTest" action="<c:url value='/file/upload'/>">
        <input type="file">
        <input type="submit">
    </form>
</body>
</html>