package com.example.donate

class Post_Model {
    private var Name: String? = null
    private var Location: String? = null
    private var Image: String? = null
    constructor(){}

    constructor(name:String?,location:String?,image:String?){
        this.Name=name
        this.Location=location
        this.Image=image
    }


    fun getname(): String? {
        return this.Name
    }

    fun setname(name: String?) {
        this.Name = name
    }

    fun getImage(): String? {
        return Image
    }

    fun setImage(image: String?) {
        this.Image = image
    }

    fun getLocation(): String? {
        return this.Location
    }

    fun setLocation(location: String?) {
        this.Location = location
    }
}