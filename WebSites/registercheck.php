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
/**		$host="localhost"; // Host name 
		$a_name="ubuntu"; // Mysql username 
		$pw="AnU#1SH@I0ps20&17"; // Mysql password 
		$db_name="shipsdb"; // Database name 
**/
		try{
			
			//$dbhost = 'ec2-13-55-194-140.ap-southeast-2.rds.amazonaws.com';
			$dbhost="localhost"; 
			$dbport = '3306';
			$dbname = 'shipsdb';
		
			$dsn = "mysql:host=".$dbhost.";port=".$dbport.";dbname=".$dbname;
			$username = "wordpress";
			$password = "anu";		

			//$dbh = new PDO($dsn, $username, $password);
	
			$link = mysqli_connect($dbhost, $username,
					$password,$dbname, $dbport) 
					or die("cannot connect to server database");
			
			// Connect to server and select database.
//			$link = mysqli_connect("$dbhost", "$dbname", "$password","$port")
//						or die("cannot connect"); 
//			mysqli_select_db($link,"$dbname")
//						or die("cannot select database");		
			
			// username and password sent from reigerster form 
			$email = $_POST["email"];
			$firstName=$_POST["fname"]; 
			$lastName=$_POST["lname"]; 
			$password = $_POST["password"];
			$preferName=$_POST["name"]; 		
			
			$table = "wp_users";
			
			$sql="SELECT ID FROM ".$table." WHERE email='".$email."'";
			$result = mysqli_query($sql);
			
			$rowCount = mysqli_num_rows($result);
			
			//debug code
			echo "there are ".$rowCount." rows";
			echo "<br>sql is: ".$sql;
			echo "<br>".$firstname."<br>".$email."<br>".$password;

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
			}else 
				{		
				//insert new user information
				echo $counter;
				$counter = $counter+1;
				$sql2="SELECT ID FROM ".$table." ORDER BY uid desc limit 1;";
				$result2 = mysqli_query($sqls);
				$uid = $result2 + 1;
				
				echo "<br>new uid is ".$uid;
				$counter = $counter+1;
				
				$sql="INSERT INTO `".$dbname."`.`".$email."` ( `uid`, `user_email` , 
					`first_name` , `password` , `last_name` , `user_nicename`)
				VALUES('".$uid.",".$email."','".$firstName."','".$password."',
						'".$lastName."','".$preferName."')";
				$result = mysqli_query($link,$sql);
		
			
				if ($result === TRUE) {
					echo $counter;
					echo "result exsits";
					$counter = $counter+1;
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
		}
		//catch exception
		catch(Exception $e) {
		  echo 'Message: ' .$e->getMessage();
		}
					
		
		mysqli_close($link); 
	?>
</div>

<div id="footer"></div>
</body>
</html>
