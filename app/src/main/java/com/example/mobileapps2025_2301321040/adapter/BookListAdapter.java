package com.example.mobileapps2025_2301321040.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapps2025_2301321040.R;
import com.example.mobileapps2025_2301321040.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder>{

    private List<Book> books = new ArrayList<>(); //list of books that will be displayed in the RecyclerView
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
        void onBookLongClick(Book book);
    }

    public void setOnBookClickListener(OnBookClickListener listener) {
        this.listener = listener;
    }

    //book list update method
    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged(); //notify RecyclerView that data has changed
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creates a single element view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position); //takes current book from the list
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onBookClick(book);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onBookLongClick(book);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    //this class holds references to layout elements
    static class BookViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvAuthor;
        BookViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
        }
    }

}
