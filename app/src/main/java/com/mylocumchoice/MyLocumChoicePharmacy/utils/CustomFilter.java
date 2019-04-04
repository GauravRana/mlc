package com.mylocumchoice.MyLocumChoicePharmacy.utils;


import android.widget.Filter;

import com.google.android.gms.games.Player;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.adapters.CountryTagListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter extends Filter {

    CountryTagListAdapter adapter;
    ArrayList<String> filterList;
    ArrayList<String> originalData;

    public CustomFilter(ArrayList<String> filterList, CountryTagListAdapter adapter) {
        this.filterList = filterList;
        this.adapter=adapter;
        this.originalData = filterList;
    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();
        FilterResults results = new FilterResults();
        final List<String> list = originalData;
        int count = list.size();
        final ArrayList<String> nlist = new ArrayList<String>(count);
        String filterableString;
        for (int i = 0; i < count; i++) {
            filterableString = list.get(i);
            if (filterableString.toLowerCase().contains(filterString)) {
                nlist.add(filterableString);
            }
        }
        results.values = nlist;
        results.count = nlist.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
//        adapter.players = (ArrayList<String>) results.values;
//        adapter.notifyDataSetChanged();
    }
}
