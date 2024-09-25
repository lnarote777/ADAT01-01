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

    val cotizaciones = leerCotizaciones(br)

    crearFicheroMedia(cotizaciones, bw)

}

fun leerCotizaciones(br: BufferedReader): Map<String, List<String>>{
    val diccionario = mutableMapOf<String, MutableList<String>>()

    br.use { reader ->
        val cabeceras = reader.readLine().split(";")

        for (nombre in cabeceras){
            diccionario[nombre] = mutableListOf()
        }

        reader.forEachLine { linea ->
            val datos = linea.split(";")
            for (i in datos.indices){
                diccionario[cabeceras[i]]?.add(datos[i])
        }
    }
    }

    return diccionario
}

fun crearFicheroMedia(cotizaciones: Map<String, List<String>>, bw: BufferedWriter) {
    val estadisticas = mutableMapOf<String, Triple<Double, Double, Double>>()

    bw.use {

            for ((nombre, datos) in cotizaciones){
                try {
                    val valores = datos.map { it.replace(".", "").replace(",", ".").toDouble() }
                    val minimo: Double = valores.minOrNull() ?: 0.0
                    val maximo: Double = valores.maxOrNull() ?: 0.0
                    val media: Double = round(valores.average() * 100 ) / 100

                    estadisticas[nombre] = Triple(minimo, maximo, media)
                }catch (_: Exception){}
            }

        it.write("Columna,Mínimo,Máximo,Media\n")
        for ((nombre, datos) in estadisticas){
            it.write("${nombre},${datos.first},${datos.second},${datos.third}\n")
        }
    }

}