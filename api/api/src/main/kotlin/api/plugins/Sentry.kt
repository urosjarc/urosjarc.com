package api.plugins

import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.*
import io.sentry.ITransaction
import io.sentry.Sentry
import io.sentry.SpanStatus


val SentryPlugin = createApplicationPlugin("SentryPlugin") {
    val transactionKey = AttributeKey<ITransaction>("SentryTransaction")

    on(MonitoringEvent(Routing.RoutingCallStarted)) { call ->
        val transaction = Sentry.startTransaction("Call", call.request.path())
        call.attributes.put(transactionKey, transaction)
    }

    on(MonitoringEvent(Routing.RoutingCallFinished)) { call ->
        call.attributes[transactionKey].finish()
    }

    on(CallFailed) { call, cause ->
        val transaction = call.attributes[transactionKey]
        transaction.throwable = cause
        transaction.status = SpanStatus.INTERNAL_ERROR
    }
}
