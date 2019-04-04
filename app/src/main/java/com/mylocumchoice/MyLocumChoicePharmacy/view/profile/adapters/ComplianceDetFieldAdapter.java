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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.ComplianceDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.UploadDocVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.DialogClickListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.GlobalConstants;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.OnIntentReceived;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.RecyclerViewClickPositionListener;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.Utils;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.CustomDialog;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.DocViewer;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.RightToWorkActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import droidninja.filepicker.FilePickerBuilder;
import retrofit2.Response;

public class ComplianceDetFieldAdapter extends RecyclerView.Adapter<ComplianceDetFieldAdapter.ViewHolder>
        implements UploadDocVP.View {
    private Context context;
    private Response<ComplianceDetailsRes> response;
    private RecyclerView.ViewHolder viewHolder;
    private ArrayList<ComplianceDetailsRes.Group> arrayList;
    private ArrayList<ComplianceDetailsRes.Field> arrayListField = new ArrayList<>();
    private String from;
    private Activity mActivity;
    private OnIntentReceived listener;
    private CallbackInterface mCallback;
    private RecyclerViewClickPositionListener itemListener;
    private PreferenceAdapterFields adapter;
    private int[] scrollXState = new int[20];
    private String fileTypes[] = {".pdf"};
    private Utils mUtils;
    private ContentValues values;

    List<String> lv_options = new ArrayList<>();
    public static Uri imageUri;


    public ComplianceDetFieldAdapter(Activity mActivity, String from
            , Context context
            , Response<ComplianceDetailsRes> response
            , ArrayList<ComplianceDetailsRes.Field> arrayListField) {

        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayListField = arrayListField;
        this.from = from;
        this.mActivity = mActivity;


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

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.ll_upload_image)
        LinearLayout llUploadImage;
        @BindView(R.id.iv_upload)
        ImageView ivUpload;
        @BindView(R.id.ll_after_upload)
        LinearLayout llAfterUpload;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_pdf)
        TextView tvPdf;
        @BindView(R.id.ll_after_upload_image)
        LinearLayout llAfterUploadImage;
        @BindView(R.id.iv_after_upload)
        ImageView ivAfterUpload;
        @BindView(R.id.ll_status)
        LinearLayout llStatus;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_after_upLoadText)
        TextView tvAfterUpLoadText;
        @BindView(R.id.shadow2)
        View shadow2;

        /**
         * ButterKnife Code
         **/


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_compliance_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (arrayListField.get(position).getTitle() != null) {
            holder.tvTitle.setText(arrayListField.get(position).getTitle());
        } else {
            holder.llTitle.setVisibility(View.GONE);
        }


        if (arrayListField.get(position).getDocumentFieldDetails().getUploadText() != null) {
            holder.tvUpLoadText.setText(arrayListField.get(position).getDocumentFieldDetails().getUploadText());
        } else {
            holder.llUpload.setVisibility(View.GONE);
        }

//        if(arrayListField.size() > 1){
//            holder.shadow.setVisibility(View.GONE);
//        }

        holder.llUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDocChooser();
//                onPickDoc();
                mCallback.onHandleSelection(position, arrayListField.get(position).getId());
//                if (mActivity.getClass().getSimpleName().contains("RightToWorkActivity")) {
//                    saveScrollInsider = RightToWorkActivity.overallYScroll;
//                    savedScroll = saveScrollInsider;
//                }

            }
        });

        if (arrayListField.get(position).getDocumentResponse() != null) {
            holder.llAfterUpload.setVisibility(View.VISIBLE);
            if (arrayListField.get(position).getDocumentResponse().getDocFileName() != null) {
                holder.tvPdf.setText(underLineText(arrayListField.get(position).getDocumentResponse().getDocFileName()));
            }

            if (arrayListField.get(position).getDocumentResponse().getStatus().getName() != null) {
                holder.tvAfterUpLoadText.setText(arrayListField.get(position).getDocumentResponse().getStatus().getName().toString());
            }

            Glide.with(mActivity).load(R.drawable.doc_icon).into(holder.ivImage);


            holder.llUpload.setVisibility(View.GONE);
        }


        holder.llAfterUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDocChooser();
                //viewPosition = vh.getAdapterPosition();
                mCallback.onHandleSelection(position, arrayListField.get(position).getId());
                //mItemListener.recyclerViewListClicked(v, position);
//                if (mActivity.getClass().getSimpleName().contains("RightToWorkActivity")) {
//                    saveScrollInsider = RightToWorkActivity.overallYScroll;
//                    RightToWorkActivity.savedScroll = saveScrollInsider;
//                }
            }
        });

        if (arrayListField.get(position).getDocumentResponse() != null) {
            if (arrayListField.get(position).getDocumentResponse().getIssueRaised() != null) {
                if (!arrayListField.get(position).getDocumentResponse().getIssueRaised().toString().equalsIgnoreCase("")) {
                    if (arrayListField.get(position).getDocumentResponse().getStatus().getName() != null) {
                        holder.tvAfterUpLoadText.setText(underLineText(arrayListField.get(position).getDocumentResponse().getStatus().getName().toString()));
                    }
                }
            } else {
                if (arrayListField.get(position).getDocumentResponse().getStatus().getName() != null) {
                    holder.tvAfterUpLoadText.setText(arrayListField.get(position).getDocumentResponse().getStatus().getName().toString());
                }
            }

            if (arrayListField.get(position).getDocumentResponse().getStatus().getColor() != null) {
                holder.tvAfterUpLoadText.setTextColor(Color.parseColor(arrayListField.get(position).getDocumentResponse().getStatus().getColor()));
            }
        }

        holder.tvPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayListField.get(position).getDocumentResponse().getDocUrl() != null) {
                    if (arrayListField.get(position).getDocumentResponse().getDocUrl() != null) {
                        if (arrayListField.get(position).getDocumentResponse().getDocUrl().contains("jpg")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("document", arrayListField.get(position).getDocumentResponse().getDocUrl());
                            bundle.putString("category", null);
                            Intent intent = new Intent(context, DocViewer.class);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }

                        if (arrayListField.get(position).getDocumentResponse().getDocUrl().contains("pdf")) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.parse(arrayListField.get(position).getDocumentResponse().getDocUrl().toString()), "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                    }
                }
            }
        });

        holder.tvAfterUpLoadText.setOnClickListener(new View.OnClickListener() {
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

        holder.tvUpLoadText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayListField.size();
    }

    @Override
    public void onUploadResponse(Context context, Response<Void> response) {
        if (response.code() == 204) {
            try {
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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


    public void onPickDoc() {
        try {
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
            } else {
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

    @Override
    public void onUploadComplianceResponse(Context context, Response<Void> response) {

    }


    public SpannableString underLineText(String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        return content;
    }


    public void openDocChooser() {
        CustomDialog customDialog = new CustomDialog(mActivity
                , 1
                , lv_options
                , new DialogClickListener() {
            @Override
            public void onMapClick(String title) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (context.checkSelfPermission(Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED || context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                            mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
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
                    } else {
                        Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "New Picture");

                        imageUri = context.getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        mActivity.startActivityForResult(mIntent, 101);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
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


    public void onPhotoPick() {
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
        }else {
            FilePickerBuilder.getInstance().setMaxCount(1)
                    .setActivityTheme(R.style.LibAppTheme)
                    .pickPhoto((Activity) context);

            FilePickerBuilder.getInstance().enableCameraSupport(false);
        }

    }
}
