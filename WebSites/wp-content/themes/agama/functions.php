<?php
	
// Do not allow direct access to the file.
if( ! defined( 'ABSPATH' ) ) exit;

/**
 * Initialize Agama Framework
 *
 * @since 1.0.1
 */
get_template_part( 'framework/class-agama-framework' );
new Agama_Framework();

/**
 * Include Agama Functions File
 *
 * @since 1.0.1
 */
get_template_part( 'framework/agama-functions' );

/**
 * Note: Do not add any custom code here. Please use a child theme so that your customizations aren't lost during updates.
 * http://codex.wordpress.org/Child_Themes
 */
 function my_custom_login() {
echo '<link rel="stylesheet" type="text/css" href="' . get_bloginfo('stylesheet_directory') . '/login/custom-login-styles.css" />';
}
add_action('login_head', 'my_custom_login');