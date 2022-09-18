package com.example.whereileftoff.models

class MediaArray {
    lateinit var id: String
    lateinit var image: String
    lateinit var title: String

    constructor(id: String, image: String, title: String) {
        this.id = id
        this.image = image
        this.title = title
    }

    constructor()
}