//package adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//import au.edu.sydney.comp5216.cakefactory.R;
//import model.FavouriteModel;
//
//public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
//
//    private Context context;
//    private ArrayList<FavouriteModel> favouriteModelArrayList;
//
//    public FavouriteAdapter(Context context, ArrayList<FavouriteModel> favouriteModelArrayList) {
//        this.context = context;
//        this.favouriteModelArrayList = favouriteModelArrayList;
//    }
//
//    @Override
//    public FavouriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new FavouriteAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
//                R.layout.item_favourites, parent,false));
//    }
//
//    @Override
//    public void onBindViewHolder(FavouriteAdapter.ViewHolder holder, int position) {
//        holder.img.setImageResource(favouriteModelArrayList.get(position).getImage());
//        holder.title.setText(favouriteModelArrayList.get(position).getTitle());
//        holder.subtitle.setText(favouriteModelArrayList.get(position).getSubtitle());
//        holder.review.setText(favouriteModelArrayList.get(position).getReview());
//    }
//
//    @Override
//    public int getItemCount() {
//        return favouriteModelArrayList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView title, subtitle, review;
//        ImageView img;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            img = itemView.findViewById(R.id.img);
//            title = itemView.findViewById(R.id.title);
//            subtitle = itemView.findViewById(R.id.subtitle);
//            review = itemView.findViewById(R.id.review);
//        }
//    }
//}
