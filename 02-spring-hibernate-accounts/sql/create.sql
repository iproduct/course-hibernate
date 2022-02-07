DROP TABLE IF EXISTS `users` ;
CREATE TABLE `users` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `username` varchar(40) DEFAULT NULL,
                         `password` varchar(80) DEFAULT NULL,
                         `fname` varchar(40) DEFAULT NULL,
                         `lname` varchar(40) DEFAULT NULL,
                         `role` varchar(40) DEFAULT NULL,
                         `active` tinyint(1) DEFAULT NULL,
                         `created` timestamp NULL DEFAULT NULL,
                         `updated` timestamp NULL DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `articles` ;
CREATE TABLE `articles` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `title` varchar(80) DEFAULT NULL,
                            `content` varchar(2048) DEFAULT NULL,
                            `author_id` bigint(20) DEFAULT NULL,
                            `picture_url` varchar(256) DEFAULT NULL,
                            `created` timestamp NULL DEFAULT NULL,
                            `updated` timestamp NULL DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `articles`
    ADD INDEX `author_id_fk_idx` (`author_id` ASC) VISIBLE;
;
ALTER TABLE `articles`
    ADD CONSTRAINT `author_id_fk`
        FOREIGN KEY (`author_id`)
            REFERENCES `hibernate_native`.`users` (`id`)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT;
