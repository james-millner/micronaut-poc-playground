package micronaut.receiver

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "table1")
data class Message (@Id val id: String, val greeting: String)