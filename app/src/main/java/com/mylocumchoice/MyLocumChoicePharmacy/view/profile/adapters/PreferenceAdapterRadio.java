package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.MultiSelectData;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.MultiSelectReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.Selection;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.SingleSelectReq;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PolarFieldPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.profile.PolarFieldVP;
import com.mylocumchoice.MyLocumChoicePharmacy.view.login.activities.LoginActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.BasicDetailsActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.FieldsInputBoxActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.OfferActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.SingleSelectActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.SkillActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class PreferenceAdapterRadio extends RecyclerView.Adapter<PreferenceAdapterRadio.ViewHolder> implements PolarFieldVP.View{

    private Context context;
    private Response<PrefernceRes> response;
    private  RecyclerView.ViewHolder viewHolder;
    private int groupSize = 0;
    private ArrayList<PrefernceRes.SelectOption> arrayList;
    private int select = 0;
    private static int lastCheckedPos = 0;
    private int row_index = -1;
    private PolarFieldPresenter presenter;
    private int field_id ;
    public static String value = "";
    public Activity mActivity;

    private String otherText;
    private int option_id;


    public PreferenceAdapterRadio(Activity mActivity, Context context, ArrayList<PrefernceRes.SelectOption> arrayList, int field_id) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.arrayList = arrayList;
        this.field_id = field_id;
        this.mActivity = mActivity;
    }

    @Override
    public void onFieldResponse(Context context, Response<Void> response) {
        try {
            String errorString;
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            errorString = jObjError.get("error").toString();
            showWarning(mActivity, "", errorString, "error");
        } catch (Exception e) {
            e.printStackTrace();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView photo;

        /** ButterKnife Code **/
        @BindView(R.id.ll_pub)
        LinearLayout llPub;
        @BindView(R.id.rb_Pub)
        ImageView rbPub;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tvOther)
        TextView tvOther;



        /** ButterKnife Code **/

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
        View view = inflater.inflate(R.layout.rv_radio_button, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.llPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(arrayList.get(position).getName());
        holder.llPub.setTag(new Integer(position));


        if(arrayList.get(position).getFieldForOption() != null){
            if(arrayList.get(position).getFieldForOption().getTextResponse() != null){
                if(position == arrayList.size() - 1) {
                    holder.tvOther.setVisibility(View.VISIBLE);
                    holder.tvOther.setText(arrayList.get(position).getFieldForOption().getTextResponse().toString());
                }else{
                    holder.tvOther.setVisibility(View.GONE);
                }
            }
        }

        if(arrayList.get(position).getSelected()) {
            holder.rbPub.setImageResource(R.drawable.rb_on);

        }else{
            holder.rbPub.setImageResource(R.drawable.rb_off);
        }

        holder.llPub.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               row_index = position;
               //notifyDataSetChanged();

               for(int i = 0; i < arrayList.size(); i++) {
                   if(i == position) {
                       if (!arrayList.get(i).getSelected()) {
                           arrayList.get(i).setSelected(true);
                           addModel(field_id, arrayList.get(i).getId(), value);
                           if (arrayList.get(position).getName().equalsIgnoreCase("Other")) {
//                               Bundle bundle = new Bundle();
//                               bundle.putString("isFrom", "singleselect");
//                               bundle.putString("title", arrayList.get(position).getFieldForOption().getPageDetails().getTitle());
//                               bundle.putString("placeholder", arrayList.get(position).getFieldForOption().getTextFieldDetails().getPlaceholder());
//                               bundle.putInt("field_id", field_id);
//                               bundle.putInt("select_id", arrayList.get(i).getId());
//                               //bundle.putStringArrayList("valueList", valueArrayList);
//                               Intent intent = new Intent(context, OfferActivity.class);
//                               intent.putExtras(bundle);
//                               ((Activity) context).startActivityForResult(intent, 2);

                               try{
                                   MultiSelectData mData = new MultiSelectData();
                                   mData.setTitle(arrayList.get(position).getFieldForOption().getTitle().toString());
                                   mData.setPlaceHolder(arrayList.get(position).getFieldForOption().getTextFieldDetails().getPlaceholder());
                                   mData.setText_response(arrayList.get(position).getFieldForOption().getTextResponse());
                                   mData.setFrom("singleselect");
                                   mData.setContext(context);
                                   mData.setActivity(mActivity);
                                   mData.setFieldID(field_id);
                                   mData.setSelectID(arrayList.get(i).getId());
                                   mData.setSelectOptionsList(arrayList);
                                   EventBus.getDefault().postSticky(mData);
                                   Intent intent = new Intent(context, FieldsInputBoxActivity.class);
                                   ((Activity) context).startActivityForResult(intent, 2);
                               }catch (Exception e){

                               }

                           }else{
                               //valueArrayList.add("");
                           }

                       }
                   }else{
                       arrayList.get(i).setSelected(false);
                   }
               }
               notifyDataSetChanged();
           }});

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public void addModel(int field_id,int option_id, String value) {
        SingleSelectReq req = new SingleSelectReq();
        presenter = new PolarFieldPresenter(this);
        presenter.postMutiSelect(context, req.add(field_id, option_id, value));
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            otherText = data.getExtras().get("text").toString();
            option_id = (int)data.getExtras().get("option_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

