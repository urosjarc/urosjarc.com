package si.programerski_klub.server.core.repos

import si.programerski_klub.server.core.domain.napredovanje.Test

interface TestiRepo {
    fun shrani(test: Test): DbRezultatShranitve<Test>
}
