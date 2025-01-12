CREATE TABLE IF NOT EXISTS tasks (
	id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    `date` DATETIME NOT NULL,
    `description` TEXT NOT NULL,
    `status` ENUM("PENDING", "CONCLUDED", "OVERDUE"),

    CONSTRAINT PK_tasks PRIMARY KEY (id)
) ENGINE = InnoDB;