<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>toidicode.com</title>
	<link rel="stylesheet" href="">
</head>

<body>
	<script type="text/javascript">
		if(confirm('Bạn có phải là fan của toidicode.com không?')){
			return  "layouts/admin/pages/dashboard";
		}else{
			document.write('Sao thế bạn?');
		}
	</script>
</body>

</html>