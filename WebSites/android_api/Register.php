register.php
<?php
 
require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['firstname']) && isset($_POST['email']) && isset($_POST['password'])) {
 
    // receiving the post params
    $email = $_POST['email'];

    $password = $_POST['password'];
	$first_name = $_POST['firstname'];
	$last_name = $_POST['lastname'];
	$user_name = $_POST['username'];
	
    // check if user is already existed with the same email
    if ($db->isUserExisted($email, $user_name)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email . "or ". $user_name;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($user_name, $first_name, $last_name, $email, $password);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["uid"] = $user["uid"];
            $response["user"]["email"] = $user["user_email"];
			$response["user"]["display_name"] = $user["display_name"];
			$response["user"]["username"] = $user["user_login"];
			$response["user"]["created_at"] = $user["user_registered"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}
?>