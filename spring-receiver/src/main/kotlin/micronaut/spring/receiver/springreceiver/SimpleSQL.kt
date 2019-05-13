package micronaut.spring.receiver.springreceiver

import org.intellij.lang.annotations.Language

class SimpleSQL {

    companion object {
         @Language("MySQL")
            const val SELECT_SINGLE_MESSAGE_BY_GREETING =
                 """
                    SELECT * FROM messages where greeting = :greeting
                """

        @Language("MySQL")
        const val INSERT_SINGLE_MESSAGE_BY_GREETING =
                """
                INSERT INTO messages (greeting)
                VALUES (:greeting)
                """

        @Language("MySQL")
        const val SELECT_SINGLE_MESSAGE_BY_ID =
                """SELECT * FROM messages WHERE id = :id"""

        @Language("MySQL")
        const val CREATE_TABLE =
                """
                    CREATE TABLE IF NOT EXISTS messages (
                    id INT(64) NOT NULL AUTO_INCREMENT,
                    greeting varchar(64) DEFAULT NULL,
                    PRIMARY KEY (id))
                    ENGINE=InnoDB
                    """

        @Language("MySQL")
        const val DELETE_TABLE_CONTENTS =
                """ DELETE FROM messages"""


    }
    }