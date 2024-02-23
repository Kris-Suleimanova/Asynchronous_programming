import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

fun checkWebsite(url: String): Boolean {
    return try {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "HEAD"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000
        connection.responseCode == HttpURLConnection.HTTP_OK
    } catch (e: Exception) {
        false
    }
}

fun main() = runBlocking {
    val websites = listOf(
        "https://www.google.com",
        "https://www.facebook.com",
        "https://www.github.com",
        "https://www.twitter.com",
        "https://www.instagram.com",
        "https://www.youtube.com",
        "https://www.linkedin.com",
        "https://www.reddit.com",
        "https://www.stackoverflow.com",
        "https://www.wikipedia.org"
    )

    val j = websites.map { url ->
        async {
            val a = checkWebsite(url)
            if (a) {
                println("Сайт $url доступен")
            } else {
                println("Сайт $url недоступен")
            }
        }
    }

    j.forEach { it.await() }
}
