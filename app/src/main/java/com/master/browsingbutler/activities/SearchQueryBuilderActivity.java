package com.master.browsingbutler.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.adapters.SearchQueryRecycleViewAdapter;
import com.master.browsingbutler.components.JavaScriptInterface;
import com.master.browsingbutler.models.ElementWrapper;
import com.master.browsingbutler.utils.Log;

import java.util.List;
import java.util.stream.Collectors;


public class SearchQueryBuilderActivity extends ActivityWithSwitchHandler {

    private static final String TAG = "SearchQueryBuilderActivity";
    private static final String GOOGLE_URL = App.getResourses().getString(R.string.google_query_url);

    private List<ElementWrapper> allTextElements;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        this.setContentView(R.layout.activity_searchquerybuilder);
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        this.allTextElements = JavaScriptInterface.getSelectedElements().stream().filter(ElementWrapper::isText).collect(Collectors.toList());

        Button selectAll = this.findViewById(R.id.select_all);
        selectAll.setOnClickListener(v -> {
            Log.d(this, "selectAll clicked");
            SearchQueryRecycleViewAdapter searchQueryRecycleViewAdapter = WebpageRetrieverActivity.configuration.getSearchQueryRecycleViewAdapater();
            List<SearchQueryRecycleViewAdapter.SearchQueryRecycleViewHolder> viewHolders = searchQueryRecycleViewAdapter.getViewHolders();
            viewHolders.forEach(SearchQueryRecycleViewAdapter.SearchQueryRecycleViewHolder::select);
            this.allTextElements.forEach(eW -> eW.setChosen(true));
        });

        Button continueCompress = this.findViewById(R.id.continue_compress);
        continueCompress.setOnClickListener(v -> this.createAndLaunchSearchQuery());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this);
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_searchquerybuilder);
        WebpageRetrieverActivity.configuration.configureList(this, "SearchQuery");
    }

    private void createAndLaunchSearchQuery() {
        Log.d(TAG, "createAndLaunchSearchQuery");
        StringBuilder searchQuery = new StringBuilder();
        this.allTextElements.stream().filter(ElementWrapper::getChosen)
                .forEach(elementWrapper -> searchQuery.append(elementWrapper.getText()).append(" "));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_URL + searchQuery.toString()));
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void switchHandler(View view, int position) {
        ElementWrapper clickedElementWrapper = this.allTextElements.get(position);
        Log.d(this, "item on pos: " + position + ", was: " + clickedElementWrapper.getChosen());
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void finish() {
        Log.d(this, "finish()");
        super.finish();
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
