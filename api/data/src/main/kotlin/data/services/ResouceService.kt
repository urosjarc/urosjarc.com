package data.services

import data.domain.Datoteka
import java.io.File

class ResouceService(
    val rootDir: File,
    val zvezkiDir: File,
) {

    fun najdi_zip_datoteke(): Collection<Datoteka> {
        return rootDir.walk().filter { it.isFile && it.extension == "zip" }.map { Datoteka(it) }.toList()
    }
}
