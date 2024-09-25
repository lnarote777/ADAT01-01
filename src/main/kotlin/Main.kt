package org.example

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

fun main(){
    val fileCotizaciones = Path.of("src/main/resources/cotizacion.csv")
    val fileMedia = Path.of("src/main/resources/media.csv")
    val br = Files.newBufferedReader(fileCotizaciones)
    val bw = Files.newBufferedWriter(fileMedia)

    val cotizaciones = leerCotizaciones(br)

    crearFicheroMedia(cotizaciones, bw)

}

fun leerCotizaciones(br: BufferedReader): Map<String, List<String>>{
    val diccionario = mutableMapOf<String, MutableList<String>>()

    br.use { it.forEachLine {linea ->
        val lineaSplit = linea.split(";")

        for (datos in lineaSplit){
            diccionario[datos] = mutableListOf()
        }


        for (i in lineaSplit.indices){
            diccionario[lineaSplit[i]]?.add(lineaSplit[i])
        }
    }


    }

    return diccionario
}

fun crearFicheroMedia(cotizaciones: Map<String, List<String>>,bw: BufferedWriter) {
    val estadisticas = mutableMapOf<String, Triple<Double, Double, Double>>()

    bw.use {
        for ((nombre, datos) in cotizaciones){
            val valores = datos.map { it.replace(",", ".").toDouble() }
            val minimo: Double = valores.min()
            val maximo: Double = valores.max()
            val media: Double = valores.average()

            estadisticas[nombre] = Triple(minimo, maximo, media)
        }

        estadisticas.forEach { linea -> it.write(linea.toString())  }
    }

}