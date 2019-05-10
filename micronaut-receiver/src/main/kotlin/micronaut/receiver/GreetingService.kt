package micronaut.receiver

import mu.KLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.sql.DataSource

object Greeting : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val greeting = varchar("name", 100)
}

@Singleton
class GreetingService @Inject constructor(val dataSource: DataSource) {

    companion object : KLogging()

    init {
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(Greeting)
        }
    }

    fun save(greeting: String) = transaction {
        Greeting.insert {
            it[Greeting.greeting] = greeting
        } get Greeting.id
    }

    fun getAllGreetings() = transaction {
        Greeting.selectAll().map {
            it[Greeting.greeting]
        }.toList()
    }

    fun getGreetingById(id: Int) = transaction {
        Greeting.select { Greeting.id eq (id) }.withDistinct().map {
            it[Greeting.greeting]
        }.first()
    }

}

