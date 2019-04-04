package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.MultiSelectData;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PolarFieldReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.Selection;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.StringFieldReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.UpdateReq;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.BasicDetailsPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PolarFieldPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PolarFieldVP;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadCVPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadDocPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadDocVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ApiResponseListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.DialogClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.OnIntentReceived;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.OnRadioClick;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickPositionListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.CustomDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.view.calender.activities.MarkAsBusyActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.AccreditationActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.BasicDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.DocViewer;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.FieldsInputBoxActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.MultiSelectActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.RightToWorkActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.SingleSelectActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.TextSelectActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLDisplay;

import butterknife.BindView;
import butterknife.ButterKnife;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class PreferenceAdapterFields extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements OnIntentReceived,
        PolarFieldVP.View,
        UploadDocVP.View {

    private Context context;
    private Response<PrefernceRes> response;
    private final int MULTISELECT = 0;
    private final int TEXT = 1;
    private final int SINGLESELECT = 2;
    private final int POLAR = 3;
    private final int MULTISELECTREG = 4;
    private final int DOCUMENT = 5;
    private final int INTEGER = 6;
    private final int MULTISELECTHIDE = 7;
    private final int DATE = 8;
    private final int MONTHYEAR = 9;
    private final int YEAR = 10;
    private final int STRING = 11;
    private final int DECIMAL = 12;
    private int groupSize = 0;
    private ArrayList<PrefernceRes.Field> arrayListField;
    private String from;

    private String FROM_PREFERENCES = "Preferences";
    private String FROM_R2W = "Right2Work";
    private String FROM_ACCR = "Accreditation";
    private String FROM_SKILLS = "Skills";

    private PolarFieldPresenter presenter;
    private UploadDocPresenter presenterUpload;

    private String filepath = "";
    private String filename = "";
    private String docUrl, docName;
    private boolean isDocument = false;
    private ArrayList<String> docPaths;
    private String fileTypes[] = {".pdf"};
    private Activity mActivity;
    private OnIntentReceived listener;
    private CallbackInterface mCallback;
    private Utils mUtils;
    private RecyclerViewClickPositionListener mItemListener;
    int viewPosition;
    private ArrayList<PrefernceRes.SelectOption> arrayList;
    private int saveScrollInsider;
    public static int savedScroll = 0;

    private ContentValues values;
    public static Uri imageUri;

    private  CustomDialog customDialog;

    List<String> lv_options=new ArrayList<>();

    ApiResponseListener apiResponseListener;


    public PreferenceAdapterFields(Activity mActivity, String from
            , Context context
            , Response<PrefernceRes> response
            , ArrayList<PrefernceRes.Field> arrayListField
            , OnIntentReceived listener
            , RecyclerViewClickPositionListener mItemListener,ApiResponseListener apiResponseListener) {

        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayListField = arrayListField;
        this.from = from;
        this.mActivity = mActivity;
        this.listener = listener;
        this.mItemListener = mItemListener;
        this.apiResponseListener = apiResponseListener;

        lv_options.add(context.getResources().getString(R.string.choose_camera));
        lv_options.add(context.getResources().getString(R.string.choose_doc));
        lv_options.add(context.getResources().getString(R.string.choose_photo));

        try {
            mCallback = (CallbackInterface) context;
        } catch (ClassCastException ex) {
            //.. should log the error or throw and exception
            Log.e("MyAdapter", "Must implement the CallbackInterface in the Activity", ex);
        }
    }


    public interface CallbackInterface {
        void onHandleSelection(int position, Integer id);
    }


    public class ViewHolderMultiselect extends RecyclerView.ViewHolder {
        // ImageView photo;

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_field)
        LinearLayout llField;
        @BindView(R.id.tv_field)
        TextView tvField;
        @BindView(R.id.shadow2)
        View shadow2;

        /**
         * ButterKnife Code
         **/

        public ViewHolderMultiselect(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderText extends RecyclerView.ViewHolder {
        // ImageView photo;

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_Sub)
        LinearLayout llSub;
        @BindView(R.id.tv_sub)
        TextView tvSub;

        /**
         * ButterKnife Code
         **/

        public ViewHolderText(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderSingleSelect extends RecyclerView.ViewHolder {
        // ImageView photo;

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_field)
        LinearLayout llField;
        @BindView(R.id.tv_field)
        TextView tvField;
        @BindView(R.id.ll_options)
        LinearLayout llOptions;
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.recyclerView)
        android.support.v7.widget.RecyclerView recyclerView;
        @BindView(R.id.ll_below_field)
        LinearLayout llBelowField;
        @BindView(R.id.tv_below_field)
        TextView tvBelowField;
        @BindView(R.id.shadow2)
        View shadow2;
        @BindView(R.id.tvOther)
        TextView tvOther;

        /**
         * ButterKnife Code
         **/

        public ViewHolderSingleSelect(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderPolar extends RecyclerView.ViewHolder {
        // ImageView photo;

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_Switch)
        LinearLayout llSwitch;
        @BindView(R.id.sw_compat)
        Switch swCompat;

        /**
         * ButterKnife Code
         **/

        public ViewHolderPolar(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class ViewHolderMultiSelectReg extends RecyclerView.ViewHolder {
        // ImageView photo;

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_field)
        LinearLayout llField;
        @BindView(R.id.tv_field)
        TextView tvField;

        /**
         * ButterKnife Code
         **/

        public ViewHolderMultiSelectReg(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderDocument extends RecyclerView.ViewHolder {
        // ImageView photo;


        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_upload)
        LinearLayout llUpload;
        @BindView(R.id.tv_upLoadText)
        TextView tvUpLoadText;
        @BindView(R.id.iv_upload)
        ImageView ivUpload;
        @BindView(R.id.ll_after_upload_image)
        LinearLayout llAfterUploaImage;

        @BindView(R.id.ll_upload_image)
        LinearLayout llUploadImage;
        @BindView(R.id.shadow2)
        View shadow;
        @BindView(R.id.ll_after_upload)
        LinearLayout ll_after_upload;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_pdf)
        TextView tvPdf;
        @BindView(R.id.iv_after_upload)
        ImageView ivAfterUpload;
        @BindView(R.id.ll_status)
        LinearLayout llStatus;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_after_upLoadText)
        TextView tvAfterUpLoadText;

        /**
         * ButterKnife Code
         **/

        public ViewHolderDocument(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class ViewHolderInteger extends RecyclerView.ViewHolder {
        // ImageView photo;

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_deb_layout)
        LinearLayout llDebLayout;
        @BindView(R.id.tv_debLab)
        TextView tvDebLab;
        @BindView(R.id.tv_debNum)
        EditText tvDebNum;
        @BindView(R.id.shadow2)
        View shadow2;

        /**
         * ButterKnife Code
         **/

        public ViewHolderInteger(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderMultiSelectHide extends RecyclerView.ViewHolder {
        // ImageView photo;
        @BindView(R.id.ll_field)
        LinearLayout llField;
        @BindView(R.id.tv_field)
        TextView tvField;
        @BindView(R.id.shadow2)
        View shadow2;

        /**
         * ButterKnife Code
         **/

        public ViewHolderMultiSelectHide(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ViewHolderDate extends RecyclerView.ViewHolder {
        // ImageView photo;

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_deb_layout)
        LinearLayout llDebLayout;
        @BindView(R.id.tv_debLab)
        TextView tvDebLab;
        public static EditText tvDebNum;
        @BindView(R.id.shadow2)
        View shadow2;

        /**
         * ButterKnife Code
         **/
        public ViewHolderDate(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvDebNum = itemView.findViewById(R.id.tv_debNum);
        }
    }

    public static class ViewHolderMonthYear extends RecyclerView.ViewHolder {
        // ImageView photo;
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_deb_layout)
        LinearLayout llDebLayout;
        @BindView(R.id.tv_debLab)
        TextView tvDebLab;
        public static EditText tvDebNum;
        @BindView(R.id.shadow2)
        View shadow2;

        /**
         * ButterKnife Code
         **/
        public ViewHolderMonthYear(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvDebNum = itemView.findViewById(R.id.tv_debNum);
        }
    }

    public static class ViewHolderYear extends RecyclerView.ViewHolder {
        // ImageView photo;

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_deb_layout)
        LinearLayout llDebLayout;
        @BindView(R.id.tv_debLab)
        TextView tvDebLab;
        public static EditText tvDebNum;
        @BindView(R.id.shadow2)
        View shadow2;

        /**
         * ButterKnife Code
         **/

        public ViewHolderYear(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvDebNum = itemView.findViewById(R.id.tv_debNum);
        }
    }

    public class ViewHolderString extends RecyclerView.ViewHolder {
        // ImageView photo;

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_deb_layout)
        LinearLayout llDebLayout;
        @BindView(R.id.tv_debLab)
        TextView tvDebLab;
        @BindView(R.id.tv_debNum)
        EditText tvDebNum;
        @BindView(R.id.shadow2)
        View shadow2;

        /**
         * ButterKnife Code
         **/

        public ViewHolderString(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        presenter = new PolarFieldPresenter(this);
        switch (viewType) {
            case MULTISELECT:
                View v1 = inflater.inflate(R.layout.adapter_lv_1, parent, false);
                ViewHolderMultiselect viewHolder = new ViewHolderMultiselect(v1);
                return viewHolder;

            case TEXT:
                View v2 = inflater.inflate(R.layout.adapter_lv_4, parent, false);
                ViewHolderText viewHolder2 = new ViewHolderText(v2);
                return viewHolder2;


            case SINGLESELECT:
                View v4 = inflater.inflate(R.layout.adapter_lv_3, parent, false);
                ViewHolderSingleSelect viewHolder4 = new ViewHolderSingleSelect(v4);
                viewHolder4.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                return viewHolder4;


            case POLAR:
                View v3 = inflater.inflate(R.layout.adapter_lv_12, parent, false);
                ViewHolderPolar viewHolder3 = new ViewHolderPolar(v3);
                return viewHolder3;


            case MULTISELECTREG:
                View v5 = inflater.inflate(R.layout.adapter_lv_1, parent, false);
                ViewHolderMultiSelectReg viewHolder5 = new ViewHolderMultiSelectReg(v5);
                return viewHolder5;

            case DOCUMENT:
                View v6 = inflater.inflate(R.layout.lv_sub_right_2_work_upload, parent, false);
                ViewHolderDocument viewHolder6 = new ViewHolderDocument(v6);
                return viewHolder6;


            case INTEGER:
                View v7 = inflater.inflate(R.layout.lv_sub_right_2_work_dbs_optional, parent, false);
                ViewHolderInteger viewHolder7 = new ViewHolderInteger(v7);
                viewHolder7.tvDebNum.setInputType(INTEGER);
                return viewHolder7;

            case MULTISELECTHIDE:
                View v8 = inflater.inflate(R.layout.adapter_lv_1, parent, false);
                ViewHolderMultiSelectHide viewHolder8 = new ViewHolderMultiSelectHide(v8);
                return viewHolder8;

            case DATE:
                View v9 = inflater.inflate(R.layout.lv_calendar_view, parent, false);
                ViewHolderDate viewHolder9 = new ViewHolderDate(v9);
                return viewHolder9;

            case MONTHYEAR:
                View v10 = inflater.inflate(R.layout.lv_calendar_view, parent, false);
                ViewHolderMonthYear viewHolder10 = new ViewHolderMonthYear(v10);
                return viewHolder10;


            case YEAR:
                View v11 = inflater.inflate(R.layout.lv_calendar_view, parent, false);
                ViewHolderYear viewHolder11 = new ViewHolderYear(v11);
                viewHolder11.tvDebNum.setInputType(INTEGER);
                viewHolder11.tvDebNum.setMaxLines(4);
                return viewHolder11;


            case STRING:
                View v12 = inflater.inflate(R.layout.lv_sub_right_2_work_dbs_optional, parent, false);
                ViewHolderString viewHolder12 = new ViewHolderString(v12);
                viewHolder12.tvDebNum.setInputType(TEXT);
                return viewHolder12;


            case DECIMAL:
                View v13 = inflater.inflate(R.layout.lv_sub_right_2_work_dbs_optional, parent, false);
                ViewHolderString viewHolder13 = new ViewHolderString(v13);
                return viewHolder13;

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case MULTISELECT:
                ViewHolderMultiselect vh1 = (ViewHolderMultiselect) holder;
                configureMultiSelectViewHolder(vh1, position);
                break;

            case TEXT:
                ViewHolderText vh2 = (ViewHolderText) holder;
                configureText(vh2, position);
                break;

            case POLAR:
                ViewHolderPolar vh3 = (ViewHolderPolar) holder;
                configurePolar(vh3, position);
                break;

            case SINGLESELECT:
                ViewHolderSingleSelect vh4 = (ViewHolderSingleSelect) holder;
                configureSingleSelect(vh4, position);
                break;

            case MULTISELECTREG:
                ViewHolderMultiSelectReg vh5 = (ViewHolderMultiSelectReg) holder;
                configureMultiSelectReg(vh5, position);
                break;

            case DOCUMENT:
                ViewHolderDocument vh6 = (ViewHolderDocument) holder;
                configureDocument(vh6, position);
                break;

            case INTEGER:
                ViewHolderInteger vh7 = (ViewHolderInteger) holder;
                configureInteger(vh7, position);
                break;

            case MULTISELECTHIDE:
                ViewHolderMultiSelectHide vh8 = (ViewHolderMultiSelectHide) holder;
                configureMultiSelectHide(vh8, position);
                break;

            case DATE:
                ViewHolderDate vh9 = (ViewHolderDate) holder;
                configureDate(vh9, position);
                break;

            case MONTHYEAR:
                ViewHolderMonthYear vh10 = (ViewHolderMonthYear) holder;
                configureMonthYear(vh10, position);
                break;


            case YEAR:
                ViewHolderYear vh11 = (ViewHolderYear) holder;
                configureYear(vh11, position);
                break;

            case STRING:
                ViewHolderString vh12 = (ViewHolderString) holder;
                configureString(vh12, position);
                break;

            case DECIMAL:
                ViewHolderString vh13 = (ViewHolderString) holder;
                configureDecimal(vh13, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return arrayListField.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayListField.get(position).getType().equalsIgnoreCase("Multi Select")) {
            return MULTISELECT;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("Polar")) {
            return POLAR;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("Text")) {
            return TEXT;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("Single Select")) {
            return SINGLESELECT;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("Multi Select - Registered With")) {
            return MULTISELECTREG;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("Document")) {
            return DOCUMENT;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("Integer")) {
            return INTEGER;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("Multi Select - Hide From")) {
            return MULTISELECTHIDE;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("Date")) {
            return DATE;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("Month & Year")) {
            return MONTHYEAR;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("Year")) {
            return YEAR;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("String")) {
            return STRING;
        } else if (arrayListField.get(position).getType().equalsIgnoreCase("Decimal")) {
            return DECIMAL;
        } else {
            return -1;
        }
    }

    private void configureMultiSelectViewHolder(PreferenceAdapterFields.ViewHolderMultiselect vh, int position) {
        if (arrayListField.get(position).getTitle() != null) {
            vh.tvField.setText(arrayListField.get(position).getTitle());
        } else {
            vh.llField.setVisibility(View.GONE);
        }


        vh.llField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectClick(position);
            }
        });

    }

    private void configureMultiSelectReg(PreferenceAdapterFields.ViewHolderMultiSelectReg vh, int position) {
        if (arrayListField.get(position).getTitle() != null) {
            vh.tvField.setText(arrayListField.get(position).getTitle());
        } else {
            vh.llField.setVisibility(View.GONE);
        }

        vh.llField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectClick(position);
            }
        });
    }

    private void configureMultiSelectHide(PreferenceAdapterFields.ViewHolderMultiSelectHide vh, int position) {
        if (arrayListField.get(position).getTitle() != null) {
            vh.tvField.setText(arrayListField.get(position).getTitle());
        } else {
            vh.llField.setVisibility(View.GONE);
        }

        vh.llField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectClick(position);
            }
        });
    }

    private void configureText(PreferenceAdapterFields.ViewHolderText vh, int position) {
        if (arrayListField.get(position).getTitle() != null) {
            vh.tvTitle.setText(arrayListField.get(position).getTitle());
        } else {
            vh.llTitle.setVisibility(View.GONE);
        }

        if (arrayListField.get(position).getTextResponse() != null) {
            vh.tvSub.setText(arrayListField.get(position).getTextResponse().toString());
            vh.tvSub.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            if (arrayListField.get(position).getTextFieldDetails().getPlaceholder() != null) {
                vh.tvSub.setText(arrayListField.get(position).getTextFieldDetails().getPlaceholder());
                vh.tvSub.setTextColor(context.getResources().getColor(R.color.grey));
            } else {
                vh.llSub.setVisibility(View.GONE);
            }
        }


        vh.llSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MultiSelectData mSelection = new MultiSelectData();
                    mSelection.setFrom("text");
                    mSelection.setFieldID(arrayListField.get(position).getId());
                    mSelection.setActivity(mActivity);
                    mSelection.setTitle(arrayListField.get(position).getPageDetails().getTitle());
                    mSelection.setPlaceHolder(arrayListField.get(position).getTextFieldDetails().getPlaceholder());
                    mSelection.setText_response(arrayListField.get(position).getTextResponse());
                    EventBus.getDefault().postSticky(mSelection);
                    Intent mIntent = new Intent(context, FieldsInputBoxActivity.class);
                    mActivity.startActivityForResult(mIntent, 110);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

//        if (arrayListField.get(position).getTextFieldDetail().getPlaceholder() != null) {
//            vh.tvSub.setText(arrayListField.get(position).getTextFieldDetail().getPlaceholder());
//        } else {
//            vh.llSub.setVisibility(View.GONE);
//        }
    }

    private void configurePolar(PreferenceAdapterFields.ViewHolderPolar vh, int position) {
        vh.tvTitle.setText(arrayListField.get(position).getTitle());
        if (arrayListField.get(position).getPolarResponse()) {
            vh.swCompat.setChecked(true);
        } else {
            vh.swCompat.setChecked(false);
        }

        PolarFieldReq request = new PolarFieldReq();
        vh.swCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    presenter.postPolar(context, request.add(arrayListField.get(position).getId(), true));
                } else {
                    presenter.postPolar(context, request.add(arrayListField.get(position).getId(), false));
                }
            }
        });
    }


    private void configureSingleSelect(PreferenceAdapterFields.ViewHolderSingleSelect vh, int position) {
        arrayList = new ArrayList<>();
        arrayList = arrayListField.get(position).getSelectOptions();
        PreferenceAdapterRadio adapterRadio = new PreferenceAdapterRadio(mActivity, context, arrayList, arrayListField.get(position).getId());
        vh.recyclerView.setAdapter(adapterRadio);

        if (arrayListField.get(position).getTitle() != null) {
            //vh.tvTitle.setText(al_fields.get(position).getTextFieldDetail().getPlaceholder());
        } else {
            vh.llTitle.setVisibility(View.GONE);
        }

        try {
            if (arrayListField.get(position).getPageDetails() == null) {
                vh.llField.setVisibility(View.GONE);
                vh.llOptions.setVisibility(View.VISIBLE);

            } else {
                if (arrayListField.get(position).getPageDetails().getTitle().equals("")
                        || arrayListField.get(position).getPageDetails().getTitle() == null
                        || arrayListField.get(position).getPageDetails().getTitle().equals("null")) {

                } else {
                    vh.llField.setVisibility(View.VISIBLE);
                    vh.llOptions.setVisibility(View.GONE);


                    if (arrayListField.get(position).getTitle() != null) {
                        vh.tvField.setText(arrayListField.get(position).getTitle());
                    } else {
                        vh.llField.setVisibility(View.GONE);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        vh.llField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Selection mSelection = new Selection();
                    mSelection.setType(arrayListField.get(position).getPageDetails().getTitle());
                    mSelection.setFrom(context.getClass().getName());
                    mSelection.setContext(context);
                    mSelection.setContext(mActivity);
                    mSelection.setFieldId(arrayListField.get(position).getId());
                    mSelection.setSelectOptionsList(arrayList);
                    EventBus.getDefault().postSticky(mSelection);
                    Intent mIntent = new Intent(context, SingleSelectActivity.class);
                    context.startActivity(mIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void configureDocument(PreferenceAdapterFields.ViewHolderDocument vh, int position) {

        if (arrayListField.get(position).getTitle() != null) {
            vh.tvTitle.setText(arrayListField.get(position).getTitle());
        } else {
            vh.llTitle.setVisibility(View.GONE);
        }


        if (arrayListField.get(position).getDocumentFieldDetails().getUploadText() != null) {
            vh.tvUpLoadText.setText(arrayListField.get(position).getDocumentFieldDetails().getUploadText());
        } else {
            vh.llUpload.setVisibility(View.GONE);
        }

        if (arrayListField.size() > 1) {
            vh.shadow.setVisibility(View.GONE);
        }

        vh.llUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDocChooser();

                //onPickDoc();
                mCallback.onHandleSelection(position, arrayListField.get(position).getId());
                viewPosition = vh.getLayoutPosition();
                mItemListener.recyclerViewListClicked(v, position);
                if (mActivity.getClass().getSimpleName().contains("RightToWorkActivity")) {
                    try {
                        saveScrollInsider = RightToWorkActivity.overallYScroll;
                        RightToWorkActivity.overallYScroll = 0;
                        RightToWorkActivity.savedScroll = saveScrollInsider;
                        Log.d("value", "jhh");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        if (arrayListField.get(position).getDocumentResponse() != null) {
            vh.ll_after_upload.setVisibility(View.VISIBLE);
            if (arrayListField.get(position).getDocumentResponse().getDocFileName() != null) {
                vh.tvPdf.setText(underLineText(arrayListField.get(position).getDocumentResponse().getDocFileName()));
            }

            if (arrayListField.get(position).getDocumentResponse().getStatus().getName() != null) {
                vh.tvAfterUpLoadText.setText(arrayListField.get(position).getDocumentResponse().getStatus().getName().toString());
            }

            vh.llUpload.setVisibility(View.GONE);
        }


        vh.llAfterUploaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onPickDoc();
                openDocChooser();
                viewPosition = vh.getAdapterPosition();
                mCallback.onHandleSelection(position, arrayListField.get(position).getId());
                mItemListener.recyclerViewListClicked(v, position);
                if (mActivity.getClass().getSimpleName().contains("RightToWorkActivity")) {
                    Log.d("value", "jhh");
                }
            }
        });

        vh.tvPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayListField.get(position).getDocumentResponse().getDocUrl() != null) {
                    if(arrayListField.get(position).getDocumentResponse().getDocUrl().contains("jpg")) {
                        try{
                            Bundle bundle = new Bundle();
                            bundle.putString("document", arrayListField.get(position).getDocumentResponse().getDocUrl());
                            bundle.putString("category", null);
                            Intent intent = new Intent(context, DocViewer.class);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    if(arrayListField.get(position).getDocumentResponse().getDocUrl().contains("pdf")) {
                        try{
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.parse(arrayListField.get(position).getDocumentResponse().getDocUrl().toString()), "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                }
            }
        });


        if (arrayListField.get(position).getDocumentResponse() != null) {
            if (arrayListField.get(position).getDocumentResponse().getIssueRaised() != null) {
                if (!arrayListField.get(position).getDocumentResponse().getIssueRaised().toString().equalsIgnoreCase("")) {
                    vh.tvAfterUpLoadText.setText(underLineText(arrayListField.get(position).getDocumentResponse().getIssueRaised().toString()));
                    vh.tvAfterUpLoadText.setText(arrayListField.get(position).getDocumentResponse().getIssueRaised().toString());
                }

                if (arrayListField.get(position).getDocumentResponse().getStatus().getName() != null) {
                    vh.tvAfterUpLoadText.setText(underLineText(arrayListField.get(position).getDocumentResponse().getStatus().getName().toString()));
                }
            }
            if (arrayListField.get(position).getDocumentResponse().getStatus().getColor() != null) {
                vh.tvAfterUpLoadText.setTextColor(Color.parseColor(arrayListField.get(position).getDocumentResponse().getStatus().getColor()));
            }
        }

        vh.tvAfterUpLoadText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayListField.get(position).getDocumentResponse() != null) {
                    if (arrayListField.get(position).getDocumentResponse().getIssueRaised() != null) {
                        if (!arrayListField.get(position).getDocumentResponse().getIssueRaised().toString().equalsIgnoreCase("")) {
                            mUtils = new Utils();
                            mUtils.showInfoDialog(mActivity, arrayListField.get(position).getDocumentResponse().getIssueRaised().toString(), GlobalConstants.PendingTextShow);
                        }
                    }
                }
            }
        });


        Glide.with(context).load(R.drawable.doc_icon).into(vh.ivImage);



    }

    private void configureInteger(PreferenceAdapterFields.ViewHolderInteger vh, int position) {

        StringFieldReq request = new StringFieldReq();
        vh.tvDebNum.setInputType(InputType.TYPE_CLASS_NUMBER);

        if (arrayListField.get(position).getTextResponse() != null) {
            vh.tvDebNum.setText(arrayListField.get(position).getTextResponse().toString());
        }

        vh.tvDebNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!vh.tvDebNum.getText().toString().equalsIgnoreCase("")) {
                    presenter.postString(context, request.add(arrayListField.get(position).getId(),
                            vh.tvDebNum.getText().toString()));
                }else{
                    presenter.postString(context, request.add(arrayListField.get(position).getId(),
                            ""));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void configureDate(PreferenceAdapterFields.ViewHolderDate vh, int position) {

        if (arrayListField.get(position).getDateResponse() != null) {
            vh.tvDebNum.setText(arrayListField.get(position).getDateResponse().toString());
        }

        if (arrayListField.get(position).getTitle() != null) {
            vh.tvDebLab.setText(arrayListField.get(position).getTitle());
        } else {
            vh.tvDebLab.setVisibility(View.GONE);
        }


        StringFieldReq request = new StringFieldReq();

        vh.llDebLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }catch (Exception e){
                    e.printStackTrace();
                }

                mUtils = new Utils();
                mUtils.fn_showDatePicker(vh.tvDebNum.getText().toString(),"M", mActivity);
            }
        });


        vh.tvDebNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }catch (Exception e){
                    e.printStackTrace();
                }

                mUtils = new Utils();
                mUtils.fn_showDatePicker(vh.tvDebNum.getText().toString(),"M", mActivity);
            }
        });


        vh.tvDebNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!vh.tvDebNum.getText().toString().equalsIgnoreCase("")) {
                    presenter.postString(context, request.add(arrayListField.get(position).getId(),
                            vh.tvDebNum.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void configureMonthYear(PreferenceAdapterFields.ViewHolderMonthYear vh, int position) {
        if (arrayListField.get(position).getTitle() != null) {
            vh.tvDebLab.setText(arrayListField.get(position).getTitle());
        } else {
            vh.tvDebLab.setVisibility(View.GONE);
        }


        if (arrayListField.get(position).getDateResponse() != null) {
            vh.tvDebNum.setText(arrayListField.get(position).getDateResponse().toString());
        }


        StringFieldReq request = new StringFieldReq();

        vh.llDebLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }catch (Exception e){
                    e.printStackTrace();
                }
                mUtils = new Utils();
                mUtils.fn_showMonthYearPicker("MY", mActivity, context);
            }
        });

        vh.tvDebNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    mUtils = new Utils();
                    mUtils.fn_showMonthYearPicker("MY", mActivity, context);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        vh.tvDebNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!vh.tvDebNum.getText().toString().equalsIgnoreCase("")) {
                    presenter.postString(context, request.add(arrayListField.get(position).getId(),
                            vh.tvDebNum.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void configureYear(PreferenceAdapterFields.ViewHolderYear vh, int position) {
        if (arrayListField.get(position).getTitle() != null) {
            vh.tvDebLab.setText(arrayListField.get(position).getTitle());
        } else {
            vh.tvDebLab.setVisibility(View.GONE);
        }

        if (arrayListField.get(position).getDateResponse() != null) {
            vh.tvDebNum.setText(arrayListField.get(position).getDateResponse().toString());
        }

        StringFieldReq request = new StringFieldReq();

        vh.llDebLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    mUtils = new Utils();
                    mUtils.fn_showMonthYearPicker("Y", mActivity, context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        vh.tvDebNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    mUtils = new Utils();
                    mUtils.fn_showMonthYearPicker("Y", mActivity, context);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        vh.tvDebNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!vh.tvDebNum.getText().toString().equalsIgnoreCase("")) {
                    presenter.postString(context, request.add(arrayListField.get(position).getId(),
                            vh.tvDebNum.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void configureString(PreferenceAdapterFields.ViewHolderString vh, int position) {

        vh.llDebLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.tvDebNum.requestFocus();
            }
        });


        if (arrayListField.get(position).getTitle() != null) {
            vh.tvDebLab.setText(arrayListField.get(position).getTitle());
        } else {
            vh.tvDebLab.setVisibility(View.GONE);
        }

        if (arrayListField.get(position).getTextResponse() != null) {
            vh.tvDebNum.setText(arrayListField.get(position).getTextResponse().toString());
        }


        StringFieldReq request = new StringFieldReq();
        vh.tvDebNum.setInputType(STRING);

        vh.tvDebNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!vh.tvDebNum.getText().toString().equalsIgnoreCase("")) {
                    presenter.postString(context, request.add(arrayListField.get(position).getId(),
                            vh.tvDebNum.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void configureDecimal(PreferenceAdapterFields.ViewHolderString vh, int position) {
        StringFieldReq request = new StringFieldReq();
        vh.tvDebNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        if (arrayListField.get(position).getTextResponse() != null) {
            vh.tvDebNum.setText(arrayListField.get(position).getTextResponse().toString());
        }

        vh.tvDebNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!vh.tvDebNum.getText().toString().equalsIgnoreCase("")) {
                    presenter.postString(context, request.add(arrayListField.get(position).getId(),
                            vh.tvDebNum.getText().toString()));
                }else{
                    presenter.postString(context, request.add(arrayListField.get(position).getId(),
                            ""));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onPickDoc() {
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //File write logic here
                    mActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);

                    mUtils = new Utils();
                    //mUtils.showPermDialog(mActivity,"", context.getResources().getString(R.string.providePerm) );
                } else {
                    FilePickerBuilder.getInstance().setMaxCount(1)
                            .setActivityTheme(R.style.LibAppTheme)
                            .addFileSupport("PDF", fileTypes)
                            .enableDocSupport(false)
                            .pickFile((Activity) context);

                }
            }else {
                FilePickerBuilder.getInstance().setMaxCount(1)
                        .setActivityTheme(R.style.LibAppTheme)
                        .addFileSupport("PDF", fileTypes)
                        .enableDocSupport(false)
                        .pickFile((Activity) context);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onPhotoPick() {
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //File write logic here
                    mActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);

                    mUtils = new Utils();
                    //mUtils.showPermDialog(mActivity,"", context.getResources().getString(R.string.providePerm) );
                } else {
                    FilePickerBuilder.getInstance().setMaxCount(1)
                            .setActivityTheme(R.style.LibAppTheme)
                            .pickPhoto((Activity) context);

                    FilePickerBuilder.getInstance().enableCameraSupport(false);
                }
            }else{
                FilePickerBuilder.getInstance().setMaxCount(1)
                        .setActivityTheme(R.style.LibAppTheme)
                        .pickPhoto((Activity) context);

                FilePickerBuilder.getInstance().enableCameraSupport(false);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void onCameraClick(){
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED ||
                        context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                    mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 100);

                    mUtils = new Utils();
                    //mUtils.showPermDialog(mActivity,"", context.getResources().getString(R.string.providePerm) );


                } else {
                    Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    imageUri = context.getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    mIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    mActivity.startActivityForResult(mIntent, 101);
                }
            }else {
                Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                imageUri = context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                mIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                mActivity.startActivityForResult(mIntent, 101);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onIntent(Intent i, int requestCode, int resultCode) {

    }


    public void multiSelectClick(int position) {
        Selection mSelection = new Selection();
        mSelection.setType("Single");
        mSelection.setFrom(context.getClass().getName());
        if (arrayListField.get(position).getSelectOptions() != null) {
            try {
                mSelection.setSelectOptions(arrayListField.get(position).getSelectOptions());
                mSelection.setFieldId(arrayListField.get(position).getId());
                mSelection.setTitle(arrayListField.get(position).getPageDetails().getTitle());
                EventBus.getDefault().postSticky(mSelection);
                mSelection.setActivity(mActivity);
                Intent mIntent = new Intent(context, MultiSelectActivity.class);
                mActivity.startActivityForResult(mIntent, 110);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // Already handled in activities
    @Override
    public void onFieldResponse(Context context, Response<Void> response) {
        if (response.code() == 204) {

        }
        else {
            apiResponseListener.onGetResponse(response);
        }
    }

    @Override
    public void onUploadResponse(Context context, Response<Void> response) {

    }

    @Override
    public void onUploadComplianceResponse(Context context, Response<Void> response) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showWarning(Activity activity, String title, String msg, String error) {

    }

    public SpannableString underLineText(String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        return content;
    }

    public void openDocChooser() {
         customDialog = new CustomDialog(mActivity
                , 1
                , lv_options
                , new DialogClickListener() {
            @Override
            public void onMapClick(String title) {
                onCameraClick();
            }
            @Override
            public void onOpenMapClick(String title) {
                onPickDoc();
            }

            @Override
            public void onThirdClick(String title) {
                onPhotoPick();
            }
        });
        customDialog.show();

    }


}