package com.example.api

import com.example.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class Part(
    @Json(name = "text") val text: String? = null
)

@JsonClass(generateAdapter = true)
data class Content(
    @Json(name = "parts") val parts: List<Part>
)

@JsonClass(generateAdapter = true)
data class GenerateContentRequest(
    @Json(name = "contents") val contents: List<Content>,
    @Json(name = "systemInstruction") val systemInstruction: Content? = null
)

@JsonClass(generateAdapter = true)
data class Candidate(
    @Json(name = "content") val content: Content? = null
)

@JsonClass(generateAdapter = true)
data class GenerateContentResponse(
    @Json(name = "candidates") val candidates: List<Candidate>? = null
)

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object GeminiNetwork {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val service: GeminiApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        retrofit.create(GeminiApiService::class.java)
    }

    /**
     * Helper to safely execute the call, checking if the API Key exists
     */
    suspend fun askGemini(prompt: String, conversationHistory: List<com.example.db.ChatMessageEntity> = emptyList()): String {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY" || apiKey == "GEMINI_API_KEY") {
            return "Ufunguo wa Gemini API haujawekwa. Tafadhali weka 'GEMINI_API_KEY' yako kwenye paneli ya siri (Secrets panel) ili AI iweze kukujibu kiotomatiki!\n\nWakati ukifanya hivyo, hapa kuna jibu la simu ya maandalizi ya Lupanulla: \nHakika! Masomo yote ya sekondari Tanzania yanahitaji uelewa wa kanuni, na ni vizuri kujiandaa na mitihani ya NECTA kwa kufanya majaribio mengi ya maswali ya miaka iliyopita."
        }

        // Build systematic user instructions to stay as a Swahili educational tutor
        val systemInstruction = "Wewe ni mwalimu mtaalamu wa sekondari nchini Tanzania unayeitwa Lupanulla AI Assistant. Saidia wanafunzi kuanzia Form 1 hadi Form 6 kuelewa masomo ya sayansi (Physics, Chemistry, Biology, Mathematics) na sanaa (History, Geography, Kiswahili, nk). Jibu kwa lugha safi ya Kiswahili na Kiingereza kulingana na swali la mwanafunzi na ueleze kwa mifano rahisi ili waweze kufaulu mitihani ya NECTA."

        // Convert conversation history into API models
        val contents = mutableListOf<Content>()
        
        // Add historical turns
        conversationHistory.forEach { msg ->
            contents.add(Content(parts = listOf(Part(text = msg.text))))
        }
        
        // Append current prompt
        contents.add(Content(parts = listOf(Part(text = prompt))))

        val request = GenerateContentRequest(
            contents = contents,
            systemInstruction = Content(parts = listOf(Part(text = systemInstruction)))
        )

        return try {
            val response = service.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text 
                ?: "Samahani, mwalimu wa AI ameshindwa kuzalisha jibu. Tafadhali jaribu tena."
        } catch (e: Exception) {
            "Mawasiliano yamefeli: ${e.localizedMessage}. Hakikisha kifaa chako kimeunganishwa kwenye mtandao au ufunguo wa API ni sahihi."
        }
    }
}
