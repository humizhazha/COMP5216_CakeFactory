package au.edu.sydney.comp5216.cakefactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adapter.ListBaseAdapter;
import custom_font.ExpandableHeightListView;
import model.Article;

/**
 * Discovery page layout, a fragment inside MainActivity
 * Show all articles from Cloud Firestore
 * User can search article name
 */
public class Discovery extends Fragment {

    private ExpandableHeightListView listview;
    private ArrayList<Article> Bean = new ArrayList<>();
    private ListBaseAdapter baseAdapter;
    EditText search;
    private FirebaseFirestore db;
    private Query mQuery;
    CollectionReference articles;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String TAG = Discovery.class.getSimpleName();

    private int[] IMAGE1 = {R.drawable.newsname1, R.drawable.newsname1, R.drawable.newsname1};

    public Discovery() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initFirestore() {
        db = FirebaseFirestore.getInstance();
        articles = db.collection("article");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.discovery, container, false);
    }

    /**
     * Read all the article data from Firestore
     */
    private void readFromDatabase() {
        // Attach a listener to read the data at our posts reference
        db.collection("article")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String author = document.get("author").toString();
                                String title = document.get("title").toString();
                                String content = document.get("content").toString();
                                String sub = document.get("newssub").toString();
                                String image = document.get("image").toString();
                                int like = Integer.parseInt(document.get("like").toString());
                                Date date = document.getDate("date");
                                String strDate = dateFormat.format(date);
                                addToList(author, title, content, strDate, sub, image,like,document.getId());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                            addbaseAdapter();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * Add the information from Firestore to ArrayList
     */
    private void addToList(String author, String title, String content, String date, String sub, String image, int like,String article_id) {
        Article article = new Article(IMAGE1[0], image, author, date, title, sub, content, like);
        article.setArticle_id(article_id);
        Bean.add(article);

    }

    private void addbaseAdapter() {
        baseAdapter = new ListBaseAdapter(getContext(), Bean);
        listview.setAdapter(baseAdapter);
        baseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseFirestore.setLoggingEnabled(true);
        initFirestore();
        readFromDatabase();

        listview = getActivity().findViewById(R.id.listview);
        search = (EditText) getActivity().findViewById(R.id.autotext);
        addSearchListener();
        setUpListViewListener();
    }

    private void setUpListViewListener(){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Article article = (Article)baseAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), ViewArticleAcitivity.class);
                if (intent != null) {
                    intent.putExtra("article", article);

                    startActivity(intent);
                }

            }
        });

    }

    /**
     * Check if any edittext field is empty
     *
     * @return if edittext is empty, return true.
     */
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    /**
     * Add search bar listener
     * Query the search keywords in Firestore
     */
    public void addSearchListener() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(search)) {
                    Toast.makeText(getContext(), "Please input what you want to search", Toast.LENGTH_SHORT).show();
                } else {
                    final ArrayList<Article> result = new ArrayList<>();
                    String s = search.getText().toString();
                    for (int i = 0; i < Bean.size(); i++) {
                        Article a = Bean.get(i);
                        String title = a.getNews();
                        if (title.contains(s)) {
                            result.add(a);
                        }
                    }
                    //Change the list view based on search result
                    baseAdapter = new ListBaseAdapter(getContext(), result);
                    listview.setAdapter(baseAdapter);
                    baseAdapter.notifyDataSetChanged();
                }
            }
        });

    }

}
