package si.urosjarc.server.core.repos

import si.urosjarc.server.core.domain.Naloga
import si.urosjarc.server.core.domain.Tematika
import si.urosjarc.server.core.domain.Zvezek

interface ZvezekRepo : DbRepo<Zvezek>
interface TematikaRepo : DbRepo<Tematika>
interface NalogaRepo : DbRepo<Naloga>
