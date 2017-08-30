<?php
/*
Plugin Name: Custom Dashboard Page
Plugin URL: http://myplugin.tophostbd.com/
Description: Custom dashboard plugin will display a specific page into dashboard .
Version: 1.0
Author: raficsedu
Author URI: http://myplugin.tophostbd.com/
Contributors: raficsedu
Text Domain: cdpp
*/

/*
|--------------------------------------------------------------------------
| CONSTANTS
|--------------------------------------------------------------------------
*/

// plugin folder url
if(!defined('cdpp_PLUGIN_URL')) {
	define('cdpp_PLUGIN_URL', plugin_dir_url( __FILE__ ));
}

/*
|--------------------------------------------------------------------------
| MAIN CLASS
|--------------------------------------------------------------------------
*/

class cdpp_custom_dashboard {
 
	/*--------------------------------------------*
	 * Constructor
	 *--------------------------------------------*/
 
	/**
	 * Initializes the plugin by setting localization, filters, and administration functions.
	 */
	function __construct() {
	
		add_action('admin_menu', array( &$this,'cdpp_register_menu') );
		add_action('load-index.php', array( &$this,'cdpp_redirect_dashboard') );
 
	} // end constructor
 
	function cdpp_redirect_dashboard() {
	
		if( is_admin() ) {
			$screen = get_current_screen();
					update_option('cdpp_settings', 91);
			if( $screen->base == 'dashboard' ) {
		update_option('cdpp_settings', 91);
				wp_redirect( admin_url( 'index.php?page=custom-dashboard' ) );
				
		
			}
		}

	}
	
	
	
	function cdpp_register_menu() {
        add_menu_page( 'Custom Dashboard Page','CDP Settings','manage_options', 'cdpp_settings',array(&$this,'cdpp_settings'),'',7);
		add_dashboard_page( 'Custom Dashboard', 'Custom Dashboard', 'read', 'custom-dashboard', array( &$this,'cdpp_create_dashboard') );
	}
	
	function cdpp_create_dashboard() {
		require_once( 'custom_dashboard.php'  );    
	}

    function cdpp_settings(){
        require_once( 'cdpp_settings.php'  );

    }
}
// instantiate plugin's class
$GLOBALS['cdpp_custom_dashboard'] = new cdpp_custom_dashboard();

    
?>
