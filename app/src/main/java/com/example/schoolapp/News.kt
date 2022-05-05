package com.example.schoolapp

class News {
    var id: String="uid"

    var head:String=""
    var body:String=""

    constructor(id: String, head: String, body: String) {
        this.id = id

        this.head = head
        this.body = body
    }
    constructor()
}