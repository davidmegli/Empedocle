-- MySQL dump 10.13  Distrib 8.0.44, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: emp_db
-- ------------------------------------------------------
-- Server version	8.0.44-0ubuntu0.24.04.1

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
INSERT INTO `agendas` VALUES (1,'A-01','Agenda','408c4988-f3ae-47da-b84e-e24741db0c9e',1);
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
INSERT INTO `fact_context` VALUES ('EX',1,'e21a558e-92fe-47f4-ba02-7fad1a4fc7a2',0,NULL,'DONE',_binary '',NULL,1,1);
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
INSERT INTO `fact_values` VALUES ('QL',1,'ab5af856-4d7c-4a4f-a74b-cab3157ece62',NULL,NULL,'Luca Toni is the best player ever. Man of the Game.',NULL,NULL,NULL);
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
INSERT INTO `facts` VALUES ('QL',1,NULL,NULL,NULL,'63d9462d-568f-476e-922d-d6f4e7b9621c','Luca Toni is the best player ever. Man of the Game.',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL);
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
/*!40000 ALTER TABLE `favorite_agendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `match_actions`
--

DROP TABLE IF EXISTS `match_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `match_actions` (
  `action_type` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `action_time` datetime(6) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  `source_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqonqyon4aidg3hs3pv3a1w4oc` (`author_id`),
  KEY `FKx7fp4ne4yrj658jdyukal1ny` (`target_id`),
  KEY `FK2yc7mup1rrros81asfqj0ujwj` (`source_id`),
  CONSTRAINT `FK2yc7mup1rrros81asfqj0ujwj` FOREIGN KEY (`source_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKqonqyon4aidg3hs3pv3a1w4oc` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKx7fp4ne4yrj658jdyukal1ny` FOREIGN KEY (`target_id`) REFERENCES `observable_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match_actions`
--

LOCK TABLES `match_actions` WRITE;
/*!40000 ALTER TABLE `match_actions` DISABLE KEYS */;
INSERT INTO `match_actions` VALUES ('CR',10,'2025-12-30 16:04:52.351000','5f26f2e7-a21a-4019-95b9-4e9aeecc0d53',1,10,NULL),('CR',11,'2025-12-30 16:05:48.356000','44fc766d-889a-415c-b67d-a1411ae05c37',1,11,NULL),('CR',12,'2025-12-30 16:06:26.484000','1ef386f4-15d0-4edb-9827-4c177d63af53',1,12,NULL);
/*!40000 ALTER TABLE `match_actions` ENABLE KEYS */;
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
INSERT INTO `measurement_session_types` VALUES (1,'Standard session for post-match technical evaluations','Journalist Match Report',86400,'c353128f-c4c4-49db-9559-9b155929f7a5',1);
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
  `birth_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `assists` int DEFAULT NULL,
  `goals` int DEFAULT NULL,
  `minutes_played` int DEFAULT NULL,
  `age` int DEFAULT NULL,
  `external_element_id` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `place_of_origin` varchar(255) DEFAULT NULL,
  `specie` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `dest_id` bigint DEFAULT NULL,
  `identifier_id` bigint DEFAULT NULL,
  `origin_id` bigint DEFAULT NULL,
  `away_team_id` bigint DEFAULT NULL,
  `home_team_id` bigint DEFAULT NULL,
  `match_id` bigint DEFAULT NULL,
  `player_id` bigint DEFAULT NULL,
  `disciplinary_status` int DEFAULT NULL,
  `player_number` int DEFAULT NULL,
  `start` datetime(6) DEFAULT NULL,
  `roster_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp57pmrddwboy2qyjjs4m15f55` (`dest_id`),
  KEY `FK4p0v5soo305x0ww2vx5gu4p0h` (`identifier_id`),
  KEY `FKk2mjpbw7hj94bxly29hyqw27r` (`origin_id`),
  KEY `FKlw8cd3hxh80gpew9p8e1lir1w` (`away_team_id`),
  KEY `FKaw71iy97hswd5xp3ixf4pyfcx` (`home_team_id`),
  KEY `FK170vpsp5yin5l8jnn3xqq0lj2` (`match_id`),
  KEY `FKv5ll1mx6b7kuwmmdq0x4p1no` (`player_id`),
  KEY `FKm64fpfeqpar8i5o5na9htpn1q` (`roster_id`),
  CONSTRAINT `FK170vpsp5yin5l8jnn3xqq0lj2` FOREIGN KEY (`match_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FK4p0v5soo305x0ww2vx5gu4p0h` FOREIGN KEY (`identifier_id`) REFERENCES `observable_entity_identifier` (`id`),
  CONSTRAINT `FKaw71iy97hswd5xp3ixf4pyfcx` FOREIGN KEY (`home_team_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKlw8cd3hxh80gpew9p8e1lir1w` FOREIGN KEY (`away_team_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKm64fpfeqpar8i5o5na9htpn1q` FOREIGN KEY (`roster_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKp57pmrddwboy2qyjjs4m15f55` FOREIGN KEY (`dest_id`) REFERENCES `player_actions` (`id`),
  CONSTRAINT `FKv5ll1mx6b7kuwmmdq0x4p1no` FOREIGN KEY (`player_id`) REFERENCES `observable_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `observable_entity`
--

LOCK TABLES `observable_entity` WRITE;
/*!40000 ALTER TABLE `observable_entity` DISABLE KEYS */;
INSERT INTO `observable_entity` VALUES ('Player',1,NULL,NULL,'825d8233-7e95-442b-a34b-4d26f88ea3ee','1980-02-20 03:00:00.000000','Luca','STRIKER','Toni',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,19,1,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('Player',2,NULL,NULL,'19ee2518-2ebc-4af4-b61e-1e6b9108af1d','1990-11-07 01:00:00.000000','David','GOALKEEPER','De Gea',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('Player',3,NULL,NULL,'2ade17de-f587-4484-bead-dbf2a0474cf3','2000-02-28 01:00:00.000000','Moise','STRIKER','Kean',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7),('Player',4,NULL,NULL,'46bb39cf-86ed-45c7-9221-4e7796af532e','1997-08-22 02:00:00.000000','Lautaro','STRIKER','Martínez',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,18,4,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('Roster',7,NULL,NULL,'757bfe42-4b33-4697-834f-a6dd8b72bbf5',NULL,'ACF Fiorentina',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('Roster',8,NULL,NULL,'9e0e1474-98df-4f4c-b457-367f6031c786',NULL,'FC Internazionale Milano',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('Roster',9,NULL,NULL,'8f7b7e35-efd2-48f3-bb8b-6d5143e29aa5',NULL,'Juventus FC',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,9,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('Match',10,NULL,NULL,'e3b63ef6-b17a-4a55-8590-bf3848ae657f',NULL,NULL,NULL,NULL,'2025-05-10 17:00:00.000000','Artemio Franchi Stadium, Florence',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,10,10,8,7,NULL,NULL,NULL,NULL,NULL,NULL),('Match',11,NULL,NULL,'d37510f8-7d9d-4e11-91b9-56d72bfb88d1',NULL,NULL,NULL,NULL,'2025-05-24 22:45:00.000000','Artemio Franchi Stadium, Florence',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,11,9,7,NULL,NULL,NULL,NULL,NULL,NULL),('Match',12,NULL,NULL,'e0cb0379-08bc-4024-9f8c-f5531a03c720',NULL,NULL,NULL,NULL,'2025-02-15 21:45:00.000000','San Siro Stadium, Milan',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,12,12,9,8,NULL,NULL,NULL,NULL,NULL,NULL),('Participation',15,NULL,NULL,'938a1ac5-f8ef-4426-9b83-5594d06f5fc7',NULL,NULL,NULL,NULL,NULL,NULL,0,1,90,NULL,NULL,NULL,NULL,NULL,NULL,NULL,15,15,NULL,NULL,10,2,0,43,'2025-05-10 17:00:00.000000',NULL),('Participation',16,NULL,NULL,'a85df6f2-ede6-4549-838e-a532b5e9823a',NULL,NULL,NULL,NULL,NULL,NULL,0,10,82,NULL,NULL,NULL,NULL,NULL,NULL,NULL,16,16,NULL,NULL,11,1,1,20,'2025-05-10 17:00:00.000000',NULL),('Participation',17,NULL,NULL,'08adc204-491f-4b2c-b1eb-ddacebfc6fe8',NULL,NULL,NULL,NULL,NULL,NULL,0,1,70,NULL,NULL,NULL,NULL,NULL,NULL,NULL,17,17,NULL,NULL,12,4,2,10,'2025-05-10 17:00:00.000000',NULL),('Player',18,NULL,NULL,'f8d33619-9435-493a-86da-88f5a9c5dc07','1997-08-22 02:00:00.000000','Lautaro','STRIKER','Martínez',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,18,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8),('Player',19,NULL,NULL,'812d0371-19ad-4625-8487-dd06a6ccdca0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,20,1,19,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7),('Player',20,NULL,NULL,'b9b6b712-7fd2-4497-9aa3-b3e4b99177f7','1980-02-20 01:00:00.000000','Luca','STRIKER','Toni',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8);
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
INSERT INTO `observable_entity_before` VALUES (1,1),(19,1),(20,1),(2,2),(3,3),(4,4),(18,4),(7,7),(8,8),(9,9),(10,10),(11,11),(12,12),(15,15),(16,16),(17,17),(18,18),(19,19),(20,19),(20,20);
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
INSERT INTO `observable_entity_identifier` VALUES ('PlayerIdentifier',1,'LT30','005185b3-f6f6-4037-8ee2-0dd72b173863',0),('PlayerIdentifier',2,'PLY-FIO-43','1e3376e7-0e16-41d3-afed-32b0595e5d53',0),('PlayerIdentifier',3,'PLY-FIO-20','ace52d13-cf2a-4f32-9ea4-71fd39867a1b',0),('PlayerIdentifier',4,'PLY-INTER-10','8088f57c-699d-4503-ab5a-8ba9ea8598a2',0),('PlayerIdentifier',7,'RST-FIO-2024','fcd83a39-68de-4dba-83b7-0c85dbd8de0c',0),('PlayerIdentifier',8,'RST-INTER-2024','9b622503-a59e-4664-9b98-fb0ea6ab448a',0),('PlayerIdentifier',9,'RST-JUVE-2024','76fb5d00-5ea4-4d1f-8cf6-ea02e6ceb955',0),('PlayerIdentifier',10,'MATCH-SERIEA-2025-12','8eda5b32-4104-4bd6-bf90-8d4160f1946c',0),('PlayerIdentifier',11,'MATCH-SERIEA-2025-15','523c3e94-7fc0-43eb-bd90-4d92af9c4f16',0),('PlayerIdentifier',12,'MATCH-SERIEA-2025-01','50e6d94e-f317-41f1-8867-7eb661b12f98',0),('PlayerIdentifier',15,'PRT-MATCH12-PLY101','4ba71e69-c1be-401a-961c-b74d3a72002f',0),('PlayerIdentifier',16,'PRT-MATCH12-PLY102','21c2ebbd-ca2e-4f30-8ee9-c4e99ff8b67c',0),('PlayerIdentifier',17,'PRT-MATCH12-PLY1','1be1e336-c247-44f0-8bdd-18ed398c6cab',0);
/*!40000 ALTER TABLE `observable_entity_identifier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `observable_entity_observable_entity`
--

DROP TABLE IF EXISTS `observable_entity_observable_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `observable_entity_observable_entity` (
  `Roster_id` bigint NOT NULL,
  `players_id` bigint NOT NULL,
  UNIQUE KEY `UK_19k5g7ukdwk593o5r121gbkqf` (`players_id`),
  KEY `FKp9b9a84pgokhmaxkp0qg0xrgp` (`Roster_id`),
  CONSTRAINT `FKp9b9a84pgokhmaxkp0qg0xrgp` FOREIGN KEY (`Roster_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKqaegj4ub94fi54nj2qvamkfii` FOREIGN KEY (`players_id`) REFERENCES `observable_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `observable_entity_observable_entity`
--

LOCK TABLES `observable_entity_observable_entity` WRITE;
/*!40000 ALTER TABLE `observable_entity_observable_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `observable_entity_observable_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participation_actions`
--

DROP TABLE IF EXISTS `participation_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participation_actions` (
  `action_type` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `action_time` datetime(6) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  `source_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq5okpkdacbfk137asb2yptw9` (`author_id`),
  KEY `FKdx80qppksrrrqo52caotx89r` (`target_id`),
  KEY `FK5kw9kis9cj6bs6rkco5s3rdnq` (`source_id`),
  CONSTRAINT `FK5kw9kis9cj6bs6rkco5s3rdnq` FOREIGN KEY (`source_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKdx80qppksrrrqo52caotx89r` FOREIGN KEY (`target_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKq5okpkdacbfk137asb2yptw9` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participation_actions`
--

LOCK TABLES `participation_actions` WRITE;
/*!40000 ALTER TABLE `participation_actions` DISABLE KEYS */;
INSERT INTO `participation_actions` VALUES ('CR',15,'2025-12-30 16:15:25.665000','8b7bea56-e581-47ca-b562-b8cc452c02ee',1,15,NULL),('CR',16,'2025-12-30 16:17:18.433000','217143d7-cd05-46e2-bf2c-5c7a1930f55e',1,16,NULL),('CR',17,'2025-12-30 16:18:40.494000','a99fc25b-cdbc-452b-abb0-bcfb2aca96ca',1,17,NULL);
/*!40000 ALTER TABLE `participation_actions` ENABLE KEYS */;
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
-- Table structure for table `player_actions`
--

DROP TABLE IF EXISTS `player_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player_actions` (
  `action_type` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `action_time` datetime(6) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  `source_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsyks70po1s1duiartxikqxqbh` (`author_id`),
  KEY `FKjufhyh4ywcs9cigmjseg8gh0g` (`target_id`),
  KEY `FKstke1nt5kxxsaeykqaaxcwp0r` (`source_id`),
  CONSTRAINT `FKjufhyh4ywcs9cigmjseg8gh0g` FOREIGN KEY (`target_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKstke1nt5kxxsaeykqaaxcwp0r` FOREIGN KEY (`source_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FKsyks70po1s1duiartxikqxqbh` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_actions`
--

LOCK TABLES `player_actions` WRITE;
/*!40000 ALTER TABLE `player_actions` DISABLE KEYS */;
INSERT INTO `player_actions` VALUES ('CR',1,'2025-12-30 13:00:33.679000','c014cf39-72ba-4bee-b099-449304f26f62',1,1,NULL),('CR',2,'2025-12-30 15:31:00.144000','7a7b3fd7-6050-4c34-866c-e73f47ad8333',1,2,NULL),('CR',3,'2025-12-30 15:33:09.588000','f61569f1-df32-4c7a-96a1-fa58d1a2597a',1,3,NULL),('CR',4,'2025-12-30 15:33:32.385000','56ed8250-b49c-4b99-8961-06b8e6c658ff',1,4,NULL),('MD',18,'2025-12-30 17:36:05.693000','849e375c-d580-48de-8f93-fff9601c3c7f',1,NULL,4),('MD',19,'2025-12-30 17:36:39.710000','695f7c67-aeae-455d-9be5-fd96c4480f0a',1,NULL,1),('MD',20,'2025-12-30 17:38:19.169000','babe77b9-4d83-4d1d-af63-754a401dcf59',1,NULL,19);
/*!40000 ALTER TABLE `player_actions` ENABLE KEYS */;
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
-- Table structure for table `roster_actions`
--

DROP TABLE IF EXISTS `roster_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roster_actions` (
  `action_type` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `action_time` datetime(6) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  `source_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8gmk5plb93vlouftoc823nvn2` (`author_id`),
  KEY `FK84cw5enaiaex2r4gt7n627280` (`target_id`),
  KEY `FK7m8y1lgml5wiv3owtg7f5mx3` (`source_id`),
  CONSTRAINT `FK7m8y1lgml5wiv3owtg7f5mx3` FOREIGN KEY (`source_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FK84cw5enaiaex2r4gt7n627280` FOREIGN KEY (`target_id`) REFERENCES `observable_entity` (`id`),
  CONSTRAINT `FK8gmk5plb93vlouftoc823nvn2` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roster_actions`
--

LOCK TABLES `roster_actions` WRITE;
/*!40000 ALTER TABLE `roster_actions` DISABLE KEYS */;
INSERT INTO `roster_actions` VALUES ('CR',7,'2025-12-30 15:48:34.661000','c95503fd-1ced-4313-8767-82bc55fd75e8',1,7,NULL),('CR',8,'2025-12-30 15:49:08.430000','f97d4057-e0f0-4bfb-b33f-162a9bc6e2ae',1,8,NULL),('CR',9,'2025-12-30 15:49:23.162000','907f7bd4-1a05-450f-9581-db14b8ee71fd',1,9,NULL);
/*!40000 ALTER TABLE `roster_actions` ENABLE KEYS */;
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
INSERT INTO `sequence_table` VALUES ('agenda',1),('authorization',0),('expression',0),('expression_link',0),('fact',0),('fact_action',0),('fact_context',100),('fact_link',0),('fact_query',0),('fact_value',0),('measurement_session_type',0),('message',0),('observable_entity',20),('observable_entity_action',20),('observable_entity_identifier',17),('phenomenon',0),('qualification',0),('role',0),('selector',0),('service',0),('staff',0),('survey_schedule',100),('type',100),('type_link',0),('unit',0),('unit_use',0),('user',0),('viewer',0),('viewer_link',0);
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
INSERT INTO `survey_schedules` VALUES (1,NULL,NULL,'2025-05-10 20:00:00.000000','PRESS-FIO-2025-043','ACCEPTED','5bc06641-fa6e-4593-beed-34eaaa38b7d8',1,1);
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
INSERT INTO `type_ancestors` VALUES (1,1);
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
INSERT INTO `types` VALUES ('TX',1,_binary '\0','Narrative evaluation of the player\'s performance by a sports journalist','Match Expert Comment',_binary '',_binary '',NULL,NULL,'41c7dd08-652f-42b1-b6b8-350c31e71400',NULL,NULL);
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
INSERT INTO `users` VALUES (1,_binary '\0','prova@unifi.it','dummy','123','123456','user','1','963cb3d9-c324-4cdc-90a7-f2ee9b3af07c');
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

-- Dump completed on 2025-12-30 18:03:13
