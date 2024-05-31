package com.example.linkblendv5.ui.home;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.linkblendv5.R;
import com.example.linkblendv5.databinding.FragmentHomeBinding;
import com.example.linkblendv5.ui.profile.Account;
import com.example.linkblendv5.ui.shared.SharedViewModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Account> visibleAccounts;
    private FragmentHomeBinding binding;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        visibleAccounts = new ArrayList<>();
        sharedViewModel.getHomeAccounts().observe(getViewLifecycleOwner(), accounts -> {
            visibleAccounts.clear();
            if (accounts != null) {
                visibleAccounts.addAll(accounts);
            }
            populateButtons();
        });

        return binding.getRoot();
    }

    private void populateButtons() {
        // Reset all buttons
        binding.buttonContainer.removeAllViews();

        // Create buttons for each visible account
        for (Account account : visibleAccounts) {
            LinearLayout accountLayout = new LinearLayout(getContext());
            accountLayout.setOrientation(LinearLayout.VERTICAL);

            TextView accountTextView = new TextView(getContext());
            accountTextView.setText(account.getAccountType() + ": " + account.getLink());
            accountLayout.addView(accountTextView);

            Button removeButton = new Button(getContext());
            removeButton.setText("Remove");
            removeButton.setOnClickListener(v -> removeAccount(account));
            accountLayout.addView(removeButton);

            binding.buttonContainer.addView(accountLayout);
        }

        // If there are less than 8 accounts, add a button to add more accounts
        if (visibleAccounts.size() < 8) {
            Button addButton = new Button(getContext());
            addButton.setText("Add More Accounts");
            addButton.setOnClickListener(v -> showAddAccountDialog());
            binding.buttonContainer.addView(addButton);
        }

        // Generate and display QR Code
        generateQRCode(visibleAccounts);
    }

    private void showAddAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select_account, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        LinearLayout accountListContainer = dialogView.findViewById(R.id.accountListContainer);

        sharedViewModel.getProfileAccounts().observe(getViewLifecycleOwner(), accounts -> {
            accountListContainer.removeAllViews();
            for (Account account : accounts) {
                TextView accountTextView = new TextView(getContext());
                accountTextView.setText(account.getAccountType() + ": " + account.getLink());
                accountTextView.setOnClickListener(v -> {
                    sharedViewModel.addHomeAccount(account);
                    dialog.dismiss();
                });
                accountListContainer.addView(accountTextView);
            }
        });

        dialog.show();
    }

    private void removeAccount(Account account) {
        sharedViewModel.removeHomeAccount(account);
    }

    private void generateQRCode(List<Account> accounts) {
        StringBuilder accountData = new StringBuilder();
        for (Account account : accounts) {
            accountData.append(account.getAccountType()).append(": ").append(account.getLink()).append("\n");
        }

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            BitMatrix bitMatrix = barcodeEncoder.encode(accountData.toString(), BarcodeFormat.QR_CODE, 400, 400);
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            ImageView qrCodeImageView = new ImageView(getContext());
            qrCodeImageView.setImageBitmap(bitmap);
            binding.buttonContainer.addView(qrCodeImageView);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
