package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import au.edu.sydney.comp5216.cakefactory.R;
import au.edu.sydney.comp5216.cakefactory.RecommendationExample;
import model.RecommendationModel;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RecommendationModel> recommendationModelArrayList;

    public RecommendationAdapter(Context context, ArrayList<RecommendationModel> recommendationModelArrayList) {
        this.context = context;
        this.recommendationModelArrayList = recommendationModelArrayList;
    }

    @Override
    public RecommendationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendationAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recommendations, parent,false));
    }

    @Override
    public void onBindViewHolder(RecommendationAdapter.ViewHolder holder, final int position) {
        holder.img.setImageResource(recommendationModelArrayList.get(position).getImage());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0){
                    Intent i = new Intent(context, RecommendationExample.class);
                    context.startActivity(i);}
                if(position == 1){
                    Intent i = new Intent(context, RecommendationExample.class);
                    context.startActivity(i);}
                if(position == 2){
                    Intent i = new Intent(context, RecommendationExample.class);
                    context.startActivity(i);}
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendationModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
        }
    }
}
