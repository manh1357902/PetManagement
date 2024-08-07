package com.project.petmanagement.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.petmanagement.MyApplication;
import com.project.petmanagement.payloads.requests.CareActivityInfoRequest;
import com.project.petmanagement.payloads.requests.CareActivityNotificationRequest;
import com.project.petmanagement.payloads.requests.ChangePasswordRequest;
import com.project.petmanagement.payloads.requests.DailyActivityLogRequest;
import com.project.petmanagement.payloads.requests.FCMToken;
import com.project.petmanagement.payloads.requests.HealRecordRequest;
import com.project.petmanagement.payloads.requests.LoginRequest;
import com.project.petmanagement.payloads.requests.OneTimeScheduleRequest;
import com.project.petmanagement.payloads.requests.OrderRequest;
import com.project.petmanagement.payloads.requests.PetRequest;
import com.project.petmanagement.payloads.requests.RecurringScheduleRequest;
import com.project.petmanagement.payloads.requests.RegisterRequest;
import com.project.petmanagement.payloads.requests.UserRequest;
import com.project.petmanagement.payloads.requests.VaccinationNotificationRequest;
import com.project.petmanagement.payloads.responses.CareActivityNotificationResponse;
import com.project.petmanagement.payloads.responses.CartResponse;
import com.project.petmanagement.payloads.responses.DailyActivityLogResponse;
import com.project.petmanagement.payloads.responses.HealRecordResponse;
import com.project.petmanagement.payloads.responses.ListCareActivityNotificationResponse;
import com.project.petmanagement.payloads.responses.ListCareActivityResponse;
import com.project.petmanagement.payloads.responses.ListCategoryResponse;
import com.project.petmanagement.payloads.responses.ListDaiLyActivityResponse;
import com.project.petmanagement.payloads.responses.ListDailyActivityLogResponse;
import com.project.petmanagement.payloads.responses.ListDiseaseResponse;
import com.project.petmanagement.payloads.responses.ListFoodTypeResponse;
import com.project.petmanagement.payloads.responses.ListHealthRecordResponse;
import com.project.petmanagement.payloads.responses.ListMedicalResponse;
import com.project.petmanagement.payloads.responses.ListNutritiousFoodResponse;
import com.project.petmanagement.payloads.responses.ListOneTimeScheduleResponse;
import com.project.petmanagement.payloads.responses.ListOrderResponse;
import com.project.petmanagement.payloads.responses.ListPetResponse;
import com.project.petmanagement.payloads.responses.ListProductResponse;
import com.project.petmanagement.payloads.responses.ListSpeciesResponse;
import com.project.petmanagement.payloads.responses.ListVaccineNotification;
import com.project.petmanagement.payloads.responses.ListVaccineResponse;
import com.project.petmanagement.payloads.responses.ListVetResponse;
import com.project.petmanagement.payloads.responses.LoginResponse;
import com.project.petmanagement.payloads.responses.MedicalDocumentResponse;
import com.project.petmanagement.payloads.responses.OrderResponse;
import com.project.petmanagement.payloads.responses.PetResponse;
import com.project.petmanagement.payloads.responses.Response;
import com.project.petmanagement.payloads.responses.UserResponse;
import com.project.petmanagement.payloads.responses.VaccineNotificationResponse;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    // Server
    String BASE_URL = "http://localhost/api/";

    StorageService storageService = MyApplication.getStorageService();
    Gson gson = new GsonBuilder()
            .setLenient().create();
    Interceptor interceptor = chain -> {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String authToken = "Bearer " + storageService.getString("token");
        builder.header("Authorization", authToken);
        return chain.proceed(builder.build());
    };
    OkHttpClient.Builder okClient = new OkHttpClient.Builder().addInterceptor(interceptor);
    APIService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okClient.build())
            .build()
            .create(APIService.class);

    // AuthController/UserController
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<LoginResponse> signup(@Body RegisterRequest registerRequest);

    @POST("auth/change_password")
    Call<Response> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @POST("auth/fcm")
    Call<com.project.petmanagement.payloads.responses.Response> setFcmToken(@Body FCMToken fcmToken);

    @DELETE("users/delete")
    Call<Response> deleteUser();

    @PUT("users/update")
    Call<UserResponse> updateUser(@Body UserRequest userRequest);

    // FoodTypeController
    @GET("food_types/all")
    Call<ListFoodTypeResponse> getAllFoodTypes();


    // NutritiousFoodController
    @GET("nutritious_food/all")
    Call<ListNutritiousFoodResponse> getAllNutritious();

    @GET("nutritious_food/search")
    Call<ListNutritiousFoodResponse> searchNutritiousFood(@Query("species_id") Long speciesId, @Query("food_type_id") Long foodTypeId, @Query("keywords") String keywords);


    // SpeciesController
    @GET("species/all")
    Call<ListSpeciesResponse> getSpecies();


    // DiseaseController
    @GET("diseases/all")
    Call<ListDiseaseResponse> getDiseases();


    // PetController
    @GET("pets/users")
    Call<ListPetResponse> getAllPetUser();

    @POST("pets/add")
    Call<PetResponse> addPet(@Body PetRequest petRequest);

    @GET("pets/{id}")
    Call<PetResponse> getPetDetail(@Path("id") Long id);

    @PUT("pets/update/{id}")
    Call<PetResponse> updatePet(@Body PetRequest petRequest, @Path("id") Long id);

    @DELETE("pets/delete/{id}")
    Call<PetResponse> deletePet(@Path("id") Long petId);


    // CategoryController
    @GET("categories/all")
    Call<ListCategoryResponse> getAllCategory();


    // ProductController
    @GET("products/all")
    Call<ListProductResponse> getAllProduct();


    // CartController
    @POST("carts/add")
    Call<CartResponse> addToCart(@Query("product_id") Long idProduct, @Query("quantity") Integer quantity);

    @GET("carts/users")
    Call<CartResponse> getCart();

    @PUT("carts/update")
    Call<CartResponse> updateCart(@Query("item_id") Long idItem, @Query("quantity") Integer quantity, @Query("selected") Boolean selected);

    @DELETE("carts/cart_items/delete/{id}")
    Call<CartResponse> deleteCartItem(@Path("id") Long idItem);


    // OrderController
    @POST("orders/create")
    Call<OrderResponse> createOrder(@Body OrderRequest orderRequest);

    @GET("orders/users")
    Call<ListOrderResponse> getOrderUser();

    @PUT("orders/cancel/{id}")
    Call<OrderResponse> cancelOrder(@Path("id") Long idOrder);


    // VetController
    @GET("vets/all")
    Call<ListVetResponse> getAllVet();

    @GET("vets/search")
    Call<ListVetResponse> searchVet(@Query("keywords") String keywords);


    // MedicalDocumentController
    @Multipart
    @POST("medical_documents/add")
    Call<MedicalDocumentResponse> addMedicalDocument(
            @Part("title") RequestBody title,
            @Part("note") RequestBody note,
            @Part("petId") RequestBody petId,
            @Part MultipartBody.Part file
    );

    @GET("medical_documents/pets/{id}")
    Call<ListMedicalResponse> getMedicalDocumentByPet(@Path("id") Long petId);

    @GET("medical_documents/{id}")
    Call<MedicalDocumentResponse> getMedicalDocumentByid(@Path("id") Long medicalId);

    @Multipart
    @PUT("medical_documents/update/{id}")
    Call<MedicalDocumentResponse> updateMedicalDocument(
            @Path("id") Long id,
            @Part("title") RequestBody title,
            @Part("note") RequestBody note,
            @Part("petId") RequestBody petId,
            @Part MultipartBody.Part file
    );

    @DELETE("medical_documents/delete/{id}")
    Call<com.project.petmanagement.payloads.responses.Response> deleteMedicalDocument(@Path("id") Long medicalId);


    // HealthRecordController
    @POST("health_records/add")
    Call<HealRecordResponse> addHealthRecord(@Body HealRecordRequest healRecordRequest);

    @PUT("health_records/update/{id}")
    Call<HealRecordResponse> updateHealthRecord(@Path("id") Long healthRecordId, @Body HealRecordRequest healRecordRequest);

    @DELETE("health_records/delete/{id}")
    Call<Response> deleteHealthRecord(@Path("id") Long id);

    @GET("health_records/pets/{id}")
    Call<ListHealthRecordResponse> getHealthRecordByPet(@Path("id") Long petId);

    @GET("health_records/static")
    Call<ListHealthRecordResponse> staticsHealthRecord(@Query("pet_id") Long petId, @Query("start_date") String startDate, @Query("end_date") String endDate);


    // VaccineController
    @GET("vaccines/pets/{id}")
    Call<ListVaccineResponse> getVaccineByPet(@Path("id") Long id);


    // OneTimeScheduleController
    @PUT("one_time_schedules/update/{vaccination_notification_id}")
    Call<ListOneTimeScheduleResponse> updateOneSchedule(@Path("vaccination_notification_id") Long vaccinationNotificationId, @Body List<OneTimeScheduleRequest> oneTimeScheduleRequestList);

    @DELETE("one_time_schedules/delete/{id}")
    Call<Response> deleteOneTimeSchedule(@Path("id") Long oneTimeScheduleId);


    // VaccinationNotificationController
    @GET("vaccination_notification/users")
    Call<ListVaccineNotification> getVaccineNotificationByUser();

    @GET("vaccination_notification/date")
    Call<ListVaccineNotification> getVaccinationNotificationByDate(@Query("date") String date);

    @POST("vaccination_notification/add")
    Call<VaccineNotificationResponse> addVaccinationNotification(@Body VaccinationNotificationRequest vaccinationNotificationRequest);

    @DELETE("vaccination_notification/delete/{id}")
    Call<com.project.petmanagement.payloads.responses.Response> deleteVaccineNotification(@Path("id") Long id);

    @GET("vaccination_notification/{id}")
    Call<VaccineNotificationResponse> getVaccineNotification(@Path("id") Long id);

    @PUT("vaccination_notification/update/{id}")
    Call<VaccineNotificationResponse> updateVaccinationNotification(@Path("id") Long id, @Body VaccinationNotificationRequest vaccinationNotificationRequest);

    // CareActivityController
    @GET("care_activities/all")
    Call<ListCareActivityResponse> getAllCareActivities();


    // CareActivityInfoController
    @PUT("care_activity_info/update/{care_activity_notification_id}")
    Call<Response> updateCareActivityInfo(@Path("care_activity_notification_id") Long careActivityNotificationId, @Body List<CareActivityInfoRequest> careActivityInfoRequestList);

    @DELETE("care_activity_info/delete/{id}")
    Call<Response> deleteCareActivityInfo(@Path("id") Long careActivityInfoId);


    // RecurringScheduleController
    @PUT("recurring_schedules/update")
    Call<Response> updateRecurringSchedule(@Body RecurringScheduleRequest recurringScheduleRequest);


    // CareActivityNotificationController
    @POST("care_activity_notification/add")
    Call<CareActivityNotificationResponse> addCareActivityNotification(@Body CareActivityNotificationRequest careActivityNotificationRequest);

    @GET("care_activity_notification/users")
    Call<ListCareActivityNotificationResponse> getAllCareActivityNotificationByUser();

    @GET("care_activity_notification/date")
    Call<ListCareActivityNotificationResponse> getCareActivityNotificationByDate(@Query("date") String date);

    @GET("care_activity_notification/{id}")
    Call<CareActivityNotificationResponse> getCareActivityNotificationById(@Path("id") Long id);

    @DELETE("care_activity_notification/delete/{id}")
    Call<Response> deleteCareActivityNotification(@Path("id") Long id);

    @PUT("care_activity_notification/update/{id}")
    Call<CareActivityNotificationResponse> updateCareActivityNotification(@Path("id") Long id, @Body CareActivityNotificationRequest careActivityNotificationRequest);


    // DailyActivityController
    @GET("daily_activities/all")
    Call<ListDaiLyActivityResponse> getAllDaiLyActivities();


    // DailyActivityLogController
    @GET("daily_activity_logs/pets/{id}")
    Call<ListDailyActivityLogResponse> getDailyLogsByPet(@Path("id") Long petId);

    @POST("daily_activity_logs/add")
    Call<DailyActivityLogResponse> addDailyLog(@Body DailyActivityLogRequest dailyActivityLogRequest);

    @GET("daily_activity_logs/{id}")
    Call<DailyActivityLogResponse> getDailyLogById(@Path("id") Long dailyLogId);

    @PUT("daily_activity_logs/update")
    Call<DailyActivityLogResponse> updateDailyLog(@Body DailyActivityLogRequest dailyActivityLogRequest);

    @DELETE("daily_activity_logs/delete/{id}")
    Call<DailyActivityLogResponse> deleteDailyLog(@Path("id") Long dailyLogId);
}
