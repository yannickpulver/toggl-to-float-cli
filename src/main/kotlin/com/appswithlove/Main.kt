package com.appswithlove

import kotlinx.serialization.json.Json
import java.time.LocalDate

val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

fun main(args: Array<String>) {

    val t2f = T2F()
    when (args.firstOrNull()) {
        "fetchProjects" -> t2f.fetchProjects()
        "addTime" -> {
            val date: String? = args.getOrNull(1)
            if (date == null) {
                System.err.println("Please pass in a valid date in format: addTime 2022-07-11")
                return
            }
            t2f.addTimeEntries(LocalDate.parse(date))
        }
        "clear" -> {
            t2f.clear()
        }
        else -> {
            println("Welcome to Toggl to Float. Please run the app with following arguments")
            println("-------")
            println("'fetchProjects'")
            println("     - to fetch all projects from Float and add them to Toggl")
            println("'addTime DATE'")
            println("     - to add time entries from toggl to float for a given date.")
            println("     - Date Format: 2022-06-24")
            println("'clear'")
            println("     - to restart toggl-to-float (remove api keys & settings)")
        }
    }
}

