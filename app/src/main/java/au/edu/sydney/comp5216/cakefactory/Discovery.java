package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import custom_font.ExpandableHeightListView;

public class Discovery extends Fragment {

    private ExpandableHeightListView listview;
    private ArrayList<Article> Bean = new ArrayList<>();
    private ListBaseAdapter baseAdapter;

    private int[] IMAGE1 = {R.drawable.newsname1, R.drawable.newsname1, R.drawable.newsname1};
    private int[] IMAGE2 = {R.drawable.img1, R.drawable.img1, R.drawable.img1};
    private int[] IMAGE3 = {R.drawable.more, R.drawable.more, R.drawable.more};
    private String[] NEWSNAME = {"Fox News .", "Fox News .", "Fox News ."};
    private String[] TITLE = {"1 day ago", "1 day ago", "1 day ago"};
    private String[] NEWS = {"Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous. Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous",
            "Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous. Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous",
            "Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous. Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous"};
    private String[] NEWSSUB = {"Why even a President Trump couldn’t make Apple manufacture iPhone in the state.","Why even a President Trump couldn’t make Apple manufacture iPhone in the state.",
            "Why even a President Trump couldn’t make Apple manufacture iPhone in the state."};
    private String[] INTREST = {"You've shown interest in iPhone","You've shown interest in iPhone","You've shown interest in iPhone"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.discovery, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listview = getActivity().findViewById(R.id.listview);

        for (int i= 0; i< IMAGE1.length; i++){
            Article bean = new Article(IMAGE1[i], IMAGE2[i], IMAGE3[i], NEWSNAME[i], TITLE[i], NEWS[i], NEWSSUB[i], INTREST[i]);
            Bean.add(bean);
        }
        baseAdapter = new ListBaseAdapter(getContext(), Bean);
        listview.setAdapter(baseAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Get the photo path from system
                // String photoPath = imageAdapter.getItem(position);
                startActivity(new Intent(getActivity(), ViewArticleAcitivity.class));
//                Intent intent = new Intent(Discovery.this, ViewArticleAcitivity.class);
//                if (intent != null) {
//                    startActivity(intent);
//                }

//                if (intent != null) {
//                    intent.putExtra("path", photoPath);
//                    intent.putExtra("position", position);
//
//                }
            }
        });
    }
}
