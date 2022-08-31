package com.appswithlove

import TimeEntryForPublishing
import com.appswithlove.floaat.FloatRepo
import com.appswithlove.store.DataStore
import com.appswithlove.toggl.TogglProject
import com.appswithlove.toggl.TogglRepo
import java.time.LocalDate

class T2F {
    val dataStore = DataStore()
    val float = FloatRepo(dataStore)
    val toggl = TogglRepo(dataStore)

    fun fetchProjects() {
        val workspace = toggl.getWorkspaces() ?: throw Exception("Couldn't get Toggle Workspace")

        val floatProjects = float.getFloatProjects()
        val togglProjects = toggl.getTogglProjects()

        val newProjects =
            floatProjects.filter { floatProject -> !togglProjects.any { it.name.contains(floatProject) } }
                .map { TogglProject(name = it) }

        if (newProjects.isNotEmpty()) {
            println("⬆️ Syncing new Float projects to Toggl — (${newProjects.size}) of ${floatProjects.size}")
            println("---")
        } else {
            println("🎉 All Float Projects already up-to-date in Toggl!")
            return
        }

        toggl.pushProjectsToToggl(workspace.id, newProjects)
    }


    fun addTimeEntries(date: LocalDate) {
        val timeEntries = toggl.getTogglTimeEntries(date)
        println("⏱ Found ${timeEntries.size} time entries for $date on Toggl!")
        if (timeEntries.isEmpty()) {
            println("Noting to do here. Do you even work?")
            return
        }
        val projects = toggl.getTogglProjects()
        val pairs = timeEntries.map { time -> time to projects.firstOrNull { it.id == time.project_id } }

        val timeEntriesOnDate = float.getFloatTimeEntries(date)
        if (timeEntriesOnDate.isNotEmpty()) {
            println("---")
            System.err.println("⚠️ There are already existing time entries for that date. Can't guarantee to not mess up. So please remove them first for $date")
            return
        }

        if (pairs.any { it.second?.projectId == null }) {
            System.err.println("⚠️ Some time entries don't have a valid project assigned. Please fix this and try again.")
            pairs.filter { it.second?.projectId == null }.forEach {
                System.err.println("  - ${it.first.description}")
            }
            return
        }

        val data = pairs.map {
            TimeEntryForPublishing(
                timeEntry = it.first,
                projectId = it.second?.projectId ?: -1,
                phaseId = it.second?.phaseId
            )
        }

        float.pushToFloat(date, data)
    }

    fun clear() {
        dataStore.clear()
    }


}