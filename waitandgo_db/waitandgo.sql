-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Ven 30 Septembre 2016 à 01:27
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
  `title` text NOT NULL,
  `category` text,
  `description` text,
  `sync` tinyint(1) NOT NULL,
  `id_local` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=86 ;

--
-- Contenu de la table `task`
--

INSERT INTO `task` (`id`, `title`, `category`, `description`, `sync`, `id_local`) VALUES
(61, 'pan', 'perso', 'holÃ  ', 1, 1),
(62, 'pan', 'perso', 'holÃ  ', 1, 2),
(63, 'cervezas', 'trabajo', 'comprar cervezas', 1, 3),
(64, 'ffd', 'tgf', 'gfgb', 1, 4),
(65, 'hdjf', 'ydh', 'dhdu', 1, 5),
(66, 'hshhs', 'zbeh', 'svdv', 1, 6),
(67, 'pan', 'perso', 'holÃ  ', 1, 1),
(68, 'pan', 'perso', 'holÃ  ', 1, 1),
(69, 'pan', 'perso', 'holÃ  ', 1, 2),
(70, 'cervezas', 'trabajo', 'comprar cervezas', 1, 3),
(71, 'ffd', 'tgf', 'gfgb', 1, 4),
(72, 'hdjf', 'ydh', 'dhdu', 1, 5),
(73, 'hshhs', 'zbeh', 'svdv', 1, 6),
(74, 'pan', 'perso', 'holÃ  ', 1, 1),
(75, 'pan', 'perso', 'holÃ  ', 1, 2),
(76, 'cervezas', 'trabajo', 'comprar cervezas', 1, 3),
(77, 'ffd', 'tgf', 'gfgb', 1, 4),
(78, 'hdjf', 'ydh', 'dhdu', 1, 5),
(79, 'hshhs', 'zbeh', 'svdv', 1, 6),
(80, 'pan', 'perso', 'holÃ  ', 1, 1),
(81, 'pan', 'perso', 'holÃ  ', 1, 2),
(82, 'cervezas', 'trabajo', 'comprar cervezas', 1, 3),
(83, 'ffd', 'tgf', 'gfgb', 1, 4),
(84, 'hdjf', 'ydh', 'dhdu', 1, 5),
(85, 'hshhs', 'zbeh', 'svdv', 1, 6);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`id`, `name`, `mail`) VALUES
(5, 'Mathieu Delalande', 'm.stapler.d@gmail.com');

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
(1, 61),
(1, 62),
(1, 63),
(1, 64),
(1, 65),
(1, 66),
(5, 68),
(5, 69),
(5, 70),
(5, 71),
(5, 72),
(5, 73),
(5, 74),
(5, 75),
(5, 76),
(5, 77),
(5, 78),
(5, 79),
(5, 80),
(5, 81),
(5, 82),
(5, 83),
(5, 84),
(5, 85);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
