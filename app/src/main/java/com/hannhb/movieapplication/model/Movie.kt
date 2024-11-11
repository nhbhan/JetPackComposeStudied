package com.hannhb.movieapplication.model

data class Movie(
    val id: String,
    val title: String,
    val year: String,
    val genre: String,
    val director: String,
    val actor: String,
    val plot: String,
    val poster: String,
    val images: List<String>,
    val rating: String
)

fun getMovies(): MutableList<Movie> {
    val data: MutableList<Movie> = ArrayList()
    for (index in 0 until 10) {
        data.add(
            Movie(
                id = "$index",
                title = "Love you ${index}",
                year = "20${index}",
                genre = "Love",
                actor = "Chao Xing",
                poster = "Duong Duong, Trinh San",
                images = listOf("https://picsum.photos/200/300",
                    "https://via.placeholder.com/150/FF0000/FFFFFF?Text=yttags.com",
                    "https://via.placeholder.com/150/FFFF00/000000?Text=google.com"),
                plot = "$index",
                rating = "$index",
                director = "Han Han"
            )
        )
    }
    return data
}
