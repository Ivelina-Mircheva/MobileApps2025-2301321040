package com.example.mobileapps2025_2301321040;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobileapps2025_2301321040.model.Book;
import com.example.mobileapps2025_2301321040.viewmodel.BookViewModel;

public class BookFormActivity extends AppCompatActivity {

    private EditText etTitle, etAuthor, etGenre, etYear, etDescription;
    private Button btnSave;
    private BookViewModel viewModel;
    private boolean editing = false; //flag that indicates when the form is in edit mode
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_form);

        //associating Java variables with XML elements
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSaveBook);

        //initializing viewModel that works with the database
        viewModel = new ViewModelProvider(this).get(BookViewModel.class);

        //check if editing
        Intent intent = getIntent();
        if(intent.hasExtra("id")){
            editing = true;
            bookId = intent.getIntExtra("id",-1);
            etTitle.setText(intent.getStringExtra("title"));
            etAuthor.setText(intent.getStringExtra("author"));
            etGenre.setText(intent.getStringExtra("genre"));
            etYear.setText(String.valueOf(intent.getIntExtra("year",0)));
            etDescription.setText(intent.getStringExtra("description"));
        }

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String author = etAuthor.getText().toString();
            String genre = etGenre.getText().toString();
            String yearStr = etYear.getText().toString();
            String description = etDescription.getText().toString();

            //check for empty fields
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(genre)
                    || TextUtils.isEmpty(yearStr) || TextUtils.isEmpty(description)) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_LONG).show();
                return; //don't save if any field is empty
            }

            //convert year to integer
            int year = Integer.parseInt(etYear.getText().toString());

            //create book object
            Book book = new Book(title, author, genre, year, description);
            if (editing) book.setId(bookId);

            if (editing) viewModel.update(book);
            else viewModel.insert(book);

            finish(); //go back to MainActivity
        });


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}