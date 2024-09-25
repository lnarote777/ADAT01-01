package org.example

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.round

fun main(){
    val fileCotizaciones = Path.of("src/main/resources/cotizacion.csv")
    val fileMedia = Path.of("src/main/resources/media.csv")
    val br = Files.newBufferedReader(fileCotizaciones)
    val bw = Files.newBufferedWriter(fileMedia)

    val fileManager = FileManager(br, bw)

    val cotizaciones = fileManager.leerCotizaciones()
    fileManager.crearFicheroMedia(cotizaciones)

}

