/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import au.edu.sydney.comp5216.cakefactory.R;
import model.Recommendation;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

/**
 * RecyclerView adapter for a list of Recommendations.
 */
public class RecommendationAdapter extends FirestoreAdapter<RecommendationAdapter.ViewHolder>{

    public interface OnRecommendationSelectedListener {
        void onRecommendationSelected(DocumentSnapshot recommendation);
    }

    private OnRecommendationSelectedListener mListener;

    public RecommendationAdapter(Query query, OnRecommendationSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recommendation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView topic;
        TextView title;
        TextView description;
        ImageView image;
        TextView number;

        ImageView heart;
        ImageView  heart_click;

        public ViewHolder(View itemView) {
            super(itemView);

            topic  = itemView.findViewById(R.id.topic);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
            number = itemView.findViewById(R.id.number);

            heart = itemView.findViewById(R.id.heart);
            heart.setVisibility(View.VISIBLE);
            heart_click = itemView.findViewById(R.id.heart_click);
            heart_click.setVisibility(View.INVISIBLE);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnRecommendationSelectedListener listener) {

            final Recommendation recommendation = snapshot.toObject(Recommendation.class);

            //Load image
            Glide.with(image.getContext())
                    .load(recommendation.getImage())
                    .into(image);

            topic.setText(recommendation.getTopic());
            title.setText(recommendation.getTitle());
            description.setText(recommendation.getDescription());
            number.setText(String.valueOf(recommendation.getNumber()));

            // Click listener
            itemView.findViewById(R.id.cardView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onRecommendationSelected(snapshot);
                    }
                }
            });

            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    heart.setVisibility(View.INVISIBLE);
                    heart_click.setVisibility(View.VISIBLE);
                    onAddHeartClicked(snapshot, recommendation.getNumber() + 1);
                    number.setText(String.valueOf(recommendation.getNumber() + 1));
                }
            });

            heart_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    heart.setVisibility(View.VISIBLE);
                    heart_click.setVisibility(View.INVISIBLE);
                    number.setText(String.valueOf(recommendation.getNumber()));
                }
            });
        }

        private void onAddHeartClicked(DocumentSnapshot snapshot, int newVal) {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef.child("recommendations")
                    .child(snapshot.getId())
                    .child("number")
                    .setValue(newVal);
        }
    }
}
