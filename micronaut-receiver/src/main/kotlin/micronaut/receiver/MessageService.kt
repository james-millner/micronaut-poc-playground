package micronaut.receiver

import com.vladsch.kotlin.jdbc.Row
import com.vladsch.kotlin.jdbc.session
import javax.inject.Inject
import javax.inject.Singleton
import com.vladsch.kotlin.jdbc.sqlQuery
import mu.KLogging
import javax.sql.DataSource

@Singleton
class MessageService @Inject constructor(
    val dataSource: DataSource
) {

    companion object : KLogging()

    //   private  val session by lazy { session(url = url,user=username,password = password) }
    private val session by lazy { session(dataSource) }

    init {
        try {
            session(dataSource).execute(sqlQuery(
            """
            CREATE TABLE IF NOT EXISTS messages (
              id varchar(64) NOT NULL ,
              greeting varchar(64) DEFAULT NULL
            ) ENGINE=InnoDB
            """.trimIndent()))
        }catch (e:Exception){
            logger.error(e.message)
        }

    }

    fun getAllMessages(): List<Message> {
        val allUsersQuery = sqlQuery("select * from messages")
        return session.list(allUsersQuery) { row -> toUser(row) }
    }

    fun saveMessage(message: Message): Message? {
        val updatedUserId = if (message.id != null) {
            session.updateGetId( sqlQuery("""
            INSERT INTO messages (id, greeting)
            VALUES (:id, :greeting)
        """.trimIndent(), mapOf("id" to message.id, "greeting" to message.greeting))
            )
        } else {
            sqlQuery("""
            UPDATE users messages greeting=:greeting, WHERE id=:id
        """.trimIndent(), mapOf("id" to message.id, "greeting" to message.greeting))
            message.id
        }
        return getMessageById(updatedUserId)
    }

    fun getMessageById(id:Int?=0):Message? = session.first(sqlQuery("select * from messages where id = ?", id), toUser)

}

val toUser: (Row) -> Message = { row ->
    println(row)
    Message(
        row.string("id") ,
        row.string("greeting")
    )
}
