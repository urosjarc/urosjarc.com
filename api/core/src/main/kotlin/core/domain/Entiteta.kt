package core.domain

import core.base.Id

sealed interface Entiteta<T> {
    var _id: Id<T>
}
