package com.mylocumchoice.MyLocumChoicePharmacy.api;


import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CancelBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.EmailSummaryResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.RatingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.UpcomingBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.UpdatePaymentResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CalendarEventsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.CreateCalendarRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.AppliedShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.calender.EventListResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.ForgotPwdRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.login.SignInRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.notification.NotificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.AccountVerifyRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ChangePwdReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceEmailRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ContactUsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailRegRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmaComplianceVerificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.RtoWResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.UpdateReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.VerificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.HidePharmacyResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftAcceptResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.RegisterReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.BasicDetailRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ChangePwdRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.EmailReferRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmaSysRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PharmacySysUpdateReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ProfileRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ReferenceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.UpdateRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.AccountDetRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.signup.RegisterRes;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Description :"for managing url calls like GET,POST.",
 * Creating RestAPI Interface to Send HTTP Request using Retrofit and We have to create an
 * interface to handle our requests. So create a new RestAPI interface that will handle all HTTP Request.
 */
public interface APIInterface {
    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1"})
    @POST(GlobalConstants.APIs.SIGNUP)
    Call<AccountDetRes> createUser(@Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1"})
    @POST(GlobalConstants.APIs.SIGNIN)
    Call<SignInRes> signIn(@Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1"})
    @POST(GlobalConstants.APIs.GPHC)
    Call<RegisterRes> register(@Body RegisterReq registerReq);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1"})
    @POST(GlobalConstants.APIs.FORGOTPASSWORD)
    Call<ForgotPwdRes> forgot(@Body JsonObject jsonObject);


    /******************/

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.ACCOUNT)
    Call<ProfileRes> getAccount(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.BASIC_DETAIL)
    Call<BasicDetailRes> getBasicDetails(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @PUT(GlobalConstants.APIs.CHANGE_PASSWORD)
    Call<ChangePwdRes> changePassword(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @PUT(GlobalConstants.APIs.UPDATE_DETAILS)
    Call<UpdateRes> updateDetails(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.REFERENCES)
    Call<ReferenceRes> getLocumReference(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Accept: application/json", "X-Api-Version: 1" })
    @Multipart
    @POST(GlobalConstants.APIs.REFERENCES)
    Call<Void> addLocumReference( @Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token
                                         ,@Part ("locum_reference[name]") RequestBody name
                                         ,@Part ("locum_reference[email]") RequestBody txtemail
                                         ,@Part ("locum_reference[job_title]") RequestBody job
                                         ,@Part ("locum_reference[company]") RequestBody company
                                         ,@Part MultipartBody.Part doc
                                        );

    @Headers({"Accept: application/json", "X-Api-Version: 1" })
    @Multipart
    @PUT
    Call<Void> addLocumReferenceID(@Url String url, @Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token
            , @Part ("locum_reference[name]") RequestBody name
            , @Part ("locum_reference[email]") RequestBody txtemail
            , @Part ("locum_reference[job_title]") RequestBody job
            , @Part ("locum_reference[company]") RequestBody company
            , @Part MultipartBody.Part doc
    );

    @Headers({"Accept: application/json", "X-Api-Version: 1" })
    @Multipart
    @PUT
    Call<Void> addLocumReferenceWithoutDoc(@Url String url, @Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token
            , @Part ("locum_reference[name]") RequestBody name
            , @Part ("locum_reference[email]") RequestBody txtemail
            , @Part ("locum_reference[job_title]") RequestBody job
            , @Part ("locum_reference[company]") RequestBody company
    );

    @Headers({"Accept: application/json", "X-Api-Version: 1" })
    @Multipart
    @PUT(GlobalConstants.APIs.UPLOAD_CV)
    Call<Void> uploadCV(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token
            ,@Part MultipartBody.Part doc
    );

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.EMAILREFER)
    Call<EmailReferRes> emailReference(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.PHARMASYS)
    Call<PharmaSysRes> getPharmaSys(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.PHARMASYSUPDATE)
    Call<PharmaSysRes> updatePharmaSys( @Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body PharmacySysUpdateReq pharmacySysUpdateReq);

    @Headers({"Accept: application/json", "X-Api-Version: 1" })
    @Multipart
    @PUT(GlobalConstants.APIs.UPDATEPROFILE)
    Call<Void> updateProfilePic(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token
            , @Part MultipartBody.Part doc
    );

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.DEACTIVATEACCOUNT)
    Call<Void> deactivateAccount(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.ACTIVATEACCOUNT)
    Call<Void> activateAccount(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.PREFERENCES)
    Call<PrefernceRes> getPreferences(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.RIGHTTOWORK)
    Call<PrefernceRes> getRightTOWORK(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.ACCREDITATION)
    Call<PrefernceRes> getAccreditations(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.SKILLS)
    Call<PrefernceRes> getSkills(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1"})
    @POST(GlobalConstants.APIs.OPEN_SHIFTS)
    Call<OpenShiftResponse> openshift(@Header("X-Locum-Email") String email,@Header("X-Locum-Token") String token,@Body JsonObject jsonObject);


    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.FIELD_RESPONSE)
    Call<Void> putToResponse(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);


    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.FIELD_RESPONSE)
    Call<Void> putToResponseOther(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);


    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.SHIFT_DETAILS +"/" + "{id}")
    Call<ShiftDetailsRes> getShiftDetails(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);

    @Headers({"Accept: application/json", "X-Api-Version: 1" })
    @Multipart
    @POST(GlobalConstants.APIs.FIELD_RESPONSE)
    Call<Void> uploadDoc(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token
            ,@Part MultipartBody.Part doc
            ,@Part ("response[field_id]") RequestBody fieldId
    );

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @HTTP(method = "DELETE",path = GlobalConstants.APIs.LOGOUT, hasBody = true)
    Call<Void> logout(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token,@Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1"})
    @POST(GlobalConstants.APIs.INVITATIONS)
    Call<OpenShiftResponse> getShiftInvitations(@Header("X-Locum-Email") String email,@Header("X-Locum-Token") String token,@Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1"})
    @POST(GlobalConstants.APIs.INVITATIONS + "/" + "{id}")
    Call<OpenShiftResponse> getShiftInvitationsDetail(@Header("X-Locum-Email") String email,@Header("X-Locum-Token") String token,@Path(value = "id", encoded = true) int id);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.GET_EVENTS)
    Call<EventListResponse> getEventsList(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.SHIFT +"/" + "{id}" + GlobalConstants.APIs.ACCEPT)
    Call<ShiftAcceptResponse> acceptShift(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.SHIFT +"/" + "{id}" + GlobalConstants.APIs.APPLY)
    Call<ShiftAcceptResponse> applyShift(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.SHIFT +"/" + "{id}" + GlobalConstants.APIs.MAKEOFFER)
    Call<ShiftAcceptResponse> makeShiftOffer(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id,@Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.CALENDAR_EVENTS)
    Call<CalendarEventsRes> getCalenderEvents(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.CALENDAR_EVENTS)
    Call<Void> postCalenderEvents(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.DELETE_CAL_EVENT +"/" + "{id}")
    Call<Void> deleteCalEvent(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.DELETE_ALL_CAL_EVENT +"/" + "{id}")
    Call<Void> deleteAllCalEvent(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.PENDINGSHIFTS)
    Call<AppliedShiftResponse> getPendingShifts(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.DECLINEDSHIFTS)
    Call<AppliedShiftResponse> getDeclinedShifts(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.CANCELAPPLICATION +"/" + "{id}")
    Call<ShiftAcceptResponse> cancelBooking(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.CLIENT_COMPLIANCE)
    Call<ComplianceRes> getCompliance(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.CLIENT_COMPLIANCE +"/" + "{id}")
    Call<ComplianceDetailsRes> getComplianceDetails(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);

    @Headers({"Accept: application/json", "X-Api-Version: 1" })
    @Multipart
    @POST(GlobalConstants.APIs.COMPLIANCE_UPLOAD)
    Call<Void> uploadComplianceDoc(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token
            ,@Part MultipartBody.Part doc
            ,@Part ("response[field_id]") RequestBody fieldId
    );

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.COMPLIANCE_EMAIL +"/" + "{id}" + "/" + "email_documents")
    Call<ComplianceEmailRes> getEmailCompliance(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.UPCOMINGBOOKINGS)
    Call<UpcomingBookingResponse> getUpcomingBookings(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.UPCOMINGBOOKINGS +"/" + "{id}")
    Call<BookingDetailResponse> getBookingDetails(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.COMPLIANCE_EMAIL +"/" + "{id}" + "/" + "request_verification")
    Call<PharmaComplianceVerificationRes> postRequestVerifyCompliance(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);


    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.NOTIFICATIONS)
    Call<NotificationRes> getNotifications(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.READNOTIFICATION + "/" + "{id}")
    Call<Void>readNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);


    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @DELETE(GlobalConstants.APIs.DELETENOTIFICATION)
    Call<Void>deleteNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.HIDE_PHARMACY +"/" + "{id}")
    Call<HidePharmacyResponse> hideFromPharmacy(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.CONTACT_US)
    Call<ContactUsRes>contactUs(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.EMAIL_REGISTRATION)
    Call<EmailRegRes>emailReg(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.COMPLETED_BOOKINGS)
    Call<CompletedBookingResponse> getCompletedBookings(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.COMPLETED_BOOKINGS +"/" + "{id}")
    Call<BookingDetailResponse> getCompletedBookingDetails(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.CANCELLED_BOOKINGS)
    Call<CompletedBookingResponse> getCancelledBookings(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.CANCELLED_BOOKINGS +"/" + "{id}")
    Call<BookingDetailResponse> getCancelledBookingDetails(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.UPDATE_BOOKING_PAYMENT +"/" + "{id}")
    Call<UpdatePaymentResponse> updateBookingPayment(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token,@Path(value = "id", encoded = true) int id, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.EMAIL_SUMMARY)
    Call<EmailSummaryResponse> getPaymentSummary(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.VERIFY_STEPS)
    Call<VerificationRes> getVerifySteps(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.ACCOUNT_VERIFICATION)
    Call<AccountVerifyRes> getVerifyAccount(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.CANCEL_BOOKING +"/" + "{id}")
    Call<CancelBookingResponse> onCancelBooking(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id,@Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    //@DELETE(GlobalConstants.APIs.DELETESINGLENOTIFICATION +"/" + "{id}")
    @DELETE(GlobalConstants.APIs.DELETENOTIFICATION1 +"/" + "{id}")
    Call<Void> onSingleNotifyDelete(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);


    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @POST(GlobalConstants.APIs.RATE_CLIENT)
    Call<RatingResponse> onRatePharmacy(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Body JsonObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.APPLIED_SHIFT_DETAILS +"/" + "{id}")
    Call<ShiftDetailsRes> getshiftApplied(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.INVITE_SHIFT_DETAILS +"/" + "{id}")
    Call<ShiftDetailsRes> getShiftInvite(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id);


    /**
     * Notification API's
     */


    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.SHIFT_DETAILS +"/" + "{id}")
    Call<ShiftDetailsRes> getShiftDetailsNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.APPLIED_SHIFT_DETAILS +"/" + "{id}")
    Call<ShiftDetailsRes> getShiftAppliedNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.INVITE_SHIFT_DETAILS +"/" + "{id}")
    Call<ShiftDetailsRes> getShiftInviteNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.RIGHTTOWORK)
    Call<PrefernceRes> getRightTOWORKNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.UPCOMINGBOOKINGS +"/" + "{id}")
    Call<BookingDetailResponse> getBookingDetailsNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.BASIC_DETAIL)
    Call<BasicDetailRes> getBasicDetailsNotifications(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.PREFERENCES)
    Call<PrefernceRes> getPreferencesNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.SKILLS)
    Call<PrefernceRes> getSkillsNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.ACCREDITATION)
    Call<PrefernceRes> getAccreditationsNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.REFERENCES)
    Call<ReferenceRes> getLocumReferenceNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.CLIENT_COMPLIANCE +"/" + "{id}")
    Call<ComplianceDetailsRes> getComplianceDetailsNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Path(value = "id", encoded = true) int id,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json","Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.GET_EVENTS)
    Call<EventListResponse> getEventsListNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.ACCOUNT)
    Call<ProfileRes> getAccountNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token,@Query("notification_id") int notification_id);

    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Api-Version: 1" })
    @GET(GlobalConstants.APIs.VERIFY_STEPS)
    Call<VerificationRes> getVerifyStepsNotification(@Header("X-Locum-Email") String email, @Header("X-Locum-Token") String token, @Query("notification_id") int notification_id);


}
