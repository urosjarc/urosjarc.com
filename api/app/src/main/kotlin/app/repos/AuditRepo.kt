package app.repos

import app.services.DbService
import com.mongodb.client.model.Filters
import domain.Audit
import extends.ime

class AuditRepo(service: DbService) {
    val collection = service.db.getCollection<Audit>(collectionName = ime<Audit>())
    fun dobi(entity_id: String): List<Audit> = this.collection.find(
        filter = Filters.eq(Audit::entiteta_id.name, entity_id)
    ).toList()
}
