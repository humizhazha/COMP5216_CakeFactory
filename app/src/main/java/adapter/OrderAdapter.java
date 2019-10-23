package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import au.edu.sydney.comp5216.cakefactory.R;
import model.OrderModel;

/**
 * RecyclerView adapter for a list of Orders.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<OrderModel> orderModelArrayList;

    public OrderAdapter(Context context, ArrayList<OrderModel> orderModelArrayList) {
        this.context = context;
        this.orderModelArrayList = orderModelArrayList;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_orders, parent,false));
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {
        holder.img.setImageResource(orderModelArrayList.get(position).getImage());
        holder.title.setText(orderModelArrayList.get(position).getTitle());
        holder.size.setText(orderModelArrayList.get(position).getSize());
        holder.date.setText(orderModelArrayList.get(position).getDate());
        holder.price.setText(orderModelArrayList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return orderModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, size, date, price;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            size = itemView.findViewById(R.id.size);
            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.price);
        }
    }
}
