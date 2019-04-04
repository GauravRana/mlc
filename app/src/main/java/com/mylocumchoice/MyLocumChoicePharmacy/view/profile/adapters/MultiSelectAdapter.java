package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.MultiSelectData;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.MultiSelectReq;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.SelectedOptionModel;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.Selection;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.FieldsInputBoxActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities.OfferActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;

public class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.ViewHolder> {
    private Context context;

    private ArrayList<Integer> optionArrayList = new ArrayList<>();
    private ArrayList<String> valueArrayList = new ArrayList<>();


    private String otherText;
    private int option_id;

    private boolean isFromActivity = false;
    private ViewHolder vh;


    public interface ClickInterface {
        public void recyclerviewOnClick(@NonNull ViewHolder holder, int position);
    }

    List<PrefernceRes.SelectOption> al_selectOptions;

    List<PrefernceRes.Field> al_fields;
    private ClickInterface listener;

    public MultiSelectAdapter(Context context, List<PrefernceRes.SelectOption> al_selectOptions, List<PrefernceRes.Field> al_fields) {
        this.context = context;
        this.al_selectOptions = al_selectOptions;
        this.al_fields = al_fields;
        optionArrayList.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /** ButterKnife Code **/

        TextView tvTitle;
        ToggleButton toggleButton;
        LinearLayout layoutPharma;
        TextView tvOther;
        LinearLayout ll_Sub;

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
        View v = inflater.inflate(R.layout.multi_select_adapter, parent, false);
        vh = new ViewHolder(v);
        vh.layoutPharma = v.findViewById(R.id.ll_pharmaManager);
        vh.toggleButton = v.findViewById(R.id.toggle);
        vh.tvTitle = (TextView) v.findViewById(R.id.tv_title);
        vh.tvOther = (TextView) v.findViewById(R.id.tvOther);
        vh.ll_Sub = (LinearLayout) v.findViewById(R.id.ll_Sub);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(al_selectOptions.get(position).getName());
        // initialize selected options
        if(al_selectOptions.get(position).getSelected()){
            holder.toggleButton.setChecked(true);
            optionArrayList.add(al_selectOptions.get(position).getId());
            if(al_selectOptions.get(position).getName().equalsIgnoreCase("Other") ||
                    al_selectOptions.get(position).getName().equalsIgnoreCase("Other PGDS")){
                if(al_selectOptions.get(position).getFieldForOption() != null){
                    if(al_selectOptions.get(position).getFieldForOption().getTextResponse() != null) {
                        if (!al_selectOptions.get(position).getFieldForOption().getTextResponse().toString().equalsIgnoreCase("")) {
                            otherText = al_selectOptions.get(position).getFieldForOption().getTextResponse().toString();
                            option_id = al_selectOptions.get(position).getId();
                        }
                    }
                }
            }
        }else{
            holder.toggleButton.setChecked(false);
        }


        //Visibility check
        if(al_selectOptions.get(position).getName().equalsIgnoreCase("Other PGDS")
                || al_selectOptions.get(position).getName().equalsIgnoreCase("Other")){
            if(al_selectOptions.get(position).getFieldForOption() != null){
                if(al_selectOptions.get(position).getFieldForOption().getTextResponse() != null) {
                    if (!al_selectOptions.get(position).getFieldForOption().getTextResponse().toString().equalsIgnoreCase("")) {
                        holder.ll_Sub.setVisibility(View.VISIBLE);
                        holder.tvOther.setText(al_selectOptions.get(position).getFieldForOption().getTextResponse().toString());
                    }
                }
            }
        }



        holder.ll_Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromActivity = true;
                MultiSelectData mData = new MultiSelectData();
                mData.setFrom("multiselect");
                mData.setOptionID(al_selectOptions.get(position).getId());
                mData.setTitle(al_selectOptions.get(position).getFieldForOption().getPageDetails().getTitle());
                mData.setPlaceHolder( al_selectOptions.get(position).getFieldForOption().getTextFieldDetails().getPlaceholder());
                mData.setOptionalText(vh.tvOther.getText().toString());
                mData.setPosition(position);
                EventBus.getDefault().postSticky(mData);
                Intent intent = new Intent(context, FieldsInputBoxActivity.class);
                ((Activity) context).startActivityForResult(intent, 2);
            }
        });


        holder.layoutPharma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.toggleButton.isChecked()) {
                    //Remove from Lists
                    optionArrayList.remove(new Integer(al_selectOptions.get(position).getId()));
                    holder.toggleButton.setChecked(false);
                    if (al_selectOptions.get(position).getName().equalsIgnoreCase("Other") ||
                            al_selectOptions.get(position).getName().equalsIgnoreCase("Other PGDS")) {
                        vh.tvOther.setText("");
                        vh.ll_Sub.setVisibility(View.GONE);
                    }
                } else {
                    //Add to Lists
                    optionArrayList.add(al_selectOptions.get(position).getId());
                    holder.toggleButton.setChecked(true);
                    if (al_selectOptions.get(position).getName().equalsIgnoreCase("Other") ||
                            al_selectOptions.get(position).getName().equalsIgnoreCase("Other PGDS")) {
                        isFromActivity = true;
                        MultiSelectData mData = new MultiSelectData();
                        mData.setFrom("multiselect");
                        mData.setOptionID(al_selectOptions.get(position).getId());
                        mData.setTitle(al_selectOptions.get(position).getFieldForOption().getPageDetails().getTitle());
                        mData.setPlaceHolder( al_selectOptions.get(position).getFieldForOption().getTextFieldDetails().getPlaceholder());
                        mData.setOptionalText(vh.tvOther.getText().toString());
                        mData.setPosition(position);
                        EventBus.getDefault().postSticky(mData);
                        Intent intent = new Intent(context, FieldsInputBoxActivity.class);
                        ((Activity) context).startActivityForResult(intent, 2);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return al_selectOptions.size();
    }


    public ArrayList<Integer> getOptions(){
        Set<Integer> hs = new HashSet<>();
        hs.addAll(optionArrayList);
        optionArrayList.clear();
        optionArrayList.addAll(hs);
        return optionArrayList;
    }


    public String getValues(){
        return otherText;
    }

    public int getOptionId(){
        return option_id;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            otherText = data.getExtras().get("text").toString();
            if(!otherText.equalsIgnoreCase("")) {
                vh.ll_Sub.setVisibility(View.VISIBLE);
                vh.tvOther.setText(otherText);
            }else{
                vh.ll_Sub.setVisibility(View.GONE);
                vh.tvOther.setText("");
            }
            option_id = (int)data.getExtras().get("option_id");

            for(int i = 0; i < al_selectOptions.size(); i++) {
                if (al_selectOptions.get(i).getName().equalsIgnoreCase("Other PGDS")
                        || al_selectOptions.get(i).getName().equalsIgnoreCase("Other")) {
                    if (otherText.equalsIgnoreCase("")) {
                        vh.toggleButton.setChecked(false);
                        optionArrayList.remove(new Integer(al_selectOptions.get(i).getId()));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
