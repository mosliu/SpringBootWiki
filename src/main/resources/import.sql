INSERT INTO spring.sprki_labthink_device_kind (id, device_kind_name, enable, device_kind_namecn) VALUES (1, 'national', true, '国标设备');
INSERT INTO spring.sprki_labthink_device_kind (id, device_kind_name, enable, device_kind_namecn) VALUES (2, 'international', true, '美标设备');
INSERT INTO spring.sprki_labthink_device_type (id, device_type_name, enable, device_type_namecn) VALUES (1, 'permebality', true, '阻隔性');
INSERT INTO spring.sprki_labthink_device_type (id, device_type_name, enable, device_type_namecn) VALUES (2, 'strength', true, '力值设备');
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `device_kind`, `device_type`) VALUES (1, 'W3/330', 1, 1);
INSERT INTO `sprki_user` (`username`, `password`, `enabled`, `regdate`, `username_alias`) VALUES ('aaa', 'aaa', '', '2016-2-25 08:56:04', '1');
INSERT INTO `sprki_user` (`username`, `password`, `enabled`, `regdate`, `username_alias`) VALUES ('admin', 'admin', '', '2016-2-17 00:00:00', 'admin');
INSERT INTO `sprki_user` (`username`, `password`, `enabled`, `regdate`, `username_alias`) VALUES ('bbb', 'bbb', '', '0000-0-0 00:00:00', 'b');
INSERT INTO `sprki_user` (`username`, `password`, `enabled`, `regdate`, `username_alias`) VALUES ('user', 'user', '', '2016-2-17 00:00:00', 'user');
INSERT INTO `sprki_authorities` (`id`, `username`, `authority`) VALUES (3, 'aaa', 'ROLE_AA');
INSERT INTO `sprki_authorities` (`id`, `username`, `authority`) VALUES (0, 'admin', 'ROLE_ADMIN');
INSERT INTO `sprki_authorities` (`id`, `username`, `authority`) VALUES (1, 'admin', 'ROLE_USER');
INSERT INTO `sprki_authorities` (`id`, `username`, `authority`) VALUES (2, 'user', 'ROLE_USER');

