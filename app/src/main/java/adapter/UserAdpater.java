//package adapter;
//
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.Query;
//
//import au.edu.sydney.comp5216.cakefactory.Favourites;
//import au.edu.sydney.comp5216.cakefactory.MainActivity;
//import au.edu.sydney.comp5216.cakefactory.ProfileFragment;
//import au.edu.sydney.comp5216.cakefactory.R;
//import de.hdodenhof.circleimageview.CircleImageView;
//import model.User;
//
///**
// * RecyclerView adapter for a list of Recommendations.
// */
//class UserAdapter extends FirestoreAdapter<UserAdapter.ViewHolder>{
//
//    public UserAdapter(Query query) {
//        super(query);
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
//                R.layout.activity_profile, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.bind(getSnapshot(position);
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        CircleImageView profile_image;
//        TextView name;
////        TextView Bio;
//        TextView favouriteNum;
//        TextView designNum;
//        TextView orderNum;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            profile_image  = itemView.findViewById(R.id.profile_image);
//            name = itemView.findViewById(R.id.name);
//            favouriteNum = itemView.findViewById(R.id.favouriteNum);
//            designNum = itemView.findViewById(R.id.designNum);
//            orderNum = itemView.findViewById(R.id.orderNum);
//        }
//
//        public void bind(final DocumentSnapshot snapshot) {
//
//            final User user = snapshot.toObject(User.class);
//
//            //Load image
//            Glide.with(profile_image.getContext())
//                    .load(user.getAvatar())
//                    .into(profile_image);
//
//            name.setText(user.getUsername());
//            favouriteNum.setText(user.getArticles().size());
//            designNum.setText(user.getDesigns().size());
//            orderNum.setText(user.getOrders().size());
//        }
//    }
//}
