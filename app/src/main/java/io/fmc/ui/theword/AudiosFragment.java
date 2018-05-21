package io.fmc.ui.theword;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.fmc.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AudiosFragment extends Fragment {


    public AudiosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audios, container, false);

        return view;
    }

}
