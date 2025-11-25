package com.example.mobileapps2025_2301321040.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobileapps2025_2301321040.BookDatabaseHelper;
import com.example.mobileapps2025_2301321040.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private BookDatabaseHelper dbHelper;

    public BookRepository(Context context){
        dbHelper = new BookDatabaseHelper(context);
    }

    //Insert book
    public void insert(Book book){
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //open db for a record
        ContentValues contentValues = new ContentValues(); //contentValues contains the columns and their values
        contentValues.put("title", book.getTitle());
        contentValues.put("author", book.getAuthor());
        contentValues.put("genre", book.getGenre());
        contentValues.put("year", book.getYear());
        contentValues.put("description", book.getDescription());
        db.insert("books", null, contentValues); //insert to db
        db.close();
    }

    //Update book
    public void update(Book book) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", book.getTitle());
        contentValues.put("author", book.getAuthor());
        contentValues.put("genre", book.getGenre());
        contentValues.put("year", book.getYear());
        contentValues.put("description", book.getDescription());
        db.update("books",contentValues, "id=?", new String[]{String.valueOf(book.getId())});
        db.close();
    }

    //Delete book
    public void delete(Book book) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("books", "id=?", new String[]{String.valueOf(book.getId())}); //delete from books where id=?
        db.close();
    }

    //Get all books
    public LiveData<List<Book>> getAllBooks(){
        MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>(); //MutableLiveData allows us to change value with setValue()
        List<Book> books = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase(); //open db to readable mode

        //performing a query on the "books" table.
        Cursor cursor = db.query("books", null, null, null, null, null, null);

        //check if there is at least one row in the cursor
        if(cursor.moveToFirst()){
            do{
                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow("author")));
                book.setGenre(cursor.getString(cursor.getColumnIndexOrThrow("genre")));
                book.setYear(cursor.getInt(cursor.getColumnIndexOrThrow("year")));
                book.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                books.add(book);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        booksLiveData.setValue(books); //book list in LiveData
        return booksLiveData; //return LiveData, which contains all the books.
    }
}
