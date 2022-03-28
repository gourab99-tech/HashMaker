package com.HashMaker.hasher;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MostUsedFragment extends Fragment implements AdapterView.OnItemClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_most_used, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] word = {
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
                "1.                 Traveller                              132M ",
        };
        ListView listView = view.findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, word);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

}
