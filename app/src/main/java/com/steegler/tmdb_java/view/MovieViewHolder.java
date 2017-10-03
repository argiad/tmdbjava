package com.steegler.tmdb_java.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.steegler.tmdb_java.R;

/**
 * Item ViewHolder for Movie List
 * Created by argi on 10/1/17.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivMoviePoster;
    public TextView tvTitle, tvRelease, tvPopularity, tvVotes;

    public MovieViewHolder(View itemView) {
        super(itemView);

        ivMoviePoster =  itemView.findViewById(R.id.ivMoviePoster);

        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvRelease = itemView.findViewById(R.id.tvRelease);
        tvPopularity = itemView.findViewById(R.id.tvPopularity);
        tvVotes = itemView.findViewById(R.id.tvVotes);

    }
}
