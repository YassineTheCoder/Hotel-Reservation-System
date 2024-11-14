package com.example.reservationbyenumiration

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

enum class TypeChamber(val prixParNuit:Double) {
    SIMPLE(100.0),
    DOUBLE(150.0),
    SUITE(300.0)
}

enum class EtatReservation(val description: String) {
    CONFIRME("Réservation confirmée et prête à l'arrivée."),
    EN_ATTENTE("En attente de confirmation."),
    ANNULLEE("Réservation annulée."),
    ARRIVE("Le client est arrivé à l'hôtel")
}

data class Chember(val type: TypeChamber, val numero:Int) {
     fun afficherDetails(): String{
         return "Chamber N°: $numero   type de Chamber: $type  Prix de Chamber: ${type.prixParNuit} MAD"
     }
}

data class Reservation(val chamber: TypeChamber, val clientnom: String,  val dateDeDebut: String,  val dateDefin: String, val etatReservation:EtatReservation ){

    init {
        val debut = LocalDate.parse(dateDeDebut, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val fin = LocalDate.parse(dateDefin, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        if (debut.isBefore(LocalDate.now())) {
            throw IllegalArgumentException("La date de début ne peut pas être dans le passé.")
        }

        if (fin.isBefore(debut)) {
            throw IllegalArgumentException("La date de fin ne peut pas être avant la date de début.")
        }

        if (fin.isBefore(LocalDate.now())) {
            throw IllegalArgumentException("La date de fin ne peut pas être dans le passé.")
        }
    }


    fun calculerprix():String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val debut = LocalDate.parse(dateDeDebut, formatter)
        val fin = LocalDate.parse(dateDefin, formatter)
        val jours = ChronoUnit.DAYS.between(debut, fin)

        val prixdereservation = jours*chamber.prixParNuit

        return "Prix de réservation pour $jours jours: $prixdereservation MAD"



    }

    fun afficherDetails(){
        println("Nom du Client: $clientnom")
        println("Type de Chamber: ${chamber.name}")
        println("Date de Debut: $dateDeDebut")
        println("Date de Fin:  $dateDefin")
        println()
    }

}
data class Client(val nom: String, val email: String){

    fun afficherDetails():String{
        return "Nom du Client: $nom, Email: $email"
    }
}

fun main() {
    val chamber1 = Chember(TypeChamber.SIMPLE, 101)
    val chamber2 = Chember(TypeChamber.DOUBLE, 202)

    val client1 = Client("yassine", "yassine@example.com")
    val client2 = Client("amin", "amin@example.com")

    val reservation1 = Reservation(chamber1.type, client1.nom, "2024-11-16", "2024-11-20",EtatReservation.CONFIRME)
    val reservation2 = Reservation(chamber2.type, client2.nom, "2024-11-17", "2024-11-25",EtatReservation.EN_ATTENTE)
    println()
    println("Chamber Details")
    println(chamber1.afficherDetails())
    println(chamber2.afficherDetails())

    println()
    println("Prixe de Reservation")
    println(reservation1.calculerprix())
    println(reservation2.calculerprix())

    println()
    println("Reservation Information ")
    reservation1.afficherDetails()
    reservation2.afficherDetails()

    println()
    println("Clinet Information")
    println(client1.afficherDetails())
    println(client2.afficherDetails())
}
