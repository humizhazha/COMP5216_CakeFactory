package au.edu.sydney.comp5216.cakefactory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * Created by apple on 18/03/16.
 */
public class TabFragment1 extends Fragment {
    protected View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragmenttab1, container, false);
        this.mView = view;
        return view;
    }


    public View getView() {
        return mView;
    }
}