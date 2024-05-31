package com.example.linkblendv5.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> mName;
    private final MutableLiveData<List<Account>> mAccounts;

    public ProfileViewModel() {
        mName = new MutableLiveData<>();
        mName.setValue("Default Name");

        mAccounts = new MutableLiveData<>();
        List<Account> initialAccounts = new ArrayList<>();
        initialAccounts.add(new Account("Email", "", "user@example.com"));
        initialAccounts.add(new Account("Twitter", "", "https://twitter.com/user"));
        mAccounts.setValue(initialAccounts);
    }

    public LiveData<String> getName() {
        return mName;
    }

    public LiveData<List<Account>> getAccounts() {
        return mAccounts;
    }

    public void addAccount(Account account) {
        List<Account> currentAccounts = mAccounts.getValue();
        if (currentAccounts != null) {
            currentAccounts.add(account);
            mAccounts.setValue(currentAccounts);
        }
    }

    public void setName(String name) {
        mName.setValue(name);
    }
}
