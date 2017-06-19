package com.fjnu.edu.helper.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fjnu.edu.helper.R;

/**
 * Created by ZhouShiqiao on 2017/6/3 0003.
 */

public class NoteMainFragmnet extends Fragment {
    private Context context;
    private View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_note_main, container, false);
        findview();
        initview();
        return view;
    }

    private void findview() {

    }

    private void initview() {

    }
}
