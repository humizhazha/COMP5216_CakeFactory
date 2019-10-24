package adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import au.edu.sydney.comp5216.cakefactory.R;
import model.DesignModel;
import model.Order;

/**
 * RecyclerView adapter for a list of Orders.
 */
public class OrderAdapter extends FirestoreAdapter<OrderAdapter.ViewHolder> {


    public interface OnOrderSelectedListener {
        void OnOrderSelectedListener(DocumentSnapshot recommendation);
    }

    private OnOrderSelectedListener mListener;

    public OrderAdapter(Query query, OnOrderSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView price;
        TextView datetime;
        TextView shape;
        TextView type;
        TextView flavour;
        DesignModel design;

        public ViewHolder(View itemView) {
            super(itemView);

            price  = itemView.findViewById(R.id.price);
            shape  = itemView.findViewById(R.id.shapeText);
            type = itemView.findViewById(R.id.typeText);
            flavour = itemView.findViewById(R.id.flavourText);
            datetime = itemView.findViewById(R.id.date_text);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnOrderSelectedListener listener) {

            final Order order = snapshot.toObject(Order.class);

            price.setText(order.getPrice());
            datetime.setText(order.getDatetime());

            String designId = order.getDesign();

            FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

            // Get all designs
            mFirestore.collection("design").document(designId).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                design = document.toObject(DesignModel.class);
                                shape.setText(design.getShape());
                                flavour.setText(design.getFlavour());
                                type.setText(design.getType());
                            }
                        }
                    });

            // Click listener
            itemView.findViewById(R.id.cardView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.OnOrderSelectedListener(snapshot);
                    }
                }
            });
        }
    }
}
