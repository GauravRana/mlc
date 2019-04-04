package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.Selection;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.MultiSelectAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.TextSelectAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TextSelectActivity extends AppActivity{

        @BindView(R.id.tv_back)
        ImageView tvBack;
        @BindView(R.id.ll_back)
        LinearLayout llBack;
        @BindView(R.id.tv_header)
        TextView tvHeader;
        @BindView(R.id.tv_dots)
        ImageView tvDots;
        @BindView(R.id.tv_clear_all)
        TextView tvClearAll;
        @BindView(R.id.ll_menuRight)
        FrameLayout llMenuRight;
        @BindView(R.id.rv_sections)
        RecyclerView rvSections;
        @BindView(R.id.layout)
        RelativeLayout layout;

        String header="";

        Selection mSelection;
        TextSelectAdapter multiSelectAdapter;

        List<PrefernceRes.SelectOption> al_selectOptions;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_right_to_work);
            ButterKnife.bind(this);
            init();
        }

        @Override
        protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        }


        @OnClick({R.id.tv_back, R.id.ll_back})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.tv_back:
                    onBackPressed();
                    break;
                case R.id.ll_back:
                    onBackPressed();
                    break;
            }
        }

        /**
         * Method to initialize views
         */
        private void init() {
            al_selectOptions=new ArrayList<>();
            mSelection= EventBus.getDefault().getStickyEvent(Selection.class);
            setHeader(mSelection.getType());

            al_selectOptions=mSelection.getSelectOptions();
            rvSections.setLayoutManager(new LinearLayoutManager(this));
            multiSelectAdapter=new TextSelectAdapter(this,al_selectOptions);
            rvSections.setAdapter(multiSelectAdapter);
        }

        public void setHeader(String headerText){
            tvHeader.setText(headerText);
            tvDots.setVisibility(View.INVISIBLE);
        }
}
