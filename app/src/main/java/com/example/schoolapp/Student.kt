package com.example.schoolapp

class Student {
    var id: String="uid"
    var name:String=""
    var grade:String=""
    var contact:String=""
   var category: String=""
    var gender: String=""
    var dob: String=""

    constructor(id: String, name: String, grade: String, contact: String, category: String, gender: String, dob: String) {
        this.id = id
        this.name = name
        this.grade = grade
        this.contact = contact
        this.category = category
        this.gender = gender
        this.dob=dob
    }
    constructor()
}