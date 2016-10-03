-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Lun 03 Octobre 2016 à 05:27
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `waitandgo`
--

-- --------------------------------------------------------

--
-- Structure de la table `task`
--

CREATE TABLE IF NOT EXISTS `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text CHARACTER SET utf8 NOT NULL,
  `category` text CHARACTER SET utf8,
  `description` text CHARACTER SET utf8,
  `sync` tinyint(1) NOT NULL,
  `id_local` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=96 ;

--
-- Contenu de la table `task`
--

INSERT INTO `task` (`id`, `title`, `category`, `description`, `sync`, `id_local`) VALUES
(92, 'pan ', 'perso', 'nendkdb', 1, 1),
(93, 'cervezas', 'trabajo', 'gebebd', 1, 2),
(94, 'hola', 'perso', 'create from mysql', 0, 0),
(95, 'vsbwbd', 'gsgs', 'sgsv', 1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `task_has_prerequisite`
--

CREATE TABLE IF NOT EXISTS `task_has_prerequisite` (
  `task_id` int(11) NOT NULL,
  `task_prerequisite_id` int(11) NOT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `mail` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`id`, `name`, `mail`) VALUES
(6, '', 'm.stapler.d@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `users_has_tasks`
--

CREATE TABLE IF NOT EXISTS `users_has_tasks` (
  `users_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `users_has_tasks`
--

INSERT INTO `users_has_tasks` (`users_id`, `task_id`) VALUES
(6, 92),
(6, 93),
(6, 94),
(6, 95);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
