<?php

/**
 * Prevent Direct Access
 *
 * @since 0.1
 */
defined( 'ABSPATH' ) or die( "Restricted access!" );

/**
 * Register text domain
 *
 * @since 4.1
 */
function HFScriptsIns_textdomain() {
    load_plugin_textdomain( HFSINS_TEXT, false, HFSINS_DIR . '/languages/' );
}
add_action( 'init', HFSINS_PREFIX . '_textdomain' );

/**
 * Print direct link to plugin admin page
 *
 * Fetches array of links generated by WP Plugin admin page ( Deactivate | Edit )
 * and inserts a link to the plugin admin page
 *
 * @since  4.1
 * @param  array $links Array of links generated by WP in Plugin Admin page.
 * @return array        Array of links to be output on Plugin Admin page.
 */
function HFScriptsIns_settings_link( $links ) {
    $page = '<a href="' . admin_url( 'options-general.php?page=' . HFSINS_SLUG . '.php' ) .'">' . __( 'Settings', HFSINS_TEXT ) . '</a>';
    array_unshift( $links, $page );
    return $links;
}
add_filter( 'plugin_action_links_' . HFSINS_BASE, HFSINS_PREFIX . '_settings_link' );

/**
 * Print additional links to plugin meta row
 *
 * @since 4.1
 */
function HFScriptsIns_plugin_row_meta( $links, $file ) {

    if ( strpos( $file, HFSINS_SLUG . '.php' ) !== false ) {

        $new_links = array(
                           'donate' => '<a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=8A88KC7TFF6CS" target="_blank"><span class="dashicons dashicons-heart"></span> ' . __( 'Donate', HFSINS_TEXT ) . '</a>'
                           );
        $links = array_merge( $links, $new_links );
    }

    return $links;
}
add_filter( 'plugin_row_meta', HFSINS_PREFIX . '_plugin_row_meta', 10, 2 );

/**
 * Register plugin's submenu in the "Settings" Admin Menu
 *
 * @since 4.1
 */
function HFScriptsIns_register_submenu_page() {
    $menu_title = HFSINS_NAME;
    $capability = 'manage_options';
    add_options_page( HFSINS_NAME, $menu_title, $capability, HFSINS_SLUG, HFSINS_PREFIX . '_render_submenu_page' );
}
add_action( 'admin_menu', HFSINS_PREFIX . '_register_submenu_page' );

/**
 * Register settings
 *
 * @since 4.1
 */
function HFScriptsIns_register_settings() {
    register_setting( HFSINS_SETTINGS . '_settings_group', HFSINS_SETTINGS . '_settings' );
    register_setting( HFSINS_SETTINGS . '_settings_group', HFSINS_SETTINGS . '_service_info' );
}
add_action( 'admin_init', HFSINS_PREFIX . '_register_settings' );
