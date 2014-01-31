<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Upload Example in JSP and Servlet - Java web application</title>
    
    </head>
 
    <body> 
        <div>
            <h3> Choose File to Upload in Server </h3>
            <form id="upload-form" name="upload-form" class="upload-box" action="upload" method="post" enctype="multipart/form-data">
                <input type="file" name="file" />
                <input type="submit" value="upload AJAx" />
            </form>

                      
        </div>
      
    </body>
</html>
<script type="text/javascript" src="js/jq.js"></script>
<script type="text/javascript">
 
var form = $('#upload-form');
form.submit(function () {
 
$.ajax({
type: form.attr('method'),
url: form.attr('action'),
enctype: form('enctype'),
data: form.serialize(),

success: function (data) {
var result=data;
$('#result').attr("value",result);
 
}
});
 
return false;
});
</script>
