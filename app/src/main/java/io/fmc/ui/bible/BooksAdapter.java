package io.fmc.ui.bible;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.fmc.R;
import io.fmc.network.BibleBook;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<BibleBook> items;

    public BooksAdapter() {
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv2_card_view_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    public void setItems(List<BibleBook> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        public BookViewHolder(View itemView) {
            super(itemView);
        }

        void bind(BibleBook book) {
            ((TextView) itemView.findViewById(R.id.country_name)).setText(book.getName());
//            ((TextView) itemView.findViewById(R.id.country_name)).setEllipsize(END);

            itemView.setOnClickListener(view -> Toast.makeText(view.getContext(), "Clicked on " +
                    book.getName(), Toast.LENGTH_LONG).show());
//            itemView.setOnClickListener(View -> Toast.makeText(View.getContext(), "Clicked on " +
//                  book.getNameLong(), Toast.LENGTH_LONG).show());
        }
    }
}
