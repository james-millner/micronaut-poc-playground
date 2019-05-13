package micronaut.spring.receiver.springreceiver

import micronaut.spring.receiver.springreceiver.SimpleSQL.Companion.CREATE_TABLE
import micronaut.spring.receiver.springreceiver.SimpleSQL.Companion.DELETE_TABLE_CONTENTS
import micronaut.spring.receiver.springreceiver.SimpleSQL.Companion.INSERT_SINGLE_MESSAGE_BY_GREETING
import micronaut.spring.receiver.springreceiver.SimpleSQL.Companion.SELECT_SINGLE_MESSAGE_BY_GREETING
import micronaut.spring.receiver.springreceiver.SimpleSQL.Companion.SELECT_SINGLE_MESSAGE_BY_ID
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.lang.Exception
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

data class Greeting(val id: Int?, val greeting: String)

@Service
class GreetingService(private val db: NamedParameterJdbcTemplate) {

    @PostConstruct
    fun createTable() = db.jdbcTemplate.update(CREATE_TABLE)

    @PreDestroy
    fun clearTableContent() = db.jdbcTemplate.update(DELETE_TABLE_CONTENTS)

    fun returnGreetingId(greeting: String): Greeting {

        saveGreeting(greeting)

        return db.queryForObject(
                SELECT_SINGLE_MESSAGE_BY_GREETING, mapOf("greeting" to greeting)
        ) { rs, _ -> Greeting(rs.getInt(1), rs.getString(2)) } ?: throw Exception("No")
    }

    private fun saveGreeting(greeting: String) =
            db.update(INSERT_SINGLE_MESSAGE_BY_GREETING, mapOf("greeting" to greeting))

    fun getGreetingById(id: Int?): Greeting =
            db.queryForObject(SELECT_SINGLE_MESSAGE_BY_ID, mapOf("id" to id)
            ) { rs, _ -> Greeting(rs.getInt(1), rs.getString(2)) } ?: throw Exception("this wasn't here bro.")

}

