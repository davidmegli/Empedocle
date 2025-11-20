-- MySQL dump 10.13  Distrib 8.0.43, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: emp_db
-- ------------------------------------------------------
-- Server version	8.0.43-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `agendas`
--

CREATE DATABASE emp_db;
USE emp_db;

DROP TABLE IF EXISTS `agendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agendas` (
  `id` bigint NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` longtext,
  `uuid` varchar(255) DEFAULT NULL,
  `measurement_session_type_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_f5axk5xrjmk4m5pcweer89r81` (`code`),
  KEY `FKh5qta4igiqacn4o4v9m7jyukj` (`measurement_session_type_id`),
  CONSTRAINT `FKh5qta4igiqacn4o4v9m7jyukj` FOREIGN KEY (`measurement_session_type_id`) REFERENCES `measurement_session_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agendas`
--

LOCK TABLES `agendas` WRITE;
/*!40000 ALTER TABLE `agendas` DISABLE KEYS */;
INSERT INTO `agendas` VALUES (1,'A-01','Agenda standard for Modulus of Elasticity Test.','6e7473b3-093e-4785-b32b-dc6937447519',1),(2,'A-02','Agenda standard for Aesthetic Grading Test.','a1b37837-f3a7-442d-875e-8942f7f7a823',2);
/*!40000 ALTER TABLE `agendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authorizations`
--

DROP TABLE IF EXISTS `authorizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorizations` (
  `id` bigint NOT NULL,
  `measurementSessionOperation` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `qualification_id` bigint NOT NULL,
  `authorization_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd1c0q27j53o43u8dbvpjghwvk` (`qualification_id`),
  KEY `FKoehh3bofmpnly4eskbu1nwisk` (`authorization_id`),
  CONSTRAINT `FKd1c0q27j53o43u8dbvpjghwvk` FOREIGN KEY (`qualification_id`) REFERENCES `qualifications` (`id`),
  CONSTRAINT `FKoehh3bofmpnly4eskbu1nwisk` FOREIGN KEY (`authorization_id`) REFERENCES `measurement_session_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorizations`
--

LOCK TABLES `authorizations` WRITE;
/*!40000 ALTER TABLE `authorizations` DISABLE KEYS */;
INSERT INTO `authorizations` VALUES (1,'END_EXAMINATION','a2b2c2d2-e1f21-3g4h-5i6j-7k8l9m0n1o2p',1,1),(2,'END_EXAMINATION','h1g1f1e1-e3f3-3g4h-5i6j-7k8l9m0n1o2p',1,2);
/*!40000 ALTER TABLE `authorizations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conditional_clear_selectors`
--

DROP TABLE IF EXISTS `conditional_clear_selectors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conditional_clear_selectors` (
  `conditional_id` bigint NOT NULL,
  `selector_id` bigint NOT NULL,
  UNIQUE KEY `UK_a6bq9v3rdwq09ksbddfattbil` (`selector_id`),
  KEY `FKjjtpxfx4nh1pqbpjjtdt5vs94` (`conditional_id`),
  CONSTRAINT `FKb5go3120c39kotvinhbce0auw` FOREIGN KEY (`selector_id`) REFERENCES `selectors` (`id`),
  CONSTRAINT `FKjjtpxfx4nh1pqbpjjtdt5vs94` FOREIGN KEY (`conditional_id`) REFERENCES `viewers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conditional_clear_selectors`
--

LOCK TABLES `conditional_clear_selectors` WRITE;
/*!40000 ALTER TABLE `conditional_clear_selectors` DISABLE KEYS */;
/*!40000 ALTER TABLE `conditional_clear_selectors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conditional_operators`
--

DROP TABLE IF EXISTS `conditional_operators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conditional_operators` (
  `id` bigint NOT NULL,
  `operators` int DEFAULT NULL,
  KEY `FK3fdek6ka276m1g8bi65co3ity` (`id`),
  CONSTRAINT `FK3fdek6ka276m1g8bi65co3ity` FOREIGN KEY (`id`) REFERENCES `viewers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conditional_operators`
--

LOCK TABLES `conditional_operators` WRITE;
/*!40000 ALTER TABLE `conditional_operators` DISABLE KEYS */;
/*!40000 ALTER TABLE `conditional_operators` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conditional_phenomena`
--

DROP TABLE IF EXISTS `conditional_phenomena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conditional_phenomena` (
  `conditional_id` bigint NOT NULL,
  `phenomenon_id` bigint NOT NULL,
  KEY `FK43tpp2ab464x2f9wlwa980tre` (`phenomenon_id`),
  KEY `FKsyhhynoc7g3p203mbekd69t54` (`conditional_id`),
  CONSTRAINT `FK43tpp2ab464x2f9wlwa980tre` FOREIGN KEY (`phenomenon_id`) REFERENCES `phenomena` (`id`),
  CONSTRAINT `FKsyhhynoc7g3p203mbekd69t54` FOREIGN KEY (`conditional_id`) REFERENCES `viewers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conditional_phenomena`
--

LOCK TABLES `conditional_phenomena` WRITE;
/*!40000 ALTER TABLE `conditional_phenomena` DISABLE KEYS */;
/*!40000 ALTER TABLE `conditional_phenomena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conditional_selectors`
--

DROP TABLE IF EXISTS `conditional_selectors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conditional_selectors` (
  `conditional_id` bigint NOT NULL,
  `selector_id` bigint NOT NULL,
  UNIQUE KEY `UK_8lpprr3pumn63cc8dlcmdfx9w` (`selector_id`),
  KEY `FKi79n3x87utv9ijb87tc1vu1wm` (`conditional_id`),
  CONSTRAINT `FK8skbiox83ikx8jo6s503897x5` FOREIGN KEY (`selector_id`) REFERENCES `selectors` (`id`),
  CONSTRAINT `FKi79n3x87utv9ijb87tc1vu1wm` FOREIGN KEY (`conditional_id`) REFERENCES `viewers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conditional_selectors`
--

LOCK TABLES `conditional_selectors` WRITE;
/*!40000 ALTER TABLE `conditional_selectors` DISABLE KEYS */;
/*!40000 ALTER TABLE `conditional_selectors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expression_ancestors`
--

DROP TABLE IF EXISTS `expression_ancestors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expression_ancestors` (
  `expression_id` bigint NOT NULL,
  `ancestor_expression_id` bigint NOT NULL,
  PRIMARY KEY (`expression_id`,`ancestor_expression_id`),
  KEY `FKfw8aj1ar2fqjpn72orm7fmr6l` (`ancestor_expression_id`),
  CONSTRAINT `FKfw8aj1ar2fqjpn72orm7fmr6l` FOREIGN KEY (`ancestor_expression_id`) REFERENCES `expressions` (`id`),
  CONSTRAINT `FKm7b6uuqyh6plpevdp8fwrm1g9` FOREIGN KEY (`expression_id`) REFERENCES `expressions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expression_ancestors`
--

LOCK TABLES `expression_ancestors` WRITE;
/*!40000 ALTER TABLE `expression_ancestors` DISABLE KEYS */;
/*!40000 ALTER TABLE `expression_ancestors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expression_links`
--

DROP TABLE IF EXISTS `expression_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expression_links` (
  `id` bigint NOT NULL,
  `priority` bigint DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `source_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp21gygdwwopw34o7f0hmiyoqd` (`source_id`),
  KEY `FK4n6nmlhpgsvlo37ysuk1ah3tj` (`target_id`),
  CONSTRAINT `FK4n6nmlhpgsvlo37ysuk1ah3tj` FOREIGN KEY (`target_id`) REFERENCES `expressions` (`id`),
  CONSTRAINT `FKp21gygdwwopw34o7f0hmiyoqd` FOREIGN KEY (`source_id`) REFERENCES `expressions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expression_links`
--

LOCK TABLES `expression_links` WRITE;
/*!40000 ALTER TABLE `expression_links` DISABLE KEYS */;
/*!40000 ALTER TABLE `expression_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expressions`
--

DROP TABLE IF EXISTS `expressions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expressions` (
  `from_class` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `parameter_id` bigint DEFAULT NULL,
  `type_link_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg6hatcngwptbg56i0bowgyyod` (`parameter_id`),
  KEY `FKf96neevtw3bw18lhokpyaues3` (`type_link_id`),
  CONSTRAINT `FKf96neevtw3bw18lhokpyaues3` FOREIGN KEY (`type_link_id`) REFERENCES `type_links` (`id`),
  CONSTRAINT `FKg6hatcngwptbg56i0bowgyyod` FOREIGN KEY (`parameter_id`) REFERENCES `fact_values` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expressions`
--

LOCK TABLES `expressions` WRITE;
/*!40000 ALTER TABLE `expressions` DISABLE KEYS */;
/*!40000 ALTER TABLE `expressions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fact_actions`
--

DROP TABLE IF EXISTS `fact_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fact_actions` (
  `from_class` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `action_time` datetime(6) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  `source_id` bigint DEFAULT NULL,
  `source1_id` bigint DEFAULT NULL,
  `source2_id` bigint DEFAULT NULL,
  `target1_id` bigint DEFAULT NULL,
  `target2_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt4tlyj893lxhra3d174edqn87` (`author_id`),
  KEY `FKkjont54y9tcy0xau4d6l7w6wi` (`target_id`),
  KEY `FKpyc6ijbhtym1e09lt15f2g98s` (`source_id`),
  KEY `FK4b19u33pg9t17ix555hhi0d9` (`source1_id`),
  KEY `FKlhs55bu06idauwv8w63x7q6jg` (`source2_id`),
  KEY `FKkjfw30noduc7nq9qa552g8wu` (`target1_id`),
  KEY `FKf72r2edk8mrmnnpuqjtnfuf96` (`target2_id`),
  CONSTRAINT `FK4b19u33pg9t17ix555hhi0d9` FOREIGN KEY (`source1_id`) REFERENCES `facts` (`id`),
  CONSTRAINT `FKf72r2edk8mrmnnpuqjtnfuf96` FOREIGN KEY (`target2_id`) REFERENCES `facts` (`id`),
  CONSTRAINT `FKkjfw30noduc7nq9qa552g8wu` FOREIGN KEY (`target1_id`) REFERENCES `facts` (`id`),
  CONSTRAINT `FKkjont54y9tcy0xau4d6l7w6wi` FOREIGN KEY (`target_id`) REFERENCES `facts` (`id`),
  CONSTRAINT `FKlhs55bu06idauwv8w63x7q6jg` FOREIGN KEY (`source2_id`) REFERENCES `facts` (`id`),
  CONSTRAINT `FKpyc6ijbhtym1e09lt15f2g98s` FOREIGN KEY (`source_id`) REFERENCES `facts` (`id`),
  CONSTRAINT `FKt4tlyj893lxhra3d174edqn87` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fact_actions`
--

LOCK TABLES `fact_actions` WRITE;
/*!40000 ALTER TABLE `fact_actions` DISABLE KEYS */;
/*!40000 ALTER TABLE `fact_actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fact_ancestors`
--

DROP TABLE IF EXISTS `fact_ancestors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fact_ancestors` (
  `fact_id` bigint NOT NULL,
  `ancestor_fact_id` bigint NOT NULL,
  PRIMARY KEY (`fact_id`,`ancestor_fact_id`),
  KEY `FKnrv0l0fv48qa32gb9850dg042` (`ancestor_fact_id`),
  CONSTRAINT `FK6kxuuur32nhnvihpowyq45ofg` FOREIGN KEY (`fact_id`) REFERENCES `facts` (`id`),
  CONSTRAINT `FKnrv0l0fv48qa32gb9850dg042` FOREIGN KEY (`ancestor_fact_id`) REFERENCES `facts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fact_ancestors`
--

LOCK TABLES `fact_ancestors` WRITE;
/*!40000 ALTER TABLE `fact_ancestors` DISABLE KEYS */;
/*!40000 ALTER TABLE `fact_ancestors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fact_before`
--

DROP TABLE IF EXISTS `fact_before`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fact_before` (
  `fact_id` bigint NOT NULL,
  `before_fact_id` bigint NOT NULL,
  PRIMARY KEY (`fact_id`,`before_fact_id`),
  KEY `FKfmfbng243irjx3kr3ixwxykmf` (`before_fact_id`),
  CONSTRAINT `FKfmfbng243irjx3kr3ixwxykmf` FOREIGN KEY (`before_fact_id`) REFERENCES `facts` (`id`),
  CONSTRAINT `FKibbvyl5ae080pks6l8gh920k1` FOREIGN KEY (`fact_id`) REFERENCES `facts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fact_before`
--

LOCK TABLES `fact_before` WRITE;
/*!40000 ALTER TABLE `fact_before` DISABLE KEYS */;
/*!40000 ALTER TABLE `fact_before` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fact_context`
--

DROP TABLE IF EXISTS `fact_context`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fact_context` (
  `from_class` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `last_update` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `was_done` bit(1) DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  `survey_schedule_id` bigint DEFAULT NULL,
  `measurement_session_type_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi0twlsid6hkkoy9aq19qumbx2` (`author_id`),
  KEY `FK198rs7i73b9yt2rxxpedms1ca` (`survey_schedule_id`),
  KEY `FK818c67nj1g1loqlwew1p8k1nb` (`measurement_session_type_id`),
  CONSTRAINT `FK198rs7i73b9yt2rxxpedms1ca` FOREIGN KEY (`survey_schedule_id`) REFERENCES `survey_schedules` (`id`),
  CONSTRAINT `FK818c67nj1g1loqlwew1p8k1nb` FOREIGN KEY (`measurement_session_type_id`) REFERENCES `measurement_session_types` (`id`),
  CONSTRAINT `FKi0twlsid6hkkoy9aq19qumbx2` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fact_context`
--

LOCK TABLES `fact_context` WRITE;
/*!40000 ALTER TABLE `fact_context` DISABLE KEYS */;
INSERT INTO `fact_context` VALUES ('EX',1,'bc975df7-6c90-41c9-aea3-5f0c2b7f247f',0,'2025-11-20 16:05:00.000000','RUNNING',_binary '\0',NULL,1,1);
/*!40000 ALTER TABLE `fact_context` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fact_links`
--

DROP TABLE IF EXISTS `fact_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fact_links` (
  `from_class` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `priority` bigint DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `source_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `refers_to_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgkiyc6jub82n2kirhas03tu44` (`source_id`),
  KEY `FKi5e421hfyo1u0hc4wmkmdkid6` (`target_id`),
  KEY `FKdon6ywmmvxwd3euprofsh582v` (`type_id`),
  KEY `FKpgc1sw151qe7l61flg9vnuwxw` (`refers_to_id`),
  CONSTRAINT `FKdon6ywmmvxwd3euprofsh582v` FOREIGN KEY (`type_id`) REFERENCES `type_links` (`id`),
  CONSTRAINT `FKgkiyc6jub82n2kirhas03tu44` FOREIGN KEY (`source_id`) REFERENCES `facts` (`id`),
  CONSTRAINT `FKi5e421hfyo1u0hc4wmkmdkid6` FOREIGN KEY (`target_id`) REFERENCES `facts` (`id`),
  CONSTRAINT `FKpgc1sw151qe7l61flg9vnuwxw` FOREIGN KEY (`refers_to_id`) REFERENCES `fact_links` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fact_links`
--

LOCK TABLES `fact_links` WRITE;
/*!40000 ALTER TABLE `fact_links` DISABLE KEYS */;
/*!40000 ALTER TABLE `fact_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fact_queries`
--

DROP TABLE IF EXISTS `fact_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fact_queries` (
  `id` bigint NOT NULL,
  `description` longtext,
  `query_fetch` bit(1) DEFAULT NULL,
  `result_limit` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `result_offset` int DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `expression_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8pbqiitjdxj4i1vas7cdrtf45` (`name`),
  KEY `FKqbqmwj0ma2pmm1qak9i2fsd9q` (`expression_id`),
  CONSTRAINT `FKqbqmwj0ma2pmm1qak9i2fsd9q` FOREIGN KEY (`expression_id`) REFERENCES `expressions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fact_queries`
--

LOCK TABLES `fact_queries` WRITE;
/*!40000 ALTER TABLE `fact_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `fact_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fact_values`
--

DROP TABLE IF EXISTS `fact_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fact_values` (
  `from_class` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `use_today` bit(1) DEFAULT NULL,
  `text` longtext,
  `qt_value` double DEFAULT NULL,
  `phen_id` bigint DEFAULT NULL,
  `qt_unit` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKofnfkoth9jrl2or80gxqs7lr0` (`phen_id`),
  KEY `FK2ikly3ohmrmuqwivwfqoft22r` (`qt_unit`),
  CONSTRAINT `FK2ikly3ohmrmuqwivwfqoft22r` FOREIGN KEY (`qt_unit`) REFERENCES `units` (`id`),
  CONSTRAINT `FKofnfkoth9jrl2or80gxqs7lr0` FOREIGN KEY (`phen_id`) REFERENCES `phenomena` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fact_values`
--

LOCK TABLES `fact_values` WRITE;
/*!40000 ALTER TABLE `fact_values` DISABLE KEYS */;
/*!40000 ALTER TABLE `fact_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facts`
--

DROP TABLE IF EXISTS `facts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facts` (
  `from_class` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `text` longtext,
  `date` datetime(6) DEFAULT NULL,
  `qt_value` double DEFAULT NULL,
  `context_id` bigint DEFAULT NULL,
  `dest_id` bigint DEFAULT NULL,
  `origin_id` bigint DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `phen_id` bigint DEFAULT NULL,
  `qt_unit` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3rvkr8iyhvxh63w27lxqdk2b3` (`context_id`),
  KEY `FKel1fwj7e1xv5u7rjoppnypab8` (`dest_id`),
  KEY `FK5u8fd8gea9fkwbu7n6c58hoah` (`origin_id`),
  KEY `FK8adwgadoy0ncv32sldxvm9ce9` (`type_id`),
  KEY `FKdcr22rcjd6o3q3iafdde7i857` (`phen_id`),
  KEY `FKg0u1tu6nhcs1x6146h03xjeb0` (`qt_unit`),
  CONSTRAINT `FK3rvkr8iyhvxh63w27lxqdk2b3` FOREIGN KEY (`context_id`) REFERENCES `fact_context` (`id`),
  CONSTRAINT `FK5u8fd8gea9fkwbu7n6c58hoah` FOREIGN KEY (`origin_id`) REFERENCES `fact_actions` (`id`),
  CONSTRAINT `FK8adwgadoy0ncv32sldxvm9ce9` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`),
  CONSTRAINT `FKdcr22rcjd6o3q3iafdde7i857` FOREIGN KEY (`phen_id`) REFERENCES `phenomena` (`id`),
  CONSTRAINT `FKel1fwj7e1xv5u7rjoppnypab8` FOREIGN KEY (`dest_id`) REFERENCES `fact_actions` (`id`),
  CONSTRAINT `FKg0u1tu6nhcs1x6146h03xjeb0` FOREIGN KEY (`qt_unit`) REFERENCES `units` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facts`
--

LOCK TABLES `facts` WRITE;
/*!40000 ALTER TABLE `facts` DISABLE KEYS */;
/*!40000 ALTER TABLE `facts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_agendas`
--

DROP TABLE IF EXISTS `favorite_agendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite_agendas` (
  `staff_id` bigint NOT NULL,
  `agenda_id` bigint NOT NULL,
  PRIMARY KEY (`staff_id`,`agenda_id`),
  KEY `FKnoi2rvcht1dpu9en0yc60lk4t` (`agenda_id`),
  CONSTRAINT `FKex62lk8mcnm3cvea865x7mwnr` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`),
  CONSTRAINT `FKnoi2rvcht1dpu9en0yc60lk4t` FOREIGN KEY (`agenda_id`) REFERENCES `agendas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_agendas`
--

LOCK TABLES `favorite_agendas` WRITE;
/*!40000 ALTER TABLE `favorite_agendas` DISABLE KEYS */;
INSERT INTO `favorite_agendas` VALUES (1,1);
/*!40000 ALTER TABLE `favorite_agendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `measurement_session_types`
--

DROP TABLE IF EXISTS `measurement_session_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `measurement_session_types` (
  `id` bigint NOT NULL,
  `description` longtext,
  `name` varchar(255) DEFAULT NULL,
  `ttl` int DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qnod1k7e6fw8mnwyea4o02cw2` (`name`),
  KEY `FKjlks6ajynxmyl9iehhivlfjfg` (`type_id`),
  CONSTRAINT `FKjlks6ajynxmyl9iehhivlfjfg` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `measurement_session_types`
--

LOCK TABLES `measurement_session_types` WRITE;
/*!40000 ALTER TABLE `measurement_session_types` DISABLE KEYS */;
INSERT INTO `measurement_session_types` VALUES (1,'Standard test protocol the compute Modulus of Elasticity','Modulus of Elasticity Test',7776000,'a5b4c3d2-e1f0-9876-5432-10fedcba9876',2),(2,'Standard test to check Aesthetic Grading','Aesthetic Grading Protocol',31536000,'e2d3c4b5-1f0e-9d8c-7b6a-54e3d2c1b0a8',1);
/*!40000 ALTER TABLE `measurement_session_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `id` bigint NOT NULL,
  `body` longtext,
  `date` datetime(6) DEFAULT NULL,
  `read` bit(1) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `observable_entity_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrgwj6b013uosqx1kgir5vweto` (`observable_entity_id`),
  CONSTRAINT `FKrgwj6b013uosqx1kgir5vweto` FOREIGN KEY (`observable_entity_id`) REFERENCES `observable_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (1,'body1','2025-06-29 02:00:00.000000',_binary '','INFO','sender1','subject1','a4f693cf-4845-4417-8492-82ccb938267b',1);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `observable_entity`
--

DROP TABLE IF EXISTS `observable_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `observable_entity` (
  `entity_type` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `external_element_id` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `place_of_origin` varchar(255) DEFAULT NULL,
  `specie` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `dest_id` bigint DEFAULT NULL,
  `identifier_id` bigint DEFAULT NULL,
  `origin_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1lhatqp5a2o9lwigcju6ax1vy` (`dest_id`),
  KEY `FK4p0v5soo305x0ww2vx5gu4p0h` (`identifier_id`),
  KEY `FK3j5dju9kkr9uq1ixrjyimihir` (`origin_id`),
  CONSTRAINT `FK1lhatqp5a2o9lwigcju6ax1vy` FOREIGN KEY (`dest_id`) REFERENCES `wood_element_actions` (`id`),
  CONSTRAINT `FK3j5dju9kkr9uq1ixrjyimihir` FOREIGN KEY (`origin_id`) REFERENCES `wood_element_actions` (`id`),
  CONSTRAINT `FK4p0v5soo305x0ww2vx5gu4p0h` FOREIGN KEY (`identifier_id`) REFERENCES `observable_entity_identifier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `observable_entity`
--

LOCK TABLES `observable_entity` WRITE;
/*!40000 ALTER TABLE `observable_entity` DISABLE KEYS */;
INSERT INTO `observable_entity` VALUES ('WoodElement',1,NULL,NULL,'05aa0faa-99f1-4141-89ed-1412fc58cb8b',160,'FR-FOR-9921','Standing tree identified for harvest next season, straight trunk, high tannin content.','France','Quercus petraea','Tree',NULL,1,1),('WoodElement',2,NULL,NULL,'77fd0a7b-96a1-4a2e-a757-bf24a57c13ff',45,'SE-PINE-KD-04','Kiln-dried boards, construction grade C24, planed all round.','Sweden','Pinus sylvestris','Sawn_Timber',NULL,2,2),('WoodElement',3,NULL,NULL,'35970952-5605-4e7f-b280-450661a5d80d',200,'IT-ANT-BEAM-X','Salvaged beams from an 18th-century farmhouse, rustic patina, cleaned and brushed.','Italy','Castanea sativa','Reclaimed_Wood',NULL,3,3),('WoodElement',4,NULL,NULL,'39a5cf7c-f1cc-4191-8469-8e675d7e2e0b',50,'AT-LARCH-P-77','Peeled round post, naturally rot-resistant, suitable for outdoor fencing.','Austria','Larix decidua','Pole',NULL,4,4),('WoodElement',5,NULL,NULL,'c6b9185b-49c1-428a-ba20-2ebf005281fa',80,'US-WAL-VEN-01','Prime veneer quality log, dark chocolate color, minimal sapwood.','USA','Juglans nigra','Log',NULL,5,5),('WoodElement',6,NULL,NULL,'a663822e-d628-4ac4-aa6d-8b8c5de1582a',35,'FI-BIRCH-S-22','Harvested stem, debarked, destined for plywood production.','Finland','Betula pendula','Stem',7,6,6),('WoodElement',7,NULL,NULL,'6309b38d-6c64-4c93-a719-76ee955bc338',40,'FI-BIRCH-S-22','Harvested stem, debarked, destined for plywood production.','France','Betula pendula','Stem',NULL,6,7);
/*!40000 ALTER TABLE `observable_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `observable_entity_before`
--

DROP TABLE IF EXISTS `observable_entity_before`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `observable_entity_before` (
  `observable_entity_id` bigint NOT NULL,
  `before_observable_entity_id` bigint NOT NULL,
  PRIMARY KEY (`observable_entity_id`,`before_observable_entity_id`),
  KEY `FK8ho3sgefciwhta3ny18yctdjx` (`before_observable_entity_id`),
  CONSTRAINT `FK89f0kwt8vifcc9am84j31mdph` FOREIGN KEY (`observable_entity_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FK8ho3sgefciwhta3ny18yctdjx` FOREIGN KEY (`before_observable_entity_id`) REFERENCES `observable_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `observable_entity_before`
--

LOCK TABLES `observable_entity_before` WRITE;
/*!40000 ALTER TABLE `observable_entity_before` DISABLE KEYS */;
INSERT INTO `observable_entity_before` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,6),(7,7);
/*!40000 ALTER TABLE `observable_entity_before` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `observable_entity_identifier`
--

DROP TABLE IF EXISTS `observable_entity_identifier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `observable_entity_identifier` (
  `entity_type` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `code` varchar(255) NOT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2xety340lv9cloniyext68dxe` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `observable_entity_identifier`
--

LOCK TABLES `observable_entity_identifier` WRITE;
/*!40000 ALTER TABLE `observable_entity_identifier` DISABLE KEYS */;
INSERT INTO `observable_entity_identifier` VALUES ('WoodElementIdentifier',1,'2001','5ed9c50c-b5fd-42b4-8ead-0601ebe3c064',0),('WoodElementIdentifier',2,'2002','d4759d0b-93ae-47b1-864f-7601a2edaa47',0),('WoodElementIdentifier',3,'2003','0de708c0-aa5a-4f28-bc32-ebdec9acd437',0),('WoodElementIdentifier',4,'2004','fba2d83c-36fc-4fd3-86ba-197d90cb747c',0),('WoodElementIdentifier',5,'2005','f5fdcaea-4814-4cc9-a6b6-df758f39e7f4',0),('WoodElementIdentifier',6,'2006','19a0cbe2-f613-4dd6-b46e-15e65dcc0965',0);
/*!40000 ALTER TABLE `observable_entity_identifier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phenomena`
--

DROP TABLE IF EXISTS `phenomena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phenomena` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` int DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phenomena`
--

LOCK TABLES `phenomena` WRITE;
/*!40000 ALTER TABLE `phenomena` DISABLE KEYS */;
/*!40000 ALTER TABLE `phenomena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qualifications`
--

DROP TABLE IF EXISTS `qualifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qualifications` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2u92exntq90ow55bcmddc9u3d` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qualifications`
--

LOCK TABLES `qualifications` WRITE;
/*!40000 ALTER TABLE `qualifications` DISABLE KEYS */;
INSERT INTO `qualifications` VALUES (1,'wood_engineer','2025-11-19 18:15:49.000000','2099-11-19 18:15:37.000000','a1b1c1d1-e232-3g4t-5i9j-7k8l9m0n1o2a');
/*!40000 ALTER TABLE `qualifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `selectors`
--

DROP TABLE IF EXISTS `selectors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `selectors` (
  `id` bigint NOT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `next_id` bigint DEFAULT NULL,
  `root_id` bigint DEFAULT NULL,
  `type_link_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5dse77wvdtih1qj4tqp5stpiu` (`next_id`),
  KEY `FKbi0h7t37k4p92rijsphc8jqba` (`root_id`),
  KEY `FK5t6d5q2nbarf9lg3f4l1v754k` (`type_link_id`),
  CONSTRAINT `FK5dse77wvdtih1qj4tqp5stpiu` FOREIGN KEY (`next_id`) REFERENCES `selectors` (`id`),
  CONSTRAINT `FK5t6d5q2nbarf9lg3f4l1v754k` FOREIGN KEY (`type_link_id`) REFERENCES `type_links` (`id`),
  CONSTRAINT `FKbi0h7t37k4p92rijsphc8jqba` FOREIGN KEY (`root_id`) REFERENCES `selectors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `selectors`
--

LOCK TABLES `selectors` WRITE;
/*!40000 ALTER TABLE `selectors` DISABLE KEYS */;
/*!40000 ALTER TABLE `selectors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence_table`
--

DROP TABLE IF EXISTS `sequence_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sequence_table` (
  `seq_name` varchar(255) NOT NULL,
  `seq_count` bigint DEFAULT NULL,
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_table`
--

LOCK TABLES `sequence_table` WRITE;
/*!40000 ALTER TABLE `sequence_table` DISABLE KEYS */;
INSERT INTO `sequence_table` VALUES ('agenda',2),('authorization',0),('expression',0),('expression_link',0),('fact',0),('fact_action',0),('fact_context',100),('fact_link',0),('fact_query',0),('fact_value',0),('measurement_session_type',0),('message',1),('observable_entity',7),('observable_entity_action',7),('observable_entity_identifier',6),('phenomenon',0),('qualification',0),('role',0),('selector',0),('service',1),('staff',1),('survey_schedule',100),('type',100),('type_link',0),('unit',0),('unit_use',0),('user',0),('viewer',0),('viewer_link',0);
/*!40000 ALTER TABLE `sequence_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `services` (
  `id` bigint NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` longtext,
  `internal_code` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `agenda_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bdbqkk6xetiy2no30d7f0e89r` (`internal_code`),
  KEY `FKt16di9g30gaqcbcrbo5n7dap` (`agenda_id`),
  CONSTRAINT `FKt16di9g30gaqcbcrbo5n7dap` FOREIGN KEY (`agenda_id`) REFERENCES `agendas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (1,'SRV-QC','Quality Control Service','INT-QC-01','ca51e436-9e16-4e21-a45a-752572808fd9',2);
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `id` bigint NOT NULL,
  `number` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `default_agenda_id` bigint DEFAULT NULL,
  `phen_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4l0qdh353ljq7gji5uhqlku34` (`default_agenda_id`),
  KEY `FKh9y3rj7845uhkyelnitmbismc` (`phen_id`),
  KEY `FKdlvw23ak3u9v9bomm8g12rtc0` (`user_id`),
  CONSTRAINT `FK4l0qdh353ljq7gji5uhqlku34` FOREIGN KEY (`default_agenda_id`) REFERENCES `agendas` (`id`),
  CONSTRAINT `FKdlvw23ak3u9v9bomm8g12rtc0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKh9y3rj7845uhkyelnitmbismc` FOREIGN KEY (`phen_id`) REFERENCES `phenomena` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'0001','c2822f5e-1c6f-48da-89a7-f4222d46ea21',1,NULL,1);
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff_agendas`
--

DROP TABLE IF EXISTS `staff_agendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff_agendas` (
  `staff_id` bigint NOT NULL,
  `agenda_id` bigint NOT NULL,
  PRIMARY KEY (`staff_id`,`agenda_id`),
  KEY `FKlmb7mk2v6f57lnwf87c84x790` (`agenda_id`),
  CONSTRAINT `FK8llmxmhclpdof527m0ehjy05w` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`),
  CONSTRAINT `FKlmb7mk2v6f57lnwf87c84x790` FOREIGN KEY (`agenda_id`) REFERENCES `agendas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff_agendas`
--

LOCK TABLES `staff_agendas` WRITE;
/*!40000 ALTER TABLE `staff_agendas` DISABLE KEYS */;
INSERT INTO `staff_agendas` VALUES (1,1);
/*!40000 ALTER TABLE `staff_agendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey_schedule_services`
--

DROP TABLE IF EXISTS `survey_schedule_services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `survey_schedule_services` (
  `survey_schedule_id` bigint NOT NULL,
  `service_id` bigint NOT NULL,
  PRIMARY KEY (`survey_schedule_id`,`service_id`),
  KEY `FKme604dses5knadsn6dbrpckbn` (`service_id`),
  CONSTRAINT `FKbao5tuscvy69r44two8lk8fg0` FOREIGN KEY (`survey_schedule_id`) REFERENCES `survey_schedules` (`id`),
  CONSTRAINT `FKme604dses5knadsn6dbrpckbn` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_schedule_services`
--

LOCK TABLES `survey_schedule_services` WRITE;
/*!40000 ALTER TABLE `survey_schedule_services` DISABLE KEYS */;
INSERT INTO `survey_schedule_services` VALUES (1,1);
/*!40000 ALTER TABLE `survey_schedule_services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey_schedules`
--

DROP TABLE IF EXISTS `survey_schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `survey_schedules` (
  `id` bigint NOT NULL,
  `acceptance_code` varchar(255) DEFAULT NULL,
  `booking_code` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `agenda_id` bigint DEFAULT NULL,
  `observable_entity_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6ub42m2j09go72kou31sa28b0` (`booking_code`,`acceptance_code`),
  UNIQUE KEY `UK_mybpl7i22hk5w5mkcb9j20caa` (`booking_code`),
  KEY `FKq349w1bmged0gbuds1n4ntfhj` (`agenda_id`),
  KEY `FKefa9yrxii6mk3b5fudumu6w1m` (`observable_entity_id`),
  CONSTRAINT `FKefa9yrxii6mk3b5fudumu6w1m` FOREIGN KEY (`observable_entity_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKq349w1bmged0gbuds1n4ntfhj` FOREIGN KEY (`agenda_id`) REFERENCES `agendas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_schedules`
--

LOCK TABLES `survey_schedules` WRITE;
/*!40000 ALTER TABLE `survey_schedules` DISABLE KEYS */;
INSERT INTO `survey_schedules` VALUES (1,'ACC-0001','BOOK-0001','2025-11-20 01:00:00.000000','SS-001','ACCEPTED','b122a400-c205-4715-a44a-322a5d12f083',1,1);
/*!40000 ALTER TABLE `survey_schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_ancestors`
--

DROP TABLE IF EXISTS `type_ancestors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_ancestors` (
  `type_id` bigint NOT NULL,
  `ancestor_type_id` bigint NOT NULL,
  PRIMARY KEY (`type_id`,`ancestor_type_id`),
  KEY `FKrnrari4y7ajiq264hc4gmued2` (`ancestor_type_id`),
  CONSTRAINT `FKmbiglwv5l9sw0a2ftckd0j3rl` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`),
  CONSTRAINT `FKrnrari4y7ajiq264hc4gmued2` FOREIGN KEY (`ancestor_type_id`) REFERENCES `types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_ancestors`
--

LOCK TABLES `type_ancestors` WRITE;
/*!40000 ALTER TABLE `type_ancestors` DISABLE KEYS */;
INSERT INTO `type_ancestors` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `type_ancestors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_links`
--

DROP TABLE IF EXISTS `type_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_links` (
  `id` bigint NOT NULL,
  `max_value` int DEFAULT NULL,
  `min_value` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `priority` bigint DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `default_value` bigint DEFAULT NULL,
  `source_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg36gauvfehdfgjfyilp0l22kk` (`default_value`),
  KEY `FK6180cr26n9gh9lx7e4ukugmum` (`source_id`),
  KEY `FKs8sth5fdro1u0bey4sn17q0vm` (`target_id`),
  CONSTRAINT `FK6180cr26n9gh9lx7e4ukugmum` FOREIGN KEY (`source_id`) REFERENCES `types` (`id`),
  CONSTRAINT `FKg36gauvfehdfgjfyilp0l22kk` FOREIGN KEY (`default_value`) REFERENCES `fact_values` (`id`),
  CONSTRAINT `FKs8sth5fdro1u0bey4sn17q0vm` FOREIGN KEY (`target_id`) REFERENCES `types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_links`
--

LOCK TABLES `type_links` WRITE;
/*!40000 ALTER TABLE `type_links` DISABLE KEYS */;
/*!40000 ALTER TABLE `type_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_phenomena`
--

DROP TABLE IF EXISTS `type_phenomena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_phenomena` (
  `type_id` bigint NOT NULL,
  `phen_id` bigint NOT NULL,
  PRIMARY KEY (`type_id`,`phen_id`),
  UNIQUE KEY `UK_svib6u011yhiiu8tabnrk9d5k` (`phen_id`),
  CONSTRAINT `FKhxqn1id995724txvwclavunqc` FOREIGN KEY (`phen_id`) REFERENCES `phenomena` (`id`),
  CONSTRAINT `FKpoi8whbw7q9i7jkd22dahlicd` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_phenomena`
--

LOCK TABLES `type_phenomena` WRITE;
/*!40000 ALTER TABLE `type_phenomena` DISABLE KEYS */;
/*!40000 ALTER TABLE `type_phenomena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `types`
--

DROP TABLE IF EXISTS `types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `types` (
  `from_class` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `anonymous` bit(1) DEFAULT NULL,
  `description` longtext,
  `name` varchar(255) DEFAULT NULL,
  `read_only` bit(1) DEFAULT NULL,
  `recurrent` bit(1) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `time_type` varchar(255) DEFAULT NULL,
  `query_def` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_17go525ou3scbmd4pcftq130f` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `types`
--

LOCK TABLES `types` WRITE;
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` VALUES ('EQL',1,_binary '\0','Visual assessment procedure to identify and classify surface defects such as knots, cracks, chromatic alterations, or fungal attacks.','Visual Defect Inspection',_binary '',_binary '\0',NULL,NULL,'57a699c5-6332-41d8-bcb9-08ccaa7fe0a0',NULL,NULL),('QT',2,_binary '\0','Automated measurement of the modulus of elasticity (MoE) and bending strength using acoustic or stress-testing equipment.','Mechanical Strength Grading',_binary '',_binary '\0',NULL,NULL,'9c96d8ed-51c3-4509-a4a4-16dfac50a32c',NULL,NULL);
/*!40000 ALTER TABLE `types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit_uses`
--

DROP TABLE IF EXISTS `unit_uses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unit_uses` (
  `id` bigint NOT NULL,
  `decimals` int DEFAULT NULL,
  `digits` int DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `unit_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKy832c0gt0lul0kq37sqb5alp` (`type_id`),
  KEY `FK3ad6e803haqnhj7tegrweiy8v` (`unit_id`),
  CONSTRAINT `FK3ad6e803haqnhj7tegrweiy8v` FOREIGN KEY (`unit_id`) REFERENCES `units` (`id`),
  CONSTRAINT `FKy832c0gt0lul0kq37sqb5alp` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit_uses`
--

LOCK TABLES `unit_uses` WRITE;
/*!40000 ALTER TABLE `unit_uses` DISABLE KEYS */;
/*!40000 ALTER TABLE `unit_uses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `units` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `simbol` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_etw07nfppovq9p7ov8hcb38wy` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units` VALUES (1,'percentage','%','e1f2d3c4-b5a6-7080-910a-b2c3d4e5f600'),(2,'megapascal','MPa','f2e3d4c5-a6b7-8090-1234-567890abcdef11'),(3,'millimeter','mm','00112233-4455-6677-8899-aabbccddeeff');
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_qualifications`
--

DROP TABLE IF EXISTS `user_qualifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_qualifications` (
  `user_id` bigint NOT NULL,
  `qual_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`qual_id`),
  KEY `FKb3bpuynamunihm8ktnsqei80x` (`qual_id`),
  CONSTRAINT `FKb3bpuynamunihm8ktnsqei80x` FOREIGN KEY (`qual_id`) REFERENCES `qualifications` (`id`),
  CONSTRAINT `FKlo3edp6k7tamaxwo0eyh4rtd` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_qualifications`
--

LOCK TABLES `user_qualifications` WRITE;
/*!40000 ALTER TABLE `user_qualifications` DISABLE KEYS */;
INSERT INTO `user_qualifications` VALUES (1,1);
/*!40000 ALTER TABLE `user_qualifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `deprecated` bit(1) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userid` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jyjiwnaabof8kpd0gclhcj2lh` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,_binary '\0','prova@unifi.it','dummy','123','12345678','user','1','963cb3d9-c324-4cdc-90a7-f2ee9b3af07c');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viewer_ancestors`
--

DROP TABLE IF EXISTS `viewer_ancestors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `viewer_ancestors` (
  `view_id` bigint NOT NULL,
  `ancestor_view_id` bigint NOT NULL,
  PRIMARY KEY (`view_id`,`ancestor_view_id`),
  KEY `FKkd59s5ao0usynwrq5a8nipass` (`ancestor_view_id`),
  CONSTRAINT `FK2o5lp0ij9wynuqat0peywr972` FOREIGN KEY (`view_id`) REFERENCES `viewers` (`id`),
  CONSTRAINT `FKkd59s5ao0usynwrq5a8nipass` FOREIGN KEY (`ancestor_view_id`) REFERENCES `viewers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viewer_ancestors`
--

LOCK TABLES `viewer_ancestors` WRITE;
/*!40000 ALTER TABLE `viewer_ancestors` DISABLE KEYS */;
/*!40000 ALTER TABLE `viewer_ancestors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viewer_links`
--

DROP TABLE IF EXISTS `viewer_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `viewer_links` (
  `from_class` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `priority` bigint DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `selector` bigint DEFAULT NULL,
  `source_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5gfijxva5cmjngjpg9in0ghcd` (`selector`),
  KEY `FK65kwi7e9a2p6oh323idt12rcd` (`source_id`),
  KEY `FKhwgi74tvltp66pom8o8uqm2r5` (`target_id`),
  CONSTRAINT `FK5gfijxva5cmjngjpg9in0ghcd` FOREIGN KEY (`selector`) REFERENCES `selectors` (`id`),
  CONSTRAINT `FK65kwi7e9a2p6oh323idt12rcd` FOREIGN KEY (`source_id`) REFERENCES `viewers` (`id`),
  CONSTRAINT `FKhwgi74tvltp66pom8o8uqm2r5` FOREIGN KEY (`target_id`) REFERENCES `viewers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viewer_links`
--

LOCK TABLES `viewer_links` WRITE;
/*!40000 ALTER TABLE `viewer_links` DISABLE KEYS */;
/*!40000 ALTER TABLE `viewer_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viewers`
--

DROP TABLE IF EXISTS `viewers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `viewers` (
  `from_class` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `anonymous` bit(1) DEFAULT NULL,
  `css` longtext,
  `description` longtext,
  `name` varchar(255) DEFAULT NULL,
  `read_only` bit(1) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `collapse` bit(1) DEFAULT NULL,
  `clear` bit(1) DEFAULT NULL,
  `orientation` int DEFAULT NULL,
  `required` bit(1) DEFAULT NULL,
  `input_length` int DEFAULT NULL,
  `accepted_types` varchar(255) DEFAULT NULL,
  `value` longtext,
  `path` varchar(255) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `clear_selector` bigint DEFAULT NULL,
  `fact_query_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dv0sca06thmm3kok5puqkd4du` (`name`),
  KEY `FKgrrrq8mek1kjs78u4vk2x6qse` (`type_id`),
  KEY `FK5auxa9h0n3l3vup1jdvw21g0a` (`clear_selector`),
  KEY `FKkkgvpp71s03w0x8pwbehewcew` (`fact_query_id`),
  CONSTRAINT `FK5auxa9h0n3l3vup1jdvw21g0a` FOREIGN KEY (`clear_selector`) REFERENCES `selectors` (`id`),
  CONSTRAINT `FKgrrrq8mek1kjs78u4vk2x6qse` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`),
  CONSTRAINT `FKkkgvpp71s03w0x8pwbehewcew` FOREIGN KEY (`fact_query_id`) REFERENCES `fact_queries` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viewers`
--

LOCK TABLES `viewers` WRITE;
/*!40000 ALTER TABLE `viewers` DISABLE KEYS */;
/*!40000 ALTER TABLE `viewers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wood_element_actions`
--

DROP TABLE IF EXISTS `wood_element_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wood_element_actions` (
  `action_type` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `action_time` datetime(6) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  `source_id` bigint DEFAULT NULL,
  `source1_id` bigint DEFAULT NULL,
  `source2_id` bigint DEFAULT NULL,
  `target1_id` bigint DEFAULT NULL,
  `target2_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgwjt2742vjxciluepos3l13e2` (`author_id`),
  KEY `FKd1xpgkdqjq526fh8s3t9poeh2` (`target_id`),
  KEY `FKrq7ochembhf14ekcirn9iax00` (`source_id`),
  KEY `FKpatbj86jkeq7sqcnswojfv6a4` (`source1_id`),
  KEY `FKjtrgf7b0pcxhilq4s5s8dfbkp` (`source2_id`),
  KEY `FK3m6hn8w4bjmt2v6vhmnkck11t` (`target1_id`),
  KEY `FK6jrtp5etedyb3lckmo9voeyp8` (`target2_id`),
  CONSTRAINT `FK3m6hn8w4bjmt2v6vhmnkck11t` FOREIGN KEY (`target1_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FK6jrtp5etedyb3lckmo9voeyp8` FOREIGN KEY (`target2_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKd1xpgkdqjq526fh8s3t9poeh2` FOREIGN KEY (`target_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKgwjt2742vjxciluepos3l13e2` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjtrgf7b0pcxhilq4s5s8dfbkp` FOREIGN KEY (`source2_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKpatbj86jkeq7sqcnswojfv6a4` FOREIGN KEY (`source1_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKrq7ochembhf14ekcirn9iax00` FOREIGN KEY (`source_id`) REFERENCES `observable_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wood_element_actions`
--

LOCK TABLES `wood_element_actions` WRITE;
/*!40000 ALTER TABLE `wood_element_actions` DISABLE KEYS */;
INSERT INTO `wood_element_actions` VALUES ('CR',1,'2025-11-19 17:40:38.550000','e69a060f-5167-419e-8ec4-b5ca360cedc0',1,1,NULL,NULL,NULL,NULL,NULL),('CR',2,'2025-11-19 17:41:14.112000','ac56bcbe-eda3-4958-9fa4-38471df9d227',1,2,NULL,NULL,NULL,NULL,NULL),('CR',3,'2025-11-19 17:41:42.530000','129a02d4-a463-4c02-b1e8-5b8a759def60',1,3,NULL,NULL,NULL,NULL,NULL),('CR',4,'2025-11-19 17:42:04.048000','e14552dc-f399-4d9d-9aab-72aa8a347f78',1,4,NULL,NULL,NULL,NULL,NULL),('CR',5,'2025-11-19 17:42:15.904000','e9261159-c020-480a-bd92-666fc32dfb30',1,5,NULL,NULL,NULL,NULL,NULL),('CR',6,'2025-11-19 17:42:29.930000','78584346-5c8a-41cb-a995-c8d3ec4f4525',1,6,NULL,NULL,NULL,NULL,NULL),('MD',7,'2025-11-20 15:28:09.701000','275299ea-836c-466a-926a-a873793cc572',1,NULL,6,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `wood_element_actions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-20 16:13:41

SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
