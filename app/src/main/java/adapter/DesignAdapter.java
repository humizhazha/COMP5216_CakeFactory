package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import au.edu.sydney.comp5216.cakefactory.R;
import model.DesignModel;

/**
 * RecyclerView adapter for a list of Designs.
 */
public class DesignAdapter extends FirestoreAdapter<DesignAdapter.ViewHolder>{

    public interface OnDesignSelectedListener {
        void OnDesignSelectedListener(DocumentSnapshot design);
    }

    private OnDesignSelectedListener mListener;

    public DesignAdapter(Query query, OnDesignSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_my_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView shape;
        TextView type;
        TextView flavour;
        TextView datetime;

        public ViewHolder(View itemView) {
            super(itemView);

            shape  = itemView.findViewById(R.id.shapeText);
            type = itemView.findViewById(R.id.typeText);
            flavour = itemView.findViewById(R.id.flavourText);
            datetime =  itemView.findViewById(R.id.date_text);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnDesignSelectedListener listener) {

            final DesignModel design = snapshot.toObject(DesignModel.class);

            shape.setText(design.getShape());
            type.setText(design.getType());
            flavour.setText(design.getFlavour());
            datetime.setText(design.getDatetime());

            // Click listener
            itemView.findViewById(R.id.cardView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.OnDesignSelectedListener(snapshot);
                    }
                }
            });
        }
    }
}
