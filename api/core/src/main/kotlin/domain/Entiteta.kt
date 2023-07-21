package domain

import base.Id

sealed interface Entiteta<T> {
    var _id: Id<T>
}
