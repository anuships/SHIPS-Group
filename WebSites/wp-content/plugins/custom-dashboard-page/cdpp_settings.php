<?php
    if(sanitize_text_field($_POST['oscimp_hidden']) == 'Y') {
        $p_id = sanitize_text_field($_POST['pagelist']);
        update_option('cdpp_settings',$p_id);
    ?>
        <div class="updated"><p><strong><?php _e('Options saved.' ); ?></strong></p></div>
    <?php
    }else{
        $p_id = get_option('cdpp_settings');
    }
?>
<div class="wrap">
    <?php    echo "<h2>" . __( 'Custom Dashoard Page Settings', 'cgs_date_time' ) . "</h2>"; ?>
    <hr />
    <form name="cgs_date_time_form" method="post" action="<?php echo str_replace( '%7E', '~', $_SERVER['REQUEST_URI']); ?>">
        <input type="hidden" name="oscimp_hidden" value="Y">
        <p><?php _e("Select Page : " ); ?>
            <?php
            $pages = get_pages();
            echo '<select name="pagelist">';
            foreach($pages as $page){
                if($page->ID == $p_id){
                    $selected = 'selected';
                }else{
                    $selected = '';
                }
                echo '<option value="'.$page->ID.'" '.$selected.'>'.$page->post_title.'</option>';
            }
            echo '</select>';
            ?>
        </p>
        <hr />

        <p class="submit" style="margin-top: 2%;width: 100%;float: left;">
            <input style="color: #fff; background-color: green;" type="submit" name="Submit" value="<?php _e('Update Options', 'cgs_date_time' ) ?>" />
        </p>
    </form>
</div>