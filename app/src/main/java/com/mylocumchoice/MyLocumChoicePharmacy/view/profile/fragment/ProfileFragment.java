package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.LogoutRequest;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ProfileRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ActivateAccountPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ActivateAccountVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.LogoutPresenterImpl;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.LogoutVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ProfilePresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.ProfileVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.CustomAlertDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ProcessDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.LoginActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.AddReferenceActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.BasicDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.ChangePassword;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.HelpActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.OfferActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.PharmaComplianceActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.PreferenceActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.References;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.RightToWorkActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.SkillsAndAcc;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.TermsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.VerificationActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends BaseFragment implements ProfileVP.View, CustomAlertDialog.OnDialogFragmentClickListener, LogoutVP.LogoutView{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int  notification_id=0;

    private OnFragmentInteractionListener mListener;


    /** ButterKnife Code **/
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.ll_profile)
    LinearLayout llProfile;
    @BindView(R.id.iv_profile)
    de.hdodenhof.circleimageview.CircleImageView ivProfile;
    @BindView(R.id.ivh_profile)
    ImageView ivhProfile;
    @BindView(R.id.tv_Name)
    TextView tvName;
    @BindView(R.id.rl_name)
    RelativeLayout rl_name;
    /*@BindView(R.id.iv_dots)
    ImageView ivDots;*/
    @BindView(R.id.tv_status)
    TextView tvStatus;
    /*@BindView(R.id.tv_NameN)
    TextView tvNameN;*/
    /*@BindView(R.id.tv_cancelR)
    TextView tvCancelR;*/
    /*@BindView(R.id.tv_rating)
    TextView tvRating;*/
    /*@BindView(R.id.ratingbar)
    ImageView ratingbar;*/
    @BindView(R.id.ll_dots)
    LinearLayout llDots;
    @BindView(R.id.iv_dotsN)
    ImageView ivDotsN;
    @BindView(R.id.shadow2)
    View shadow2;
    @BindView(R.id.shadow3)
    View shadow3;
    @BindView(R.id.ll_pwd)
    LinearLayout llPwd;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;
    @BindView(R.id.iv_pwd)
    ImageView ivPwd;
    @BindView(R.id.ll_preference)
    LinearLayout llPreference;
    @BindView(R.id.tv_prefs)
    TextView tvPrefs;
    @BindView(R.id.iv_warning_pref)
    ImageView ivWarningPref;
    @BindView(R.id.iv_name_warning)
    ImageView ivWarningName;
    @BindView(R.id.iv_prefs)
    ImageView ivPrefs;
    @BindView(R.id.shadow4)
    View shadow4;
    @BindView(R.id.shadow5)
    View shadow5;
    @BindView(R.id.ll_right)
    LinearLayout llRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_warning_right)
    ImageView ivWarningRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_skills)
    LinearLayout llSkills;
    @BindView(R.id.tv_skills)
    TextView tvSkills;
    @BindView(R.id.iv_warning_skills)
    ImageView ivWarningSkills;
    @BindView(R.id.ll_refer)
    LinearLayout llRefer;
    @BindView(R.id.tv_refer)
    TextView tvRefer;
    @BindView(R.id.iv_warning_refer)
    ImageView ivWarningRefer;
    @BindView(R.id.iv_refer)
    ImageView ivRefer;
    @BindView(R.id.shadow6)
    View shadow6;
    @BindView(R.id.shadow7)
    View shadow7;
    @BindView(R.id.ll_text)
    LinearLayout llText;
    @BindView(R.id.tv_pharma)
    TextView tvPharma;
    @BindView(R.id.ll_pharmacy)
    RelativeLayout llPharmacy;
    @BindView(R.id.tv_pharma_sp)
    TextView tvPharmaSp;
    @BindView(R.id.iv_warning_comp)
    ImageView ivWarningComp;
    @BindView(R.id.iv_pharma_sp)
    ImageView ivPharmaSp;
    @BindView(R.id.shadow8)
    View shadow8;
    @BindView(R.id.shadow9)
    View shadow9;
    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.iv_shop)
    ImageView ivShop;
    @BindView(R.id.shadow10)
    View shadow10;
    @BindView(R.id.shadow11)
    View shadow11;
    @BindView(R.id.ll_help)
    LinearLayout llHelp;
    @BindView(R.id.tv_help)
    TextView tvHelp;
    @BindView(R.id.iv_help)
    ImageView ivHelp;
    @BindView(R.id.ll_contact_us)
    LinearLayout llContactUs;
    @BindView(R.id.tv_contact_us)
    TextView tvContactUs;
    @BindView(R.id.iv_contact_us)
    ImageView ivContactUs;
    @BindView(R.id.ll_terms)
    LinearLayout llTerms;
    @BindView(R.id.tv_terms)
    TextView tvTerms;
    @BindView(R.id.iv_terms)
    ImageView ivTerms;
    @BindView(R.id.shadow12)
    View shadow12;
    @BindView(R.id.shadow13)
    View shadow13;
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.shadow14)
    View shadow14;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_Version)
    TextView tvVersion;
    @BindView(R.id.shadow15)
    View shadow15;
    @BindView(R.id.ll_deactivate)
    LinearLayout llDeactivate;
    @BindView(R.id.tv_deactivate)
    TextView tvDeactivate;
    @BindView(R.id.shadow16)
    View shadow16;
    @BindView(R.id.ratingper)
    TextView ratingper;
    @BindView(R.id.tv_Rate)
    TextView tv_Rate;
    @BindView(R.id.ratingLayout)
    LinearLayout ratingLayout;
    @BindView(R.id.layout_cancelRate)
    LinearLayout layout_cancelRate;
    @BindView(R.id.layout_Rate)
    LinearLayout layout_Rate;

    /** ButterKnife Code **/

    String filepath;
    private SignOutAlert signOutAlert;
    private ProfilePresenter presenter;
    private Activity activity;
    private ProfileRes profileRes;
    private ArrayList<String> photoPaths;
    private CustomAlertDialog generalDialogFragment;
    public static final int GET_FROM_GALLERY = 3;
    private ProcessDialog dialog;
    private ArrayList<ProfileRes> arrayList;
    private String shop_url;

    private Utils mUtils;


    LogoutVP.LogoutPresenter mPresenter;
    ActivateAccountPresenter activateAccountPresenter;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        try {
            notification_id = getArguments().getInt("notification_id");
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new LogoutPresenterImpl(this, getActivity());
        presenter = new ProfilePresenter(this, getActivity());
        getVersionName();
        //getAccount();
        return view;
    }

    private void getVersionName() {

        PackageInfo packageInfo= null;
        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            Log.e("version name is",packageInfo.versionName);
            tvVersion.setText("Version" + " " + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //Version

    }

    @OnClick(R.id.ll_profile)
    public void onClick() {
        startActivity(BasicDetailsActivity.class);
    }

    @OnClick(R.id.ll_shop)
    public void onShopClick() {
        //startActivity(ShopActivity.class);
        try{
            if (!shop_url.equalsIgnoreCase("")) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shop_url));
                startActivity(browserIntent);
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //getActivity().finish();
        //  finish();
    }


    @OnClick(R.id.iv_profile)
    public void onProfile() {
        //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        onPhotoPick();
    }


    @OnClick(R.id.ll_pwd)
    public void onPwdClick() {
        startActivity(ChangePassword.class);
    }

    @OnClick(R.id.ll_preference)
    public void onPrefClick()
    {
        Intent intent = new Intent(getActivity(), PreferenceActivity.class);
        intent.putExtra("from", "profileFrag");
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_from_right, R.anim.slide_to_left).toBundle();
        startActivity(intent, bundle);
    }

//    @OnClick(R.id.ll_skills)
//    public void onSkillsClick() {
//        startActivity(SkillsAndAcc.class);
//    }

    @OnClick(R.id.ll_right)
    public void onRightsClick() {
        Intent intent = new Intent(getActivity(), RightToWorkActivity.class);
        intent.putExtra("from", "profileFrag");
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_from_right, R.anim.slide_to_left).toBundle();
        startActivity(intent, bundle);
    }

    @OnClick(R.id.ll_refer)
    public void onReferClick() {
        startActivity(References.class);
    }


    @OnClick(R.id.ll_help)
    public void onHelpClick() {
        Bundle bundle = new Bundle();
        bundle.putString("status", "Contact");
        startActivityWithBundle(HelpActivity.class, bundle);
    }

    @OnClick(R.id.ll_terms)
    public void onTermsClick() {
        startActivity(TermsActivity.class);
    }

    @OnClick(R.id.ll_logout)
    public void onLogoutClick() {
        alert(getResources().getString(R.string.logoutText), "Cancel", "Confirm");
    }

    @OnClick(R.id.ll_contact_us)
    public void onContactClick() {
        Bundle bundle = new Bundle();
        bundle.putString("isFrom", "Contact");
        startActivityWithBundle(OfferActivity.class, bundle);
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @OnClick(R.id.ll_deactivate)
    public void onDeactClick() {
//        Bundle bundle = new Bundle();
//        bundle.putString("isFrom", "deactivate");
//        startActivityWithBundle(OfferActivity.class, bundle);
    }

    @OnClick(R.id.ll_pharmacy)
    public void onComplianceClick() {
        Intent intent = new Intent(getActivity(), PharmaComplianceActivity.class);
        intent.putExtra("from", "profileFrag");
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }


    @OnClick(R.id.ll_skills)
    public void onSkClick() {
        Intent intent = new Intent(getActivity(), SkillsAndAcc.class);
        intent.putExtra("from", "profileFrag");
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onOkClicked(CustomAlertDialog dialog) {
        presenter.activate(getActivity());
    }

    @Override
    public void onCancelClicked(CustomAlertDialog dialog) {
        generalDialogFragment.dismiss();
    }

    @Override
    public void onLogoutSuccess(Response<Void> response) {
        if (response.code() == 204) {
            startActivity(LoginActivity.class);
            SharedPrefManager.getInstance(getActivity()).clearAllPreferences();
            getActivity().finish();
        }else{
            handleAPIErrors(activity, response);
        }
    }

    @Override
    public void onLogoutFailure() {
        showWarning(getActivity(), "", getResources().getString(R.string.errorTimeOut), "error");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    @Override
//    public void onActivate(Response<Void> response) {
//
//    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }

    public void alert(String title, String cancel, String accept) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .create();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_tc, null);
        TextView tv = dialogView.findViewById(R.id.tvText);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        TextView tvAccept = dialogView.findViewById(R.id.tv_clear);
        tv.setText(title);
        tvAccept.setText(cancel);
        tvCancel.setText(accept);
        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accept.equalsIgnoreCase("Confirm")) {
                    dialog.dismiss();
                    mPresenter.onLogout(LogoutRequest.add(SharedPrefManager.getInstance(getActivity()).getFCMToken()));
                } else if (accept.equalsIgnoreCase("Activate")) {
                    presenter.activate(getActivity());
                    //getAccount();
                    dialog.dismiss();
                }
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setView(dialogView);
        dialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    photoPaths = new ArrayList<>();
                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                    filepath = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA).get(0).toString();
                    String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
                    try {
                        Glide.with(this).load(new File(filepath)).into(ivProfile);
                        //ivhProfile.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    updateProfileRequest(filepath);

                }
                break;

        }
//            Uri selectedImage = data.getData();
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
//                ivProfile.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
    }


    public void onPhotoPick() {

        try {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //File write logic here
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

                mUtils = new Utils();
                //mUtils.showPermDialog(getActivity(),"", getResources().getString(R.string.providePerm) );
            } else {
                FilePickerBuilder.getInstance().setMaxCount(1)
                        .setActivityTheme(R.style.LibAppTheme)
                        .pickPhoto(this);
                FilePickerBuilder.getInstance().enableCameraSupport(false);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getAccount() {
        if(getArguments()!=null){
            presenter.getAccountNotification(getActivity(),getArguments().getInt("notification_id"));
        }else {
            presenter.getAccount(getActivity());
        }
    }


    public void updateProfileRequest(String filepath) {
        File file;
        file = new File(filepath);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;

        if (fileSizeInMB > 10) {

        }
        RequestBody rPic = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part mpDoc = MultipartBody.Part.createFormData("locum[profile_pic]", file.getName(), rPic);
        presenter.uploadImage(getActivity(), mpDoc);
    }


    @Override
    public void getProfileSuccess(Response<ProfileRes> response) {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            Log.e("PROFILE_FRAGMENT", "response: " + gson.toJson(response.body()));
            if (response.code() == 200) {
                try {

                    if(response.body().getPreferencesWarning()){
                        ivWarningPref.setVisibility(View.VISIBLE);
                    }else{
                        ivWarningPref.setVisibility(View.GONE);
                    }


                    if(response.body().getRightToWorkWarning()){
                        ivWarningRight.setVisibility(View.VISIBLE);
                    }else{
                        ivWarningRight.setVisibility(View.GONE);
                    }


                    if(response.body().getSkillsWarning() || response.body().getAccreditationsWarning() || response.body().isSystems_warning()){
                        ivWarningSkills.setVisibility(View.VISIBLE);
                    }else{
                        ivWarningSkills.setVisibility(View.GONE);
                    }


                    if(response.body().getReferencesWarning()){
                        ivWarningRefer.setVisibility(View.VISIBLE);
                    }else {
                        ivWarningRefer.setVisibility(View.GONE);
                    }


                    if(response.body().getRating() != null){
                        tv_Rate.setText(response.body().getRating().toString());
                        ratingLayout.setVisibility(View.VISIBLE);
                    }else{
                        layout_Rate.setVisibility(View.GONE);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10,60,0,0);
                        rl_name.setLayoutParams(params);
                    }


                    if(response.body().getCancellationRate() != null){
                        ratingper.setText(response.body().getCancellationRate().toString());
                        ratingLayout.setVisibility(View.VISIBLE);
                    }else{
                        layout_cancelRate.setVisibility(View.GONE);
                    }


                    if(response.body().getPharmacyComplianceWarning()){
                        ivWarningComp.setVisibility(View.VISIBLE);
                    }else{
                        ivWarningComp.setVisibility(View.GONE);
                    }

                    if(response.body().isBasic_details_warning()){
                        ivWarningName.setVisibility(View.VISIBLE);
                    }else{
                        ivWarningName.setVisibility(View.GONE);
                    }

                    arrayList = new ArrayList<ProfileRes>();
                    //arrayList.addAll(response);
                    shop_url = response.body().getShopLink();
                    tvName.setText(response.body().getName().toString());

                    if (response.body().getStatus() != null) {
                        if (response.body().getStatus().getName() != null) {
                            String str_status = response.body().getStatus().getName();
                            if (response.body().getStatus().getIsLink()) {
                                tvStatus.setText(underLineText(str_status));
                            } else {
                                tvStatus.setText(response.body().getStatus().getName().toString());
                            }
                        }
                        if (response.body().getStatus().getColour() != null) {
                            tvStatus.setTextColor(Color.parseColor(response.body().getStatus().getColour().toString()));
                        }
                    }

                    postResponses(response);

//
                    if (!response.body().getProfilePicUrl().toString().equalsIgnoreCase("")) {
                        try {
                            Glide.with(getActivity()).load(response.body().getProfilePicUrl()).into(ivProfile);
                          //  ivhProfile.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if(response.body().isUnseen_notifications()){
                        LandingActivity.click_bell.setImageDrawable(getResources().getDrawable(R.drawable.notify_dot));
                    }else{
                        LandingActivity.click_bell.setImageDrawable(getResources().getDrawable(R.drawable.menu_bell_off));
                    }

                    try {
                        SharedPrefManager.getInstance(getActivity()).setNotifyDot(response.body().isUnseen_notifications());
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else{
                handleAPIErrors(activity, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccessUpload(Response<Void> response) {
        try {
            String errorString;
            Gson gson = new GsonBuilder().serializeNulls().create();
            Log.e("REFERENCES", "response: " + gson.toJson(response.body()));
            if (response.code() == 201 || response.code() == 200) {
                try {
                    showWarning(getActivity(), "","Profile Pic Successfully Updated", "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getAccount();
            } else{
                handleAPIErrors(activity, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public void postResponses(Response<ProfileRes> response) {
        tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (response.body().getStatus().getIsLink()) {
                    Intent mIntent = new Intent(getActivity(), VerificationActivity.class);
                    startActivity(mIntent);
                } else if (response.body().getDeactivated()) {
                    alert(getResources().getString(R.string.activate_dialog), "Cancel", "Activate");
                    // getAccount();
                }

            }
        });

        llHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("status", response.body().getDeactivated());
                startActivityWithBundle(HelpActivity.class, bundle);
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

    }

    @Override
    public void activateSuccess(Response<Void> response) {
            if (response.code() == 204) {
                try{
                    Intent intent = new Intent(getActivity(), LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    LandingActivity.openFragmentPosition = 5;

                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            getAccount();
                        }
                    }, 200);


                }catch (Exception e) {
                    e.printStackTrace();
                }
            } else{
                handleAPIErrors(activity, response);
            }
    }

    @Override
    public void onResume(){
        super.onResume();
        try {
            getAccount();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}