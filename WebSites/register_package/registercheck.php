<?php session_start(); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../css/general.css">
<link rel="stylesheet" type="text/css" href="../css/register.css">
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
 
require_once './register_package/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

$button = "<div style='width:160px; margin:auto;'>
								 <button onclick='goToMain()'>
									Go back to Home Page
								 </button>
							   </div>";
 
if (isset($_POST['fname']) && isset($_POST['email']) && isset($_POST['password'])) {
 
    // receiving the post params
    $email = $_POST['email'];

    $password = $_POST['password'];
	$first_name = $_POST['fname'];
	$last_name = $_POST['lname'];
	$user_name = $_POST['name'];
	if(!$user_name){
				$user_name = $first_name;
	}
	
    // check if user is already existed with the same email
    if ($db->isUserExisted($email, $user_name)) {
        // user already existed
        $response["error"] = TRUE;
         $response["error_msg"] = "User already existed with email: " 
                 . $email . " or user name: ". $user_name;
        echo "<h2><strong>".$response["error_msg"]."</strong></h2>";
		//echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($user_name, $first_name, $last_name, $email, $password);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            //$response["uid"] = $user["uid"];
            $response["user"]["email"] = $user["user_email"];
			$response["user"]["display_name"] = $user["display_name"];			
			$response["user"]["username"] = $user["user_login"];
			$response["user"]["created_at"] = $user["user_registered"];
                        echo "<h2><strong>Successfully Register</strong></h2>";
			//echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo "<h2><strong>".$response["error_msg"]."</strong></h2>";
			//echo json_encode($response);
			
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo "<h2><strong>".$response["error_msg"]."</strong></h2>";
	//echo json_encode($response);
}

echo $button;
?>
</div>

<div id="footer"></div>
</body>
</html>