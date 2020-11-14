package io.fmc.ui.bible;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import io.fmc.R;
import io.fmc.network.BibleBook;

//import io.fmc2.FellowshipApplication;

public class BibleFragment extends Fragment implements BibleScreen {

    private String bibleId = "de4e12af7f28f599-02";
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;

    @Inject BiblePresenter biblePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View rv = inflater.inflate(R.layout.fragment_about_us,container,false);

        TextView mTextView = (TextView) rv.findViewById(R.id.text); //findViewById(R.id.text);


        return rv;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        AppController.getAppContext().getApplicationContext()
//        ((FellowshipApplication) context.getApplicationContext()).getAppComponent().inject(this);

        biblePresenter.bind(this);

        biblePresenter.fetchBibleBooks(bibleId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        booksAdapter = new BooksAdapter();
        recyclerView.setAdapter(booksAdapter);
        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* In order to have to documentation work, they instantiate these variables above
        *     private RecyclerView mRecyclerView;
              private RecyclerView.Adapter mAdapter;
              private RecyclerView.LayoutManager mLayoutManager;

              mLayoutManager = new LinearLayoutManager(getActivity());
              mRecyclerView.setLayoutManager(mLayoutManager);
        */


    }

    @Override
    public void onDestroy() {
        biblePresenter.unbind();
        super.onDestroy();
    }

    @Override
    public void onNewBibleBooks(List<BibleBook> books) {
        booksAdapter.setItems(books);
    }

    /*    private void setUpRecyclerView(RecyclerView rv, ArrayList<AboutUsModel> models) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(models);
        rv.setAdapter(adapter);
    }*/


    }

