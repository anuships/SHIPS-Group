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
function my_custom_login() {
echo '<link rel="stylesheet" type="text/css" href="' . get_bloginfo('stylesheet_directory') . '/login/custom-login-styles.css" />';
}
add_action('login_head', 'my_custom_login');
function my_login_logo_url() {
return get_bloginfo( 'url' );
}

add_filter( 'login_headerurl', 'my_login_logo_url' );

function my_login_logo_url_title() {
return 'SHIPS';
}
add_filter( 'login_headertitle', 'my_login_logo_url_title' );
/**
 * Note: Do not add any custom code here. Please use a child theme so that your customizations aren't lost during updates.
 * http://codex.wordpress.org/Child_Themes
 */
 
// Filter wp_nav_menu() to add a login/logout link to the nav menu
add_filter( 'wp_nav_menu_items', 'my_nav_menu_login_link', 10, 2 );
function my_nav_menu_login_link($menu, $args) {

    if( $args->theme_location == 'primary' ) { // The name of your nav menu location, see http://codex.wordpress.org/Navigation_Menus#Display_Menus_on_Theme

        // If the user is logged in, show logout link, otherwise show login link
        if (is_user_logged_in())
        {
            $logout_link = '<li><a href="' . wp_logout_url() .'" title="Logout">Logout</a></li>';
            $menu        = $menu.$logout_link;
            return $menu;
        }
        else
        {
            // If a the visitor was attempting to access a protected page, extract the URI
            if(!empty($_REQUEST["_s2member_vars"]))
                @list($restriction_type, $requirement_type, $requirement_type_value, $seeking_type, $seeking_type_value, $seeking_uri)
                    = explode("..", stripslashes((string)$_REQUEST["_s2member_vars"]));

            if (!empty($seeking_uri)) {
                $URI = base64_decode($seeking_uri);
            }

            // If we have a link to redirect to after login, use it, otherwise use the default login URL
            if(!empty($URI)) {
                $login_link = '<li><a href="' . wp_login_url( esc_url($URI) ) .'" title="Login">Login</a></li>';
                $menu        = $menu.$login_link;
            }
            else {
                $login_link = '<li><a href="' . wp_login_url() .'" title="Login">Login</a></li>';
                $menu        = $menu.$login_link;
            }
        }
    }

    return $menu;
}