SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `parent_id` bigint(20) DEFAULT NULL COMMENT 'parent menu',
                            `name` varchar(64) NOT NULL,
                            `path` varchar(255) DEFAULT NULL COMMENT 'menu URL',
                            `perms` varchar(255) DEFAULT NULL COMMENT 'permission',
                            `component` varchar(255) DEFAULT NULL,
                            `type` int(5) NOT NULL COMMENT 'type  0：directory  1：menu   2：button',
                            `icon` varchar(32) DEFAULT NULL COMMENT 'icon',
                            `orderNum` int(11) DEFAULT NULL COMMENT 'sort',
                            `created` datetime NOT NULL,
                            `updated` datetime DEFAULT NULL,
                            `statu` int(5) NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', 'System management', '', 'sys:manage', '', '0', 'el-icon-s-operation', '1', '2021-01-15 18:58:18', '2021-01-15 18:58:20', '1');
INSERT INTO `sys_menu` VALUES ('2', '1', 'User management', '/sys/users', 'sys:user:list', 'sys/User', '1', 'el-icon-s-custom', '1', '2021-01-15 19:03:45', '2021-01-15 19:03:48', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', 'Role management', '/sys/roles', 'sys:role:list', 'sys/Role', '1', 'el-icon-rank', '2', '2021-01-15 19:03:45', '2021-01-15 19:03:48', '1');
INSERT INTO `sys_menu` VALUES ('4', '1', 'Menu management', '/sys/menus', 'sys:menu:list', 'sys/Menu', '1', 'el-icon-menu', '3', '2021-01-15 19:03:45', '2021-01-15 19:03:48', '1');
INSERT INTO `sys_menu` VALUES ('5', '0', 'System tool', '', 'sys:tools', null, '0', 'el-icon-s-tools', '2', '2021-01-15 19:06:11', null, '1');
INSERT INTO `sys_menu` VALUES ('6', '5', 'Digital dictionary', '/sys/dicts', 'sys:dict:list', 'sys/Dict', '1', 'el-icon-s-order', '1', '2021-01-15 19:07:18', '2021-01-18 16:32:13', '1');
INSERT INTO `sys_menu` VALUES ('7', '3', 'Add role', '', 'sys:role:save', '', '2', '', '1', '2021-01-15 23:02:25', '2021-01-17 21:53:14', '0');
INSERT INTO `sys_menu` VALUES ('9', '2', 'Add user', null, 'sys:user:save', null, '2', null, '1', '2021-01-17 21:48:32', null, '1');
INSERT INTO `sys_menu` VALUES ('10', '2', 'Edit user', null, 'sys:user:update', null, '2', null, '2', '2021-01-17 21:49:03', '2021-01-17 21:53:04', '1');
INSERT INTO `sys_menu` VALUES ('11', '2', 'Delete user', null, 'sys:user:delete', null, '2', null, '3', '2021-01-17 21:49:21', null, '1');
INSERT INTO `sys_menu` VALUES ('12', '2', 'Assign roles', null, 'sys:user:role', null, '2', null, '4', '2021-01-17 21:49:58', null, '1');
INSERT INTO `sys_menu` VALUES ('13', '2', 'Reset password', null, 'sys:user:repass', null, '2', null, '5', '2021-01-17 21:50:36', null, '1');
INSERT INTO `sys_menu` VALUES ('14', '3', 'Edit role', null, 'sys:role:update', null, '2', null, '2', '2021-01-17 21:51:14', null, '1');
INSERT INTO `sys_menu` VALUES ('15', '3', 'Delete role', null, 'sys:role:delete', null, '2', null, '3', '2021-01-17 21:51:39', null, '1');
INSERT INTO `sys_menu` VALUES ('16', '3', 'Assign permission', null, 'sys:role:perm', null, '2', null, '5', '2021-01-17 21:52:02', null, '1');
INSERT INTO `sys_menu` VALUES ('17', '4', 'Add menu', null, 'sys:menu:save', null, '2', null, '1', '2021-01-17 21:53:53', '2021-01-17 21:55:28', '1');
INSERT INTO `sys_menu` VALUES ('18', '4', 'Edit menu', null, 'sys:menu:update', null, '2', null, '2', '2021-01-17 21:56:12', null, '1');
INSERT INTO `sys_menu` VALUES ('19', '4', 'Delete menu', null, 'sys:menu:delete', null, '2', null, '3', '2021-01-17 21:56:36', null, '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `name` varchar(64) NOT NULL,
                            `code` varchar(64) NOT NULL,
                            `remark` varchar(64) DEFAULT NULL COMMENT 'comment',
                            `created` datetime DEFAULT NULL,
                            `updated` datetime DEFAULT NULL,
                            `statu` int(5) NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `name` (`name`) USING BTREE,
                            UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('3', 'Normal User', 'normal', 'Only basic view function', '2021-01-04 10:09:14', '2021-01-30 08:19:52', '1');
INSERT INTO `sys_role` VALUES ('6', 'Admin User', 'admin', 'By default, the system has the highest permission and cannot edit or modify it arbitrarily', '2021-01-16 13:29:03', '2021-01-17 15:50:45', '1');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `role_id` bigint(20) NOT NULL,
                                 `menu_id` bigint(20) NOT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('60', '6', '1');
INSERT INTO `sys_role_menu` VALUES ('61', '6', '2');
INSERT INTO `sys_role_menu` VALUES ('62', '6', '9');
INSERT INTO `sys_role_menu` VALUES ('63', '6', '10');
INSERT INTO `sys_role_menu` VALUES ('64', '6', '11');
INSERT INTO `sys_role_menu` VALUES ('65', '6', '12');
INSERT INTO `sys_role_menu` VALUES ('66', '6', '13');
INSERT INTO `sys_role_menu` VALUES ('67', '6', '3');
INSERT INTO `sys_role_menu` VALUES ('68', '6', '7');
INSERT INTO `sys_role_menu` VALUES ('69', '6', '14');
INSERT INTO `sys_role_menu` VALUES ('70', '6', '15');
INSERT INTO `sys_role_menu` VALUES ('71', '6', '16');
INSERT INTO `sys_role_menu` VALUES ('72', '6', '4');
INSERT INTO `sys_role_menu` VALUES ('73', '6', '17');
INSERT INTO `sys_role_menu` VALUES ('74', '6', '18');
INSERT INTO `sys_role_menu` VALUES ('75', '6', '19');
INSERT INTO `sys_role_menu` VALUES ('76', '6', '5');
INSERT INTO `sys_role_menu` VALUES ('77', '6', '6');
INSERT INTO `sys_role_menu` VALUES ('96', '3', '1');
INSERT INTO `sys_role_menu` VALUES ('97', '3', '2');
INSERT INTO `sys_role_menu` VALUES ('98', '3', '3');
INSERT INTO `sys_role_menu` VALUES ('99', '3', '4');
INSERT INTO `sys_role_menu` VALUES ('100', '3', '5');
INSERT INTO `sys_role_menu` VALUES ('101', '3', '6');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `username` varchar(64) DEFAULT NULL,
                            `password` varchar(64) DEFAULT NULL,
                            `avatar` varchar(255) DEFAULT NULL,
                            `email` varchar(64) DEFAULT NULL,
                            `city` varchar(64) DEFAULT NULL,
                            `created` datetime DEFAULT NULL,
                            `updated` datetime DEFAULT NULL,
                            `last_login` datetime DEFAULT NULL,
                            `statu` int(5) NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UK_USERNAME` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$R7zegeWzOXPw871CmNuJ6upC0v8D373GuLuTw8jn6NET4BkPRZfgK', 'http://img.crcz.com/allimg/201912/01/1575177481483419.jpg', '123@qq.com', 'Guangzhou', '2021-01-12 22:13:53', '2021-01-16 16:57:32', '2020-12-30 08:38:37', '1');
INSERT INTO `sys_user` VALUES ('2', 'test', '$2a$10$0ilP4ZD1kLugYwLCs4pmb.ZT9cFqzOZTNaMiHxrBnVIQUGUwEvBIO', 'http://img.crcz.com/allimg/201912/01/1575177481483419.jpg', 'test@qq.com', null, '2021-01-30 08:20:22', '2021-01-30 08:55:57', null, '1');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint(20) NOT NULL,
                                 `role_id` bigint(20) NOT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('4', '1', '6');
INSERT INTO `sys_user_role` VALUES ('7', '1', '3');
INSERT INTO `sys_user_role` VALUES ('13', '2', '3');