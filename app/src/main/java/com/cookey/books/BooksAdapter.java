package com.cookey.books;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> implements View.OnClickListener {

    ArrayList<Book> books;
    Context context;

    public BooksAdapter(ArrayList<Book> books, Context ctx) {
        this.books = books;
        this.context = ctx;
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
        ImageView tvImage;
        CardView tvCardView;

        public BookViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_authors);
            tvPublisher = itemView.findViewById(R.id.tv_publisher);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvImage = itemView.findViewById(R.id.image_book);
            tvCardView = itemView.findViewById(R.id.theCard);
            itemView.setOnClickListener(this);
        }

        public void bind(Book book){
            tvTitle.setText(book.title);
            tvAuthor.setText(book.author);
            tvPublisher.setText(book.publisher);
            tvDate.setText(book.publishedDate);
            if(!book.thumbnail.isEmpty()) {
                Picasso.get()
                        .load(book.thumbnail)
                        .placeholder(R.drawable.book_open)
                        .into(tvImage);
            }else{
                tvImage.setBackgroundResource(R.drawable.book_open);
            }

            if(getAdapterPosition() % 5 == 0){
                tvCardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.color1));
            }else if(getAdapterPosition() % 5 == 1){
                tvCardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.color2));
            }else if(getAdapterPosition() % 5 == 2){
                tvCardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.color3));
            }else if(getAdapterPosition() % 5 == 3){
                tvCardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.color4));
            }else if(getAdapterPosition() % 5 == 4){
                tvCardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.color5));
            }

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
