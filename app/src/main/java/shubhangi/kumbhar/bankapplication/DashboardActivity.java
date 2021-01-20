package shubhangi.kumbhar.bankapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import shubhangi.kumbhar.bankapplication.fragment.AccountsFragment;
import shubhangi.kumbhar.bankapplication.fragment.TransactionFragment;
import shubhangi.kumbhar.bankapplication.fragment.TransferFragment;
import shubhangi.kumbhar.bankapplication.fragment.ViewAllCustomerFragment;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationAccounts:

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, AccountsFragment.newInstance())
                            .commit();
                    return true;

                case R.id.navigationTransactions:

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, TransactionFragment.newInstance())
                            .commit();
                    return true;

                case R.id.navigationTransfer:

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, TransferFragment.newInstance())
                            .commit();
                    return true;

                case R.id.navigationAllCustomer:

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, ViewAllCustomerFragment.newInstance())
                            .commit();
                    return true;

            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.navigationAccounts);
//        bottomNavigationView.setSelectedItemId(R.id.navigationTransactions);
//        bottomNavigationView.setSelectedItemId(R.id.navigationTransfer);
//        bottomNavigationView.setSelectedItemId(R.id.navigationAllCustomer);



    }
}