package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class ShiftDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int HEADER = 0;
    private final int LOCATION = 1;
    private final int VACANCY = 2;
    private final int OTHER = 3;
    private final int STRING = 4;
    private final int LIST = 5;
    private final int TEXT = 6;
    private final int DATE = 7;
    private int count = 0;
    private int newCount = 0;

    private Context context;
    String distance;
    private ShiftDetailsRes response;
    private ArrayList<ShiftDetailsRes.Detail> arrayListDetail;
    private ArrayList<ShiftDetailsRes.List> arrayListList = new ArrayList<>();

    public ShiftDetailsAdapter(Context context, ShiftDetailsRes response, ArrayList<ShiftDetailsRes.Detail> arrayListDetail,String distance) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.distance = distance;
        this.arrayListDetail = arrayListDetail;
        for(int i = 0; i < response.getShiftDetails().getSections().size(); i++){
            count =  response.getShiftDetails().getSections().get(i).getDetails().size();
            newCount = newCount +  count;
        }
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder {
        // ImageView photo;

        /** ButterKnife Code **/
        @BindView(R.id.ll_profile)
        LinearLayout llProfile;
        @BindView(R.id.iv_profile)
        de.hdodenhof.circleimageview.CircleImageView ivProfile;
        @BindView(R.id.ll_name)
        LinearLayout llName;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ll_price)
        LinearLayout llPrice;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_neg)
        TextView tvNeg;
        @BindView(R.id.shadow)
        View shadow;


//        /** ButterKnife Code **/

        public ViewHolderHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderLocation extends RecyclerView.ViewHolder {
         ImageView photo;

        /** ButterKnife Code **/



        @BindView(R.id.tv_place)
        TextView tvPlace;
        @BindView(R.id.tv_place_sub)
        TextView tvPlaceSub;
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.layout)
        LinearLayout layout;

//        /** ButterKnife Code **/

        public ViewHolderLocation(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderPharma extends RecyclerView.ViewHolder {
        // ImageView photo;

//        /** ButterKnife Code **/


            @BindView(R.id.ll_pharma)
            TextView llPharma;
            @BindView(R.id.ll_pharma_det)
            LinearLayout llPharmaDet;
            @BindView(R.id.tv_pharma)
            TextView tvPharma;
            @BindView(R.id.tv_pharma_det)
            TextView tvPharmaDet;
            @BindView(R.id.shadow)
            View shadow;

//        /** ButterKnife Code **/

        public ViewHolderPharma(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class ViewHolderMandatory extends RecyclerView.ViewHolder {
        // ImageView photo;

        /** ButterKnife Code **/

        @BindView(R.id.ll_Mandatory)
        TextView ll_Mandatory;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.shadow)
        View shadow;

//        /** ButterKnife Code **/

        public ViewHolderMandatory(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderParking extends RecyclerView.ViewHolder {
        // ImageView photo;

        /** ButterKnife Code **/


        @BindView(R.id.ll_Parking)
        LinearLayout llParking;
        @BindView(R.id.tvParking)
        TextView tvParking;
        @BindView(R.id.tvParkingDetails)
        TextView tvParkingDetails;
//        /** ButterKnife Code **/

        public ViewHolderParking(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:
                View v1 = inflater.inflate(R.layout.adapter_top, parent, false);
                ShiftDetailsAdapter.ViewHolderHeader viewHolder = new ShiftDetailsAdapter.ViewHolderHeader(v1);
                return viewHolder;

            case LOCATION:
                View v2 = inflater.inflate(R.layout.adapter_details, parent, false);
                ShiftDetailsAdapter.ViewHolderLocation viewHolder2 = new ShiftDetailsAdapter.ViewHolderLocation(v2);
                viewHolder2.ivImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_white_pin));
                return viewHolder2;


            case VACANCY:
                View v3 = inflater.inflate(R.layout.adapter_details, parent, false);
                ShiftDetailsAdapter.ViewHolderLocation viewHolder3 = new ShiftDetailsAdapter.ViewHolderLocation(v3);
                viewHolder3.ivImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_white_vac));
                return viewHolder3;

            case OTHER:
                View v4 = inflater.inflate(R.layout.adapter_details, parent, false);
                ShiftDetailsAdapter.ViewHolderLocation viewHolder4 = new ShiftDetailsAdapter.ViewHolderLocation(v4);
                viewHolder4.ivImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_white_perls));
                return viewHolder4;

            case STRING:
                View v5 = inflater.inflate(R.layout.adapter_pharma_details, parent, false);
                ShiftDetailsAdapter.ViewHolderPharma viewHolder5 = new ShiftDetailsAdapter.ViewHolderPharma(v5);
                return viewHolder5;

            case LIST:
                View v6 = inflater.inflate(R.layout.adapter_mandatory, parent, false);
                ShiftDetailsAdapter.ViewHolderMandatory viewHolder6 = new ShiftDetailsAdapter.ViewHolderMandatory(v6);
                return viewHolder6;


            case TEXT:
                View v7 = inflater.inflate(R.layout.adapter_parking, parent, false);
                ShiftDetailsAdapter.ViewHolderParking viewHolder7 = new ShiftDetailsAdapter.ViewHolderParking(v7);
                return viewHolder7;

            case DATE:
                View v8 = inflater.inflate(R.layout.adapter_details, parent, false);
                ShiftDetailsAdapter.ViewHolderLocation viewHolder8 = new ShiftDetailsAdapter.ViewHolderLocation(v8);
                viewHolder8.ivImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_white_cal));
                return viewHolder8;

        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case HEADER:
                ShiftDetailsAdapter.ViewHolderHeader vh1 = (ShiftDetailsAdapter.ViewHolderHeader) holder;
                configureheader(vh1, position);
                break;

            case VACANCY:
                ShiftDetailsAdapter.ViewHolderLocation vh2 = (ShiftDetailsAdapter.ViewHolderLocation) holder;
                configureVacancy(vh2, position);
                break;

            case LOCATION:
                ShiftDetailsAdapter.ViewHolderLocation vh3 = (ShiftDetailsAdapter.ViewHolderLocation) holder;
                configureLocation(vh3, position);
                break;

            case STRING:
                ShiftDetailsAdapter.ViewHolderPharma vh4 = (ShiftDetailsAdapter.ViewHolderPharma) holder;

                configureString(vh4, position);
                break;

            case LIST:
                ShiftDetailsAdapter.ViewHolderMandatory vh5 = (ShiftDetailsAdapter.ViewHolderMandatory) holder;
                configureList(vh5, position);
                break;

            case OTHER:
                ShiftDetailsAdapter.ViewHolderLocation vh6 = (ShiftDetailsAdapter.ViewHolderLocation) holder;
                configureOther(vh6, position);
                break;

            case TEXT:
                ShiftDetailsAdapter.ViewHolderParking vh7 = (ShiftDetailsAdapter.ViewHolderParking) holder;
                configureText(vh7, position);
                break;

            case DATE:
                ShiftDetailsAdapter.ViewHolderLocation vh8 = (ShiftDetailsAdapter.ViewHolderLocation) holder;
                configureDate(vh8, position);
                break;

        }
    }

    private void configureheader(ShiftDetailsAdapter.ViewHolderHeader vh, int position) {
        vh.tvName.setText((String) arrayListDetail.get(position).getValue1().toString());

        if(response.getShiftDetails().getEmergency()){
            vh.tvNeg.setVisibility(View.VISIBLE);
            vh.tvPrice.setText(arrayListDetail.get(position).getValue2().toString());
            vh.tvNeg.setText("Negotiable");
        }else {
            vh.tvNeg.setVisibility(View.GONE);
            vh.tvPrice.setVisibility(View.VISIBLE);
            vh.tvPrice.setText(arrayListDetail.get(position).getValue2().toString());
        }

        if (position == 0) {
            vh.shadow.setVisibility(View.VISIBLE);
            if (arrayListDetail.get(position).getImageUrl() != null) {
                if (arrayListDetail.get(position).getImageUrl() instanceof String) {
                    if (!((String) arrayListDetail.get(position).getImageUrl()).equals("") && !((String) arrayListDetail.get(position).getImageUrl()).equals(null)) {
                        Glide.with(context).load((String) arrayListDetail.get(position).getImageUrl()).into(vh.ivProfile);
                    } else {
                        Glide.with(context).load(R.drawable.pharmacy_placeholder).into(vh.ivProfile);
                    }
                } else {
                    Glide.with(context).load(R.drawable.pharmacy_placeholder).into(vh.ivProfile);
                }
            } else {
                Glide.with(context).load(R.drawable.pharmacy_placeholder).into(vh.ivProfile);
            }
        } else {
            vh.shadow.setVisibility(View.GONE);

            if (arrayListDetail.get(position).getImageUrl() != null) {
                if (arrayListDetail.get(position).getImageUrl() instanceof String) {
                    if (!((String) arrayListDetail.get(position).getImageUrl()).equals("") && !((String) arrayListDetail.get(position).getImageUrl()).equals(null)) {
                        Glide.with(context).load((String) arrayListDetail.get(position).getImageUrl()).into(vh.ivProfile);
                    } else {
                        Glide.with(context).load(R.drawable.pharmacy_placeholder).into(vh.ivProfile);
                    }
                } else {
                    Glide.with(context).load(R.drawable.pharmacy_placeholder).into(vh.ivProfile);
                }
            } else {
                Glide.with(context).load(R.drawable.pharmacy_placeholder).into(vh.ivProfile);
            }

        }
    }

    private void configureVacancy(ShiftDetailsAdapter.ViewHolderLocation vh, int position) {
        vh.tvPlace.setText(arrayListDetail.get(position).getValue1().toString());
        vh.tvPlaceSub.setText(arrayListDetail.get(position).getValue2().toString());
    }

    private void configureLocation(ShiftDetailsAdapter.ViewHolderLocation vh, int position) {
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , R.dimen._55sdp);
//        vh.layout.setLayoutParams(layoutParams);

        try {
            vh.tvPlace.setText(arrayListDetail.get(position).getValue1().toString());
            if (position == arrayListDetail.size()) {

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(distance.equals("")||distance==null){
            vh.tvPlaceSub.setVisibility(View.GONE);
        }else {
            vh.tvPlaceSub.setVisibility(View.VISIBLE);
            vh.tvPlaceSub.setText(distance);
        }

    }

    private void configureOther(ShiftDetailsAdapter.ViewHolderLocation vh, int position) {
        vh.tvPlace.setText(arrayListDetail.get(position).getValue1().toString());
        vh.tvPlaceSub.setText(arrayListDetail.get(position).getValue2().toString());
    }

    private void configureString(ShiftDetailsAdapter.ViewHolderPharma vh, int position) {
        vh.tvPharma.setText(arrayListDetail.get(position).getValue1().toString());
        vh.tvPharmaDet.setText(arrayListDetail.get(position).getValue2().toString());


        if(position == 0) {
            vh.shadow.setVisibility(View.VISIBLE);
        }else{
            vh.shadow.setVisibility(View.GONE);
        }

        if(position == 0) {
            vh.llPharma.setVisibility(View.VISIBLE);
        }else{
            vh.llPharma.setVisibility(View.GONE);

        }
    }

    private void configureList(ShiftDetailsAdapter.ViewHolderMandatory vh, int position) {

        vh.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        arrayListList = arrayListDetail.get(position).getLists();
        SkillsListAdapterOption adapter = new SkillsListAdapterOption(context, response, arrayListList);
        vh.recyclerView.setAdapter(adapter);

        if(position == 0) {
            vh.shadow.setVisibility(View.VISIBLE);
        }else{
            vh.shadow.setVisibility(View.GONE);
        }

        if(position == 0) {
            vh.ll_Mandatory.setVisibility(View.VISIBLE);
        }else{
            vh.ll_Mandatory.setVisibility(View.GONE);
        }

//        Log.e("al_values",al_values.get(position).getTitle());
//        vh.tvTitle.setText(al_values.get(position).getTitle());
    }


    private void configureText(ShiftDetailsAdapter.ViewHolderParking vh, int position) {
        vh.tvParking.setText(arrayListDetail.get(position).getValue1().toString());
        vh.tvParkingDetails.setText(arrayListDetail.get(position).getValue2().toString());


    }

    private void configureDate(ShiftDetailsAdapter.ViewHolderLocation vh, int position) {
        vh.tvPlace.setText(arrayListDetail.get(position).getValue1().toString());
        vh.tvPlaceSub.setText(arrayListDetail.get(position).getValue2().toString());
    }


    @Override
    public int getItemViewType(int position) {
        try {
            if (arrayListDetail.get(position).getType().equalsIgnoreCase("header")) {
                return HEADER;
            } else if (arrayListDetail.get(position).getType().equalsIgnoreCase("location")) {
                return LOCATION;
            } else if (arrayListDetail.get(position).getType().equalsIgnoreCase("vacancies")) {
                return VACANCY;
            } else if (arrayListDetail.get(position).getType().equalsIgnoreCase("other")) {
                return OTHER;
            } else if (arrayListDetail.get(position).getType().equalsIgnoreCase("string")) {
                return STRING;
            } else if (arrayListDetail.get(position).getType().equalsIgnoreCase("list")) {
                return LIST;
            } else if (arrayListDetail.get(position).getType().equalsIgnoreCase("text")) {
                return TEXT;
            } else if (arrayListDetail.get(position).getType().equalsIgnoreCase("date")) {
                return DATE;
            } else {
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d("EXCEPTION", e.toString());
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return arrayListDetail.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
