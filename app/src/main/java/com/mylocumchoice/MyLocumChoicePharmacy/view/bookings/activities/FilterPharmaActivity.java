package com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.Clients;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBooking;
import com.mylocumchoice.MyLocumChoicePharmacy.model.bookings.CompletedBookingResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.OpenShiftResponse;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.ScrollViewWithMaxHeight;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.AppActivity;
import com.mylocumchoice.MyLocumChoicePharmacy.view.bookings.adapters.PharmacyFilterAdapter;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

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

public class FilterPharmaActivity extends AppActivity {


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
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.tagBar)
    TagContainerLayout tagBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_done)
    TextView tvDone;
    @BindView(R.id.fl_button)
    FrameLayout flButton;
    @BindView(R.id.ll_frameLayout)
    LinearLayout llFrameLayout;
    @BindView(R.id.shadow21)
    View shadow21;
    private int tapPosition = 0;
    PharmacyFilterAdapter adapter;

    CompletedBookingResponse completedBookingResponse;
    List<CompletedBookingResponse.Client> lv_clients = new ArrayList<>();
    List<CompletedBookingResponse.Client> items = new ArrayList<>();
    List<CompletedBookingResponse.Client> searchItems = new ArrayList<>();

    List<Integer> lv_countyId = new ArrayList<>();
    CompletedBooking completedBookingFilters = null;
    TagContainerLayout mTagContainerLayout;

    boolean isSearched=false;

    int RESULT_CODE_FILTERS=10;

    @BindView(R.id.customScroll)
    ScrollViewWithMaxHeight customScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_pharma);
        mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagBar);
        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setHeader();
        completedBookingResponse = EventBus.getDefault().getStickyEvent(CompletedBookingResponse.class);
        lv_clients.addAll(completedBookingResponse.getClients());

        if (EventBus.getDefault().getStickyEvent(CompletedBooking.class) != null) {
            completedBookingFilters = EventBus.getDefault().getStickyEvent(CompletedBooking.class);
        }


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

        //mTagContainerLayout.setCrossAreaWidth(1f);
        //mTagContainerLayout.setBackground(ContextCompat.getDrawable(this,R.drawable.tag_back));
        mTagContainerLayout.setBackgroundColor(getResources().getColor(R.color.tag_color));

        /*try {
            lv_countyId = completedBookingFilters.getClientIds();
            for (int i = 0; i < lv_clients.size(); i++) {
                for (int j = 0; j < lv_countyId.size(); j++) {
                    if (lv_countyId.get(j) == lv_clients.get(i).getId()) {
                        items.add(lv_clients.get(i));
                        mTagContainerLayout.addTag(lv_clients.get(i).getName());
                        lv_clients.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        try {
            if(EventBus.getDefault().getStickyEvent(Clients.class)!=null){
                lv_countyId=new ArrayList<>();
                List<CompletedBookingResponse.Client> lv_client = new ArrayList<>();
                lv_client = EventBus.getDefault().getStickyEvent(Clients.class).getClients();
                for (int i = 0; i < lv_clients.size(); i++) {
                    for (int j = 0; j < lv_client.size(); j++) {
                        if (lv_client.get(j).getId() == lv_clients.get(i).getId()) {
                            items.add(lv_clients.get(i));
                            mTagContainerLayout.addTag(lv_clients.get(i).getName());
                            lv_clients.remove(i);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        Collections.sort(lv_clients, CompletedBookingResponse.clientComparator);
        if(lv_clients.size()>0) {
            shadow21.setVisibility(View.VISIBLE);
            adapter = new PharmacyFilterAdapter(this, adapter, lv_clients, new PharmacyFilterAdapter.onClientClick() {
                @Override
                public void onItemClick(int position, List<CompletedBookingResponse.Client> lv_client) {
                    items.add(lv_client.get(position));
                    mTagContainerLayout.addTag(lv_client.get(position).getName());
                    lv_client.remove(position);

                    try {
                        if (lv_client.size() > 0) {
                            shadow21.setVisibility(View.VISIBLE);
                        } else {
                            shadow21.setVisibility(View.GONE);
                        }
                    }catch (NullPointerException ne){
                        ne.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
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
                if(count>=1){
                    isSearched=true;
                }else{
                    isSearched=false;
                }
                //adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //filter(s.toString());

                if (isSearched) {
                    recyclerView.setAdapter(null);
                    searchItems.clear();
                    for (int i = 0; i < lv_clients.size(); i++) {
                        if (lv_clients.get(i).getName().toLowerCase().contains(editText.getText().toString().toLowerCase())) {
                            searchItems.add(lv_clients.get(i));
                        }
                    }

                    adapter = new PharmacyFilterAdapter(FilterPharmaActivity.this, adapter, searchItems, new PharmacyFilterAdapter.onClientClick() {

                        public void onItemClick(int position, List<CompletedBookingResponse.Client> lv_county) {

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
                                    for (int i = 0; i < lv_clients.size(); i++) {
                                        if (lv_clients.get(i).getName().equals(lv_county.get(position).getName())) {
                                            lv_clients.remove(i);
                                        }
                                    }

                                    lv_county.remove(position);
                                }
                            } else {
                                items.add(lv_county.get(position));
                                mTagContainerLayout.addTag(lv_county.get(position).getName());
                                customScroll.fullScroll(View.FOCUS_DOWN);
                                for (int i = 0; i < lv_clients.size(); i++) {
                                    if (lv_clients.get(i).getName().equals(lv_county.get(position).getName())) {
                                        lv_clients.remove(i);
                                    }
                                }
                                lv_county.remove(position);
                            }

                            adapter.notifyDataSetChanged();
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    recyclerView.setAdapter(null);
                    adapter = new PharmacyFilterAdapter(FilterPharmaActivity.this, adapter, lv_clients, new PharmacyFilterAdapter.onClientClick() {
                        @Override
                        public void onItemClick(int position, List<CompletedBookingResponse.Client> lv_county) {

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

            /*@Override
            public void onSelectedTagDrag(int position, String text) {

            }*/

            @Override
            public void onTagCrossClick(int position) {

                try {
                    boolean isMatched=false;
                    for (int i = 0; i < lv_clients.size(); i++) {
                        if(lv_clients.get(i).getName().equals(items.get(position).getName())){
                            isMatched=true;
                            items.remove(position);
                            adapter.notifyDataSetChanged();
                            mTagContainerLayout.removeTag(position);
                        }
                    }
                    if(!isMatched){
                        lv_clients.add(items.get(position));
                        items.remove(position);
                        adapter.notifyDataSetChanged();
                        mTagContainerLayout.removeTag(position);
                    }
                    Collections.sort(lv_clients, CompletedBookingResponse.clientComparator);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        ArrayList<CompletedBookingResponse.Client> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (CompletedBookingResponse.Client s : lv_clients) {
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
        onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setHeader() {
        tvHeader.setText("Select Pharmacies");
        tvClearAll.setVisibility(View.GONE);
        tvClearAll.setText(getResources().getString(R.string.text_clear));
        tvDots.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_clear_all, R.id.tv_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear_all:
                /*try {
                    lv_countyId = completedBookingFilters.getClientIds();
                        for (int j = 0; j < lv_countyId.size(); j++) {
                            try {
                                lv_clients.add(items.get(j));
                                items.remove(j);
                                adapter.notifyDataSetChanged();
                                mTagContainerLayout.removeTag(j);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    completedBookingFilters.getClientIds().clear();
                    EventBus.getDefault().removeStickyEvent(Clients.class);
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                break;
            case R.id.tv_done:

                if(items.size()>0) {
                    Clients clients = new Clients();
                    clients.setClients(items);
                    EventBus.getDefault().postSticky(clients);
                }else{
                    EventBus.getDefault().removeStickyEvent(Clients.class);
                }

                Intent data = new Intent();
                setResult(RESULT_CODE_FILTERS, data);
                onBackPressed();
                break;
        }
    }

}
