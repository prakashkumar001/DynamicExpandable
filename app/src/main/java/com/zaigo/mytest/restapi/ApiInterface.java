package com.zaigo.mytest.restapi;



import com.zaigo.mytest.model.GetBoxAttributeModel;
import com.zaigo.mytest.model.StoreCategoryModel;
import com.zaigo.mytest.model.StoreChildModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {


    @GET("common_recv.php")
    Call<GetBoxAttributeModel> getBoxattrib(@HeaderMap Map<String, String> headers, @Query("jobid") String jobid, @Query("boxid") String boxid, @Query("tag") String tag);
    @POST("store_category.php")
    Call<StoreCategoryModel> getStoreCategory();


    @FormUrlEncoded
    @POST("store_product.php")
    Call<StoreChildModel> getStoreChildList(@Field("cat_id") String cat_id);
   /* @GET("contacts/")
    Call<Example> getTopRatedMovies();*/

   /* @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })*/
    //http://boolfox.com/rest/index.php/htc/checkout_cart
    //htc/product_search


   /* @FormUrlEncoded
    @POST("common_recv.php")
    Call<LoginModel> getUserLogin(@HeaderMap Map<String, String> headers, @Field("email") String email, @Field("password") String password, @Field("tag") String tag);


    @GET("common_recv.php")
    Call<JobListModel> getListOfJob(@HeaderMap Map<String, String> headers, @Query("userid") String userid, @Query("tag") String tag);

    @GET("common_recv.php")
    Call<JobInfoModel> getJobInfo(@HeaderMap Map<String, String> headers, @Query("jobid") String jobid, @Query("tag") String tag);

    @GET("common_recv.php")
    Call<BoxTemplateModel> getBoxtemplate(@HeaderMap Map<String, String> headers, @Query("jobid") String jobid, @Query("tag") String tag);

    @GET("common_recv.php")
    Call<GetBoxAttributeModel> getBoxattrib(@HeaderMap Map<String, String> headers, @Query("jobid") String jobid, @Query("boxid") String boxid, @Query("tag") String tag);

    @FormUrlEncoded
    @POST("common_recv.php")
    Call<BoxCreationModel> boxcreationaction(@HeaderMap Map<String, String> headers, @FieldMap HashMap<String, String> defaultData);

    @GET("common_recv.php")
    Call<JobinfoboxDetailModel> getdetailJobInfo(@HeaderMap Map<String, String> headers, @Query("jobid") String jobid, @Query("tag") String tag);

    @GET("common_recv.php")
    Call<BoxinfoeditModel> getBoxedit(@HeaderMap Map<String, String> headers, @Query("jobid") String jobid, @Query("boxid") String boxid, @Query("boxmasterid") String boxmasterid, @Query("tag") String tag);


    @Multipart
    @POST("common_recv.php")
    Call<ImageCreationModel> imageUpload(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part file, @Part("boxmasterid") RequestBody boxmasterid, @Part("userid") RequestBody userid, @Part("tag") RequestBody tag);


    @Multipart
    @POST("common_recv.php")
    Call<ImageCreationModel> getImageUploadMap(@HeaderMap Map<String, String> headers, @PartMap HashMap<String, RequestBody> defaultData, @Part("boxmasterid") RequestBody boxmasterid, @Part("userid") RequestBody userid, @Part("tag") RequestBody tag);

*/

   /* @POST("common_recv.php")
    Call<LoginModel> getUser(@HeaderMap Map<String, String> headers);


    @FormUrlEncoded
    @POST("register")
    Call<RegisterModel> getRegister(@Field("email") String email, @Field("password") String password, @Field("first_name") String first_name, @Field("last_name") String last_name, @Field("hcp_registration_number") String hcp_registration_number, @Field("hospital_or_practice") String hospital_or_practice, @Field("marketing") String marketing);




    @GET("options/get")
    Call<CategoryResponse> getCategorySample(@HeaderMap Map<String, String> headers);


    @GET("getHospitals")
    Call<List<HospitalModel>> getHospitals(@HeaderMap Map<String, String> headers);



    @POST("orders/orders/")
    Call<OrdersResponse> getOrderSample(@HeaderMap Map<String, String> headers, @Body OrdersGivenResponse body);

    @GET("resources/resources/")
    Call<List<OrdersLiteratureResponse>> getOrderLiterature(@HeaderMap Map<String, String> headers);

    @GET("user/orders/")
    Call<YourOrders> getYourOrder(@HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @POST("user/update")
    Call<EditDetailsModel> getEditDeatils(@HeaderMap Map<String, String> headers, @Field("first_name") String first_name, @Field("last_name") String last_name, @Field("email") String email, @Field("password") String password);
*/
    /*@GET("products/products")
    Call<List<ProductResponse>> getProducts(@HeaderMap Map<String, String> headers);*/
    /*@POST("htc/product_search")
    Call<List<TestExample>> getUserProduct_Search(@Query("qry_string") String qry_string);*/
}