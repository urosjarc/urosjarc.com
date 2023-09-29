package data.services

import java.io.File

class ResouceService(
    val rootDir: File,
    val zvezkiDir: File,
) {

    fun najdi_zip_datoteke(): List<File> {
        return rootDir.walk().filter { it.isFile && it.extension == "zip" }.toList()
    }

    fun najdi_zvezek(): List<File> {
        return rootDir.walk().filter { it.isFile && it.extension == "zip" }.toList()
    }
}
