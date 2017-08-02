<?php
/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the
 * installation. You don't have to use the web site, you can
 * copy this file to "wp-config.php" and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * MySQL settings
 * * Secret keys
 * * Database table prefix
 * * ABSPATH
 *
 * @link https://codex.wordpress.org/Editing_wp-config.php
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('DB_NAME', 'shipsdb');

/** MySQL database username */
define('DB_USER', 'root');

/** MySQL database password */
define('DB_PASSWORD', '');

/** MySQL hostname */
define('DB_HOST', 'localhost');

/** Database Charset to use in creating database tables. */
define('DB_CHARSET', 'utf8mb4');

/** The Database Collate type. Don't change this if in doubt. */
define('DB_COLLATE', '');

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         '1v1sPQWRNe/4M!!9A6@GYgK?,+<6MV{xG6W^Uz|cRKZ0-k_c+axM@B7GfN$]&^jO');
define('SECURE_AUTH_KEY',  'vl~El}RkxQ(E#cmZkYU_Lk{!+)I0bn sJ;L!P3hd*YQ)PmvsU$moOc[MR,MqNC1;');
define('LOGGED_IN_KEY',    'P0C9nUkOAC&vQQWJxa]6]NBsqnEdMO$AXGZ7}s(X6-zjPGT=:0` tA~F%(?*QQ7(');
define('NONCE_KEY',        'wiWA:kTbi~_ObbrVjD;;gsuh[DuuC2s9>gAsfpf4*Q1;I,39D6HY*fCJ.n.tGOT8');
define('AUTH_SALT',        '{{a1B&sH29QLW%s>(*,%f Gr#nmE8Dx_>xu#5oU~hHolW4ZJ<UfXEUz((t=][*i|');
define('SECURE_AUTH_SALT', '|s, 89AufobM~+Db-h5E83,=y~9v2P7HqSu4>>z(oCrE2R%etB~Ie)k$/11-N?oa');
define('LOGGED_IN_SALT',   'U66_B;!eL.?tI .@[01F _rAQWD&7g5}>kSg4eL@osOxm6yW2KS!j R1+2^Sp*eX');
define('NONCE_SALT',       'y@AojS+!kC>g0Akm[`T>o ,)o57mf%:sVoT7UsB2ye`1S:+P=`Qqg]K7o+L`aSS.');

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'wp_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the Codex.
 *
 * @link https://codex.wordpress.org/Debugging_in_WordPress
 */
define('WP_DEBUG', false);

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
