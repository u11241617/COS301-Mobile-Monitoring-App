-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 22, 2015 at 07:18 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mmadb`
--
DROP DATABASE IF EXISTS mmadb;
CREATE DATABASE mmadb;
USE mmadb;
-- --------------------------------------------------------

--
-- Table structure for table `accessleveltb`
--

CREATE TABLE IF NOT EXISTS `accessleveltb` (
  `accessLevelID` int(100) NOT NULL,
  `description` varchar(300) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `accessleveltb`
--

INSERT INTO `accessleveltb` (`accessLevelID`, `description`) VALUES
(1, 'Administrator'),
(2, 'User');

-- --------------------------------------------------------

--
-- Table structure for table `browsertb`
--

CREATE TABLE IF NOT EXISTS `browsertb` (
  `browserID` int(100) NOT NULL,
  `name` varchar(150) COLLATE utf16_bin NOT NULL COMMENT 'Actual name of browser'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf16 COLLATE=utf16_bin COMMENT='The reason for seperating visitedWebsiteTb and browsertb is to eliminate a *..*';

--
-- Dumping data for table `browsertb`
--

INSERT INTO `browsertb` (`browserID`, `name`) VALUES
(1, 'Chrome'),
(2, 'Opera Mini'),
(3, 'Internet');

-- --------------------------------------------------------

--
-- Table structure for table `calltb`
--

CREATE TABLE IF NOT EXISTS `calltb` (
  `callID` int(100) NOT NULL,
  `deviceID` int(100) NOT NULL,
  `type` varchar(20) COLLATE utf16_bin NOT NULL COMMENT 'Recieved or Missed or Dailed',
  `source` int(15) NOT NULL,
  `destination` int(15) NOT NULL,
  `datetime` datetime NOT NULL,
  `duration` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- --------------------------------------------------------

--
-- Table structure for table `devicetb`
--

CREATE TABLE IF NOT EXISTS `devicetb` (
  `deviceID` int(100) NOT NULL COMMENT 'This is the device IMEI number (cant be null)',
  `userID` int(100) NOT NULL,
  `make` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Samsung',
  `model` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Galaxy Note 2',
  `os` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Android',
  `network` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Vodacom'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin COMMENT='This table consists of the basic mobile device info';

-- --------------------------------------------------------

--
-- Table structure for table `smstb`
--

CREATE TABLE IF NOT EXISTS `smstb` (
  `smsID` int(100) NOT NULL COMMENT 'Each sms needs to have an ID in order to track them',
  `deviceID` int(11) NOT NULL COMMENT 'References mobile device',
  `type` varchar(20) COLLATE utf16_bin NOT NULL COMMENT 'Recieved or Sent',
  `source` varchar(300) COLLATE utf16_bin NOT NULL,
  `destination` varchar(300) COLLATE utf16_bin NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- --------------------------------------------------------

--
-- Table structure for table `usertb`
--

CREATE TABLE IF NOT EXISTS `usertb` (
  `userID` int(100) NOT NULL,
  `accessLevelID` int(100) NOT NULL,
  `email` varchar(300) COLLATE utf16_bin NOT NULL,
  `password` varchar(300) COLLATE utf16_bin NOT NULL,
  `firstLogin` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- --------------------------------------------------------

--
-- Table structure for table `visitedwebsitetb`
--

CREATE TABLE IF NOT EXISTS `visitedwebsitetb` (
  `visitedWebsiteID` int(150) NOT NULL COMMENT 'The value here should be the website URL',
  `deviceID` int(11) NOT NULL,
  `browserID` int(100) NOT NULL,
  `url` varchar(150) COLLATE utf16_bin NOT NULL,
  `dateTime` datetime NOT NULL,
  `frequency` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin COMMENT='This table is needed in order to cross reference browsing activities';


-- --------------------------------------------------------

--
-- Table structure for table `apppermissions`
--

CREATE TABLE IF NOT EXISTS `apppermissions` (
  `id` int(11) NOT NULL,
  `app_id` int(11) NOT NULL,
  `label` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;


--
-- Table structure for table `wifitb`
--

CREATE TABLE IF NOT EXISTS `wifitb` (
  `id` int(11) NOT NULL,
  `ssid` varchar(255) NOT NULL,
  `mac` varchar(255) NOT NULL,
  `time` varchar(150) NOT NULL,
  `bssid` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `deviceId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `deviceapps`
--

CREATE TABLE IF NOT EXISTS `deviceapps` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `version` varchar(100) NOT NULL,
  `device_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=507 DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `wifitb`
--
ALTER TABLE `wifitb`
  ADD PRIMARY KEY (`id`), ADD KEY `wifitb_ibfk_2` (`deviceId`);

--
-- Indexes for table `deviceapps`
--
ALTER TABLE `deviceapps`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `unique_name` (`name`), ADD KEY `deviceapps._ibfk_1` (`device_id`);

--
-- Indexes for table `apppermissions`
--
ALTER TABLE `apppermissions`
  ADD PRIMARY KEY (`id`), ADD KEY `apppermissions._ibfk_1` (`app_id`);

--
-- Indexes for table `accessleveltb`
--
ALTER TABLE `accessleveltb`
  ADD PRIMARY KEY (`accessLevelID`);

--
-- Indexes for table `browsertb`
--
ALTER TABLE `browsertb`
  ADD PRIMARY KEY (`browserID`);

--
-- Indexes for table `calltb`
--
ALTER TABLE `calltb`
  ADD PRIMARY KEY (`callID`), ADD KEY `deviceID` (`deviceID`);

--
-- Indexes for table `devicetb`
--
ALTER TABLE `devicetb`
  ADD PRIMARY KEY (`deviceID`), ADD KEY `userID` (`userID`);

--
-- Indexes for table `smstb`
--
ALTER TABLE `smstb`
  ADD PRIMARY KEY (`smsID`), ADD KEY `deviceID` (`deviceID`);

--
-- Indexes for table `usertb`
--
ALTER TABLE `usertb`
  ADD PRIMARY KEY (`userID`), ADD KEY `accessLevelID` (`accessLevelID`);

--
-- Indexes for table `visitedwebsitetb`
--
ALTER TABLE `visitedwebsitetb`
  ADD PRIMARY KEY (`visitedWebsiteID`), ADD KEY `browserID` (`browserID`), ADD KEY `deviceID` (`deviceID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accessleveltb`
--
ALTER TABLE `accessleveltb`
  MODIFY `accessLevelID` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `browsertb`
--
ALTER TABLE `browsertb`
  MODIFY `browserID` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `calltb`
--
ALTER TABLE `calltb`
  MODIFY `callID` int(100) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `devicetb`
--
ALTER TABLE `devicetb`
  MODIFY `deviceID` int(100) NOT NULL AUTO_INCREMENT COMMENT 'This is the device IMEI number (cant be null)';
--
-- AUTO_INCREMENT for table `smstb`
--
ALTER TABLE `smstb`
  MODIFY `smsID` int(100) NOT NULL AUTO_INCREMENT COMMENT 'Each sms needs to have an ID in order to track them';
--
-- AUTO_INCREMENT for table `usertb`
--
ALTER TABLE `usertb`
  MODIFY `userID` int(100) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `visitedwebsitetb`
--
ALTER TABLE `visitedwebsitetb`
  MODIFY `visitedWebsiteID` int(150) NOT NULL AUTO_INCREMENT COMMENT 'The value here should be the website URL';
--
-- Constraints for dumped tables
--

--
-- Constraints for table `calltb`
--
ALTER TABLE `calltb`
ADD CONSTRAINT `calltb_ibfk_1` FOREIGN KEY (`deviceID`) REFERENCES `devicetb` (`deviceID`);

--
-- Constraints for table `devicetb`
--
ALTER TABLE `devicetb`
ADD CONSTRAINT `devicetb_ibfk_4` FOREIGN KEY (`userID`) REFERENCES `usertb` (`userID`);

--
-- Constraints for table `smstb`
--
ALTER TABLE `smstb`
ADD CONSTRAINT `smstb_ibfk_1` FOREIGN KEY (`deviceID`) REFERENCES `devicetb` (`deviceID`);

--
-- Constraints for table `usertb`
--
ALTER TABLE `usertb`
ADD CONSTRAINT `usertb_ibfk_1` FOREIGN KEY (`accessLevelID`) REFERENCES `accessleveltb` (`accessLevelID`);

--
-- Constraints for table `visitedwebsitetb`
--
ALTER TABLE `visitedwebsitetb`
ADD CONSTRAINT `visitedwebsitetb_ibfk_1` FOREIGN KEY (`browserID`) REFERENCES `browsertb` (`browserID`),
ADD CONSTRAINT `visitedwebsitetb_ibfk_2` FOREIGN KEY (`deviceID`) REFERENCES `devicetb` (`deviceID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
