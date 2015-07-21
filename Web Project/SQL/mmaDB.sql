-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 21, 2015 at 08:16 PM
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

-- --------------------------------------------------------

--
-- Table structure for table `browsertb`
--

CREATE TABLE IF NOT EXISTS `browsertb` (
  `browserID` int(100) NOT NULL COMMENT 'This can be the browser release version',
  `visitedWebsiteID` varchar(150) COLLATE utf16_bin NOT NULL,
  `name` varchar(150) COLLATE utf16_bin NOT NULL COMMENT 'Actual name of browser'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin COMMENT='The reason for seperating visitedWebsiteTb and browsertb is to eliminate a *..*';

-- --------------------------------------------------------

--
-- Table structure for table `calltb`
--

CREATE TABLE IF NOT EXISTS `calltb` (
  `callID` int(100) NOT NULL,
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
  `smsID` int(100) NOT NULL COMMENT 'Reference to the table',
  `callID` int(100) NOT NULL COMMENT 'Reference to the table',
  `browserID` int(100) NOT NULL COMMENT 'Reference to the table',
  `make` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Samsung',
  `model` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Galaxy Note 2',
  `os` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Android',
  `network` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Vodacom'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin COMMENT='This table consists of the basic mobile device info';

-- --------------------------------------------------------

--
-- Table structure for table `profiletb`
--

CREATE TABLE IF NOT EXISTS `profiletb` (
  `profileID` int(11) NOT NULL,
  `deviceID` int(100) NOT NULL,
  `name` varchar(300) COLLATE utf16_bin NOT NULL,
  `surname` varchar(300) COLLATE utf16_bin NOT NULL,
  `gender` varchar(2) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf16 COLLATE=utf16_bin COMMENT='Stores the user details';

-- --------------------------------------------------------

--
-- Table structure for table `smstb`
--

CREATE TABLE IF NOT EXISTS `smstb` (
  `smsID` int(100) NOT NULL COMMENT 'Each sms needs to have an ID in order to track them',
  `deviceID` int(11) NOT NULL COMMENT 'References mobile device',
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
  `profileID` int(100) NOT NULL,
  `email` varchar(300) COLLATE utf16_bin NOT NULL,
  `password` varchar(300) COLLATE utf16_bin NOT NULL,
  `firstLogin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- --------------------------------------------------------

--
-- Table structure for table `visitedwebsitetb`
--

CREATE TABLE IF NOT EXISTS `visitedwebsitetb` (
  `visitedWebsiteID` varchar(150) COLLATE utf16_bin NOT NULL COMMENT 'The value here should be the website URL',
  `dateTime` datetime NOT NULL,
  `frequency` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin COMMENT='This table is needed in order to cross reference browsing activities';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accessleveltb`
--
ALTER TABLE `accessleveltb`
  ADD PRIMARY KEY (`accessLevelID`);

--
-- Indexes for table `browsertb`
--
ALTER TABLE `browsertb`
  ADD PRIMARY KEY (`browserID`), ADD KEY `visitedWebsiteID` (`visitedWebsiteID`);

--
-- Indexes for table `calltb`
--
ALTER TABLE `calltb`
  ADD PRIMARY KEY (`callID`);

--
-- Indexes for table `devicetb`
--
ALTER TABLE `devicetb`
  ADD PRIMARY KEY (`deviceID`), ADD KEY `smsID` (`smsID`), ADD KEY `phoneID` (`callID`), ADD KEY `browserID` (`browserID`);

--
-- Indexes for table `profiletb`
--
ALTER TABLE `profiletb`
  ADD PRIMARY KEY (`profileID`), ADD KEY `deviceID` (`deviceID`), ADD KEY `deviceID_2` (`deviceID`);

--
-- Indexes for table `smstb`
--
ALTER TABLE `smstb`
  ADD PRIMARY KEY (`smsID`);

--
-- Indexes for table `usertb`
--
ALTER TABLE `usertb`
  ADD PRIMARY KEY (`userID`), ADD KEY `accessLevelID` (`accessLevelID`), ADD KEY `profileID` (`profileID`);

--
-- Indexes for table `visitedwebsitetb`
--
ALTER TABLE `visitedwebsitetb`
  ADD PRIMARY KEY (`visitedWebsiteID`);

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
  MODIFY `browserID` int(100) NOT NULL AUTO_INCREMENT COMMENT 'This can be the browser release version';
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
-- AUTO_INCREMENT for table `profiletb`
--
ALTER TABLE `profiletb`
  MODIFY `profileID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
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
-- Constraints for dumped tables
--

--
-- Constraints for table `browsertb`
--
ALTER TABLE `browsertb`
ADD CONSTRAINT `browsertb_ibfk_1` FOREIGN KEY (`visitedWebsiteID`) REFERENCES `visitedwebsitetb` (`visitedWebsiteID`);

--
-- Constraints for table `devicetb`
--
ALTER TABLE `devicetb`
ADD CONSTRAINT `devicetb_ibfk_1` FOREIGN KEY (`smsID`) REFERENCES `smstb` (`smsID`),
ADD CONSTRAINT `devicetb_ibfk_2` FOREIGN KEY (`callID`) REFERENCES `calltb` (`callID`),
ADD CONSTRAINT `devicetb_ibfk_3` FOREIGN KEY (`browserID`) REFERENCES `browsertb` (`browserID`);

--
-- Constraints for table `profiletb`
--
ALTER TABLE `profiletb`
ADD CONSTRAINT `profiletb_ibfk_1` FOREIGN KEY (`deviceID`) REFERENCES `devicetb` (`deviceID`);

--
-- Constraints for table `usertb`
--
ALTER TABLE `usertb`
ADD CONSTRAINT `usertb_ibfk_1` FOREIGN KEY (`accessLevelID`) REFERENCES `accessleveltb` (`accessLevelID`),
ADD CONSTRAINT `usertb_ibfk_2` FOREIGN KEY (`profileID`) REFERENCES `profiletb` (`profileID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
