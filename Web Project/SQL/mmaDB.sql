-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 23, 2015 at 07:09 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mmadb`
--

-- --------------------------------------------------------

--
-- Table structure for table `accessleveltb`
--

CREATE TABLE IF NOT EXISTS `accessleveltb` (
`accessLevelID` int(5) NOT NULL,
  `description` varchar(300) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `accessleveltb`
--

INSERT INTO `accessleveltb` (`accessLevelID`, `description`) VALUES
(1, 'Administrator'),
(2, 'Device User');

-- --------------------------------------------------------

--
-- Table structure for table `browsertb`
--

CREATE TABLE IF NOT EXISTS `browsertb` (
  `browserID` varchar(150) COLLATE utf16_bin NOT NULL COMMENT 'This can be the browser release version',
  `visitedWebsiteID` varchar(150) COLLATE utf16_bin NOT NULL,
  `name` varchar(150) COLLATE utf16_bin NOT NULL COMMENT 'Actual name of browser'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin COMMENT='The reason for seperating visitedWebsiteTb and browsertb is to eliminate a *..*';

-- --------------------------------------------------------

--
-- Table structure for table `calltb`
--

CREATE TABLE IF NOT EXISTS `calltb` (
  `callID` varchar(100) COLLATE utf16_bin NOT NULL,
  `deviceID` varchar(100) COLLATE utf16_bin NOT NULL,
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
  `deviceID` varchar(100) COLLATE utf16_bin NOT NULL COMMENT 'This is the device IMEI number (cant be null)',
  `profileID` int(11) NOT NULL,
  `make` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Samsung',
  `model` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Galaxy Note 2',
  `os` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Android',
  `network` varchar(300) COLLATE utf16_bin NOT NULL COMMENT 'e.g Vodacom'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin COMMENT='This table consists of the basic mobile device info';

--
-- Dumping data for table `devicetb`
--

INSERT INTO `devicetb` (`deviceID`, `profileID`, `make`, `model`, `os`, `network`) VALUES
('111111111', 3, 'Samsung', 'Galaxy Note 2', 'Android', 'Vodacome');

-- --------------------------------------------------------

--
-- Table structure for table `profiletb`
--
/*
CREATE TABLE IF NOT EXISTS `profiletb` (
`profileID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `name` varchar(300) COLLATE utf16_bin NOT NULL,
  `surname` varchar(300) COLLATE utf16_bin NOT NULL,
  `gender` varchar(2) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf16 COLLATE=utf16_bin COMMENT='Stores the user details';
*/
--
-- Dumping data for table `profiletb`
--
/*
INSERT INTO `profiletb` (`profileID`, `userID`, `name`, `surname`, `gender`) VALUES
(3, 1, 'Khathutshelo Shaun', 'Matidza', 'M'),
(4, 2, 'Sylvester', 'Mpangane', 'M');
*/
-- --------------------------------------------------------

--
-- Table structure for table `smstb`
--

CREATE TABLE IF NOT EXISTS `smstb` (
  `smsID` varchar(100) COLLATE utf16_bin NOT NULL COMMENT 'Each sms needs to have an ID in order to track them',
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
`userID` int(15) NOT NULL,
  `email` varchar(300) COLLATE utf16_bin NOT NULL,
  `password` varchar(300) COLLATE utf16_bin NOT NULL,
  `accessLevel` int(5) NOT NULL,
  `firstLogin` boolean NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- --------------------------------------------------------

--
-- Table structure for table `visitedwebsitetb`
--

CREATE TABLE IF NOT EXISTS `visitedwebsitetb` (
  `visitedWebsiteID` varchar(150) COLLATE utf16_bin NOT NULL COMMENT 'The value here should be the website URL',
  `deviceID` varchar(200) COLLATE utf16_bin NOT NULL,
  `dateTime` datetime NOT NULL,
  `frequency` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin COMMENT='This table is needed in order to cross reference browsing activities';

--
-- Dumping data for table `visitedwebsitetb`
--

INSERT INTO `visitedwebsitetb` (`visitedWebsiteID`, `deviceID`, `dateTime`, `frequency`) VALUES
('www.cs.up.ac.za', '111111111111111', '2015-06-23 12:25:24', 2);

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
 ADD PRIMARY KEY (`callID`), ADD KEY `deviceID` (`deviceID`);

--
-- Indexes for table `devicetb`
--
ALTER TABLE `devicetb`
 ADD PRIMARY KEY (`deviceID`), ADD KEY `userID` (`userID`);
/*
--
-- Indexes for table `profiletb`
--
ALTER TABLE `profiletb`
 ADD PRIMARY KEY (`profileID`), ADD KEY `userID` (`userID`);
*/
--
-- Indexes for table `smstb`
--
ALTER TABLE `smstb`
 ADD PRIMARY KEY (`smsID`), ADD KEY `deviceID` (`deviceID`);

--
-- Indexes for table `usertb`
--
ALTER TABLE `usertb`
 ADD PRIMARY KEY (`userID`), ADD KEY `accessLevel` (`accessLevel`);

--
-- Indexes for table `visitedwebsitetb`
--
ALTER TABLE `visitedwebsitetb`
 ADD PRIMARY KEY (`visitedWebsiteID`), ADD KEY `deviceID` (`deviceID`(191));

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accessleveltb`
--
ALTER TABLE `accessleveltb`
MODIFY `accessLevelID` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `profiletb`
--
/*ALTER TABLE `profiletb`
MODIFY `profileID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;*/
--
-- AUTO_INCREMENT for table `usertb`
--
ALTER TABLE `usertb`
MODIFY `userID` int(15) NOT NULL AUTO_INCREMENT;
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
ADD CONSTRAINT `devicetb_ibfk_1` FOREIGN KEY (`smsID`) REFERENCES `smstb` (`smsID`) ON UPDATE NO ACTION;
ADD CONSTRAINT `devicetb_ibfk_2` FOREIGN KEY (`callID`) REFERENCES `calltb` (`callID`) ON UPDATE NO ACTION;
ADD CONSTRAINT `devicetb_ibfk_3` FOREIGN KEY (`visitedWebsiteID`) REFERENCES `visitedWebsitetb` (`visitedWebsiteID`) ON UPDATE NO ACTION;

--
-- Constraints for table `usertb`
--
ALTER TABLE `usertb`
ADD CONSTRAINT `usertb_ibfk_1` FOREIGN KEY (`accessLevel`) REFERENCES `accessleveltb` (`accessLevelID`) ON UPDATE NO ACTION,
ADD CONSTRAINT `usertb_ibfk_2` FOREIGN KEY (`deviceID`) REFERENCES `devicetb` (`deviceID`) ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
