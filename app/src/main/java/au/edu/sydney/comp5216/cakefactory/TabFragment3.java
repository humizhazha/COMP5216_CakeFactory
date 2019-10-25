package au.edu.sydney.comp5216.cakefactory;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import adapter.JayBaseAdapter;

public class TabFragment3 extends Fragment {


    private View view;

    private ListView listview;

    Typeface fonts1, fonts2;

    private int[] IMAGE = {R.drawable.box, R.drawable.ball, R.drawable.bag,
            R.drawable.box, R.drawable.ball};

    private String[] TITLE = {"Teak & Steel Petanque Set", "Lemon Peel Baseball", "Seil Marschall Hiking Pack", "Teak & Steel Petanque Set", "Lemon Peel Baseball"};

    private String[] DESCRIPTION = {"One Size", "One Size", "Size L", "One Size", "One Size"};

    private String[] DATE = {"$ 220.00", "$ 49.00", "$ 320.00", "$ 220.00", "$ 49.00"};

    private ArrayList<Bean> Bean;
    private JayBaseAdapter baseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmenttab3, container, false);


        listview = (ListView) view.findViewById(R.id.listview);


        Bean = new ArrayList<Bean>();

        for (int i = 0; i < TITLE.length; i++) {

            Bean bean = new Bean(IMAGE[i], TITLE[i], DESCRIPTION[i], DATE[i]);
            Bean.add(bean);

        }


        baseAdapter = new JayBaseAdapter(getActivity(), Bean) {
        };

        listview.setAdapter(baseAdapter);


        return view;

    }
}