package app.repos

import app.services.DbService
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import domain.Audit
import domain.Status
import extends.ime

class StatusRepo(val service: DbService) {
    val collection = service.db.getCollection<Status>(collectionName = ime<Status>())
    fun posodobi_tip(id: String, test_id: String, tip: Status.Tip): Status? {
        val r = this.collection.findOneAndUpdate(
            filter = Filters.and(
                Filters.eq(Status::_id.name, id),
                Filters.eq(Status::test_id.name, test_id)
            ), update = Updates.set(Status::tip.name, tip)
        ) ?: return null

        val audit = Audit(
            entiteta = ime<Status>(),
            tip = Audit.Tip.STATUS_POSODOBITEV,
            opis = r.tip.name,
            entiteta_id = id
        )

        this.service.ustvari(audit)

        return r
    }
}
