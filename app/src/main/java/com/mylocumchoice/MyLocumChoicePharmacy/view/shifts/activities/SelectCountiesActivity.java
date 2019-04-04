package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.Counties;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ScrollViewWithMaxHeight;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters.CountryTagListAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.ColorFactory;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SelectCountiesActivity extends AppActivity {

    @BindView(R.id.listView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_dots)
    ImageView tvDots;
    @BindView(R.id.tv_clear_all)
    TextView tvClearAll;
    @BindView(R.id.ll_menuRight)
    FrameLayout llMenuRight;
    @BindView(R.id.fl_button)
    FrameLayout flButton;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.tagBar)
    TagContainerLayout tagBar;
    @BindView(R.id.tv_done)
    TextView tvDone;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    @BindView(R.id.customScroll)
    ScrollViewWithMaxHeight customScroll;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.shadow21)
    View shadow21;

    private boolean isFirstClicked = false;
    private boolean isSecondClicked = false;
    private boolean isThirdClicked = false;
    private boolean isFourthClicked = false;
    private boolean isFifthClicked = false;
    private int tapPosition = 0;
    public ArrayList<OpenShiftResponse.County> allCounties = new ArrayList<>();
    public ArrayList<OpenShiftResponse.County> allSavedCounties = new ArrayList<>();
    public ArrayList<OpenShiftResponse.County> myItems = new ArrayList<>();
    public ArrayList<OpenShiftResponse.County> items = new ArrayList<>();
    public ArrayList<OpenShiftResponse.County> searchItems = new ArrayList<>();
    private CountryTagListAdapter adapter;

    OpenShiftResponse openShiftResponse = new OpenShiftResponse();

    List<Integer> lv_countyId = new ArrayList<>();

    public int COUNTIES_RESULT_CODE = 103;

    boolean isSearched = false;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_counties);
        TagContainerLayout mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagBar);
        ButterKnife.bind(this);
        setHeader();

        mTagContainerLayout.setTheme(ColorFactory.NONE);
        mTagContainerLayout.setTagBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTagContainerLayout.setRippleAlpha(0);
        mTagContainerLayout.setBorderColor(getResources().getColor(R.color.tag_color));
        mTagContainerLayout.setTagBorderColor(getResources().getColor(R.color.colorPrimary));
        mTagContainerLayout.setCrossColor(getResources().getColor(R.color.white));
        mTagContainerLayout.setTagTextColor(getResources().getColor(R.color.white));
        mTagContainerLayout.setTagTextSize(getResources().getDimension(R.dimen.length));
        mTagContainerLayout.setBorderRadius(0f);
        mTagContainerLayout.setTagVerticalPadding(25);
        mTagContainerLayout.setHorizontalInterval(5);
        mTagContainerLayout.setIsTagViewClickable(true);
        mTagContainerLayout.setCrossLineWidth(getResources().getInteger(R.integer.cross));

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTagContainerLayout.getLayoutParams();
        params.setMargins(getResources().getInteger(R.integer.tagMargins), 0, getResources().getInteger(R.integer.tagMargins), 0);
        mTagContainerLayout.setLayoutParams(params);

        mTagContainerLayout.setBackgroundColor(getResources().getColor(R.color.tag_color));

        openShiftResponse = EventBus.getDefault().getStickyEvent(OpenShiftResponse.class);
        if(openShiftResponse!=null) {
            myItems.addAll(openShiftResponse.getCounties());
            allCounties.addAll(openShiftResponse.getCounties());
        }

        try {
            lv_countyId = SortActivity.openShift.getCountyIds();
            if(lv_countyId.size()>0) {
                for (int i = 0; i < myItems.size(); i++) {
                    for (int j = 0; j < lv_countyId.size(); j++) {
                        if (lv_countyId.get(j) == myItems.get(i).getId()) {
                            items.add(myItems.get(i));
                            mTagContainerLayout.addTag(myItems.get(i).getName());
                            allSavedCounties.add(myItems.get(i));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            for(int i=0;i<myItems.size();i++){
                for(int j=0;j<allSavedCounties.size();j++){
                    if(allSavedCounties.get(j).getId()==myItems.get(i).getId()){
                        myItems.remove(i);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        Collections.sort(myItems, OpenShiftResponse.countyNameComparator);
        if(myItems.size()>0) {
            shadow21.setVisibility(View.VISIBLE);
            adapter = new CountryTagListAdapter(this, adapter, myItems, new CountryTagListAdapter.onCountryClick() {
                @Override
                public void onItemClick(int position, List<OpenShiftResponse.County> lv_county) {
                    if(position<lv_county.size()) {
                        boolean isMatched = false;
                        if (items.size() >= 1) {
                            for (int i = 0; i < items.size(); i++) {
                                if (items.get(i).getName().equals(lv_county.get(position).getName())) {
                                    isMatched = true;
                                }
                            }
                            if (!isMatched) {
                                items.add(lv_county.get(position));
                                mTagContainerLayout.addTag(lv_county.get(position).getName());
                                customScroll.fullScroll(View.FOCUS_DOWN);
                                lv_county.remove(position);
                            }
                        } else {
                            items.add(lv_county.get(position));
                            mTagContainerLayout.addTag(lv_county.get(position).getName());
                            customScroll.fullScroll(View.FOCUS_DOWN);
                            lv_county.remove(position);
                        }
                        try {
                            if (myItems.size() > 0) {
                                shadow21.setVisibility(View.VISIBLE);
                            } else {
                                shadow21.setVisibility(View.GONE);
                            }
                        } catch (NullPointerException ne) {
                            ne.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            recyclerView.setAdapter(adapter);
        }else{
            shadow21.setVisibility(View.GONE);
        }


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count >= 1) {
                    isSearched = true;
                } else {
                    isSearched = false;
                }


                // adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //filter(s.toString());


                if (isSearched) {
                    recyclerView.setAdapter(null);
                    searchItems.clear();
                    for (int i = 0; i < myItems.size(); i++) {
                        if (myItems.get(i).getName().toLowerCase().contains(editText.getText().toString().toLowerCase())) {
                            searchItems.add(myItems.get(i));
                        }
                    }

                    adapter = new CountryTagListAdapter(SelectCountiesActivity.this, adapter, searchItems, new CountryTagListAdapter.onCountryClick() {
                        @Override
                        public void onItemClick(int position, List<OpenShiftResponse.County> lv_county) {
                            if(position<lv_county.size()) {
                                boolean isMatched = false;
                                if (items.size() >= 1) {
                                    for (int i = 0; i < items.size(); i++) {
                                        if (items.get(i).getName().equals(lv_county.get(position).getName())) {
                                            isMatched = true;
                                        }
                                    }
                                    if (!isMatched) {
                                        items.add(lv_county.get(position));
                                        mTagContainerLayout.addTag(lv_county.get(position).getName());
                                        customScroll.fullScroll(View.FOCUS_DOWN);
                                        for (int i = 0; i < myItems.size(); i++) {
                                            if (myItems.get(i).getName().equals(lv_county.get(position).getName())) {
                                                myItems.remove(i);
                                            }
                                        }

                                        lv_county.remove(position);
                                    }
                                } else {
                                    items.add(lv_county.get(position));
                                    mTagContainerLayout.addTag(lv_county.get(position).getName());
                                    customScroll.fullScroll(View.FOCUS_DOWN);
                                    for (int i = 0; i < myItems.size(); i++) {
                                        if (myItems.get(i).getName().equals(lv_county.get(position).getName())) {
                                            myItems.remove(i);
                                        }
                                    }
                                    lv_county.remove(position);
                                }

                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    recyclerView.setAdapter(null);
                    adapter = new CountryTagListAdapter(SelectCountiesActivity.this, adapter, myItems, new CountryTagListAdapter.onCountryClick() {
                        @Override
                        public void onItemClick(int position, List<OpenShiftResponse.County> lv_county) {
                            if(position<lv_county.size()) {
                                boolean isMatched = false;
                                if (items.size() >= 1) {
                                    for (int i = 0; i < items.size(); i++) {
                                        if (items.get(i).getName().equals(lv_county.get(position).getName())) {
                                            isMatched = true;
                                        }
                                    }
                                    if (!isMatched) {
                                        items.add(lv_county.get(position));
                                        mTagContainerLayout.addTag(lv_county.get(position).getName());
                                        customScroll.fullScroll(View.FOCUS_DOWN);
                                        lv_county.remove(position);
                                    }
                                } else {
                                    items.add(lv_county.get(position));
                                    mTagContainerLayout.addTag(lv_county.get(position).getName());
                                    customScroll.fullScroll(View.FOCUS_DOWN);
                                    lv_county.remove(position);
                                }

                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }
        });



        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {

            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(final int position, String text) {
                // ...
            }

           /* @Override
            public void onSelectedTagDrag(int position, String text) {

            }*/

            @Override
            public void onTagCrossClick(int position) {

                /*if(isSearched){
                    filter(editText.getText().toString());
                    adapter.getFilter().filter(editText.getText().toString());
                    items.remove(position);
                    mTagContainerLayout.removeTag(position);
                    adapter.notifyDataSetChanged();
                }else{*/
                /* if (position < mTagContainerLayout.getChildCount()) {*/

                if (isSearched) {
                    try {
                        boolean isMatched = false;
                        for (int i = 0; i < searchItems.size(); i++) {
                            if (searchItems.get(i).getName().equals(items.get(position).getName())) {
                                isMatched = true;
                                myItems.add(items.get(position));
                                items.remove(position);
                                adapter.notifyDataSetChanged();
                                mTagContainerLayout.removeTag(position);
                            }
                        }
                        if (!isMatched) {
                            searchItems.add(items.get(position));
                            myItems.add(items.get(position));
                            items.remove(position);
                            adapter.notifyDataSetChanged();
                            mTagContainerLayout.removeTag(position);
                        }
                        Collections.sort(myItems, OpenShiftResponse.countyNameComparator);
                        Collections.sort(searchItems, OpenShiftResponse.countyNameComparator);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        boolean isMatched = false;
                        for (int i = 0; i < myItems.size(); i++) {
                            if (myItems.get(i).getName().equals(items.get(position).getName())) {
                                isMatched = true;
                                items.remove(position);
                                adapter.notifyDataSetChanged();
                                mTagContainerLayout.removeTag(position);
                            }
                        }
                        if (!isMatched) {
                            myItems.add(items.get(position));
                            items.remove(position);
                            try {
                                adapter.notifyDataSetChanged();
                            }catch (NullPointerException ne){
                                ne.printStackTrace();
                            }
                            mTagContainerLayout.removeTag(position);
                        }
                        Collections.sort(myItems, OpenShiftResponse.countyNameComparator);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {
                    if (myItems.size() > 0) {
                        shadow21.setVisibility(View.VISIBLE);
                    } else {
                        shadow21.setVisibility(View.GONE);
                    }
                }catch (NullPointerException ne){
                    ne.printStackTrace();
                }

                if(myItems.size()==1){
                    adapter = new CountryTagListAdapter(SelectCountiesActivity.this, adapter, myItems, new CountryTagListAdapter.onCountryClick() {
                        @Override
                        public void onItemClick(int position, List<OpenShiftResponse.County> lv_county) {
                            if(position<lv_county.size()) {
                                boolean isMatched = false;
                                if (items.size() >= 1) {
                                    for (int i = 0; i < items.size(); i++) {
                                        if (items.get(i).getName().equals(lv_county.get(position).getName())) {
                                            isMatched = true;
                                        }
                                    }
                                    if (!isMatched) {
                                        items.add(lv_county.get(position));
                                        mTagContainerLayout.addTag(lv_county.get(position).getName());
                                        customScroll.fullScroll(View.FOCUS_DOWN);
                                        lv_county.remove(position);
                                    }
                                } else {
                                    items.add(lv_county.get(position));
                                    mTagContainerLayout.addTag(lv_county.get(position).getName());
                                    customScroll.fullScroll(View.FOCUS_DOWN);
                                    lv_county.remove(position);
                                }


                                try {
                                    if (myItems.size() > 0) {
                                        shadow21.setVisibility(View.VISIBLE);
                                    } else {
                                        shadow21.setVisibility(View.GONE);
                                    }
                                } catch (NullPointerException ne) {
                                    ne.printStackTrace();
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }else{
                    try {
                        adapter.notifyDataSetChanged();
                    }catch (NullPointerException ne){
                        ne.printStackTrace();
                    }
                }


                           /* if(editText.getText().toString().length()>=1) {
                                adapter.getFilter().filter(editText.getText().toString());
                                filter(editText.getText().toString());
                            }
*/


                if (items.size() == 0 || items == null) {
                    ArrayList<Integer> al_clearCounties = new ArrayList<>();
                    SortActivity.openShift.setCountyIds(al_clearCounties);
                }
                // }
                //}

            }
        });


        /*KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
                    flButton.setVisibility(View.GONE);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flButton.setVisibility(View.VISIBLE);
                        }
                    }, 200);
                }
            }
        });*/


    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<OpenShiftResponse.County> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (OpenShiftResponse.County s : myItems) {
            //if the existing elements contains the search input
            if (s.getName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }

    @OnClick(R.id.ll_back)
    public void onBackClick() {
        //onBackPressed();
        try {
            if (SortActivity.openShift.getCountyIds() != null) {
                ArrayList<OpenShiftResponse.County> countiesList = new ArrayList<>();
                for (int i = 0; i < allCounties.size(); i++) {
                    for (int j = 0; j < SortActivity.openShift.getCountyIds().size(); j++) {
                        if (SortActivity.openShift.getCountyIds().get(j) == allCounties.get(i).getId()) {
                            countiesList.add(allCounties.get(i));
                        }
                    }
                }
                Counties counties = new Counties();
                counties.setLv_county(countiesList);

                EventBus.getDefault().removeStickyEvent(Counties.class);
                EventBus.getDefault().postSticky(counties);

            }

            Intent mIntent = new Intent();
            setResult(COUNTIES_RESULT_CODE, mIntent);
            finish();
        }catch (Exception e){
            e.printStackTrace();
            Intent mIntent = new Intent();
            setResult(COUNTIES_RESULT_CODE, mIntent);
            finish();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setHeader() {
        tvHeader.setText(getResources().getString(R.string.text_select_counties));
        tvDots.setVisibility(View.INVISIBLE);
    }

    public void updateData(ArrayList<String> list) {
//        receiptlist.clear();
//        receiptlist.addAll(list);
//        this.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_done)
    public void onViewClicked() {
        Counties counties = new Counties();
        counties.setLv_county(items);
        EventBus.getDefault().removeStickyEvent(Counties.class);
        EventBus.getDefault().postSticky(counties);
        Intent mIntent = new Intent();
        setResult(COUNTIES_RESULT_CODE, mIntent);
        finish();
    }
}
