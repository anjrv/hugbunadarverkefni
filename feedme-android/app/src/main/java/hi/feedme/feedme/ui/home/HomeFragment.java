package hi.feedme.feedme.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;

import hi.feedme.feedme.MainActivity;
import hi.feedme.feedme.R;
import hi.feedme.feedme.listeners.RecipeListNwCallback;
import hi.feedme.feedme.logic.Networking;
import hi.feedme.feedme.models.SimplifiedRecipe;

/**
 * The main fragment of the application.
 *
 * It contains a filters toolbar as well as a list of SimplifiedRecipes
 */
public class HomeFragment extends Fragment {
    // Column count currently unused
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    /**
     * Use fragment toolbar as main supporting action bar for the main activity
     *
     * @param v the view to get the toolbar from
     */
    private void initToolbar(View v) {
        Toolbar toolbar = v.findViewById(R.id.toolbar2);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

        // May want to show something here later
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * Initialize and set the recyclerView adapter for the list simplified recipes
     *
     * @param v the view the recyclerView is in
     */
    private void initAdapter(View v) {
        this.recyclerView = v.findViewById(R.id.list);
        Context context = recyclerView.getContext();

        if (mColumnCount <= 1) {
            this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            this.recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        this.recyclerView.setAdapter(new RecipeRecyclerViewAdapter(RecipeContent.items));
    }

    /**
     * Helper function to repopulate the recyclerView content items
     *
     * @param rs a list of SimplifiedRecipe objects to replace the current content with
     */
    private void setData(ArrayList<SimplifiedRecipe> rs) {
        RecipeContent.items.clear();

        for (SimplifiedRecipe r : rs) {
            RecipeContent.items.add(r);
            RecipeContent.itemMap.put(r.getId(), r);
        }

        // This is fine for the purpose of changing the entire set
        // It is only more efficient to use ItemChanged if changing single items
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * Helper function to request new recipes based on the chosen filter settings
     *
     * @param scals     The calorie slider
     * @param sf        The fat slider
     * @param scarbs    The carbs slider
     * @param sp        The proteins slider
     */
    private void refreshRecipes(RangeSlider scals, RangeSlider sf, RangeSlider scarbs, RangeSlider sp) {
        Networking conn = ((MainActivity) getActivity()).getNetwork();
        List<Float> currVals;

        // Populate querystring based on filters
        String query = "?sort=rating";
        currVals = scals.getValues();
        query += "&mincalories=" + Math.round(currVals.get(0));
        query += "&maxcalories=" + Math.round(currVals.get(1));

        currVals = sf.getValues();
        query += "&minfats=" + Math.round(currVals.get(0));
        query += "&maxfats=" + Math.round(currVals.get(1));

        currVals = scarbs.getValues();
        query += "&mincarbs=" + Math.round(currVals.get(0));
        query += "&maxcarbs=" + Math.round(currVals.get(1));

        currVals = sp.getValues();
        query += "&minproteins=" + Math.round(currVals.get(0));
        query += "&maxproteins=" + Math.round(currVals.get(1));

        // Backend call
        conn.getRecipes(new RecipeListNwCallback() {
            @Override
            public void notifySuccess(ArrayList<SimplifiedRecipe> response) throws JsonProcessingException {
                // Replace recyclerview items
                setData(response);
            }

            @Override
            public void notifyError(VolleyError error) {

            }
        }, query);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initToolbar(view);
        initAdapter(view);

        // Would split this initialization into another function using view as argument
        // But we need these sliders anyway for the onclick method
        RangeSlider scals = view.findViewById(R.id.slider_calories);
        scals.setValues(0.0f, 1000.0f);

        RangeSlider sf = view.findViewById(R.id.slider_fats);
        sf.setValues(0.0f, 100.0f);

        RangeSlider scarbs = view.findViewById(R.id.slider_carbs);
        scarbs.setValues(0.0f, 100.0f);

        RangeSlider sp = view.findViewById(R.id.slider_protein);
        sp.setValues(0.0f, 100.0f);

        Button btn = view.findViewById(R.id.search_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshRecipes(scals, sf, scarbs, sp);
            }
        });

        return view;
    }
}