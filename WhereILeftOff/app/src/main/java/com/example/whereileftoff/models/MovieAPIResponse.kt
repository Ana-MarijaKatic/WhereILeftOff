package com.example.whereileftoff.models

class MovieAPIResponse {
    lateinit var id: String
    lateinit var image: String
    lateinit var title: String
    lateinit var runtime: String
    lateinit var yearReleased: String
    lateinit var rating: String
    lateinit var overview: String

    constructor(
        id: String, image: String, title: String, runtime: String,
        yearReleased: String, rating: String, overview: String
    ) {
        this.id = id
        this.image = image
        this.title = title
        this.runtime = runtime
        this.yearReleased = yearReleased
        this.rating = rating
        this.overview = overview
    }

    constructor()
}