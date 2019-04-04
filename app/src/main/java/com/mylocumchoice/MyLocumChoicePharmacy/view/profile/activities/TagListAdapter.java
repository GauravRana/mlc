package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagListAdapter extends BaseAdapter{

    private final Context context;
    private List<String> arrayList = new LinkedList<>();
    private int change = 0;
    private int change1 = 0;
    private int change2 = 0;
    private int change3 = 0;
    private int change4 = 0;


    public TagListAdapter(Context context, List<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_tag_list, parent, false);
            ButterKnife.bind(this, convertView);
            holder = new ViewHolder();
            Log.d("WHAT the VALUE", arrayList.get(position).toString());
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.iv_add = convertView.findViewById(R.id.iv_add);
            holder.tv_title.setText(arrayList.get(position).toString());
            holder.layout_list = (LinearLayout) convertView.findViewById(R.id.layout_list);

            holder.layout_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position == 0) {
                        if (change == 0) {
                            holder.layout_list.setBackgroundColor(ContextCompat.getColor(context, R.color.screen_background));
                            holder.iv_add.setImageDrawable(context.getResources().getDrawable(R.drawable.eye_close));
                            change = 1;
                        } else {
                            holder.layout_list.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                            holder.iv_add.setImageDrawable(context.getResources().getDrawable(R.drawable.eye_open));
                            change = 0;
                        }
                    }

                    if(position == 1) {
                        if (change1 == 0) {
                            holder.layout_list.setBackgroundColor(ContextCompat.getColor(context, R.color.screen_background));
                            holder.iv_add.setImageDrawable(context.getResources().getDrawable(R.drawable.eye_close));
                            change1 = 1;
                        } else {
                            holder.layout_list.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                            holder.iv_add.setImageDrawable(context.getResources().getDrawable(R.drawable.eye_open));
                            change1 = 0;
                        }

                    }

                    if(position == 2) {
                        if (change2 == 0) {
                            holder.layout_list.setBackgroundColor(ContextCompat.getColor(context, R.color.screen_background));
                            holder.iv_add.setImageDrawable(context.getResources().getDrawable(R.drawable.eye_close));
                            change2 = 1;
                        } else {
                            holder.layout_list.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                            holder.iv_add.setImageDrawable(context.getResources().getDrawable(R.drawable.eye_open));
                            change2 = 0;
                        }

                    }

                    if(position == 3) {
                        if (change3 == 0) {
                            holder.layout_list.setBackgroundColor(ContextCompat.getColor(context, R.color.screen_background));
                            holder.iv_add.setImageDrawable(context.getResources().getDrawable(R.drawable.eye_close));
                            change3 = 1;
                        } else {
                            holder.layout_list.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                            holder.iv_add.setImageDrawable(context.getResources().getDrawable(R.drawable.eye_open));
                            change3 = 0;
                        }

                    }
                    if(position == 4) {
                        if (change4 == 0) {
                            holder.layout_list.setBackgroundColor(ContextCompat.getColor(context, R.color.screen_background));
                            holder.iv_add.setImageDrawable(context.getResources().getDrawable(R.drawable.eye_close));
                            change4 = 1;
                        } else {
                            holder.layout_list.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                            holder.iv_add.setImageDrawable(context.getResources().getDrawable(R.drawable.eye_open));
                            change4 = 0;
                        }

                    }
                }
            });

            convertView.setTag(holder);
        }else{
            holder =(ViewHolder) convertView.getTag();
        }
        return convertView;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public class ViewHolder {
        @BindView(R.id.iv_add)
        ImageView iv_add;

        @BindView(R.id.iv_img)
        ImageView iv_img;


        @BindView(R.id.tv_title)
        TextView tv_title;

        @BindView(R.id.iv_profile)
        ImageView iv_profile;

        @BindView(R.id.layout_list)
        LinearLayout layout_list;
    }
}
