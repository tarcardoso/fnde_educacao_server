<html>
<head>
<title>File Uploading Form</title>
</head>
<body>
<h3>File Upload:</h3>
Select a file to upload: <br />
<form action="./upload.jsp" method="post" enctype="multipart/form-data">
	<input type="file" name="file" size="50" />
	<p>
	ID TIME LINE: <input type="text" name="idPost" value="1" size="50" />
	</p>
	<br/>
	<input type="submit" value="Upload File" />
</form>
</body>
</html>