package com.example.kathleenmcnulty.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.kathleenmcnulty.androidlabs.R.id.frame;


public class MessageFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ID";
    private static final String ARG_PARAM2 = "MESSAGE";

    private int id;
    private long dbid;
    private String msg;
    Context parent;

    //no matter how you got here, the data is in the getArguments
    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        Bundle bun = getArguments();
        id = bun.getInt(ARG_PARAM1);
        //dbid = bun.getLong("DBID");
        //Log.d("DBIDINFRAGMENT", dbid+"");
        msg = bun.getString(ARG_PARAM2);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        parent = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View gui = inflater.inflate(R.layout.fragment_message, null);
        TextView idTextView = (TextView) gui.findViewById(R.id.textView2);
        idTextView.setText("ID: "+id);
        TextView msgTextView = (TextView)gui.findViewById(R.id.textView3);
        msgTextView.setText(msg);
        Button btn = (Button) gui.findViewById(R.id.button44);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity().getClass() == ChatWindow.class){
                    ((ChatWindow)getActivity()).deleteFromDb(id);
                }else{
                    Intent i = new Intent(getActivity(), ChatWindow.class);
                    //i.putExtra("DBID", dbid);
                    i.putExtra("ID", id);
                    getActivity().setResult(Activity.RESULT_OK, i);
                    getActivity().finish();
                }


            }
        });
        return gui;
    }

}
