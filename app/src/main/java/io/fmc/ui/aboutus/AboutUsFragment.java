package io.fmc.ui.aboutus;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.fmc.R;
import io.fmc.adapters.CustomRecyclerAdapter;
import io.fmc.data.models.AboutUsModel;

public class AboutUsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.fragment_about_us, container, false);
        return rv;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerview);



        String[] titles = getActivity().getResources().getStringArray(R.array.models_titles);
        String[] arraylist = getActivity().getResources().getStringArray(R.array.models_details);
        ArrayList<AboutUsModel> models = new ArrayList<>();
        int size = titles.length;
        for (int i = 0; i< size; i++) {
            models.add(new AboutUsModel(titles[i], arraylist[i]));
        }

        setUpRecyclerView(rv, models);

    }

    private void setUpRecyclerView(RecyclerView rv, ArrayList<AboutUsModel> models) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(models);
        rv.setAdapter(adapter);
    }
}

