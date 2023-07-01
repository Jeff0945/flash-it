package com.coretech.flashit.ui.home;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.coretech.flashit.AppDatabase;
import com.coretech.flashit.CardShape;
import com.coretech.flashit.CardShapeAdapter;
import com.coretech.flashit.CenterLayoutManager;
import com.coretech.flashit.ModelCardSets;
import com.coretech.flashit.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private CardShapeAdapter adapter;
    private List<CardShape> cardShapes = new ArrayList<>();
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        adapter = new CardShapeAdapter(cardShapes);
        recyclerView.setLayoutManager(new CenterLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemSpacingDecoration(24)); // Set the spacing between items

        searchView = rootView.findViewById(R.id.search_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCardShapes(newText);
                return true;
            }
        });

        AppDatabase database = AppDatabase.getInstance(getContext());

        AsyncTask.execute(() -> {
            List<ModelCardSets> cardSets = database.cardSets().all();

            for(int i = 0; i < cardSets.size(); i++) {
                cardShapes.add(new CardShape(cardSets.get(i)));
            }
        });
        adapter.notifyDataSetChanged();

        return rootView;
    }

    //added
    private void filterCardShapes(String query) {
        AppDatabase database = AppDatabase.getInstance(getContext());

        if (query.isEmpty()) {
            AsyncTask.execute(() -> {
                cardShapes.clear();

                for (ModelCardSets cardSet : database.cardSets().all()) {
                    cardShapes.add(new CardShape(cardSet));
                }
            });
        } else {
            AsyncTask.execute(() -> {
                cardShapes.clear();

                for (ModelCardSets cardSet : database.cardSets().search(query)) {
                    cardShapes.add(new CardShape(cardSet));
                }
            });
        }

        adapter.notifyDataSetChanged();
    }

    private static class ItemSpacingDecoration extends RecyclerView.ItemDecoration {
        private int spacing;

        public ItemSpacingDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = spacing;
            outRect.right = spacing;
            outRect.top = spacing;
            outRect.bottom = spacing;
        }
    }
}
