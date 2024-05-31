package com.example.linkblendv5.ui.shared;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.linkblendv5.ui.profile.Account;
import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Account>> profileAccounts;
    private final MutableLiveData<List<Account>> homeAccounts;

    public SharedViewModel() {
        profileAccounts = new MutableLiveData<>(new ArrayList<>());
        homeAccounts = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Account>> getProfileAccounts() {
        return profileAccounts;
    }

    public void setProfileAccounts(List<Account> accounts) {
        profileAccounts.setValue(accounts);
    }

    public LiveData<List<Account>> getHomeAccounts() {
        return homeAccounts;
    }

    public void addHomeAccount(Account account) {
        List<Account> currentAccounts = homeAccounts.getValue();
        if (currentAccounts != null && !currentAccounts.contains(account)) {
            currentAccounts.add(account);
            homeAccounts.setValue(currentAccounts);
        }
    }

    public void removeHomeAccount(Account account) {
        List<Account> currentAccounts = homeAccounts.getValue();
        if (currentAccounts != null) {
            currentAccounts.remove(account);
            homeAccounts.setValue(currentAccounts);
        }
    }
}
