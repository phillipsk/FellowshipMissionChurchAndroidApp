package io.fmc.ui.bible;


import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class BibleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View rv = inflater.inflate(R.layout.fragment_about_us,container,false);
        return rv;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerview);

        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());


    }


/*    private void setUpRecyclerView(RecyclerView rv, ArrayList<AboutUsModel> models) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(models);
        rv.setAdapter(adapter);
    }*/

}
