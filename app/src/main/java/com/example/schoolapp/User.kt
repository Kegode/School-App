package com.example.schoolapp

class User {
    var id: String="uid"
    var name:String=""
    var email:String=""
    var password:String=""
    var status:String=""

    constructor(id: String, name: String, email: String, password: String, status:String) {
        this.id = id
        this.name = name
        this.email = email
        this.password = password
        this.status = status
    }
    constructor()
}


