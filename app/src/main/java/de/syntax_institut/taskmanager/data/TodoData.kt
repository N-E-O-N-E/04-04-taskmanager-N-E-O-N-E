package de.syntax_institut.taskmanager.data

class TodoData(
    var title: String
)

var todoSamples = listOf<TodoData>(
    TodoData(title = "Einkaufsliste schreiben"),
    TodoData(title = "E-Mails beantworten"),
    TodoData(title = "Projektunterlagen überprüfen"),
    TodoData(title = "Workout planen"),
    TodoData(title = "Freunde zum Abendessen einladen"),
    TodoData(title = "Rechnung bezahlen"),
    TodoData(title = "Buchkapitel lesen"),
    TodoData(title = "Zimmer aufräumen"),
    TodoData(title = "Arzttermin vereinbaren"),
    TodoData(title = "Urlaubsplanung abschließen")
)
