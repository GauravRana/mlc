package com.mylocumchoice.MyLocumChoicePharmacy.utils;

public class GlobalConstants {

    public interface BaseAPI {

        //public String BASE_URL = "http://192.168.1.94:3000"; //dev url
        //public String BASE_URL = "https://pharmacy.mylocumchoice.poplify.com"; //staging url
        public String BASE_URL = "https://pharmacy.mylocumchoice.com"; //live url
        public String PO = BASE_URL + "/locums/privacy_policy";
        public String TU = BASE_URL + "/locums/terms_of_use";
        public String AP = BASE_URL + "/locums/acceptable_use_policy";
        public String GP = BASE_URL + "/locums/google_terms";
        public String FAQs = BASE_URL + "/locums/faqs";

        public String REG_URL=BASE_URL+"/locums/registration_terms";
        public String SHOP_LINK= "https://store.mylocumchoice.uk/";
    }

    public interface APIs {
        public String GPHC = "/locums/validate_gphc_no";
        public String SIGNUP = "/locums";
        public String SIGNIN = "/locums/sign_in";
        public String FORGOTPASSWORD = "/locums/password";
        public String ACCOUNT = "/locums/account";
        public String BASIC_DETAIL = "/locums/basic_details";
        public String CHANGE_PASSWORD = "/locums/password";
        public String UPDATE_DETAILS = "/locums";
        public String REFERENCES = "/locum_references";
        public String EMAILREFER = "/locum_references/email_form";
        public String PHARMASYS = "/locums/systems";
        public String PHARMASYSUPDATE = "/locums/systems";
        public String UPDATEPROFILE = "/locums";
        public String DEACTIVATEACCOUNT = "/locums/deactivate_account";
        public String ACTIVATEACCOUNT = "/locums/activate_account";
        public String PREFERENCES = "/locums/preferences";
        public String RIGHTTOWORK = "/locums/right_to_work";
        public String ACCREDITATION = "/locums/accreditations";
        public String SKILLS = "/locums/skills";
        public String UPLOAD_CV = "/locums";
        public String OPEN_SHIFTS = "/open_shifts";
        public String INVITATIONS = "/invitations";
        public String SHIFT_DETAILS = "/open_shifts";
        public String APPLIED_SHIFT_DETAILS = "/applied_shifts";
        public String INVITE_SHIFT_DETAILS = "/invitations";
        public String LOGOUT = "/locums/sign_out";
        public String FIELD_RESPONSE = "/locums/field_response";
        public String GET_EVENTS = "/locums/events";
        public String SHIFT = "/shifts";
        public String ACCEPT = "/accept";
        public String APPLY = "/apply";
        public String MAKEOFFER = "/make_an_offer";
        public String CALENDAR_EVENTS = "/locums/calendar_events";
        public String PENDINGSHIFTS = "/pending_applications";
        public String DECLINEDSHIFTS = "/declined_applications";
        public String UPCOMINGBOOKINGS = "/upcoming_bookings";
        public String CANCELAPPLICATION = "/cancel_application";
        public String DELETE_CAL_EVENT = "/locums/delete_calendar_event";
        public String DELETE_ALL_CAL_EVENT = "/locums/delete_future_calendar_events";
        public String CLIENT_COMPLIANCE = "/locums/client_compliances";
        public String COMPLIANCE_UPLOAD = "/locums/client_compliances/response";
        public String COMPLIANCE_EMAIL = "/locums/client_compliances";
        public String NOTIFICATIONS = "/locums/notifications";
        public String READNOTIFICATION = "/locums/read_notification";
        public String DELETENOTIFICATION = "/locums/delete_notifications";
        public String DELETESINGLENOTIFICATION = "/locums/delete_notification";
        public String DELETENOTIFICATION1 = "/locums/delete_notification";
        public String CONTACT_US = "/locums/contact_query";
        public String EMAIL_REGISTRATION = "/locums/email_registration_terms";
        public String HIDE_PHARMACY = "/locums/hide_me_from_pharmacy";
        public String COMPLETED_BOOKINGS = "/completed_bookings";
        public String CANCELLED_BOOKINGS = "/cancelled_bookings";
        public String UPDATE_BOOKING_PAYMENT = "/update_booking_payment";
        public String EMAIL_SUMMARY = "/completed_bookings/email_summary";
        public String VERIFY_STEPS = "/locums/verification_steps";
        public String ACCOUNT_VERIFICATION = "/locums/request_verification";
        public String CANCEL_BOOKING = "/cancel_booking";
        public String RATE_CLIENT = "/rate_client";
    }

    public static int ConfirmHidePharmacy=3;
    public static int AcceptAlert=4;
    public static int PendingTextShow=5;
    public static int InfoReference=6;
    public static int ShowReason=7;
    public static int ConfirmCancel=8;

    public static String SelectedAddress="";
    public static Double SelectedLat=0.0;
    public static Double SelectedLong=0.0;
    public static boolean isEntered=false;

    public static boolean currentLocation=false;
    public static boolean isACtivityResulted=false;

}
