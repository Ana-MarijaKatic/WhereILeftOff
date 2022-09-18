package com.example.whereileftoff.models

class SeriesAPIResponse {
    lateinit var id: String
    lateinit var image: String
    lateinit var title: String
    lateinit var season: String
    lateinit var episode: String
    lateinit var yearReleased: String
    lateinit var rating: String
    lateinit var overview: String

    constructor(
        id: String, image: String, title: String, season: String, episode: String,
        yearReleased: String, rating: String, overview: String
    ) {
        this.id = id
        this.image = image
        this.title = title
        this.season = season
        this.episode = episode
        this.yearReleased = yearReleased
        this.rating = rating
        this.overview = overview
    }

    constructor()
}