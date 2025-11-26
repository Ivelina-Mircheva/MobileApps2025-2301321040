package com.example.mobileapps2025_2301321040.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mobileapps2025_2301321040.model.Book;
import com.example.mobileapps2025_2301321040.repository.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private BookRepository repository;
    private LiveData<List<Book>> allBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);

        repository = new BookRepository(application);
        allBooks = repository.getAllBooks();
    }

    public void insert(Book book) {
        repository.insert(book);
    }

    public void update(Book book) {
        repository.update(book);
    }

    public void delete(Book book) {
        repository.delete(book);
    }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }
}
