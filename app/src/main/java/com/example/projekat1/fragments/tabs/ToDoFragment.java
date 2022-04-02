package com.example.projekat1.fragments.tabs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat1.R;
import com.example.projekat1.activities.MainActivity;
import com.example.projekat1.fragments.BottomNavFragment;
import com.example.projekat1.fragments.EditTicketFragment;
import com.example.projekat1.fragments.NewTicketFragment;
import com.example.projekat1.recycler.TicketAdapter;
import com.example.projekat1.recycler.TicketDiffItemCallback;
import com.example.projekat1.viewModels.SharedViewModel;


public class ToDoFragment extends Fragment {
    private RecyclerView recyclerView;
    private SharedViewModel sharedViewModel;
    private TicketAdapter ticketAdapter;
    private EditText searchTodoTickets;

    public ToDoFragment() {
        super(R.layout.fragment_todo);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        initView(view);
        initObservers();
        initRecycler();
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.listRv);
        searchTodoTickets = view.findViewById(R.id.searchTodoTickets);
    }

    private void initObservers() {
        sharedViewModel.getTicketsTodoLiveData().observe(getViewLifecycleOwner(), tickets -> {
            ticketAdapter.submitList(tickets);
        });

        searchTodoTickets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                sharedViewModel.filterTodoTickets(s.toString());
            }
        });
    }

    private void initRecycler() {
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();//todo zasto ne radi ako se koristi child, a radi sa parentovim
        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        BottomNavFragment bottomNavFragment = (BottomNavFragment)  this.getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.MAIN_FRAGMENT);

        ticketAdapter = new TicketAdapter(sharedViewModel, new TicketDiffItemCallback(), ticket -> {
//            Toast.makeText(getActivity(), ticket.getId() + "asdfasdf", Toast.LENGTH_SHORT).show();
            transaction.replace(R.id.mainFragContainer, new EditTicketFragment());
            transaction.addToBackStack(null);
            transaction.commit();

//            Intent intent = new Intent(view.getContext(), SingleFragmentDisplay.class);
//            startActivity(intent);
//
//            transaction.add(R.id.singleFratView, new EditTicketFragment());
//            transaction.commit();


        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(ticketAdapter);
    }
}
