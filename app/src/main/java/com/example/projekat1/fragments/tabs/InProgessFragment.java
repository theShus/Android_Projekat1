package com.example.projekat1.fragments.tabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat1.R;
import com.example.projekat1.activities.MainActivity;
import com.example.projekat1.activities.SingleFragmentDisplay;
import com.example.projekat1.fragments.BottomNavFragment;
import com.example.projekat1.recycler.TicketAdapter;
import com.example.projekat1.recycler.TicketDiffItemCallback;
import com.example.projekat1.viewModels.SharedViewModel;

public class InProgessFragment extends Fragment {

    private RecyclerView recyclerView;
    private SharedViewModel sharedViewModel;
    public static  TicketAdapter ticketAdapter;
    private EditText searchProgressTickets;
    public static final int REQUEST_CODE = 1;


    public InProgessFragment() {
        super(R.layout.fragment_in_progress);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        initView(view);
        initObservers();
        initRecycler(view);
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.listRvProgress);
        searchProgressTickets = view.findViewById(R.id.searchProgressTickets);
    }

    private void initObservers() {
        sharedViewModel.getTicketsInProgressLiveData().observe(getViewLifecycleOwner(), tickets -> {
            ticketAdapter.submitList(tickets);
        });

        searchProgressTickets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                sharedViewModel.filterProgressTickets(s.toString());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE) {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE);
                sharedViewModel.updateTicket(sharedPreferences.getString(MainActivity.MAIN_FRAGMENT,""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecycler(View view) {
        BottomNavFragment bottomNavFragment = (BottomNavFragment)  this.requireActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.MAIN_FRAGMENT);
        ticketAdapter = new TicketAdapter(sharedViewModel, new TicketDiffItemCallback(), ticket -> {
//            Toast.makeText(getActivity(), ticket.getId() + "asdfasdf", Toast.LENGTH_SHORT).show();//todo pokusaj da ovo provalis kako se radi / pitaj asistenta
//            transaction.replace(R.id.mainFragContainer, new EditTicketFragment());
//            transaction.addToBackStack(null);
//            transaction.commit();
            Intent intent = new Intent(view.getContext(),  SingleFragmentDisplay.class);
            intent.putExtra("ticket", ticket);
            startActivityForResult(intent , REQUEST_CODE);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(ticketAdapter);
    }
}
