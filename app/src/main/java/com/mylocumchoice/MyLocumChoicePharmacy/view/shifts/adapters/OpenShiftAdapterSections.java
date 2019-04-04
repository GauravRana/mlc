package com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.profile.PrefernceRes;
import com.mylocumchoice.MyLocumChoicePharmacy.model.shifts.ShiftDetailsRes;
import com.mylocumchoice.MyLocumChoicePharmacy.view.profile.adapters.PreferenceAdapterFields;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Response;

public class OpenShiftAdapterSections extends RecyclerView.Adapter<OpenShiftAdapterSections.ViewHolder> {
    private Context context;
    private ShiftDetailsRes response;
    private RecyclerView.ViewHolder viewHolder;
    private ArrayList<ShiftDetailsRes.Detail> arrayListDetail = new ArrayList<>();
    private ArrayList<ShiftDetailsRes.Section> arrayListSection;
    private String from;
    String distance;
    boolean isDeclined;


    public OpenShiftAdapterSections(Context context, ShiftDetailsRes response, ArrayList<ShiftDetailsRes.Section> arrayListSection,String distance,boolean isDeclined) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.response = response;
        this.arrayListSection = arrayListSection;
        this.from = from;
        this.distance = distance;
        this.isDeclined = isDeclined;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView photo;
        private RecyclerView recyclerView;
        private View shadow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            shadow = itemView.findViewById(R.id.shadow);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setNestedScrollingEnabled(true);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_open_shift_sections, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        arrayListDetail = arrayListSection.get(position).getDetails();
        ShiftDetailsAdapter adapter = new ShiftDetailsAdapter(context, response, arrayListDetail,distance);
        holder.recyclerView.setAdapter(adapter);

        if (isDeclined) {

        } else {

            switch (context.getResources().getDisplayMetrics().densityDpi) {

                case DisplayMetrics.DENSITY_HIGH:
                    if (position == arrayListSection.size() - 1) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.shadow.getLayoutParams();
                        params.setMargins(0, 0, 0, 205);
                    }
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    if (position == arrayListSection.size() - 1) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.shadow.getLayoutParams();
                        params.setMargins(0, 0, 0, 105);
                    }
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    if (position == arrayListSection.size() - 1) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.shadow.getLayoutParams();
                        params.setMargins(0, 0, 0, 160);
                    }
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    if (position == arrayListSection.size() - 1) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.shadow.getLayoutParams();
                        params.setMargins(0, 0, 0, 210);
                    }
                    break;
                default:
                    if (position == arrayListSection.size() - 1) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.shadow.getLayoutParams();
                        params.setMargins(0, 0, 0, 150);
                    }
                    break;

            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayListSection.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
