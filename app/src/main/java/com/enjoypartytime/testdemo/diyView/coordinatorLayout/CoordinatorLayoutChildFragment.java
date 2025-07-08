package com.enjoypartytime.testdemo.diyView.coordinatorLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.diyView.coordinatorLayout.adapter.CoordinatorLayoutChildAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/11
 */
public class CoordinatorLayoutChildFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_coordinator_layout_child, container, false);
        initViews();
        return rootView;
    }

    private void initViews() {
        SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.coordinator_swipe_refresh_layout);
        RecyclerView recyclerView = rootView.findViewById(R.id.coordinator_recycler_view);

        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(false));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            stringList.add("第" + i + "个选项");
        }
        recyclerView.setAdapter(new CoordinatorLayoutChildAdapter(getContext(), stringList));

    }

}
