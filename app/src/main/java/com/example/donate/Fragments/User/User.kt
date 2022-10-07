package com.example.donate.Fragments.User

class User {
    var name:String?=null
    var uid:String?=null
    var email:String?=null

    constructor(){}

    constructor(name:String?,uid:String?,email:String?){
        this.name=name
        this.uid=uid
        this.email=email
    }
}