package adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import au.edu.sydney.comp5216.cakefactory.EditProfileActivity;
import au.edu.sydney.comp5216.cakefactory.R;
import au.edu.sydney.comp5216.cakefactory.RecommendationActivity;
import model.Profile;

/**
 * RecyclerView adapter for a list of Recommendations.
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Profile> profileModelArrayList;

    public ProfileAdapter(Context context, ArrayList<Profile> profileModelArrayList) {
        this.context = context;
        this.profileModelArrayList = profileModelArrayList;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_profile, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, final int position) {
        holder.icon.setImageResource(profileModelArrayList.get(position).getIcon());
        holder.arrow.setImageResource(profileModelArrayList.get(position).getArrow());
        holder.title.setText(profileModelArrayList.get(position).getTitle());

        // OnClick event for Recommendations and Profile function
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0){
                    Intent i = new Intent(context, RecommendationActivity.class);
                    context.startActivity(i);}
                if(position == 1){
                    Intent i = new Intent(context, EditProfileActivity.class);
                    context.startActivity(i);}
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon, arrow;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            arrow = itemView.findViewById(R.id.arrow);
            title = itemView.findViewById(R.id.title);
        }
    }
}
