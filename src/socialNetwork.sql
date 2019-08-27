-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           5.7.23 - MySQL Community Server (GPL)
-- SE du serveur:                Win64
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Listage de la structure de la base pour socialnetwork
DROP DATABASE IF EXISTS `socialnetwork`;
CREATE DATABASE IF NOT EXISTS `socialnetwork` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `socialnetwork`;

-- Listage de la structure de la table socialnetwork. clubs
DROP TABLE IF EXISTS `clubs`;
CREATE TABLE IF NOT EXISTS `clubs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Listage des données de la table socialnetwork.clubs : ~6 rows (environ)
/*!40000 ALTER TABLE `clubs` DISABLE KEYS */;
INSERT INTO `clubs` (`id`, `name`) VALUES
	(4, 'club1'),
	(5, 'club2'),
	(6, 'club3'),
	(7, 'club4'),
	(8, 'club5');
/*!40000 ALTER TABLE `clubs` ENABLE KEYS */;

-- Listage de la structure de la table socialnetwork. members
DROP TABLE IF EXISTS `members`;
CREATE TABLE IF NOT EXISTS `members` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- Listage des données de la table socialnetwork.members : ~5 rows (environ)
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` (`id`, `name`, `firstName`, `age`) VALUES
	(24, 'CALITRO', 'Rael', 36),
	(25, 'ROMAGNOLI', 'Emmanuel', 43),
	(26, 'BEJAOUI', 'Ala-eddine', 21),
	(28, 'DAVID', 'Alexandre', 24),
	(30, 'DUPONT', 'Jean', 32);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;

-- Listage de la structure de la table socialnetwork. members_clubs
DROP TABLE IF EXISTS `members_clubs`;
CREATE TABLE IF NOT EXISTS `members_clubs` (
  `idMember` int(11) DEFAULT NULL,
  `idClub` int(11) DEFAULT NULL,
  KEY `FK_members_clubs_members` (`idMember`),
  KEY `FK_members_clubs_clubs` (`idClub`),
  CONSTRAINT `FK_members_clubs_clubs` FOREIGN KEY (`idClub`) REFERENCES `clubs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_members_clubs_members` FOREIGN KEY (`idMember`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Listage des données de la table socialnetwork.members_clubs : ~8 rows (environ)
/*!40000 ALTER TABLE `members_clubs` DISABLE KEYS */;
INSERT INTO `members_clubs` (`idMember`, `idClub`) VALUES
	(24, 5),
	(24, 4),
	(25, 6),
	(28, 5),
	(28, 8),
	(30, 7),
	(30, 4),
	(26, 8);
/*!40000 ALTER TABLE `members_clubs` ENABLE KEYS */;

-- Listage de la structure de la table socialnetwork. members_sports
DROP TABLE IF EXISTS `members_sports`;
CREATE TABLE IF NOT EXISTS `members_sports` (
  `idMember` int(11) NOT NULL,
  `idSport` int(11) NOT NULL,
  KEY `FK__members` (`idMember`),
  KEY `FK_members_sports_sports` (`idSport`),
  CONSTRAINT `FK__members` FOREIGN KEY (`idMember`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_members_sports_sports` FOREIGN KEY (`idSport`) REFERENCES `sports` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Listage des données de la table socialnetwork.members_sports : ~6 rows (environ)
/*!40000 ALTER TABLE `members_sports` DISABLE KEYS */;
INSERT INTO `members_sports` (`idMember`, `idSport`) VALUES
	(24, 12),
	(24, 8),
	(24, 9),
	(25, 12),
	(25, 10),
	(28, 8),
	(28, 13),
	(30, 8),
	(30, 13),
	(26, 13);
/*!40000 ALTER TABLE `members_sports` ENABLE KEYS */;

-- Listage de la structure de la table socialnetwork. sports
DROP TABLE IF EXISTS `sports`;
CREATE TABLE IF NOT EXISTS `sports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

-- Listage des données de la table socialnetwork.sports : ~24 rows (environ)
/*!40000 ALTER TABLE `sports` DISABLE KEYS */;
INSERT INTO `sports` (`id`, `name`) VALUES
	(8, 'football'),
	(9, 'tennis'),
	(10, 'escrime'),
	(11, 'boxe'),
	(12, 'cyclisme'),
	(13, 'escalade'),
	(42, 'escrime'),
	(43, 'escrime'),
	(44, 'escrime'),
	(45, 'escrime'),
	(46, 'escrime'),
	(47, 'escrime'),
	(48, 'tennis'),
	(49, 'escalade'),
	(51, 'escrime'),
	(53, 'escrime'),
	(54, 'cyclisme'),
	(56, 'escrime'),
	(57, 'escrime'),
	(58, 'escrime'),
	(59, 'tennis'),
	(60, 'escrime'),
	(61, 'escrime'),
	(62, 'escrime');
/*!40000 ALTER TABLE `sports` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
