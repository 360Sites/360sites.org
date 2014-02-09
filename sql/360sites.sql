-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 08, 2014 at 02:17 PM
-- Server version: 5.5.25
-- PHP Version: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `360sites`
--

-- --------------------------------------------------------

--
-- Table structure for table `object`
--

CREATE TABLE IF NOT EXISTS `object` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(15) COLLATE utf8_bin NOT NULL,
  `country` varchar(40) COLLATE utf8_bin NOT NULL,
  `city` varchar(40) COLLATE utf8_bin NOT NULL,
  `address` varchar(50) COLLATE utf8_bin NOT NULL,
  `description` varchar(100) COLLATE utf8_bin NOT NULL,
  `del_date` datetime NOT NULL,
  `del_status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `user_id_2` (`user_id`),
  KEY `user_id_3` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` char(50) COLLATE utf8_bin NOT NULL,
  `password` char(100) COLLATE utf8_bin NOT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `phon_number` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `first_name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `last_name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `user_type` int(1) NOT NULL DEFAULT '1',
  `creation_date` datetime DEFAULT NULL,
  `is_valid_email` tinyint(1) DEFAULT '0',
  `is_valid_phone_number` tinyint(1) DEFAULT '0',
  `is_valid_user` tinyint(1) DEFAULT '0',
  `last_login_date` datetime DEFAULT NULL,
  `used_language` varchar(3) COLLATE utf8_bin NOT NULL,
  `last_brawser` varchar(50) COLLATE utf8_bin NOT NULL,
  `last_ip` varchar(15) COLLATE utf8_bin NOT NULL,
  `session_id` varchar(150) COLLATE utf8_bin DEFAULT NULL,
  `forgot_password_token` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `forgot_password_token_expiry_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_name` (`user_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=11 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `user_name`, `password`, `email`, `phon_number`, `first_name`, `last_name`, `user_type`, `creation_date`, `is_valid_email`, `is_valid_phone_number`, `is_valid_user`, `last_login_date`, `used_language`, `last_brawser`, `last_ip`, `session_id`, `forgot_password_token`, `forgot_password_token_expiry_date`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', NULL, NULL, 'Admin', NULL, 50, NULL, 0, 0, 1, NULL, '', '', '', NULL, NULL, NULL),
(2, 'aram', '59b82aa4663e0a4489bb8ffe71669ecd', NULL, NULL, 'Aram', NULL, 50, NULL, 0, 0, 1, NULL, '', '', '', NULL, NULL, NULL),
(3, 'ashot', '6023c55115500feb1687bf2b865f1591', NULL, NULL, 'Ashot', NULL, 50, NULL, 0, 0, 1, NULL, '', '', '', NULL, NULL, NULL),
(4, 'grish', '5ce4bd5d1bf451828e32bbf717abafd5', NULL, NULL, 'Grish', NULL, 50, NULL, 0, 0, 1, NULL, '', '', '', NULL, NULL, NULL),
(5, 'karen', 'ba952731f97fb058035aa399b1cb3d5c', NULL, NULL, 'Karen', NULL, 50, NULL, 0, 0, 1, NULL, '', '', '', NULL, NULL, NULL),
(6, 'admin', '21232f297a57a5a743894a0e4a801fc3', NULL, NULL, 'Admin', NULL, 50, NULL, 0, 0, 1, NULL, '', '', '', NULL, NULL, NULL),
(7, 'aram', '59b82aa4663e0a4489bb8ffe71669ecd', NULL, NULL, 'Aram', NULL, 50, NULL, 0, 0, 1, NULL, '', '', '', NULL, NULL, NULL),
(8, 'ashot', '6023c55115500feb1687bf2b865f1591', NULL, NULL, 'Ashot', NULL, 50, NULL, 0, 0, 1, NULL, '', '', '', NULL, NULL, NULL),
(9, 'grish', '5ce4bd5d1bf451828e32bbf717abafd5', NULL, NULL, 'Grish', NULL, 50, NULL, 0, 0, 1, NULL, '', '', '', NULL, NULL, NULL),
(10, 'karen', 'ba952731f97fb058035aa399b1cb3d5c', NULL, NULL, 'Karen', NULL, 50, NULL, 0, 0, 1, NULL, '', '', '', NULL, NULL, NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
