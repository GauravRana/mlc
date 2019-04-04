package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

    public class TextSelectAdapter extends RecyclerView.Adapter<TextSelectAdapter.ViewHolder> {
        private Context context;

        List<PrefernceRes.SelectOption> al_selectOptions;

        public TextSelectAdapter(Context context, List<PrefernceRes.SelectOption> al_selectOptions) {
            this.context = context;
            this.al_selectOptions = al_selectOptions;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            /** ButterKnife Code **/
            @BindView(R.id.ll_pub)
            LinearLayout llPub;
            @BindView(R.id.rb_Pub)
            ImageView rbPub;
            @BindView(R.id.tv_title)
            TextView tvTitle;


            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        @NonNull
        @Override
        public TextSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.rv_radio_button, parent, false);
            final TextSelectAdapter.ViewHolder vh = new TextSelectAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull TextSelectAdapter.ViewHolder holder, int position) {
            holder.tvTitle.setText(al_selectOptions.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return al_selectOptions.size();
        }
}
