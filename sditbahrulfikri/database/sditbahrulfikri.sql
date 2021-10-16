-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 30, 2021 at 03:44 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 7.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sditbahrulfikri`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `notelp` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`, `nama`, `alamat`, `notelp`, `email`) VALUES
(10001, 'admin', '12345', 'SDIT Bahrul Fikri', 'Jl. Kecak, Depok 2 Tengah', '02177822005', 'sditbahrulfikri@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `jenispembayaran`
--

CREATE TABLE `jenispembayaran` (
  `kodepembayaran` varchar(20) NOT NULL,
  `namapembayaran` varchar(50) NOT NULL,
  `perbulan` double NOT NULL,
  `jumlahbayar` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jenispembayaran`
--

INSERT INTO `jenispembayaran` (`kodepembayaran`, `namapembayaran`, `perbulan`, `jumlahbayar`) VALUES
('001.201.999', 'SPP', 700000, 7000000),
('002.202.828', 'DSP', 0, 5000000),
('003.203.901', 'Snack Siswa', 100000, 1100000),
('004.204.998', 'Buku', 0, 1200000),
('005.205.992', 'Kegiatan Siswa', 140000, 800000);

-- --------------------------------------------------------

--
-- Table structure for table `kelas`
--

CREATE TABLE `kelas` (
  `id_kelas` varchar(10) NOT NULL,
  `namakelas` varchar(10) NOT NULL,
  `walikelas` varchar(50) NOT NULL,
  `nipguru` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kelas`
--

INSERT INTO `kelas` (`id_kelas`, `namakelas`, `walikelas`, `nipguru`) VALUES
('A001', '1', 'Nasir', '00129396642'),
('A002', 'II', 'Rama Ali', '00102293938'),
('A003', 'III', 'Siska Ramatani', '00920884936'),
('A004', 'IV', 'Nurlina', '00855200214'),
('A005', 'V', 'Ravi Putra', '00085922442'),
('A006', 'VI', 'Resti Eka Umami', '00070093772'),
('B001', '1', 'Abdul', '00125599032'),
('B002', 'II', 'Rosalia', '00109299224'),
('B003', 'III', 'Lely Lustami', '00977422821'),
('B004', 'IV', 'Fajar Saepudin', '00832002821'),
('B005', 'V', 'Alviansyah', '00740021838'),
('B006', 'VI', 'Ali Syamsudin', '00740553287'),
('C001', '1', 'Siska Siti Aisyah', '00129394043'),
('C002', 'II', 'Aji Pamungkas', '00114992512'),
('C003', 'III', 'Syariel Sinta', '00900313841'),
('C004', 'IV', 'Putri Sundari', '00883002844'),
('C005', 'V', 'Heni Lestari', '00885229022'),
('C006', 'VI', 'Muhammad Bagas', '00703845302'),
('D001', '1', 'Sulaiman syaib', '00130848482'),
('D002', 'II', 'Nurwanti', '00101992774'),
('D003', 'III', 'Vidya Anggraeni', '00948842300'),
('D004', 'IV', 'Rosyanti', '00800273341'),
('D005', 'V', 'Abu Sulaiman', '00709274731'),
('D006', 'VI', 'Aliyudin Resmana', '00700922875');

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

CREATE TABLE `pembayaran` (
  `no_pembayaran` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `id_catat` varchar(20) NOT NULL,
  `nis` varchar(20) NOT NULL,
  `namasiswa` varchar(50) NOT NULL,
  `kelas` varchar(10) NOT NULL,
  `namabayar` varchar(50) NOT NULL,
  `carapembayaran` varchar(50) NOT NULL,
  `jumlahbulan` int(11) NOT NULL,
  `keterangan` varchar(50) NOT NULL,
  `subtotal` double NOT NULL,
  `total` double NOT NULL,
  `bayar` double NOT NULL,
  `kembalian` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pembayaran`
--

INSERT INTO `pembayaran` (`no_pembayaran`, `tanggal`, `id_catat`, `nis`, `namasiswa`, `kelas`, `namabayar`, `carapembayaran`, `jumlahbulan`, `keterangan`, `subtotal`, `total`, `bayar`, `kembalian`) VALUES
(23, '2021-05-22', 'B0001', '2015008947', 'Adelia Shidiqia', 'VI', 'SPP', 'Bulanan', 4, '-', 2800000, 2800000, 3000000, 200000),
(24, '2021-05-22', 'B0002', '2015008988', 'Abimanyu Anugrah', 'VI', 'DSP', '1x Bayar', 0, '-', 5000000, 6400000, 6500000, 100000),
(25, '2021-05-22', 'B0002', '2015008988', 'Abimanyu Anugrah', 'VI', 'SPP', 'Bulanan', 2, '-', 1400000, 6400000, 6500000, 100000),
(26, '2021-05-22', 'B0003', '2016900212', 'Reza Nugraha', 'V', 'DSP', '1x Bayar', 0, '-', 5000000, 5000000, 5000000, 0),
(27, '2021-05-22', 'B0004', '20169993751', 'Freya Humaira', 'V', 'SPP', 'Bulanan', 4, '-', 2800000, 3900000, 4000000, 100000),
(28, '2021-05-22', 'B0004', '20169993751', 'Freya Humaira', 'V', 'Snack Siswa', '1x Bayar', 0, '-', 1100000, 3900000, 4000000, 100000),
(29, '2021-05-22', 'B0005', '20172391028', 'Putri Rahma Aprilia', 'IV', 'Snack Siswa', 'Bulanan', 8, '-', 800000, 3600000, 3700000, 100000),
(30, '2021-05-22', 'B0005', '20172391028', 'Putri Rahma Aprilia', 'IV', 'SPP', 'Bulanan', 4, '-', 2800000, 3600000, 3700000, 100000),
(31, '2021-05-22', 'B0006', '2017394021', 'Yudha Alifiansyah', 'IV', 'SPP', '1x Bayar', 0, '-', 7000000, 8100000, 8100000, 0),
(32, '2021-05-22', 'B0006', '2017394021', 'Yudha Alifiansyah', 'IV', 'Snack Siswa', '1x Bayar', 0, '-', 1100000, 8100000, 8100000, 0),
(33, '2021-05-22', 'B0007', '20180038208', 'Rahmawati', 'III', 'SPP', 'Bulanan', 2, '-', 1400000, 3400000, 3500000, 100000),
(34, '2021-05-22', 'B0007', '20180038208', 'Rahmawati', 'III', 'Kegiatan Siswa', '1x Bayar', 0, '-', 800000, 3400000, 3500000, 100000),
(35, '2021-05-22', 'B0007', '20180038208', 'Rahmawati', 'III', 'Buku', '1x Bayar', 0, '-', 1200000, 3400000, 3500000, 100000),
(36, '2021-05-22', 'B0008', '2018123842', 'Muhammad Rafiq', 'III', 'Snack Siswa', '1x Bayar', 0, '-', 1100000, 3300000, 3300000, 0),
(37, '2021-05-22', 'B0008', '2018123842', 'Muhammad Rafiq', 'III', 'Kegiatan Siswa', '1x Bayar', 0, '-', 800000, 3300000, 3300000, 0),
(38, '2021-05-22', 'B0008', '2018123842', 'Muhammad Rafiq', 'III', 'SPP', 'Bulanan', 2, '-', 1400000, 3300000, 3300000, 0),
(39, '2021-05-22', 'B0009', '2018213441', 'Huda Muhammad', 'III', 'SPP', '1x Bayar', 0, '-', 7000000, 8500000, 9000000, 500000),
(40, '2021-05-22', 'B0009', '2018213441', 'Huda Muhammad', 'III', 'Buku', '1x Bayar', 0, '-', 1200000, 8500000, 9000000, 500000),
(41, '2021-05-22', 'B0009', '2018213441', 'Huda Muhammad', 'III', 'Snack Siswa', 'Bulanan', 3, '-', 300000, 8500000, 9000000, 500000),
(42, '2021-05-22', 'B0010', '2019102934', 'Peony Kinasih Putri', 'II', 'DSP', '1x Bayar', 0, '-', 5000000, 8200000, 8200000, 0),
(43, '2021-05-22', 'B0010', '2019102934', 'Peony Kinasih Putri', 'II', 'Snack Siswa', '1x Bayar', 0, '-', 1100000, 8200000, 8200000, 0),
(44, '2021-05-22', 'B0010', '2019102934', 'Peony Kinasih Putri', 'II', 'SPP', 'Bulanan', 3, '-', 2100000, 8200000, 8200000, 0),
(45, '2021-05-22', 'B0011', '20193773903', 'Andara Thalita Syakieb', 'II', 'Buku', '1x Bayar', 0, '-', 1200000, 10900000, 11000000, 100000),
(46, '2021-05-22', 'B0011', '20193773903', 'Andara Thalita Syakieb', 'II', 'DSP', 'Angsuran', 0, '-', 5000000, 10900000, 11000000, 100000),
(47, '2021-05-22', 'B0011', '20193773903', 'Andara Thalita Syakieb', 'II', 'SPP', 'Bulanan', 4, '-', 2800000, 10900000, 11000000, 100000),
(48, '2021-05-22', 'B0011', '20193773903', 'Andara Thalita Syakieb', 'II', 'Kegiatan Siswa', '1x Bayar', 0, '-', 800000, 10900000, 11000000, 100000),
(49, '2021-05-22', 'B0011', '20193773903', 'Andara Thalita Syakieb', 'II', 'Snack Siswa', '1x Bayar', 0, '-', 1100000, 10900000, 11000000, 100000),
(50, '2021-05-22', 'B0012', '2020009502', 'Atha Rafka Setiawan', '1', 'SPP', '1x Bayar', 0, '-', 7000000, 10100000, 10200000, 100000),
(51, '2021-05-22', 'B0012', '2020009502', 'Atha Rafka Setiawan', '1', 'Snack Siswa', 'Angsuran', 0, '-', 1100000, 10100000, 10200000, 100000),
(52, '2021-05-22', 'B0012', '2020009502', 'Atha Rafka Setiawan', '1', 'Buku', '1x Bayar', 0, '-', 1200000, 10100000, 10200000, 100000),
(53, '2021-05-22', 'B0012', '2020009502', 'Atha Rafka Setiawan', '1', 'Kegiatan Siswa', '1x Bayar', 0, '-', 800000, 10100000, 10200000, 100000),
(54, '2021-05-22', 'B0013', '2020333559', 'Andriansyah Setya', '1', 'SPP', 'Bulanan', 6, '-', 4200000, 12300000, 12500000, 200000),
(55, '2021-05-22', 'B0013', '2020333559', 'Andriansyah Setya', '1', 'Snack Siswa', '1x Bayar', 0, '-', 1100000, 12300000, 12500000, 200000),
(56, '2021-05-22', 'B0013', '2020333559', 'Andriansyah Setya', '1', 'DSP', 'Angsuran', 0, '-', 5000000, 12300000, 12500000, 200000),
(57, '2021-05-22', 'B0013', '2020333559', 'Andriansyah Setya', '1', 'Buku', '1x Bayar', 0, '-', 1200000, 12300000, 12500000, 200000),
(58, '2021-05-22', 'B0013', '2020333559', 'Andriansyah Setya', '1', 'Kegiatan Siswa', 'Angsuran', 0, '-', 800000, 12300000, 12500000, 200000),
(59, '2021-05-30', 'B0014', '2015008947', 'Adelia Shidiqia', 'VI', 'SPP', 'Bulanan', 2, '-', 1400000, 2500000, 2500000, 0),
(60, '2021-05-30', 'B0014', '2015008947', 'Adelia Shidiqia', 'VI', 'Snack Siswa', '1x Bayar', 0, '-', 1100000, 2500000, 2500000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `pendapatannonsiswa`
--

CREATE TABLE `pendapatannonsiswa` (
  `id_catat` varchar(20) NOT NULL,
  `tanggal` date NOT NULL,
  `sumber` varchar(50) NOT NULL,
  `jumlah` double NOT NULL,
  `keterangan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pendapatannonsiswa`
--

INSERT INTO `pendapatannonsiswa` (`id_catat`, `tanggal`, `sumber`, `jumlah`, `keterangan`) VALUES
('N0001', '2020-06-09', 'Pemda Jawa Barat', 150000000, 'Dana Bos'),
('N0002', '2021-05-03', 'Dinas Pendidikan', 10000000, 'Bantuan Dana Siswa'),
('N0003', '2021-05-03', 'Pemda Jawa Barat', 30000000, 'Dana Bos-3');

-- --------------------------------------------------------

--
-- Table structure for table `pengeluaran`
--

CREATE TABLE `pengeluaran` (
  `no_transaksi` int(11) NOT NULL,
  `id_pengeluaran` varchar(11) NOT NULL,
  `tanggal` date NOT NULL,
  `keterangan` varchar(255) NOT NULL,
  `rincian` varchar(100) NOT NULL,
  `nilai` double NOT NULL,
  `jumlah` int(11) NOT NULL,
  `subtotal` double NOT NULL,
  `total` double NOT NULL,
  `penanggung_jawab` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pengeluaran`
--

INSERT INTO `pengeluaran` (`no_transaksi`, `id_pengeluaran`, `tanggal`, `keterangan`, `rincian`, `nilai`, `jumlah`, `subtotal`, `total`, `penanggung_jawab`) VALUES
(1, 'K0001', '2021-03-26', 'Kegiatan Outing', 'Penyewaan Tenda', 100000, 6, 600000, 600000, 'Heri Susanto'),
(2, 'K0001', '2021-03-26', 'Kegiatan Outing', 'Konsumsi', 1000000, 0, 0, 600000, 'Yeni Amalia'),
(9, 'K0002', '2021-03-28', 'Seminar Parenting', 'Konsumsi', 2000000, 2, 4000000, 5000000, 'Susanto'),
(10, 'K0002', '2021-03-28', 'Seminar Parenting', 'MC', 500000, 2, 1000000, 5000000, 'Dio Mulya'),
(11, 'K0003', '2021-04-01', 'Camping kelas 5', 'Penyewaan alat makan', 300000, 1, 300000, 1900000, 'Surti'),
(12, 'K0003', '2021-04-01', 'Camping kelas 5', 'Penyewaan tronton', 800000, 2, 1600000, 1900000, 'Yudhi'),
(13, 'K0004', '2021-05-30', 'Outbond', 'Konsumsi', 200000, 10, 2000000, 2300000, 'Agus'),
(14, 'K0004', '2021-05-30', 'Outbond', 'Baju Panitia', 30000, 10, 300000, 2300000, 'Fahrizal');

-- --------------------------------------------------------

--
-- Table structure for table `siswa`
--

CREATE TABLE `siswa` (
  `nis` varchar(20) NOT NULL,
  `namasiswa` varchar(100) NOT NULL,
  `tempatlahir` varchar(50) NOT NULL,
  `tgllahir` date NOT NULL,
  `jeniskelamin` varchar(10) NOT NULL,
  `agama` varchar(20) NOT NULL,
  `nohp` varchar(100) NOT NULL,
  `alamat` varchar(20) NOT NULL,
  `namakelas` varchar(10) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `siswa`
--

INSERT INTO `siswa` (`nis`, `namasiswa`, `tempatlahir`, `tgllahir`, `jeniskelamin`, `agama`, `nohp`, `alamat`, `namakelas`, `status`) VALUES
('2015008947', 'Adelia Shidiqia', 'Depok', '2010-05-04', 'Perempuan', 'Islam', '087746259924', 'Villa Kebagusan', 'VI', 'Aktif'),
('2015008988', 'Abimanyu Anugrah', 'Bekasi', '2010-08-02', 'Laki-laki', 'Islam', '08993774099', 'Jl sultan Agung', 'VI', 'Aktif'),
('2015098539', 'Sultan Haidar', 'Depok', '2009-11-30', 'Laki-laki', 'Islam', '081357005284', 'Jalan Batu Ampar II', 'VI', 'Aktif'),
('2016858580', 'Pradikta Sandik', 'Depok', '2011-03-09', 'Laki-laki', 'Islam', '081200766578', 'Jl Pangulah Utara', 'V', 'Aktif'),
('2016900212', 'Reza Nugraha', 'Jakarta', '2011-06-12', 'Laki-laki', 'Islam', '081283937718', 'Jl. Bandung', 'V', 'Aktif'),
('2016999375', 'Freya Humaira', 'Jakarta', '2011-05-11', 'Perempuan', 'Islam', '088897642215', 'Jl Palmerah biru', 'V', 'Aktif'),
('2017239108', 'Putri Rahma Aprilia', 'Jakarta', '2011-08-11', 'Perempuan', 'Islam', '081728291727', 'Jl.Anggur', 'IV', 'Aktif'),
('2017239345', 'Rama Bestari', 'Jakarta', '2011-09-22', 'Perempuan', 'Islam', '081728291727', 'Jl. tengah ponco', 'IV', 'Aktif'),
('2017394021', 'Yudha Alifiansyah', 'Depok', '2011-05-06', 'Laki-laki', 'Islam', '081328399122', 'Perum Sukamakmur', 'IV', 'Aktif'),
('2018003828', 'Rahmawati ', 'Depok', '2012-01-29', 'Perempuan', 'Islam', '0898747732', 'Perum Puri Indah', 'III', 'Aktif'),
('2018123842', 'Muhammad Rafiq', 'Bekasi', '2012-03-09', 'Laki-laki', 'Islam', '081293800291', 'Jl Makmur Jaya', 'III', 'Aktif'),
('2018213412', 'Yosi Rudiantara', 'Malang', '2012-12-04', 'Laki-laki', 'Islam', '08127277172', 'Depok Permai', 'III', 'Aktif'),
('2018213441', 'Huda Muhammad', 'Jakarta', '2013-10-15', 'Laki-laki', 'Islam', '08172882551', 'Jl. Jeruk 4', 'III', 'Aktif'),
('2018764657', 'Sheo dion ', 'Bekasi', '2013-03-04', 'Laki-laki', 'Islam', '081349027317', 'Perum Bumi Pelangi', 'III', 'Aktif'),
('2019102934', 'Peony Kinasih Putri', 'Semarang', '2012-08-17', 'Perempuan', 'Islam', '0813775022', 'Jl Otista', 'II', 'Aktif'),
('2019213410', 'Kurniawati', 'Bogor', '2012-04-05', 'Perempuan', 'Islam', '08821938192', 'Gg. Jambu', 'II', 'Aktif'),
('2019377396', 'Andara Thalita Syakieb', 'Bandung', '2014-03-15', 'Perempuan', 'Islam', '085699943023', 'Jl Raya Kota Baru', 'II', 'Aktif'),
('2020009502', 'Atha Rafka Setiawan', 'Bekasi', '2015-07-16', 'Laki-laki', 'Islam', '087753900285', 'Perum Ekamas Permai', '1', 'Aktif'),
('2020109485', 'Nadhifa Syaqueena', 'Jakarta', '2014-02-20', 'Perempuan', 'Islam', '08889337426', 'Jl Peony III', '1', 'Aktif'),
('2020333559', 'Andriansyah Setya', 'Bandung', '2015-09-16', 'Laki-laki', 'Islam', '089847277920', 'Jl Kebagusan', '1', 'Aktif');

-- --------------------------------------------------------

--
-- Table structure for table `suratkeluar`
--

CREATE TABLE `suratkeluar` (
  `noagenda` varchar(20) NOT NULL,
  `tgl_surat` date NOT NULL,
  `nosurat` varchar(50) NOT NULL,
  `pengirim` varchar(100) NOT NULL,
  `kpd` varchar(100) NOT NULL,
  `isi` varchar(100) NOT NULL,
  `lampiran` varchar(10) NOT NULL,
  `ket` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `suratkeluar`
--

INSERT INTO `suratkeluar` (`noagenda`, `tgl_surat`, `nosurat`, `pengirim`, `kpd`, `isi`, `lampiran`, `ket`) VALUES
('L0001', '2020-06-13', '002/DL/6/2020', 'SDIT Bahrul Fikri', 'SDIT Dahrul Salam', 'Dialog Lingkungan', '2', '-'),
('L0002', '2020-06-20', '003/RK/6/2020', 'SDIT Bahrul Fikri', 'Dinas Pendidikan Depok', 'Rapat Kerja', '3', '-'),
('L0003', '2020-06-10', '004/GF/6/2020', 'Panitia Gathering', 'Komite Sekolah', 'Family Gathering', '1', '-'),
('L0004', '2021-05-01', '005/YZ/5/2021', 'SDIT Bahrul Fikri', 'Yayasan Bahrul \'Ulum', 'Rapat Agenda Sekolah', '-', ''),
('L0005', '2021-05-03', '006/CK/5/2021', 'SDIT Bahrul Fikri', 'Dinas Kesehatan', 'Daftar Pegawai untuk Vaksinasi', '5', '');

-- --------------------------------------------------------

--
-- Table structure for table `suratmasuk`
--

CREATE TABLE `suratmasuk` (
  `noagenda` varchar(20) NOT NULL,
  `tgl_surat` date NOT NULL,
  `nosurat` varchar(50) NOT NULL,
  `pengirim` varchar(100) NOT NULL,
  `tgl_terima` date NOT NULL,
  `kpd` varchar(100) NOT NULL,
  `isi` varchar(100) NOT NULL,
  `lampiran` varchar(10) NOT NULL,
  `ket` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `suratmasuk`
--

INSERT INTO `suratmasuk` (`noagenda`, `tgl_surat`, `nosurat`, `pengirim`, `tgl_terima`, `kpd`, `isi`, `lampiran`, `ket`) VALUES
('M0001', '2020-06-01', '001/H/6/2020', 'Kemendikbud', '2020-06-09', 'SDIT Bahrul Fikri', 'Rapat Nasional', '1', '-'),
('M0002', '2020-06-02', '150/JK/6/2020', 'SDN 06 Pagi', '2020-06-09', 'Kepala Sekolah', 'Diskusi Guru', '-', '-'),
('M0003', '2020-06-06', '005/RG/6/2020', 'Dinas Pendidikan Jawa Barat', '2020-06-09', 'SDIT Bahrul Fikri', 'Rapat Guru', '3', 'Ditunggu konfimasi kedatangan'),
('M0004', '2021-03-20', '007/23/MRT/2021', 'Dinas Pendidikan Jawa Barat', '2021-03-28', 'Kepala Sekolah', 'Seminar Pimpinan', '-', '-'),
('M0005', '2021-05-01', '002/K/2021', 'Walikota Depok', '2021-05-03', 'SDIT Bahrul Fikri', 'Undangan Buka Bersama', '-', 'Ditunggu konfirmasi'),
('M0006', '2021-05-02', '003/MS/MEI/2021', 'Dinas Kesehatan', '2021-05-03', 'SDIT Bahrul Fikri', 'Vaksinasi Covid-19', '2', 'Penting'),
('M0007', '2021-05-03', '004/HY/MEI/2021', 'Ketua PGRI', '2021-05-03', 'Kepala Sekolah', 'Webinar Sistem Pendidikan', '1', '-');

-- --------------------------------------------------------

--
-- Table structure for table `tmp_pembayaran`
--

CREATE TABLE `tmp_pembayaran` (
  `id_tmp` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `id_catat` varchar(20) NOT NULL,
  `nis` varchar(20) NOT NULL,
  `namasiswa` varchar(50) NOT NULL,
  `kelas` varchar(10) NOT NULL,
  `namabayar` varchar(50) NOT NULL,
  `carapembayaran` varchar(20) NOT NULL,
  `jumlahbulan` int(11) NOT NULL,
  `keterangan` varchar(50) NOT NULL,
  `subtotal` double NOT NULL,
  `bayar` double NOT NULL,
  `kembalian` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tmp_pengeluaran`
--

CREATE TABLE `tmp_pengeluaran` (
  `id_tmp` int(11) NOT NULL,
  `id_pengeluaran` varchar(20) NOT NULL,
  `tanggal` date NOT NULL,
  `keterangan` varchar(255) NOT NULL,
  `rincian` varchar(100) NOT NULL,
  `nilai` double NOT NULL,
  `jumlah` double NOT NULL,
  `subtotal` double NOT NULL,
  `total` double NOT NULL,
  `penanggung_jawab` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jenispembayaran`
--
ALTER TABLE `jenispembayaran`
  ADD PRIMARY KEY (`kodepembayaran`);

--
-- Indexes for table `kelas`
--
ALTER TABLE `kelas`
  ADD PRIMARY KEY (`id_kelas`);

--
-- Indexes for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`no_pembayaran`);

--
-- Indexes for table `pendapatannonsiswa`
--
ALTER TABLE `pendapatannonsiswa`
  ADD PRIMARY KEY (`id_catat`);

--
-- Indexes for table `pengeluaran`
--
ALTER TABLE `pengeluaran`
  ADD PRIMARY KEY (`no_transaksi`);

--
-- Indexes for table `siswa`
--
ALTER TABLE `siswa`
  ADD PRIMARY KEY (`nis`);

--
-- Indexes for table `suratkeluar`
--
ALTER TABLE `suratkeluar`
  ADD PRIMARY KEY (`noagenda`);

--
-- Indexes for table `suratmasuk`
--
ALTER TABLE `suratmasuk`
  ADD PRIMARY KEY (`noagenda`);

--
-- Indexes for table `tmp_pembayaran`
--
ALTER TABLE `tmp_pembayaran`
  ADD PRIMARY KEY (`id_tmp`);

--
-- Indexes for table `tmp_pengeluaran`
--
ALTER TABLE `tmp_pengeluaran`
  ADD PRIMARY KEY (`id_tmp`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `no_pembayaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT for table `pengeluaran`
--
ALTER TABLE `pengeluaran`
  MODIFY `no_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `tmp_pembayaran`
--
ALTER TABLE `tmp_pembayaran`
  MODIFY `id_tmp` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tmp_pengeluaran`
--
ALTER TABLE `tmp_pengeluaran`
  MODIFY `id_tmp` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
