package com.example.linkblendv5.ui.scan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.linkblendv5.R;
import com.example.linkblendv5.ui.profile.Account;

import java.util.List;

public class SharedFragment extends Fragment {

    private String sharedCodeText;
    private View rootView;

    public SharedFragment(String sharedCodeText) {
        this.sharedCodeText = sharedCodeText;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shared, container, false);
        parseSharedCode(sharedCodeText);
        return rootView;
    }

    private void parseSharedCode(String codeText) {
        // Assuming codeText is JSON, parse it
        // Example: {"name": "John Doe", "accounts": [{"accountType": "Facebook", "iconFilePath": "/path/to/icon", "link": "http://facebook.com/johndoe"}]}
        // Code to parse and display content
        // Set name and accounts in UI elements
    }
}
