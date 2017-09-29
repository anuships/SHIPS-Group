<?php
/**
 * Our custom dashboard page
 */
$current_user = wp_get_current_user();
	if (isset($_POST['pid'])) {
			echo('yes');
			update_option('cdpp_settings', $_POST['pid']);
		}
    
?>
<div class="wrap about-wrap">
<head>
  <link rel="stylesheet" href="style.css">
</head>
	<h1 style="text-align:center;"><?php echo 'Welcome '.$current_user->user_login; ?></h1>
	
	<hr>
	<div class="changelog" style="margin-top: 5%;">
		<?php
			wp_enqueue_style('cl-chanimal-styles', plugin_dir_url( __FILE__ ) . 'style.css' );
			if (isset($_GET['page_id'])) {
				update_option('cdpp_settings', $_GET['page_id']);
			}
			$p_id = get_option('cdpp_settings');
            if(empty($p_id)){
                echo '<h3>You need to select a page to display here in (CDP Settings -> Select Page)</h3>';
            }else{
                $blog_id = get_current_blog_id();
                if($blog_id==1){
                    $p = get_post($p_id);
                    echo do_shortcode($p->post_content, false);
                    	
					//echo apply_filters('the_content', $p->post_content);
                }else{
                    global $blog_id;
                    switch_to_blog(1);
                    $p = get_post($p_id);
					echo do_shortcode($p->post_content, false);
                    //echo apply_filters('the_content', $p->post_content);
                    restore_current_blog();
                }
            }
		?>
	</div>

</div>
<?php //include( ABSPATH . 'wp-admin/admin-footer.php' );