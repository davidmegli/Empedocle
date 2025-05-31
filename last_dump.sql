-- MySQL dump 10.13  Distrib 8.0.41, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: emp_db DATE 31-05-2025 H:19:09
-- ------------------------------------------------------
-- Server version	8.0.41-0ubuntu0.24.04.1

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

CREATE DATABASE emp_db;
USE emp_db;
--
-- Table structure for table `agendas`
--

DROP TABLE IF EXISTS `agendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agendas` (
                           `id` bigint NOT NULL,
                           `branch_name` varchar(255) DEFAULT NULL,
                           `code` varchar(255) DEFAULT NULL,
                           `description` longtext,
                           `uuid` varchar(255) DEFAULT NULL,
                           `measurement_session_type_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `UK_f5axk5xrjmk4m5pcweer89r81` (`code`),
                           KEY `FKrxg8lojc7b1mbydwsfomy7jj` (`measurement_session_type_id`),
                           CONSTRAINT `FKrxg8lojc7b1mbydwsfomy7jj` FOREIGN KEY (`measurement_session_type_id`) REFERENCES `measurement_session_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agendas`
--

LOCK TABLES `agendas` WRITE;
/*!40000 ALTER TABLE `agendas` DISABLE KEYS */;
INSERT INTO `agendas` VALUES (1,NULL,'001','Agenda test 001','963cb3d2-c314-4ccc-90a7-f2ee9b3af07b',1),(2,NULL,'002','Agenda Sara','e60f50d0-2ba0-482f-b15b-0bacf124320c',NULL);
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
                                  `examOperation` varchar(255) DEFAULT NULL,
                                  `uuid` varchar(255) DEFAULT NULL,
                                  `qualification_id` bigint NOT NULL,
                                  `authorization_id` bigint NOT NULL,
                                  `measurementSessionOperation` varchar(255) DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FKd1c0q27j53o43u8dbvpjghwvk` (`qualification_id`),
                                  KEY `FKfj44ovkvkklajlua1cugapvik` (`authorization_id`),
                                  CONSTRAINT `FKd1c0q27j53o43u8dbvpjghwvk` FOREIGN KEY (`qualification_id`) REFERENCES `qualifications` (`id`),
                                  CONSTRAINT `FKfj44ovkvkklajlua1cugapvik` FOREIGN KEY (`authorization_id`) REFERENCES `measurement_session_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorizations`
--

LOCK TABLES `authorizations` WRITE;
/*!40000 ALTER TABLE `authorizations` DISABLE KEYS */;
INSERT INTO `authorizations` VALUES (1,'END_MEASUREMENT_SESSION','d62c30f0-e0c6-42db-9e12-4a7158ce84af',1,1,NULL);
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
INSERT INTO `fact_actions` VALUES ('CR',1,'2023-11-23 14:51:57.186000','ed7f5f0d-b917-49fd-a5d2-01f5c67e61fe',1,1,NULL,NULL,NULL,NULL,NULL),('CR',2,'2023-11-23 14:51:57.186000','948c7f1f-e943-4419-83d4-ae870a6f26a1',1,2,NULL,NULL,NULL,NULL,NULL),('CR',4,'2023-11-23 14:51:57.186000','5afafc17-853d-49a7-b8d9-d3ef43d9b95f',1,4,NULL,NULL,NULL,NULL,NULL);
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
INSERT INTO `fact_ancestors` VALUES (1,1),(2,1),(4,1),(2,2),(4,4);
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
INSERT INTO `fact_before` VALUES (1,1),(2,2),(4,4);
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
                                `survey_schedule_id` bigint DEFAULT NULL,
                                `author_id` bigint DEFAULT NULL,
                                `measurement_session_type_id` bigint DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                KEY `FKda063wx5ks7famw876ak7mkgd` (`survey_schedule_id`),
                                KEY `FKi0twlsid6hkkoy9aq19qumbx2` (`author_id`),
                                KEY `FKp5vl6pgqbb3xwp8t89jfsyb23` (`measurement_session_type_id`),
                                CONSTRAINT `FKda063wx5ks7famw876ak7mkgd` FOREIGN KEY (`survey_schedule_id`) REFERENCES `survey_schedules` (`id`),
                                CONSTRAINT `FKi0twlsid6hkkoy9aq19qumbx2` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
                                CONSTRAINT `FKp5vl6pgqbb3xwp8t89jfsyb23` FOREIGN KEY (`measurement_session_type_id`) REFERENCES `measurement_session_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fact_context`
--

LOCK TABLES `fact_context` WRITE;
/*!40000 ALTER TABLE `fact_context` DISABLE KEYS */;
INSERT INTO `fact_context` VALUES ('EX',1,'41c1ca0e-7253-4246-b930-636e62f129ae',3,'2025-03-29 16:27:17.297000','CONCLUDED',_binary '',1,1,1),('EX',52,'8ec08235-2480-4279-ae41-6e493e728c0f',2,'2023-11-23 15:54:26.487000','TODO',NULL,52,1,1),('EX',102,'63f0d581-1b9c-4847-b8a7-d7fb5470a527',0,'2025-03-29 19:05:50.322000','TODO',NULL,102,NULL,NULL),('EX',103,'81ab0abf-53f7-4d2a-b866-f4ae47efb1f8',0,'2025-03-29 19:09:12.084000','TODO',NULL,103,NULL,NULL),('EX',152,'63864e2e-a411-4196-b563-59577fb24ad7',0,'2025-04-01 16:49:41.123000','TODO',NULL,152,NULL,NULL),('EX',153,'6f3c1e68-585c-4f6b-9855-c3f38151be2c',0,'2025-04-01 16:56:11.904000','TODO',NULL,153,NULL,NULL),('EX',202,'c7994464-89f4-4bba-9579-4b7eaed5ad92',0,'2025-04-01 16:57:59.253000','TODO',NULL,202,NULL,NULL);
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
INSERT INTO `fact_links` VALUES ('IN',1,NULL,'39d641a0-9390-452c-9d7b-e56ecbd305d7',1,2,1,NULL),('IN',3,NULL,'82eded5c-4c97-4e55-ae44-704ae21a7207',1,4,3,NULL);
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
INSERT INTO `facts` VALUES ('CX',1,'ACTIVE',NULL,NULL,'49b006db-d9f2-465c-a5c9-1b03d516c866',NULL,NULL,NULL,1,NULL,1,1,NULL,NULL),('QT',2,'ACTIVE',NULL,NULL,'b9254704-3013-436a-99d1-849f2e7c4c8f',NULL,NULL,70,1,NULL,2,2,NULL,9),('QT',4,'ACTIVE',NULL,NULL,'07d15e9f-e07a-49a8-a8a9-fd4b7b199e5f',NULL,NULL,170,1,NULL,4,4,NULL,8);
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
                                             UNIQUE KEY `UK_m0s5yk68201xhbnbpudvv26f5` (`name`),
                                             KEY `FKng01v3s7hypn7x0i1ju1001f0` (`type_id`),
                                             CONSTRAINT `FKng01v3s7hypn7x0i1ju1001f0` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `measurement_session_types`
--

LOCK TABLES `measurement_session_types` WRITE;
/*!40000 ALTER TABLE `measurement_session_types` DISABLE KEYS */;
INSERT INTO `measurement_session_types` VALUES (1,'Una struttura minimale di prova','Test_base',8,'bee21769-1f63-4689-a754-a12d5c58e88e',1),(2,NULL,'Careggi',24,'f9c5bb5b-a9f9-4063-8626-d1597ef4342d',1);
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
                            KEY `FKo887ey1coinxqhwi12ehu5je6` (`observable_entity_id`),
                            CONSTRAINT `FKo887ey1coinxqhwi12ehu5je6` FOREIGN KEY (`observable_entity_id`) REFERENCES `observable_entities` (`id`)
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
-- Table structure for table `observable_entities`
--

DROP TABLE IF EXISTS `observable_entities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `observable_entities` (
                                       `id` bigint NOT NULL,
                                       `identifier` varchar(255) DEFAULT NULL,
                                       `uuid` varchar(255) DEFAULT NULL,
                                       `dest_id` bigint DEFAULT NULL,
                                       `identifier_id` bigint DEFAULT NULL,
                                       `origin_id` bigint DEFAULT NULL,
                                       PRIMARY KEY (`id`),
                                       KEY `FK58d0w5iot0xappyumw740nrit` (`dest_id`),
                                       KEY `FKkj6oq41nco9l3e8cp0qak9ets` (`identifier_id`),
                                       KEY `FK6a5to19lepw1o3anxi2o57j4i` (`origin_id`),
                                       CONSTRAINT `FK58d0w5iot0xappyumw740nrit` FOREIGN KEY (`dest_id`) REFERENCES `observable_entity_actions` (`id`),
                                       CONSTRAINT `FK6a5to19lepw1o3anxi2o57j4i` FOREIGN KEY (`origin_id`) REFERENCES `observable_entity_actions` (`id`),
                                       CONSTRAINT `FKkj6oq41nco9l3e8cp0qak9ets` FOREIGN KEY (`identifier_id`) REFERENCES `observable_entity_identifiers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `observable_entities`
--

LOCK TABLES `observable_entities` WRITE;
/*!40000 ALTER TABLE `observable_entities` DISABLE KEYS */;
INSERT INTO `observable_entities` VALUES (1,NULL,'34bb554d-0a07-4422-96cf-090cc256a315',2,NULL,1),(2,NULL,'caa9f176-068d-47a1-8b2d-f5f440e2511c',NULL,NULL,2),(3,NULL,'bd94107b-8bd3-4ca4-9103-653bd5680b1a',NULL,NULL,3);
/*!40000 ALTER TABLE `observable_entities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `observable_entity_actions`
--

DROP TABLE IF EXISTS `observable_entity_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `observable_entity_actions` (
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
                                             KEY `FKrp5njy79u4xq2t59957qeaqov` (`author_id`),
                                             KEY `FK42mwugopao6gvd8osfco2hau7` (`target_id`),
                                             KEY `FKtm6qkxf3tjk15n8589v30tkic` (`source_id`),
                                             KEY `FK9kpbtj56b33jl3llqlhynb2nm` (`source1_id`),
                                             KEY `FKntxympskqh6q1bqaa8kn5d1ay` (`source2_id`),
                                             KEY `FK1sfb1mm2aomamtb8y02m7lrij` (`target1_id`),
                                             KEY `FKikkikqvwqat51f3m20uef8yj5` (`target2_id`),
                                             CONSTRAINT `FK1sfb1mm2aomamtb8y02m7lrij` FOREIGN KEY (`target1_id`) REFERENCES `observable_entities` (`id`),
                                             CONSTRAINT `FK42mwugopao6gvd8osfco2hau7` FOREIGN KEY (`target_id`) REFERENCES `observable_entities` (`id`),
                                             CONSTRAINT `FK9kpbtj56b33jl3llqlhynb2nm` FOREIGN KEY (`source1_id`) REFERENCES `observable_entities` (`id`),
                                             CONSTRAINT `FKikkikqvwqat51f3m20uef8yj5` FOREIGN KEY (`target2_id`) REFERENCES `observable_entities` (`id`),
                                             CONSTRAINT `FKntxympskqh6q1bqaa8kn5d1ay` FOREIGN KEY (`source2_id`) REFERENCES `observable_entities` (`id`),
                                             CONSTRAINT `FKrp5njy79u4xq2t59957qeaqov` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
                                             CONSTRAINT `FKtm6qkxf3tjk15n8589v30tkic` FOREIGN KEY (`source_id`) REFERENCES `observable_entities` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `observable_entity_actions`
--

LOCK TABLES `observable_entity_actions` WRITE;
/*!40000 ALTER TABLE `observable_entity_actions` DISABLE KEYS */;
INSERT INTO `observable_entity_actions` VALUES ('CR',1,'2023-11-23 11:51:25.481000','6e4ef6f3-e842-4f88-8d5a-7a51df0d1e4e',1,1,NULL,NULL,NULL,NULL,NULL),('MD',2,'2023-11-23 16:03:06.689000','c75040f9-cfa9-4b10-96fe-a62167b13252',1,2,1,NULL,NULL,NULL,NULL),('CR',3,'2025-03-29 18:54:02.571000','ffdb3cb8-b692-4818-967f-601740d26690',1,3,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `observable_entity_actions` ENABLE KEYS */;
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
                                            KEY `FKl4hp5amm770l3deoi1ju6l2kr` (`before_observable_entity_id`),
                                            CONSTRAINT `FKks44dohnobuf8g3y856lf7l02` FOREIGN KEY (`observable_entity_id`) REFERENCES `observable_entities` (`id`),
                                            CONSTRAINT `FKl4hp5amm770l3deoi1ju6l2kr` FOREIGN KEY (`before_observable_entity_id`) REFERENCES `observable_entities` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `observable_entity_before`
--

LOCK TABLES `observable_entity_before` WRITE;
/*!40000 ALTER TABLE `observable_entity_before` DISABLE KEYS */;
INSERT INTO `observable_entity_before` VALUES (1,1),(2,1),(2,2),(3,3);
/*!40000 ALTER TABLE `observable_entity_before` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `observable_entity_identifiers`
--

DROP TABLE IF EXISTS `observable_entity_identifiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `observable_entity_identifiers` (
                                                 `id` bigint NOT NULL,
                                                 `code` varchar(255) NOT NULL,
                                                 `uuid` varchar(255) DEFAULT NULL,
                                                 `version` bigint DEFAULT NULL,
                                                 PRIMARY KEY (`id`),
                                                 UNIQUE KEY `UK_nkpwpb19bvpq78xmjtkrq52wn` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `observable_entity_identifiers`
--

LOCK TABLES `observable_entity_identifiers` WRITE;
/*!40000 ALTER TABLE `observable_entity_identifiers` DISABLE KEYS */;
/*!40000 ALTER TABLE `observable_entity_identifiers` ENABLE KEYS */;
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
INSERT INTO `phenomena` VALUES (-47,'Bianchi Gianni',NULL,NULL,NULL,'85cef78c-9336-439c-9579-a09ddd44843f'),(1,'admin admin',NULL,NULL,NULL,'c3c6846a-a5e5-4a12-a1f4-563065fe4cdd'),(2,'Rossi Mario',NULL,NULL,NULL,'4594c745-0ad4-4d36-97c2-3ac59cd388e4');
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
INSERT INTO `qualifications` VALUES (1,'medico',NULL,NULL,'5b180e25-e915-4247-962a-18eff20aedd3');
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
INSERT INTO `roles` VALUES (1,'administrator','ca35e95a-9fca-4403-b39c-74dce462a3c4'),(2,'basic user','9473b63c-0b0a-4efd-a155-65ef26b9632f');
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
INSERT INTO `selectors` VALUES (1,'0e56bf8e-0c8d-4c94-b65a-2a67a7430ede',NULL,1,2),(2,'6f4a5eec-7300-457c-9132-3be4db1abfea',NULL,2,2),(3,'31cacf40-81f8-4bd7-833d-206fb03e24a3',NULL,3,3),(4,'486f7517-73ac-4a09-850b-2b8760a6b44e',NULL,4,3),(5,'32e079aa-c7e3-4878-b40f-938e7d7acd1a',NULL,5,1),(6,'a370cbe6-c0aa-452b-a954-1d744391e0cd',NULL,6,1);
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
INSERT INTO `sequence_table` VALUES ('agenda',2),('atc',0),('authorization',100),('drug_index',0),('exam_type',2),('expression',0),('expression_link',0),('fact',150),('fact_action',150),('fact_context',300),('fact_link',150),('fact_query',0),('fact_value',0),('health_service',0),('icd_9_cm',0),('icd9cm',0),('message',0),('observable_entity',3),('observable_entity_action',3),('observable_entity_identifier',0),('phenomenon',51),('qualification',0),('role',0),('selector',100),('staff',3),('survey_schedule',300),('type',150),('type_link',100),('unit',0),('unit_use',4),('user',3),('viewer',100),('viewer_link',100),('viewer_use',100);
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
                            UNIQUE KEY `UK_rirbir8murqcupq95kgca8qc9` (`internal_code`),
                            KEY `FKp3djcrsm01m4hvwsrdld4ofob` (`agenda_id`),
                            CONSTRAINT `FKp3djcrsm01m4hvwsrdld4ofob` FOREIGN KEY (`agenda_id`) REFERENCES `agendas` (`id`)
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
                         `branch_name` varchar(255) DEFAULT NULL,
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
INSERT INTO `staff` VALUES (1,NULL,'000000','9e9e552e-23c7-405d-b727-14fa2490c36b',NULL,1,1),(2,NULL,NULL,'0e1ed72f-5e0e-41d9-8b37-7838188e896b',1,2,2),(3,NULL,NULL,'32475adf-f537-4dbc-b95a-5c68b88747f6',NULL,-47,3);
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
INSERT INTO `staff_agendas` VALUES (1,1),(2,1),(3,1);
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
                                            KEY `FK63c1jrjdr3jtm98broixw1v4` (`service_id`),
                                            CONSTRAINT `FK63c1jrjdr3jtm98broixw1v4` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`),
                                            CONSTRAINT `FK7smp9csy21h26g51aii9gvfn8` FOREIGN KEY (`survey_schedule_id`) REFERENCES `survey_schedules` (`id`)
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
                                    UNIQUE KEY `UK577svnt3hw4qmcptb0sif4py1` (`booking_code`,`acceptance_code`),
                                    UNIQUE KEY `UK_3q9npuf7n7qq4dy4gokw9kmri` (`booking_code`),
                                    UNIQUE KEY `UK6ub42m2j09go72kou31sa28b0` (`booking_code`,`acceptance_code`),
                                    KEY `FKqbu91romxyf60rjd8i8qxt0yo` (`agenda_id`),
                                    KEY `FK8exap5wmg8kmb1g1rx3by21yt` (`observable_entity_id`),
                                    CONSTRAINT `FK8exap5wmg8kmb1g1rx3by21yt` FOREIGN KEY (`observable_entity_id`) REFERENCES `observable_entities` (`id`),
                                    CONSTRAINT `FKqbu91romxyf60rjd8i8qxt0yo` FOREIGN KEY (`agenda_id`) REFERENCES `agendas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_schedules`
--

LOCK TABLES `survey_schedules` WRITE;
/*!40000 ALTER TABLE `survey_schedules` DISABLE KEYS */;
INSERT INTO `survey_schedules` VALUES (1,'ACC20231123155157AG001','BOOK20231123155157AG001','2023-11-23 14:51:57.025000',NULL,'ACCEPTED','f9c9edd7-4497-457b-b57c-52d098f41043',1,1),(52,'ACC20231123165426AG001','BOOK20231123165426AG001',NULL,NULL,'ACCEPTED','84037c0f-9eb5-46cc-8faf-bf71a7d5820c',1,1),(102,'ACC20250329190550AG001','BOOK20250329190550AG001','2025-03-29 19:05:50.322000',NULL,'ACCEPTED','95fba055-3454-490b-8345-0f6ee07f24e7',1,3),(103,'ACC20250329190912AG001','BOOK20250329190912AG001','2025-03-29 19:09:12.084000',NULL,'ACCEPTED','b479d3ed-ca86-49c7-bb0c-59fab962fa9c',1,2),(152,'ACC20250401164941AG001','BOOK20250401164941AG001','2025-04-01 16:49:41.123000',NULL,'ACCEPTED','8ac557d0-634d-4d76-b466-8e343ef22f26',1,3),(153,'ACC20250401165611AG001','BOOK20250401165611AG001','2025-04-01 16:56:11.904000',NULL,'ACCEPTED','2df4153d-1110-4996-a98f-7f0f6981d9ab',1,2),(202,'ACC20250401165759AG002','BOOK20250401165759AG002','2025-04-01 16:57:59.253000',NULL,'ACCEPTED','118c6230-a702-493a-93ed-5725953942e0',2,3);
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
INSERT INTO `type_ancestors` VALUES (1,1),(2,1),(3,1),(4,1),(2,2),(3,3),(4,4),(52,52);
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
INSERT INTO `type_links` VALUES (1,1,1,'Peso',1,'03d659d7-3564-465e-a972-a4d9d870b88b',NULL,1,2),(2,1,1,'BMI',2,'3dac8fe5-e2fa-49dc-936d-d461ab1c82a5',NULL,1,3),(3,1,1,'Altezza',0,'9fc9132d-1b00-4621-9676-40639495e87d',NULL,1,4);
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
INSERT INTO `types` VALUES ('CX',1,_binary '\0',NULL,'AltezzaPesoBMI',_binary '\0',_binary '\0',NULL,NULL,'23476e35-5887-4b8f-b784-f8ef61e3d39a',NULL,NULL),('QT',2,_binary '',NULL,NULL,NULL,_binary '\0',NULL,NULL,'ef6fd490-f202-487e-9f2a-ac76db634c93',NULL,NULL),('QT',3,_binary '',NULL,NULL,NULL,_binary '\0',NULL,NULL,'fe938bdd-4d58-4192-b64e-c7ebe1d3059a',NULL,NULL),('QT',4,_binary '',NULL,NULL,NULL,_binary '\0',NULL,NULL,'0c96dbb7-7e65-41c2-addd-e791b5599a0d',NULL,NULL),('QT',52,_binary '\0',NULL,'Fever',_binary '\0',_binary '\0',NULL,NULL,'26d00931-8e60-427a-b784-28f73b01bd10',NULL,NULL);
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
INSERT INTO `unit_uses` VALUES (1,2,5,'389d446d-02ef-4489-8790-ea6b8d45fa52',2,9),(2,2,4,'c326cfcd-46a4-490d-bbb3-c33541bf8e6d',3,27),(3,0,3,'4605413e-1a11-43fb-b5fb-256d301c88e6',4,8),(4,2,2,'4e475dfd-c2ae-4271-8ae1-bd08a615fdcc',52,15);
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
INSERT INTO `units` VALUES (1,'Millimetri quadrati','mmq','d3a0b618-c782-423d-b388-99905a8bd63d'),(2,'Millimetri di mercurio','mmHg','f4ea1cf8-3a91-4855-8229-1763dd07fc62'),(3,'Micron','micron','000d69dc-b7d1-49fd-8f6b-6836447edfb4'),(4,'Diottria','D','553bd079-88e1-4f69-8686-efe52d7ebdc8'),(5,'Grado sessagesimale','°','2e0b5326-5e39-4f00-9a84-b8b954117327'),(6,'Sistema Internazionale','° INT','807e18a5-0591-4705-b318-a08c456402d8'),(7,'Sistema TABO','° TABO','1c06ff91-1cf0-461b-94b6-01501757e0cf'),(8,'Centimetro','cm','2f627876-f499-47db-8ca9-11a66c24bb13'),(9,'Chilogrammo','kg','9b9c515a-5939-4175-9a5d-f1acb8448c51'),(10,'Millimetro','mm','44e8d02a-6b24-4da1-8a3b-6ba94c709902'),(11,'Percentuale','%','5e65189a-749d-47e5-b6ab-e730c981db30'),(12,'ml/m2','ml/m<sup>2</sup>','553b4125-4c63-4255-bfb9-065b70b5c051'),(13,'gr/m2','g/m<sup>2</sup>','17103b4a-d821-4c8d-8932-4b92adea1678'),(14,'Metro cubo','m<sup>3</sup>','dbfb2c30-7684-46d8-aa1a-5275856db4fe'),(15,'(Indefinito)',' ','9a000751-254d-4f9f-b7c6-96a3c46d5947'),(16,'b/min','b/min','4949265d-f36e-4891-9880-c40d96984f6a'),(17,'Watt/3min','Watt/3min','2349c0da-d835-4937-9c66-65a236a24175'),(18,'MHz','MHz','3311cc5d-ba75-4b7d-abd9-51ffa70a2eb6'),(19,'Minuto','min','b9508667-8c56-498f-993e-b1d5a1cdac4c'),(20,'Watt','Watt','eca3a218-2e58-46f8-bcff-2fc63427040d'),(21,'Metro al secondo','m/sec','e63e1cf5-734a-4059-ab7a-d6d1e670f3df'),(22,'Metro','m','648272c2-6159-4cc1-a226-0fac6cdec58e'),(23,'Anno','anno/i','61706366-b535-4631-8e1f-b8dc3eb60537'),(24,'Mese','mese/i','c11da5df-a7a0-4caf-84a4-78fa15198c37'),(25,'Millilitro','ml','546684c1-eb48-43f8-b650-48cdf4b0fc85'),(26,'Metro Quadrato','m<sup>2</sup>','ec0418b6-4e6f-4aba-ad17-99681cc24426'),(27,'kg/m2','kg/m<sup>2</sup>','b190f86b-e723-477c-86fd-89b8a1523075'),(28,'Centimetro quadrato','cm<sup>2</sup>','2527c871-3324-4856-a696-c57c530ec638'),(29,'mm/m2','mm/m<sup>2</sup>','3a6578fb-f30b-47c4-ba70-09c7cda0d538'),(30,'Grammo','g','2523dec5-fa55-48c9-bd24-4cb206be601c'),(31,'Secondo','s','96e8c0fa-306f-47cb-818c-680376afc4f7'),(32,'Ora','h','d219a011-2c01-46d0-89d8-55a4165f2b2e'),(33,'bpm','bpm','13033faf-dd05-4feb-ad76-6a4eb76d5931'),(34,'BEV/H','BEV/H','ad39e69d-c301-4717-9529-5e53801a9b1d');
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
INSERT INTO `user_qualifications` VALUES (1,1),(2,1),(3,1);
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
INSERT INTO `user_roles` VALUES (1,1),(1,2),(2,2),(3,2);
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
INSERT INTO `users` VALUES (1,_binary '\0',NULL,'admin','O0+DAMjF2QZ4t9w/TVn5Ct4QgjarQQUFqdWXnh+VTV8=',NULL,'admin','administrator','8e151682-e1ec-444a-b735-84facaca1547'),(2,_binary '\0',NULL,'Mario','73l8gRjwLftklgfdXT+MdiMEjJwGPVMsyVxe16iYpk8=',NULL,'Rossi','mario.rossi','691624e4-de48-463d-a731-e354ec0fccca'),(3,_binary '\0',NULL,'Gianni','73l8gRjwLftklgfdXT+MdiMEjJwGPVMsyVxe16iYpk8=',NULL,'Bianchi','gianni.bianchi','c11a0f2a-6e65-4004-8716-a985cdf90ce2');
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
INSERT INTO `viewer_ancestors` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(2,2),(3,3),(4,3),(5,3),(6,3),(7,3),(8,3),(9,3),(10,3),(11,3),(12,3),(13,3),(14,3),(15,3),(16,3),(17,3),(18,3),(4,4),(5,4),(6,4),(7,4),(8,4),(5,5),(6,6),(7,6),(8,6),(7,7),(8,8),(9,9),(10,9),(11,9),(12,9),(13,9),(10,10),(11,11),(12,11),(13,11),(12,12),(13,13),(14,14),(15,14),(16,14),(17,14),(18,14),(15,15),(16,15),(17,15),(16,16),(17,17),(18,18);
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
INSERT INTO `viewer_links` VALUES ('SOTTOVISTA',1,0,'bdc4585c-f050-4584-9fca-dfba60d9636f',NULL,NULL,1,2),('SOTTOVISTA',2,1,'27a39685-4ca5-4871-b0ee-3593e5f54121',NULL,NULL,1,3),('SOTTOVISTA',3,2,'8cbd34a8-6598-4f80-ae2e-e69ce937f376',NULL,NULL,3,4),('SOTTOVISTA',4,0,'46a29ec6-e8ee-461d-9bc8-3263914e45b5',NULL,1,4,5),('SOTTOVISTA',5,1,'419cd7e7-a3ab-48a5-b5e2-b3d15c88e1f1',NULL,2,4,6),('SOTTOVISTA',6,0,'3deb87b0-56cd-4a37-a05d-41bc366757b0',NULL,NULL,6,7),('SOTTOVISTA',7,1,'d076c478-804c-4bd2-864b-4f4860942841',NULL,NULL,6,8),('SOTTOVISTA',8,0,'76bdc58f-273c-4d73-a635-4da3511e9551',NULL,NULL,3,9),('SOTTOVISTA',9,0,'9edf804b-8612-4681-a791-8041babc3eb7',NULL,3,9,10),('SOTTOVISTA',10,1,'529ba040-7950-4063-a771-47d9c452ccbd',NULL,4,9,11),('SOTTOVISTA',11,0,'31e7c58a-5be6-4337-9fb3-1a9e7e32a230',NULL,NULL,11,12),('SOTTOVISTA',12,1,'11053d65-3b57-46b6-b21f-df044daff7c8',NULL,NULL,11,13),('SOTTOVISTA',13,1,'0a9ce352-7ab2-4775-90f8-a86c1f8dc6fd',NULL,NULL,3,14),('SOTTOVISTA',14,1,'c8b42bc9-0147-494c-a4ce-ddc5d630f9d4',NULL,5,14,15),('SOTTOVISTA',15,0,'44e200c9-2e8f-48e9-97fc-9da3257c51e7',NULL,NULL,15,16),('SOTTOVISTA',16,1,'8ddfb3cf-31f5-4ac4-8edd-5cce01b14089',NULL,NULL,15,17),('SOTTOVISTA',17,0,'a7256a71-7f92-471e-a8ac-43eb3e1d53d4',NULL,6,14,18);
/*!40000 ALTER TABLE `viewer_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viewer_uses`
--

DROP TABLE IF EXISTS `viewer_uses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `viewer_uses` (
                               `id` bigint NOT NULL,
                               `context` varchar(255) DEFAULT NULL,
                               `uuid` varchar(255) DEFAULT NULL,
                               `qualification_id` bigint NOT NULL,
                               `viewer_id` bigint NOT NULL,
                               `measurement_session_type_id` bigint NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FK9qkl2h4bcaikw51rgppcl24gu` (`qualification_id`),
                               KEY `FKpb8nrdnguclwhfu6his81rknr` (`viewer_id`),
                               CONSTRAINT `FK9qkl2h4bcaikw51rgppcl24gu` FOREIGN KEY (`qualification_id`) REFERENCES `qualifications` (`id`),
                               CONSTRAINT `FKpb8nrdnguclwhfu6his81rknr` FOREIGN KEY (`viewer_id`) REFERENCES `viewers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viewer_uses`
--

LOCK TABLES `viewer_uses` WRITE;
/*!40000 ALTER TABLE `viewer_uses` DISABLE KEYS */;
INSERT INTO `viewer_uses` VALUES (1,'EDIT','b56b291e-c62a-4064-a58c-39c00d0533a4',1,1,1);
/*!40000 ALTER TABLE `viewer_uses` ENABLE KEYS */;
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
INSERT INTO `viewers` VALUES ('BOX',1,_binary '\0','.fieldset {background-color: #f9f9f9; margin: 5px;}\r\n.fieldset .fieldset {background-color: white;}\r\n.fieldset .fieldset  .fieldset {background-color: #f9f9f9;}\r\n.label_text {font-weight: bold;}\r\n.outputPath_text {font-weight: bold;}\r\n.grid_table .grid_cell_0 {width: 25%; text-align: right; padding-right: 15px; vertical-align: top;}\r\n.grid_cell_1 .grid_table {width: auto;}\r\n.grid_cell_1 .grid_table .grid_cell_0 {padding-right: 0px; width: auto;}\r\n.grid_cell_1 .grid_table .grid_cell_1 {padding-left: 6px;}\r\n.box_label {font-weight: bold; background: transparent; background-image: -webkit-linear-gradient(bottom, #f9f9f9 50%, transparent 50%); padding-left: 6px; padding-right: 6px;}\r\n.fieldset .fieldset .box_label {font-weight: bold; background: transparent; background-image: -webkit-linear-gradient(bottom, transparent 50%, #f9f9f9 50%); padding-left: 6px; padding-right: 6px;}\r\n.fieldset .fieldset .fieldset .box_label {font-weight: bold; background: transparent; background-image: -webkit-linear-gradient(bottom, #f9f9f9 50%, transparent 50%); padding-left: 6px; padding-right: 6px;}\r\n.fieldset {padding: 10px 0 3px 0;}\r\n',NULL,'AltezzaPesoBMI_default_edit',NULL,'21ec53bc-e150-428f-a62d-835707e389c4',NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),('LABEL',2,NULL,NULL,NULL,NULL,NULL,'4492f5fd-33ef-4ff0-9a80-201a80fed616',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'AltezzaPesoBMI',NULL,NULL,NULL,NULL,NULL),('GRID',3,NULL,NULL,NULL,NULL,NULL,'31751d4f-1b2f-48b8-9b13-f9b474b8c5d5',NULL,_binary '\0',NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('GRID',4,NULL,NULL,NULL,NULL,NULL,'a8e8fb7e-9d2a-47b4-92d0-192bcf7bce98',NULL,_binary '\0',NULL,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('OUTPUT_PATH',5,NULL,NULL,NULL,NULL,NULL,'8a531c2b-dfc8-4b67-85a7-21cdd830837d',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('GRID',6,NULL,NULL,NULL,NULL,NULL,'59b6cd64-0fc8-41e5-bb66-fd957a73d375',NULL,_binary '\0',NULL,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('INPUTTEXT',7,NULL,NULL,NULL,NULL,NULL,'d3775d8d-ebda-4d97-a88e-6e47801a2e99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('COMBO',8,NULL,NULL,NULL,NULL,NULL,'643a1cc7-e69a-4232-a19e-70e1b3b7fe1b',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('GRID',9,NULL,NULL,NULL,NULL,NULL,'610e02cd-5171-4246-bbd0-6800a09173bf',NULL,_binary '\0',NULL,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('OUTPUT_PATH',10,NULL,NULL,NULL,NULL,NULL,'ec249edc-7a00-428e-81a6-befadcdb8ae2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('GRID',11,NULL,NULL,NULL,NULL,NULL,'9d77938a-4e45-410b-aa81-894d48e7382b',NULL,_binary '\0',NULL,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('INPUTTEXT',12,NULL,NULL,NULL,NULL,NULL,'f4e41c51-19a8-4cbb-8b52-6d02b508b7c3',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('COMBO',13,NULL,NULL,NULL,NULL,NULL,'0ed784d0-9c1c-4403-9153-7abeb8ac8cf6',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('GRID',14,NULL,NULL,NULL,NULL,NULL,'bf57b4ff-0126-4f93-990a-d814b32174aa',NULL,_binary '\0',NULL,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('GRID',15,NULL,NULL,NULL,NULL,NULL,'d502f0c2-b5fc-4d31-aa2f-c9f6e357b00c',NULL,_binary '\0',NULL,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('INPUTTEXT',16,NULL,NULL,NULL,NULL,NULL,'b592519d-6506-455e-806b-23b06ff38e5b',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('COMBO',17,NULL,NULL,NULL,NULL,NULL,'804b051c-cae8-4db7-962e-5635cdaa3e4d',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('OUTPUT_PATH',18,NULL,NULL,NULL,NULL,NULL,'8eb27d37-0e8b-43b4-960c-d5b9fa303fb1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `viewers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-31 19:08:24
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));