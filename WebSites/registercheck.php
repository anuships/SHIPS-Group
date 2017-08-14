<?php session_start(); ?>
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


    
    <?php
		$host="localhost"; // Host name 
		$a_name="root"; // Mysql username 
		$pw=""; // Mysql password 
		$db_name="anu-ships"; // Database name 
		
		
		// Connect to server and select database.
		$link = mysqli_connect("$host", "$a_name", "$pw")
					or die("cannot connect"); 
		mysqli_select_db($link,"$db_name")
					or die("cannot select database");		
		
		// username and password sent from form 
		$email = $_POST["email"];
		$firstName=$_POST["fname"]; 
		$lastName=$_POST["lname"]; 
		$password = $_POST["password"];
		$preferName=$_POST["name"]; 		
		
		$sql="SELECT * FROM userinfo WHERE email='".$email."'";
		$result = mysqli_query($link,$sql);
		
		$rowCount = mysqli_num_rows($result);
		
		if($rowCount > 0){
			//echo "Has result of ".$rowCount." rows";
			$msg = "<div style='width:200px; margin:auto; align='center'>
					    	<p style='font-size:20px'>E-mail already been used</p>
					</div>";
				
			$button = "<div style='width:160px; margin:auto;'>
							 <button onclick='goToMain()'>
							    Go back to Home Page
							 </button>
					   </div>";
			echo $msg.$button;
		}else{
			
			//insert new user information
			$sql="INSERT INTO `anu-ships`.`userinfo` ( `email` , 
				`firstName` , `password` , `lastName` , `preferName`)
			VALUES('".$email."','".$firstName."','".$password."',
					'".$lastName."','".$preferName."')";
			$result = mysqli_query($link,$sql);
	
		
			if ($result === TRUE) {
				$msg = "<div style='width:100px; margin:auto; align='center'>
					    	<p style='font-size:20px'>Sucessful</p>
					    </div>";
				
				$button = "<div style='width:160px; margin:auto;'>
							 <button onclick='goToMain()'>
							    Go back to Home Page
							 </button>
						   </div>";
				echo $msg.$button;
			} else {
				echo "Error: ".$sql."<br>".mysql_error($link);
			}
		}
		mysqli_close($link); 
	?>
</div>

<div id="footer"></div>
</body>
</html>

