package com.example.books;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ProgressBar mLoadngProgress;
    private RecyclerView rvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);
        rvResult = findViewById(R.id.rv_books);
        mLoadngProgress = findViewById(R.id.pb_loading);
        LinearLayoutManager bookLayoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(bookLayoutManager);
        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        URL bookURL;
        try {
            if (query==null || query.isEmpty()){
                bookURL = ApiUtil.buildURL("cooking");
            }else{
                bookURL = new URL(query);
            }

            new BookQueryTask().execute(bookURL);

        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_advanced_searc:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        try{
            URL bookURL = ApiUtil.buildURL(query);
            new BookQueryTask().execute(bookURL);
        }catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public class BookQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;
            try{
                result = ApiUtil.getJson(searchURL);
            } catch (IOException e) {
                Log.d("Error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView tvError = findViewById(R.id.tv_error);
            mLoadngProgress.setVisibility(View.INVISIBLE);
            if (result == null){
                tvError.setVisibility(View.VISIBLE);
                rvResult.setVisibility(View.INVISIBLE);
            }else{
                tvError.setVisibility(View.INVISIBLE);
                rvResult.setVisibility(View.VISIBLE);
                ArrayList<Book> books = ApiUtil.getBookFromJson(result);
                String resultString = "";
                BooksAdapter booksAdapter = new BooksAdapter(books);
                rvResult.setAdapter(booksAdapter);
            }

//            for (Book book : books){
//                resultString = resultString + book.title + "\n" +
//                        book.publishedDate + "\n\n";
//            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadngProgress.setVisibility(View.VISIBLE);
        }
    }
}
