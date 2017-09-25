<?php
 
class DB_Functions {
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
		require_once('./wp-includes/class-phpass.php');
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }
 
    /**
     * Storing new user
     * returns user details
     */
	 public function addUUID($email){
		  $stmt = $this->conn->prepare("UPDATE wp_users SET uid=? WHERE user_email=?;");
		  $uuid = uniqid('', true);
		   $stmt->bind_param("ss", $uuid, $email);
		   $result = $stmt->execute();
			$stmt->close();
			if ($result){
				return $uuid;
			}else{
				return NULL;
			}
	 }
    public function storeUser($username, $first_name, $last_name, $email, $password) {
        $uuid = uniqid('', true);
        $hash = $this->hashPASS($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
		$status = 0;
		$display_name = $first_name;
		if ($first_name == NULL){
			$display_name = $username;
		}else{
			$display_name .= " ";
			$display_name .= $last_name;
        }
		$null_string = " ";
		
			$stmt = $this->conn->prepare("INSERT INTO wp_users(user_login, user_pass, user_nicename,user_email, user_url, user_registered,
			user_activation_key,user_status, display_name, uid) VALUES(?,?,?,?,?,NOW(),?,?,?,?)");
		 $stmt->bind_param("ssssssiss",
							$username,$encrypted_password, $username,$email, $null_string,$null_string,$status,$display_name, $uuid);	
        $result = $stmt->execute();
        $stmt->close();
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM wp_users WHERE user_email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $user_id = $user["ID"];
			$nickname_str = "nickname";
			$nickname_val = $username;
			$first_name_str = "first_name";
			$first_name_val = $first_name;
			$last_name_str = "last_name";
			$last_name_val = $last_name;
			$description_str = "description";
			$description_val = "";		
			$rich_editing_str = "rich_editing";
			$rich_editing_val = "true";
			$comment_shortcuts_str = "comment_shortcuts";
			$comment_shortcuts_val = "false";
			$admin_color_str = "admin_color";
			$admin_color_val = "fresh";
			$use_ssl_str = "use_ssl";
			$use_ssl_val = 0;
			$show_admin_bar_front_str = "show_admin_bar_front";
			$show_admin_bar_front_val = "true";
			$locale_str = "locale";
			$locale_val = "";
			$capabilities_str = "wp_capabilities";
			$capabilities_val ="a:1:{s:10:\"subscriber\";b:1;}";
			$wp_user_level_str = "wp_user_level";
			$wp_user_level_val = 0;
			$dismissed_wp_pointers_str = "dismissed_wp_pointers";
			$dismissed_wp_pointers_val = "";
			$stmt = $this->conn->prepare("INSERT INTO wp_usermeta(user_id, meta_key, meta_value) 
									VALUES
									(?,?,?),
									(?,?,?),
									(?,?,?),
									(?,?,?),
									(?,?,?),
									(?,?,?),
									(?,?,?),
									(?,?,?),
									(?,?,?),
									(?,?,?),
									(?,?,?),
									(?,?,?),
									(?,?,?)");
			$stmt->bind_param("issississississississisiissississisiiss",
							$user_id ,$nickname_str, $nickname_val,
							$user_id,$first_name_str, $first_name_val,
							$user_id, $last_name_str,$last_name_val, 
							$user_id,$description_str,$description_val, 
							$user_id,$rich_editing_str, $rich_editing_val,
							$user_id, $comment_shortcuts_str,$comment_shortcuts_val,
							$user_id, $admin_color_str, $admin_color_val,
							$user_id,$use_ssl_str, $use_ssl_val,
							$user_id, $show_admin_bar_front_str, $show_admin_bar_front_val,
							$user_id,$locale_str, $locale_val,
							$user_id,$capabilities_str, $capabilities_val, 
							$user_id,$wp_user_level_str,$wp_user_level_val, 
							$user_id, $dismissed_wp_pointers_str,$dismissed_wp_pointers_val 
							);
			$result = $stmt->execute();		
			$stmt->close();
			return $user;
        } else {
            return false;
        }
    }
 
    /**
     * Get user by email(or username) and password
     */
    public function getUserByEmailAndPassword($email, $password) {
 
        $stmt = $this->conn->prepare("SELECT * FROM wp_users WHERE user_email = ? OR user_login=?");
 
        $stmt->bind_param("ss", $email, $email);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            $encrypted_password = $user['user_pass'];
			$wp_hasher = new PasswordHash(8, TRUE);
            // check for password equality
            if ($wp_hasher->CheckPassword($password, $encrypted_password)) {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($email, $username) {
        $stmt = $this->conn->prepare("SELECT user_email from wp_users WHERE user_email = ? OR user_login=?");
        $stmt->bind_param("ss", $email, $username);
        $stmt->execute();
        $stmt->store_result();
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }
 
    /**
     * Encrypting password
     * @param password
     * returns encrypted password
     */
    public function hashPASS($password) {
		$wp_hasher = new PasswordHash(8, TRUE);		
		$wp_hash = $wp_hasher->HashPassword( $password );
		$hash = array("encrypted" => $wp_hash);
        return $hash;
    }
 
 
}
 
?>