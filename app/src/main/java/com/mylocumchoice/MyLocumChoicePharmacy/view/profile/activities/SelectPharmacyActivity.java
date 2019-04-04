package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class SelectPharmacyActivity extends AppActivity {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    private int change = 0;


    private int tapPosition = 0;

    public ArrayList<String> myItems = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pharmacy);
        TagContainerLayout mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagBar);
        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tvHeader.setText("Hide me from Pharmacies");
        tvDots.setVisibility(View.GONE);
        tvClearAll.setVisibility(View.VISIBLE);
        tvClearAll.setText("Clear");
        TagListAdapter adapter = new TagListAdapter(this, myItems);
        myItems.add(0, "Llyods");
        myItems.add(1, "Roland");
        myItems.add(2, "Tesco");

        myItems.add(3, "Devon");
        myItems.add(4, "Boots");

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //mTagContainerLayout.addTag(myItems.get(position).toString(), position);
                mTagContainerLayout.addTag(myItems.get(position).toString());
                myItems.remove(position);
                adapter.notifyDataSetChanged();
                //adapter.notifyDataSetInvalidated();
            }
        });
    }


    @OnClick(R.id.ll_back)
    public void onBackClick(){ finish(); }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
