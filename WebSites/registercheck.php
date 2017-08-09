
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/general.css">
<link rel="stylesheet" type="text/css" href="css/register.css">
<title>SHIPS-ANU Register Checking</title>

<script>
function goToMain() {
  window.location.assign("./ships.html");
}
</script>

</head>

<body>
<div id="header"></div>
<div id="content">
	<div style="width:100px; margin:auto;">
    	<p style="font-size:20px">Sucessful</p>
    </div>
    <div style="width:200px; margin:auto;">
	    <button onclick="goToMain">Go back to Home Page</button>
    </div>
    
    <?php		

		$host="localhost"; // Host name 
		$a_name="ships"; // Mysql username 
		$pw="123"; // Mysql password 
		$db_name="anu-ships"; // Database name 
		
		
		// Connect to server and select database.
		$link=mysql_connect("$host", "$a_name", "$pw")or die("cannot connect"); 
		mysql_select_db("$db_name")or die("cannot select database");		
		
		// username and password sent from form 
		$email = $_POST["email"];
		$firstName=$_POST["fname"]; 
		$lastName=$_POST["lname"]; 
		$password = $_POST["password"];
		$preferName=$_POST["name"]; 
		
		
		$sql="SELECT * FROM user WHERE email='" . $email . "'";
		
		//testing sql
		$sql="INSERT INTO `anu-ships`.`user` ( `email` , `firstName` , `lastName` , `password` , `preferName`)
		VALUES('test3@anu.edu.au','test3','anu','123','noName')";
		$result=mysql_query($sql);
		
		
		$result=mysql_query($sql);
		$count=mysql_num_rows($result);
		
		

		
		//real implementation
		$sql="INSERT INTO `anu-ships`.`user` ( `email` , `firstName` , `lastName` , `password` , `preferName`)
		VALUES('" . $email . "','" . $firstName . "','" . $lastName . "','" . $password . "','" . $preferName . "')";
		$result=mysql_query($sql);
	
		if ($link->query($sql) === TRUE) {
			echo "New record created successfully";
		} else {
			echo "Error: " . $sql . "<br>" . $link->error;
		}
		
		mysql_close($link); 
		?>

</div>

<div id="footer"></div>
</body>
</html>

