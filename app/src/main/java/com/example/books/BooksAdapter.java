package com.example.books;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> implements View.OnClickListener {

    ArrayList<Book> books;

    public BooksAdapter(ArrayList<Book> books) {
        this.books = books;
    }



    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void onClick(View v) {

    }


    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvAuthor;
        TextView tvDate;
        TextView tvPublisher;

        public BookViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_authors);
            tvPublisher = itemView.findViewById(R.id.tv_publisher);
            tvDate = itemView.findViewById(R.id.tv_date);
            itemView.setOnClickListener(this);
        }

        public void bind(Book book){
            tvTitle.setText(book.title);
            tvAuthor.setText(book.author);
            tvPublisher.setText(book.publisher);
            tvDate.setText(book.publishedDate);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Book selectBook = books.get(position);
            Intent intent = new Intent(v.getContext(), BookDetail.class);
            intent.putExtra("Book", selectBook);
            v.getContext().startActivity(intent);
        }
    }

}
