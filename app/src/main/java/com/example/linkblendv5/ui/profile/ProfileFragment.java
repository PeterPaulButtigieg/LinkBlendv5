package com.example.linkblendv5.ui.profile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.linkblendv5.R;
import com.example.linkblendv5.databinding.FragmentProfileBinding;
import com.example.linkblendv5.ui.shared.SharedViewModel;
import java.util.List;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView nameTextView = binding.nameTextView;
        profileViewModel.getName().observe(getViewLifecycleOwner(), nameTextView::setText);

        final LinearLayout accountsContainer = binding.accountsContainer;
        profileViewModel.getAccounts().observe(getViewLifecycleOwner(), accounts -> {
            accountsContainer.removeAllViews();
            for (Account account : accounts) {
                View accountView = inflater.inflate(R.layout.item_account, accountsContainer, false);
                TextView accountTypeTextView = accountView.findViewById(R.id.accountTypeTextView);
                TextView accountLinkTextView = accountView.findViewById(R.id.accountLinkTextView);

                accountTypeTextView.setText(account.getAccountType());
                accountLinkTextView.setText(account.getLink());

                accountsContainer.addView(accountView);
            }
            sharedViewModel.setProfileAccounts(accounts);
        });

        Button addAccountButton = binding.addAccountButton;
        addAccountButton.setOnClickListener(v -> showAddAccountDialog(inflater));

        Button editNameButton = binding.editNameButton;
        editNameButton.setOnClickListener(v -> showEditNameDialog(inflater));

        return root;
    }

    private void showAddAccountDialog(LayoutInflater inflater) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_add_account, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        EditText accountTypeEditText = dialogView.findViewById(R.id.accountTypeEditText);
        EditText accountLinkEditText = dialogView.findViewById(R.id.accountLinkEditText);
        Button saveButton = dialogView.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            String accountType = accountTypeEditText.getText().toString().trim();
            String accountLink = accountLinkEditText.getText().toString().trim();

            if (!accountType.isEmpty() && !accountLink.isEmpty()) {
                addNewAccount(accountType, accountLink);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showEditNameDialog(LayoutInflater inflater) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_edit_name, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        EditText editNameEditText = dialogView.findViewById(R.id.editNameEditText);
        Button saveNameButton = dialogView.findViewById(R.id.saveNameButton);

        saveNameButton.setOnClickListener(v -> {
            String newName = editNameEditText.getText().toString().trim();

            if (!newName.isEmpty()) {
                profileViewModel.setName(newName);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addNewAccount(String accountType, String accountLink) {
        Account newAccount = new Account(accountType, "", accountLink);
        profileViewModel.addAccount(newAccount);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
