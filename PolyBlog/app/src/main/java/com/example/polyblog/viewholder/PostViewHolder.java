package com.example.polyblog.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.polyblog.R;
import com.example.polyblog.models.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {
	public ImageView starView;
	private TextView authorView;
	private TextView bodyView;
	private TextView numStarsView;
	private TextView titleView;

	public PostViewHolder(View itemView) {
		super(itemView);
		titleView = itemView.findViewById(R.id.post_title);
		authorView = itemView.findViewById(R.id.post_author);
		starView = itemView.findViewById(R.id.star);
		numStarsView = itemView.findViewById(R.id.post_num_stars);
		bodyView = itemView.findViewById(R.id.post_body);
	}

	public void bindToPost(Post post, View.OnClickListener starClickListener) {
		titleView.setText(post.title);
		authorView.setText(post.author);
		numStarsView.setText(String.valueOf(post.starCount));
		bodyView.setText(post.body);
		starView.setOnClickListener(starClickListener);
	}
}