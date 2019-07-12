package ru.ktsstudio.myapplication.data.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ktsstudio.myapplication.data.models.CommentReaction
import ru.ktsstudio.myapplication.data.models.GithubRepository
import ru.ktsstudio.myapplication.data.models.GithubUser
import ru.ktsstudio.myapplication.data.models.ReactionCreateBody
import ru.ktsstudio.myapplication.data.models.ReactionType
import ru.ktsstudio.myapplication.data.models.RepositorySort
import ru.ktsstudio.myapplication.data.models.SortOrder

interface GithubApiService {

    @GET("/users")
    fun getUsers(
        @Query("since") fromUserId: String? = null
    ): Call<List<GithubUser>>

    @GET("/search/repositories")
    fun searchRepositories(
        @Query("q") searchQuery: String,
        @Query("sort") sort: RepositorySort?,
        @Query("order") order: SortOrder?
    ): Call<List<GithubRepository>>

    @GET("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions")
    fun getCommentReactions(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("comment_id") commentId: String
    ): Call<List<CommentReaction>>

    @POST("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions")
    fun postCommentReaction(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("comment_id") commentId: String,
        @Body body: ReactionCreateBody
    ): Call<CommentReaction>

    @FormUrlEncoded
    @POST("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions")
    fun postCommentReactionByForm(
        @Header("HeaderName") header: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("comment_id") commentId: String,
        @Field("content") reactionType: ReactionType
    ): Call<CommentReaction>

    @Multipart
    @POST("/upload")
    fun uploadFile(
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>
}