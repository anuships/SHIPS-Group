<?php
require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['email']) && isset($_POST['password'])) {
 
    // receiving the post params
    $email = $_POST['email'];
    $password = $_POST['password'];
 
    // get the user by email and password
    $user = $db->getUserByEmailAndPassword($email, $password);
 
    if ($user != false) {
        // user is found
        $response["error"] = FALSE;
		if ($user["uid"] == NULL){
			$db = new DB_Functions();
			$uuid = $db->addUUID($email); 
		    $response["uid"] = $uuid;
		}else{
			$response["uid"] = $user["uid"];
		}
		$response["user"]["id"] = $user["ID"];
		$response["user"]["user_login"] = $user["user_login"];
        $response["user"]["displayname"] = $user["display_name"];
		$response["user"]["email"] = $user["user_email"];
        $response["user"]["created_at"] = $user["user_registered"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Login credentials are wrong. Please try again!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters email or password is missing!";
    echo json_encode($response);
}
?>