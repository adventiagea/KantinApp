-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 21, 2022 at 11:07 AM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kp_ayu`
--

-- --------------------------------------------------------

--
-- Table structure for table `bon`
--

CREATE TABLE `bon` (
  `id_bon` int(10) NOT NULL,
  `tanggal` varchar(30) NOT NULL,
  `menu` varchar(50) NOT NULL,
  `jumlah` int(10) NOT NULL,
  `harga_satuan` int(20) NOT NULL,
  `harga_total` int(20) NOT NULL,
  `id_user` int(10) NOT NULL,
  `id_pelanggan` int(10) NOT NULL,
  `pembayaran` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bon`
--

INSERT INTO `bon` (`id_bon`, `tanggal`, `menu`, `jumlah`, `harga_satuan`, `harga_total`, `id_user`, `id_pelanggan`, `pembayaran`) VALUES
(1, 'null', '', 1, 0, 0, 9, 22, 0),
(2, 'null', '', 1, 0, 0, 9, 22, 0),
(3, '05/02/2022', 'a', 1, 1, 1, 9, 22, 0),
(4, '22/2/2022', 'Bakwan', 5, 1000, 5000, 9, 23, 0),
(5, '12/02/2022', 'bakwan', 1, 1000, 1000, 6, 26, 0),
(6, '17/02/2022', 'gorengan', 1, 1000, 1000, 9, 22, 0),
(7, '16/02/2022', 'gorengan', 1, 1000, 1000, 45, 27, 1000);

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` int(4) NOT NULL,
  `nama_pelanggan` varchar(20) NOT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `id_user` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `nama_pelanggan`, `no_hp`, `id_user`) VALUES
(2, 'Adven', '082389942256', 6),
(3, 'Ben', '123456', 6),
(4, 'Ven', '9876543210', 7),
(7, 'tes', NULL, 1),
(8, 'gea', NULL, 7),
(9, 'ani', NULL, 7),
(10, 'ventia', NULL, 7),
(11, '1', NULL, 1),
(12, '1', NULL, 1),
(13, '1', NULL, 1),
(19, '3', NULL, 1),
(20, '3', NULL, 1),
(21, 'op', NULL, 1),
(22, 'a', NULL, 9),
(23, 'ven', NULL, 9),
(24, 'ven', NULL, 9),
(26, 'tia', NULL, 6),
(27, 'apa', NULL, 45);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(4) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  `nama_kantin` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`, `nama_kantin`) VALUES
(1, 'tes', 'testing', 'testing'),
(6, 'Admin', '123Admin', 'Admin'),
(7, 'Testing', '123', 'Testing'),
(8, '"tes"', '"testing"', '"testing"'),
(9, '1', '1', 'testing'),
(10, '"testing"', '"testing"', '"testing"'),
(24, 'tia', '123', 'testing'),
(29, 'a', 'a', 'testing'),
(43, '2', '2', '3'),
(44, 'gea', 'apahayo', 'Mantap'),
(45, 'aaa', 'aaa', 'aaa');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bon`
--
ALTER TABLE `bon`
  ADD PRIMARY KEY (`id_bon`),
  ADD UNIQUE KEY `id_bon` (`id_bon`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username_2` (`username`),
  ADD KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bon`
--
ALTER TABLE `bon`
  MODIFY `id_bon` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `pelanggan`
--
ALTER TABLE `pelanggan`
  MODIFY `id_pelanggan` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD CONSTRAINT `pelanggan_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
