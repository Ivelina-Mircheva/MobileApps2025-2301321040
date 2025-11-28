package com.example.mobileapps2025_2301321040;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapps2025_2301321040.adapter.BookListAdapter;
import com.example.mobileapps2025_2301321040.model.Book;
import com.example.mobileapps2025_2301321040.viewmodel.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;

    private RecyclerView recyclerView;
    private BookListAdapter adapter;
    private BookViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        fabAdd = findViewById(R.id.fabAddBook);
        recyclerView = findViewById(R.id.recyclerViewBooks);

        adapter = new BookListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //arranging items in RecyclerView
        recyclerView.setAdapter(adapter); //Connecting the adapter to the RecyclerView

        //initializing viewModel that works with the database
        viewModel = new ViewModelProvider(this).get(BookViewModel.class);

        //observe all books in the database via LiveData, and with each change,
        // the list in the adapter is automatically updated
        viewModel.getAllBooks().observe(this, books -> adapter.setBooks(books));

        //handle book item clicks
        adapter.setOnBookClickListener(new BookListAdapter.OnBookClickListener() {
            @Override
            public void onBookClick(Book book) {
                //open BookFormActivity to edit the book
                Intent intent = new Intent(MainActivity.this, BookFormActivity.class);
                intent.putExtra("id", book.getId());
                intent.putExtra("title", book.getTitle());
                intent.putExtra("author", book.getAuthor());
                intent.putExtra("genre", book.getGenre());
                intent.putExtra("year", book.getYear());
                intent.putExtra("description", book.getDescription());
                startActivity(intent);
            }

            @Override
            public void onBookLongClick(Book book) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete book")
                        .setMessage("Are you sure you want to delete this book?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.delete(book);
                            }
                        })
                        .setNegativeButton("No", null).show();
            }
        });

        //Relate activities
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BookFormActivity.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}