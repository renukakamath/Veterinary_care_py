/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - veterinary_carestanns
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`veterinary_carestanns` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `veterinary_carestanns`;

/*Table structure for table `assign_pet` */

DROP TABLE IF EXISTS `assign_pet`;

CREATE TABLE `assign_pet` (
  `assign_id` int(11) NOT NULL AUTO_INCREMENT,
  `request_id` int(11) DEFAULT NULL,
  `pets_id` int(11) DEFAULT NULL,
  `staff_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`assign_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `assign_pet` */

insert  into `assign_pet`(`assign_id`,`request_id`,`pets_id`,`staff_id`,`date`,`status`) values 
(2,1,1,2,'2023-03-20','accept');

/*Table structure for table `bookings` */

DROP TABLE IF EXISTS `bookings`;

CREATE TABLE `bookings` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `schedule_id` int(11) DEFAULT NULL,
  `pets_id` int(11) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `bookings` */

insert  into `bookings`(`book_id`,`schedule_id`,`pets_id`,`date_time`,`status`) values 
(1,1,1,'2022-01-20','pending'),
(2,2,1,'2023-01-25 10:08:47','Paid'),
(3,2,1,'2023-01-25 10:10:17','Paid'),
(4,2,1,'2023-01-26 21:19:17','Paid'),
(5,2,2,'2023-01-26 21:29:22','accept'),
(6,2,2,'2023-01-26 21:32:56','Paid'),
(7,2,2,'2023-01-26 21:41:32','Paid'),
(8,4,1,'2023-03-23 14:51:34','Paid'),
(9,1,1,'2023-03-24 10:35:38','Paid'),
(10,2,1,'2023-03-27 11:02:47','Paid'),
(11,5,1,'2023-03-30 12:34:06','Paid');

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `chat_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `sender_type` varchar(50) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `receiver_type` varchar(50) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`chat_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `chat` */

insert  into `chat`(`chat_id`,`sender_id`,`sender_type`,`receiver_id`,`receiver_type`,`message`,`date_time`) values 
(2,2,'doctor',4,'user','hiii','2023-01-24 14:05:51'),
(3,17,'user',2,'doctor','hi','2023-03-23 14:57:06'),
(4,2,'doctor',17,'user','hello','2023-03-24 10:19:59'),
(5,1,'doctor',17,'user','hiiiiii','2023-03-30 12:33:29'),
(6,17,'user',24,'doctor','hi','2023-03-30 12:34:17');

/*Table structure for table `doctors` */

DROP TABLE IF EXISTS `doctors`;

CREATE TABLE `doctors` (
  `doctor_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `Image` varchar(1000) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  `achievements` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`doctor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `doctors` */

insert  into `doctors`(`doctor_id`,`login_id`,`first_name`,`last_name`,`Image`,`phone`,`email`,`place`,`qualification`,`achievements`) values 
(7,21,'basil','tt','static/imagea24a220f-5104-45a5-8cba-b4770d652171Picture1.jpg','9979876766','anjanapandavath@gmail.com','mekkad','mbbs,md',NULL),
(8,22,'melbin','johny','static/image2d614ce7-91c6-4c7a-98a8-cc44914e46c7Picture1.jpg','9879786675','anjanapandavath@gmail.com','kalady','mbbs,md',NULL),
(9,24,'melbin','johny','static/image4fa0db8e-0fb4-4c84-b8e7-59ad7da08095Picture1.jpg','9879786675','rahulrahul19042002@gmail.com','kalady','mbbs','static/image180de48d-d076-4253-a4ea-29da5390a4f0Picture1.jpg');

/*Table structure for table `lab_tests` */

DROP TABLE IF EXISTS `lab_tests`;

CREATE TABLE `lab_tests` (
  `lab_test_id` int(11) NOT NULL AUTO_INCREMENT,
  `lab_id` int(11) DEFAULT NULL,
  `test_name` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `rate` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`lab_test_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `lab_tests` */

insert  into `lab_tests`(`lab_test_id`,`lab_id`,`test_name`,`description`,`rate`) values 
(1,1,'blood test','bp,cholestrol',70);

/*Table structure for table `laboratory` */

DROP TABLE IF EXISTS `laboratory`;

CREATE TABLE `laboratory` (
  `lab_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `lab_name` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`lab_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `laboratory` */

insert  into `laboratory`(`lab_id`,`login_id`,`lab_name`,`phone`,`email`) values 
(1,3,'neethii','7788456100','neethii@gmail.com'),
(2,6,'nakkara','1234567890','nakkara@gmail.com');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(3,'neethii@gmail.com','7788456100','lab'),
(4,'medi@gmail.com','8520369701','pharmacy'),
(24,'rahulrahul19042002@gmail.com','9879786675','doctor'),
(6,'nakkara@gmail.com','1234567890','lab'),
(7,'nakkara@gmail.com','1234567890','pharmacy'),
(10,'amala','amala','user'),
(17,'basil','basil','user'),
(12,'shop','shop','shop'),
(13,'appu','appu','staff'),
(14,'ammu','ammu','staff'),
(15,'anjana','anjana','staff'),
(23,'anjanapandavath@gmail.com','1234567890','doctor'),
(18,'anjanapandavath@gmail.com','9856789986','shop'),
(21,'anjanapandavath@gmail.com','9979876766','doctor'),
(22,'anj','123','doctor'),
(25,'anjanapandavath@gmail.com','1234567822','shop');

/*Table structure for table `medical_notes` */

DROP TABLE IF EXISTS `medical_notes`;

CREATE TABLE `medical_notes` (
  `medical_note_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`medical_note_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `medical_notes` */

insert  into `medical_notes`(`medical_note_id`,`booking_id`,`description`,`date_time`) values 
(5,1,'abcdef','2023-01-24 17:07:30'),
(4,1,'chindumon is very weak','2023-01-24 15:53:18'),
(6,5,'sample','2023-01-26 21:53:48');

/*Table structure for table `medicine_prescriptions` */

DROP TABLE IF EXISTS `medicine_prescriptions`;

CREATE TABLE `medicine_prescriptions` (
  `med_pres_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) DEFAULT NULL,
  `medicine_id` int(11) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  `med_pres_description` varchar(500) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`med_pres_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `medicine_prescriptions` */

insert  into `medicine_prescriptions`(`med_pres_id`,`book_id`,`medicine_id`,`date_time`,`med_pres_description`,`status`) values 
(2,1,2,'2023-01-24 15:31:55','qwertyuio','Accept'),
(3,1,2,'2023-01-24 17:07:09','asdfghj','Accept'),
(4,5,2,'2023-01-26 21:53:33','hello','Paid');

/*Table structure for table `medicines` */

DROP TABLE IF EXISTS `medicines`;

CREATE TABLE `medicines` (
  `medicine_id` int(11) NOT NULL AUTO_INCREMENT,
  `pharmacy_id` int(11) DEFAULT NULL,
  `medicine_name` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `expiry_date` varchar(50) DEFAULT NULL,
  `rate` decimal(10,0) DEFAULT NULL,
  `available_qty` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`medicine_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `medicines` */

insert  into `medicines`(`medicine_id`,`pharmacy_id`,`medicine_name`,`description`,`expiry_date`,`rate`,`available_qty`) values 
(2,1,'astrovastin','cholestrol 50mg','2026-07-11',60,25);

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `order` */

/*Table structure for table `patients` */

DROP TABLE IF EXISTS `patients`;

CREATE TABLE `patients` (
  `patient_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `house_name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `blood_group` varchar(50) DEFAULT NULL,
  `dob` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `patients` */

/*Table structure for table `payments` */

DROP TABLE IF EXISTS `payments`;

CREATE TABLE `payments` (
  `pay_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) DEFAULT NULL,
  `amount` decimal(20,0) DEFAULT NULL,
  `payment_date` varchar(50) DEFAULT NULL,
  `payment_type` varchar(50) DEFAULT NULL,
  `payment_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pay_id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Data for the table `payments` */

insert  into `payments`(`pay_id`,`book_id`,`amount`,`payment_date`,`payment_type`,`payment_time`) values 
(4,1,70,'NA','Medicne',NULL),
(2,1,70,'NA','Lab',NULL),
(3,1,70,'NA','Lab',NULL),
(5,2,400,'2023-01-25','Doctor',NULL),
(6,3,400,'2023-01-25','Doctor',NULL),
(7,4,400,'2023-01-26','Doctor',NULL),
(8,5,400,'2023-01-26','Doctor',NULL),
(9,6,400,'2023-01-26','Doctor',NULL),
(10,7,400,'2023-01-26','Doctor',NULL),
(11,5,5000,'2023-01-26','Medicne',NULL),
(12,5,70,'2023-01-26','Lab',NULL),
(13,8,500,'2023-03-23','Doctor',NULL),
(14,9,350,'2023-03-24','Doctor',NULL),
(15,10,400,'2023-03-27','Doctor',NULL),
(16,11,565,'2023-03-30','Doctor','12:34:06'),
(17,1,5000,'2023-03-30','vaccinebooking','21:55:25'),
(18,1,5000,'2023-03-30','vaccinebooking','21:55:46'),
(19,1,5000,'2023-03-30','vaccinebooking','21:56:27');

/*Table structure for table `pets` */

DROP TABLE IF EXISTS `pets`;

CREATE TABLE `pets` (
  `pets_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `breed` varchar(100) DEFAULT NULL,
  `age` varchar(100) DEFAULT NULL,
  `other_details` varchar(100) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `license` varchar(500) DEFAULT NULL,
  `diseases` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`pets_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `pets` */

insert  into `pets`(`pets_id`,`user_id`,`name`,`type`,`breed`,`age`,`other_details`,`image`,`license`,`diseases`) values 
(1,5,'chindumon','carnivorous mammal','Aphrodite Giant','4 months','yellow color,height:35 cm','static/uploads/2f614833-479c-4211-84a7-67633b9cbb04.jpg','static/uploads/2f614833-479c-4211-84a7-67633b9cbb04.jpg',NULL),
(2,4,'hai','hello','hai','21','hai','static/uploads/2f614833-479c-4211-84a7-67633b9cbb04.jpg','static/uploads/2f614833-479c-4211-84a7-67633b9cbb04.jpg',NULL),
(3,5,'abab','Dog','lab','3','hagvajsjVgajahsnhsjzjznsvbsjskzn','static/uploads/ee496512-0253-4daa-8b6b-a46920cfee54.jpg','static/uploads/2f614833-479c-4211-84a7-67633b9cbb04.jpg',NULL);

/*Table structure for table `pharmacy` */

DROP TABLE IF EXISTS `pharmacy`;

CREATE TABLE `pharmacy` (
  `pharmacy_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `pharmacy_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pharmacy_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `pharmacy` */

insert  into `pharmacy`(`pharmacy_id`,`login_id`,`pharmacy_name`,`email`,`phone`) values 
(1,4,'medi','medi@gmail.com','8520369701'),
(2,7,'nakkara','nakkara@gmail.com','1234567890');

/*Table structure for table `physical_conditions` */

DROP TABLE IF EXISTS `physical_conditions`;

CREATE TABLE `physical_conditions` (
  `physical_condition_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) DEFAULT NULL,
  `blood_pressure` varchar(50) DEFAULT NULL,
  `sugar` varchar(50) DEFAULT NULL,
  `cholesterol` varchar(50) DEFAULT NULL,
  `height` varchar(50) DEFAULT NULL,
  `weight` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`physical_condition_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `physical_conditions` */

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` int(11) DEFAULT NULL,
  `product` varchar(100) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `quantity` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `product` */

insert  into `product`(`product_id`,`shop_id`,`product`,`amount`,`quantity`,`date`,`image`) values 
(4,1,'aaa','500','2','2023-03-23','static/uploads/32b99ba6-115f-4428-9f45-85501511fea8Picture1.jpg'),
(5,3,'sdfuio2','40','2','2023-04-03','static/uploads/b8d78dc3-5b59-4821-99c0-1bace558e851background-images-hd-1.webp');

/*Table structure for table `productbook` */

DROP TABLE IF EXISTS `productbook`;

CREATE TABLE `productbook` (
  `productbook_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`productbook_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `productbook` */

insert  into `productbook`(`productbook_id`,`product_id`,`amount`,`date`,`status`,`user_id`) values 
(1,4,'500','2023-03-31','booked','4'),
(2,4,'500','2023-03-31','booked','4'),
(3,4,'500','2023-03-31','booked','4'),
(4,4,'500','2023-03-31','booked','4');

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `rating_id` int(11) NOT NULL AUTO_INCREMENT,
  `rating` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `review` varchar(100) DEFAULT NULL,
  `staffbooking_id` varchar(100) DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`rating_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `rating` */

insert  into `rating`(`rating_id`,`rating`,`date`,`review`,`staffbooking_id`,`user_id`) values 
(1,'3.0','2023-03-31','bdhdh','1','4');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `pets_id` int(11) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`request_id`,`pets_id`,`status`,`date`) values 
(1,1,'assigned','20-03-2015');

/*Table structure for table `schedule` */

DROP TABLE IF EXISTS `schedule`;

CREATE TABLE `schedule` (
  `schedule_id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) DEFAULT NULL,
  `start_time` varchar(50) DEFAULT NULL,
  `end_time` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `fee_amount` decimal(20,0) DEFAULT NULL,
  PRIMARY KEY (`schedule_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `schedule` */

insert  into `schedule`(`schedule_id`,`doctor_id`,`start_time`,`end_time`,`date`,`fee_amount`) values 
(1,1,'09:00','11:00','2023-01-25',350),
(2,2,'10:00','12:30','2023-01-26',400),
(3,3,'12:30','13:00','2023-03-13',500),
(4,4,'22:00','12:45','2023-03-23',500),
(5,9,'11:58','13:00','2023-04-07',565);

/*Table structure for table `shop` */

DROP TABLE IF EXISTS `shop`;

CREATE TABLE `shop` (
  `shop_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`shop_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `shop` */

insert  into `shop`(`shop_id`,`login_id`,`name`,`phone`,`email`,`latitude`,`longitude`) values 
(1,12,'anu','9979876766','anu@gmail.com',NULL,NULL),
(2,18,'bvjg','9856789986','anjanapandavath@gmail.com',NULL,NULL),
(3,25,'anu','1234567890','anjanapandavath@gmail.com','9.981414849650523','76.29755873553468');

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `staff_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `staff` */

insert  into `staff`(`staff_id`,`login_id`,`fname`,`lname`,`phone`,`email`,`type`,`latitude`,`longitude`) values 
(1,13,'appu','kuttan','7896541230','a@gmail.com','Groomers',NULL,NULL),
(2,14,'ammu','ammus','7896541023','a@gmail.com','Trainers',NULL,NULL),
(3,15,'Anjana','anil','9856789986','anjanapandavath@gmail.com','Trainers',NULL,NULL);

/*Table structure for table `staffbooking` */

DROP TABLE IF EXISTS `staffbooking`;

CREATE TABLE `staffbooking` (
  `staffbooking_id` int(11) NOT NULL AUTO_INCREMENT,
  `staff_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `user_id` int(100) DEFAULT NULL,
  PRIMARY KEY (`staffbooking_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `staffbooking` */

insert  into `staffbooking`(`staffbooking_id`,`staff_id`,`date`,`status`,`user_id`) values 
(1,1,'2023-03-31','booked',4);

/*Table structure for table `test_prescription` */

DROP TABLE IF EXISTS `test_prescription`;

CREATE TABLE `test_prescription` (
  `test_pres_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) DEFAULT NULL,
  `test_id` int(11) DEFAULT NULL,
  `lab_pres_description` varchar(500) DEFAULT NULL,
  `report_description` varchar(500) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`test_pres_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `test_prescription` */

insert  into `test_prescription`(`test_pres_id`,`book_id`,`test_id`,`lab_pres_description`,`report_description`,`date_time`,`status`) values 
(2,1,1,'zxcvb','qwertyyyyyy','2023-01-24 17:07:50','Accept'),
(3,5,1,'ok','oke','2023-01-26 21:54:02','Paid');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `house_name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`user_id`,`login_id`,`first_name`,`last_name`,`phone`,`email`,`house_name`,`place`,`latitude`,`longitude`) values 
(4,10,'amala','tk','6238526459','amala@gmail.com','streethouse','alpy','near beach',NULL),
(5,17,'basil','pv','9446359821','anjanapandavath@gmail.com','basil','hzhsvs','bahbabs',NULL);

/*Table structure for table `vaccination` */

DROP TABLE IF EXISTS `vaccination`;

CREATE TABLE `vaccination` (
  `vaccination_id` int(11) NOT NULL AUTO_INCREMENT,
  `vaccination` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `rate` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`vaccination_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `vaccination` */

insert  into `vaccination`(`vaccination_id`,`vaccination`,`date`,`rate`) values 
(1,'sdad','21-2-3123','5000');

/*Table structure for table `vaccinebooking` */

DROP TABLE IF EXISTS `vaccinebooking`;

CREATE TABLE `vaccinebooking` (
  `vaccinebooking_id` int(11) NOT NULL AUTO_INCREMENT,
  `vaccination_id` int(11) DEFAULT NULL,
  `amt` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`vaccinebooking_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `vaccinebooking` */

insert  into `vaccinebooking`(`vaccinebooking_id`,`vaccination_id`,`amt`,`date`,`user_id`) values 
(1,1,'5000','2023-03-30','4');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
