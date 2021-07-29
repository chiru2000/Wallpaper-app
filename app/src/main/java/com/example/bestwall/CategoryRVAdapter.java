package com.example.bestwall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolders> {
    private ArrayList<CategoryModal> categoryModalArrayList;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryRVAdapter(ArrayList<CategoryModal> categoryModalArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryModalArrayList = categoryModalArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_rv_layout,parent,false);
        return new CategoryRVAdapter.ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
          CategoryModal categoryModal= categoryModalArrayList.get(position);
          holder.textCategory.setText(categoryModal.getCategory());
          if(!categoryModal.getImgURL().isEmpty()) {
              Glide.with(context).load(categoryModal.getImgURL()).into(holder.imgCategory);
          } else
          {
              holder.imgCategory.setImageResource(R.drawable.ic_launcher_background);
          }
              holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             categoryClickInterface.onCategoryClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModalArrayList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        private TextView textCategory;
        private ImageView imgCategory;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);

            textCategory= itemView.findViewById(R.id.idTextCategory);
            imgCategory= itemView.findViewById(R.id.idimageCategory);

        }
    }
    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }
}
