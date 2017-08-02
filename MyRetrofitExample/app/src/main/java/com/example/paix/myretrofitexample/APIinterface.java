package com.example.paix.myretrofitexample;

/**
 * Created by paix on 8/1/17.
 * Information:
 *
 * - Every method of an interface represents one possible API call
 * Example:
 * @GET("users")
 * Call<List<User>> getUsers()
 *
 * - Replacement Blocks can be used to adjust the URL with the help of the @Path annotation
 *   (The value of the parameter is bound to the specific replacement block)
 * Example:
 * @GET("users/{name}/commits")
 * Call<Lists<Commit>> getCommitsByName(@Path("name") String name)
 *
 * - Query parameters are added with the @Query annotation on a method parameter.
 *   (They are automatically added at the end of the URL)
 * @GET("users")
 * Call<User> getUserById(@Query("id") Integer id)
 *
 * - Body annotation on a method parameter tells retrofit to use the object as the request body
 * @POST("users")
 * Call<User> postUser(@Body User user)
 *
 * - Authentication with annotations can be made for basic Http Basic Authentication
 *   (To generate Basic authentication you can use OkHttps credentials class)
 * @GET("user")
 * Call<UserDetails> getUserDetails(@Header("Authorization") String credentials)
 *
 * IMPORTANT:
 * - This Interface represents the template || example for the other APIs
 */

public interface APIinterface {



}
