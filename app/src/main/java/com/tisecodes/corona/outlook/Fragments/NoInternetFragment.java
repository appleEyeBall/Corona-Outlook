package com.tisecodes.corona.outlook.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tisecodes.corona.outlook.Activities.MainActivity;
import com.tisecodes.corona.outlook.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoInternetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoInternetFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public NoInternetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoInternetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoInternetFragment newInstance(String param1, String param2) {
        NoInternetFragment fragment = new NoInternetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    Button tryAgain;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_no_internet, container, false);
        Toast.makeText(getActivity(), "No internet Connection", Toast.LENGTH_SHORT).show();
        tryAgain = (Button) v.findViewById(R.id.btnNoInternet);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getMainActivity().recreate();
            }
        });
        return v;

    }

}
