package com.example.bbcnewsreader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.widget.Filter;
import android.widget.Filterable;

public class FavoriteArticlesAdapter extends RecyclerView.Adapter<FavoriteArticlesAdapter.ArticleViewHolder> implements Filterable {

    private List<Article> articleList; // List of articles to display
    private List<Article> articleListFull; // Full list of articles (for filtering)
    private OnItemClickListener onItemClickListener; // Listener for item click events

    /**
     * Interface for handling item click events.
     */
    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    /**
     * Constructor to initialize the adapter with a list of articles.
     *
     * @param articles List of articles to display.
     */
    public FavoriteArticlesAdapter(List<Article> articles) {
        this.articleList = articles;
        this.articleListFull = new ArrayList<>(articles); // Create a copy of the full list
    }

    /**
     * Set a click listener for RecyclerView items.
     *
     * @param listener Listener for item click events.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article currentArticle = articleList.get(position);
        holder.bind(currentArticle);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    /**
     * ViewHolder class to hold and manage the views for each article item.
     */
    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView dateTextView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(articleList.get(position));
                    }
                }
            });
        }

        public void bind(Article article) {
            titleTextView.setText(article.getTitle());
            descriptionTextView.setText(article.getDescription());
            dateTextView.setText(article.getDate());
        }
    }

    // Filterable interface methods

    @Override
    public Filter getFilter() {
        return articleFilter;
    }

    private Filter articleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Article> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(articleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Article article : articleListFull) {
                    if (article.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(article);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            articleList.clear();
            articleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    /**
     * Method to update the article list with the full list (used when clearing the search input).
     */
    public void updateFullList() {
        articleList.clear();
        articleList.addAll(articleListFull);
        notifyDataSetChanged();
    }
}
