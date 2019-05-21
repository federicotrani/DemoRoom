package com.example.demoroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.demoroom.db.Post;

import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> data;
    private Context context;
    private LayoutInflater inflater;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClicked(Post post);
    }

    public PostAdapter(Context context, OnDeleteButtonClickListener listener){
        this.data = new ArrayList<>();
        this.context = context;
        this.onDeleteButtonClickListener = listener;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = inflater.inflate(R.layout.layout_post_ites, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Post> newData){
        if(data != null){
            PostDiffCallback postDiffCallback = new PostDiffCallback(data, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(postDiffCallback);

            data.clear();
            data.addAll(newData);
        }else{
            // primer inicializacion
            data = newData;
        }
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvContent;
        private Button btnDelete;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(final Post post){
            if(post !=null){
                tvTitle.setText(post.getTitle());
                tvContent.setText(post.getContent());
                btnDelete.setOnClickListener(v->{
                    onDeleteButtonClickListener.onDeleteButtonClicked(post);
                });
            }
        }
    }

    class PostDiffCallback extends DiffUtil.Callback {

        private final List<Post> oldPosts, newPosts;

        public PostDiffCallback(List<Post> oldPosts, List<Post> newPosts) {
            this.oldPosts = oldPosts;
            this.newPosts = newPosts;
        }

        @Override
        public int getOldListSize() {
            return oldPosts.size();
        }

        @Override
        public int getNewListSize() {
            return newPosts.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).getId() == newPosts.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
        }
    }
}
