package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.BookingDetailResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int HEADER = 0;
    private final int CONTACT = 1;
    private final int LOCATION = 2;
    private final int VACANCY = 3;
    private final int OTHER = 4;
    private final int STRING = 5;
    private final int LIST = 6;
    private final int TEXT = 7;
    private final int DATE = 8;
    private int count = 0;
    private int newCount = 0;

    private Context context;
    private Activity activity;
    ItemClickListener itemClickListener;
    //String distance;
    private BookingDetailResponse response;
    private List<BookingDetailResponse.Detail> arrayListDetail;
    private List<BookingDetailResponse.Lists> arrayListList = new ArrayList<>();

    public BookingDetailsAdapter(Context context, BookingDetailResponse response, List<BookingDetailResponse.Detail> arrayListDetail,ItemClickListener itemClickListener) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.activity = activity;
        //this.distance = distance;
        this.arrayListDetail = arrayListDetail;
        this.itemClickListener = itemClickListener;
        for (int i = 0; i < response.getShiftDetails().getSections().size(); i++) {
            count = response.getShiftDetails().getSections().get(i).getDetails().size();
            newCount = newCount + count;
        }
    }
    public BookingDetailsAdapter(Context context, BookingDetailResponse response, List<BookingDetailResponse.Detail> arrayListDetail) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.activity = activity;
        //this.distance = distance;
        this.arrayListDetail = arrayListDetail;
        this.itemClickListener = itemClickListener;
        for (int i = 0; i < response.getShiftDetails().getSections().size(); i++) {
            count = response.getShiftDetails().getSections().get(i).getDetails().size();
            newCount = newCount + count;
        }
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder {
        // ImageView photo;

        /**
         * ButterKnife Code
         **/
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
        @BindView(R.id.topSpace)
        LinearLayout topSpace;
        @BindView(R.id.layout)
        LinearLayout layout;



//        /** ButterKnife Code **/

        public ViewHolderHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolderLocation extends RecyclerView.ViewHolder {
        ImageView photo;

        /**
         * ButterKnife Code
         **/

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

        /**
         * ButterKnife Code
         **/

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

        /**
         * ButterKnife Code
         **/


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

    public class ViewHolderContact extends RecyclerView.ViewHolder {


        @BindView(R.id.ll_call)
        LinearLayout ll_call;
        @BindView(R.id.ll_Email)
        LinearLayout ll_Email;
        @BindView(R.id.ll_EEmail)
        LinearLayout ll_EEmail;


        public ViewHolderContact(View itemView) {
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
                ViewHolderHeader viewHolder = new ViewHolderHeader(v1);
                return viewHolder;

            case LOCATION:
                View v2 = inflater.inflate(R.layout.adapter_details, parent, false);
                ViewHolderLocation viewHolder2 = new ViewHolderLocation(v2);
                viewHolder2.ivImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_white_pin));
                return viewHolder2;


            case VACANCY:
                View v3 = inflater.inflate(R.layout.adapter_details, parent, false);
                ViewHolderLocation viewHolder3 = new ViewHolderLocation(v3);
                viewHolder3.ivImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_white_vac));
                return viewHolder3;

            case OTHER:
                View v4 = inflater.inflate(R.layout.adapter_details, parent, false);
                ViewHolderLocation viewHolder4 = new ViewHolderLocation(v4);
                viewHolder4.ivImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_white_perls));
                return viewHolder4;

            case STRING:
                View v5 = inflater.inflate(R.layout.adapter_pharma_details, parent, false);
                ViewHolderPharma viewHolder5 = new ViewHolderPharma(v5);
                return viewHolder5;

            case LIST:
                View v6 = inflater.inflate(R.layout.adapter_mandatory, parent, false);
                ViewHolderMandatory viewHolder6 = new ViewHolderMandatory(v6);
                return viewHolder6;


            case TEXT:
                View v7 = inflater.inflate(R.layout.adapter_parking, parent, false);
                ViewHolderParking viewHolder7 = new ViewHolderParking(v7);
                return viewHolder7;

            case DATE:
                View v8 = inflater.inflate(R.layout.adapter_details, parent, false);
                ViewHolderLocation viewHolder8 = new ViewHolderLocation(v8);
                viewHolder8.ivImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_white_cal));
                return viewHolder8;

            case CONTACT:
                View v9 = inflater.inflate(R.layout.layout_contact, parent, false);
                ViewHolderContact viewHolder9 = new ViewHolderContact(v9);
                return viewHolder9;


        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER:
                ViewHolderHeader vh1 = (ViewHolderHeader) holder;
                configureheader(vh1, position);
                break;

            case VACANCY:
                ViewHolderLocation vh2 = (ViewHolderLocation) holder;
                configureVacancy(vh2, position);
                break;

            case LOCATION:
                ViewHolderLocation vh3 = (ViewHolderLocation) holder;
                configureLocation(vh3, position);
                break;

            case STRING:
                ViewHolderPharma vh4 = (ViewHolderPharma) holder;

                configureString(vh4, position);
                break;

            case LIST:
                ViewHolderMandatory vh5 = (ViewHolderMandatory) holder;
                configureList(vh5, position);
                break;

            case OTHER:
                ViewHolderLocation vh6 = (ViewHolderLocation) holder;
                configureOther(vh6, position);
                break;

            case TEXT:
                ViewHolderParking vh7 = (ViewHolderParking) holder;
                configureText(vh7, position);
                break;

            case DATE:
                ViewHolderLocation vh8 = (ViewHolderLocation) holder;
                configureDate(vh8, position);
                break;

            case CONTACT:
                ViewHolderContact vh9 = (ViewHolderContact) holder;
                configureContact(vh9, position);
                break;

        }
    }

    private void configureheader(ViewHolderHeader vh, int position) {
        vh.tvName.setText((String) arrayListDetail.get(position).getValue1());
        vh.tvNeg.setVisibility(View.GONE);
        /*if(vh.tvName.getText().length() < 15){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT);
            params.weight = 1.0f;
            params.gravity = Gravity.CENTER;
            vh.llName.setLayoutParams(params);
        }*/
        vh.tvPrice.setText((String) arrayListDetail.get(position).getValue2());

        if(((String) arrayListDetail.get(position).getValue2()).contains(context.getResources().getString(R.string.pound))&& ((String)arrayListDetail.get(position).getValue2()).contains("/hr")){
            vh.tvPrice.setText((String) arrayListDetail.get(position).getValue2());
        }else {
            vh.tvPrice.setText(context.getResources().getString(R.string.pound) + (String) arrayListDetail.get(position).getValue2() + "/hr");
        }
        vh.topSpace.setVisibility(View.GONE);
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

    private void configureVacancy(ViewHolderLocation vh, int position) {
        vh.tvPlace.setText(arrayListDetail.get(position).getValue1().toString());
        vh.tvPlaceSub.setText(arrayListDetail.get(position).getValue2().toString());
    }

    private void configureLocation(ViewHolderLocation vh, int position) {
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , R.dimen._55sdp);
//        vh.layout.setLayoutParams(layoutParams);

        vh.tvPlace.setText(arrayListDetail.get(position).getValue1().toString());
        if(arrayListDetail.get(position).getValue2()==null){
            vh.tvPlaceSub.setVisibility(View.GONE);
        }



        /*if (distance.equals("")) {
            vh.tvPlaceSub.setVisibility(View.GONE);
        } else {
            vh.tvPlaceSub.setVisibility(View.VISIBLE);
            vh.tvPlaceSub.setText(distance);
        }*/

    }

    private void configureOther(ViewHolderLocation vh, int position) {
        vh.tvPlace.setText(arrayListDetail.get(position).getValue1().toString());
        vh.tvPlaceSub.setText(arrayListDetail.get(position).getValue2().toString());
    }

    private void configureString(ViewHolderPharma vh, int position) {
        vh.tvPharma.setText(arrayListDetail.get(position).getValue1().toString());
        vh.tvPharmaDet.setText(arrayListDetail.get(position).getValue2().toString());


        if (position == 0) {
            vh.shadow.setVisibility(View.VISIBLE);
        } else {
            vh.shadow.setVisibility(View.GONE);
        }

        if (position == 0) {
            vh.llPharma.setVisibility(View.VISIBLE);
        } else {
            vh.llPharma.setVisibility(View.GONE);

        }
    }

    private void configureList(ViewHolderMandatory vh, int position) {

        vh.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        arrayListList = arrayListDetail.get(position).getLists();
        BookingAdapterOptions adapter = new BookingAdapterOptions(context, response, arrayListList);
        vh.recyclerView.setAdapter(adapter);

        if (position == 0) {
            vh.shadow.setVisibility(View.VISIBLE);
        } else {
            vh.shadow.setVisibility(View.GONE);
        }

        if (position == 0) {
            vh.ll_Mandatory.setVisibility(View.VISIBLE);
        } else {
            vh.ll_Mandatory.setVisibility(View.GONE);
        }

//        Log.e("al_values",al_values.get(position).getTitle());
//        vh.tvTitle.setText(al_values.get(position).getTitle());
    }


    private void configureText(ViewHolderParking vh, int position) {
        vh.tvParking.setText(arrayListDetail.get(position).getValue1().toString());
        vh.tvParkingDetails.setText(arrayListDetail.get(position).getValue2().toString());


    }

    private void configureDate(ViewHolderLocation vh, int position) {
        vh.tvPlace.setText(arrayListDetail.get(position).getValue1().toString());
        vh.tvPlaceSub.setText(arrayListDetail.get(position).getValue2().toString());
    }

    private void configureContact(ViewHolderContact vh, int position) {

        vh.ll_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (itemClickListener != null) {
                        for(int i=0;i<arrayListDetail.get(position).getDetails().size();i++){
                            if(arrayListDetail.get(position).getDetails().get(i).getName().equalsIgnoreCase("contact_no")){
                                if(arrayListDetail.get(position).getDetails().get(i).getValue()!=null){
                                    if(arrayListDetail.get(position).getDetails().get(i).getValue() instanceof String){
                                        /*Uri number = Uri.parse("tel:" + (String) arrayListDetail.get(position).getDetails().get(i).getValue().trim());
                                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                        callIntent.setData(number);
                                        context.startActivity(callIntent);*/

                                        itemClickListener.onClick((String) arrayListDetail.get(position).getDetails().get(i).getValue().trim());
                                    }
                                }
                            }
                        }
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }
        });


        vh.ll_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<arrayListDetail.get(position).getDetails().size();i++) {
                    if (arrayListDetail.get(position).getDetails().get(i).getName().equalsIgnoreCase("email")) {
                        if(arrayListDetail.get(position).getDetails().get(i).getValue()!=null){
                            if(arrayListDetail.get(position).getDetails().get(i).getValue() instanceof String){
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("message/rfc822");
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{(String) arrayListDetail.get(position).getDetails().get(i).getValue()});
                                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                                intent.putExtra(Intent.EXTRA_TEXT, "");
                                try {
                                    context.startActivity(Intent.createChooser(intent, ""));
                                } catch (ActivityNotFoundException ex) {
                                    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        });

        vh.ll_EEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<arrayListDetail.get(position).getDetails().size();i++){
                        if(arrayListDetail.get(position).getDetails().get(i).getValue()!=null){
                            if(arrayListDetail.get(position).getDetails().get(i).getValue() instanceof String){
                                /*Uri number = Uri.parse("tel:" + (String) arrayListDetail.get(position).getDetails().get(i).getValue().trim());
                                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                                callIntent.setData(number);
                                context.startActivity(callIntent);*/

                                itemClickListener.onClick((String) arrayListDetail.get(position).getDetails().get(i).getValue().trim());

                            }
                        }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListDetail.size();
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (arrayListDetail.get(position).getType().equalsIgnoreCase("header")) {
                return HEADER;
            } else if (arrayListDetail.get(position).getType().equalsIgnoreCase("location")) {
                return LOCATION;
            } else if (arrayListDetail.get(position).getType().equalsIgnoreCase("contact")) {
                return CONTACT;
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
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("EXCEPTION", e.toString());
        }
        return -1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
