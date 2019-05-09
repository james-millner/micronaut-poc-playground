package micronaut.receiver

import mu.KLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
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
class GreetingService @Inject @Named("mysql") constructor(val dataSource: DataSource) {

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

}

