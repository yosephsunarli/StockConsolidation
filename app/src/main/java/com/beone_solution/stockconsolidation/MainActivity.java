package com.beone_solution.stockconsolidation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.beone_solution.stockconsolidation.adapter.ListConsolidationAdapter;
import com.beone_solution.stockconsolidation.data.remote.ApiUtils;
import com.beone_solution.stockconsolidation.data.remote.ConsolidationServices;
import com.beone_solution.stockconsolidation.model.ListConsolidationModel;
import com.beone_solution.stockconsolidation.model.PostDataModel;
import com.beone_solution.stockconsolidation.model.PostResultModel;
import com.beone_solution.stockconsolidation.widget.RobotoRegularTextView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
{
    Toolbar toolbar;
    private MaterialSearchView searchView;
    private RecyclerView listConsolidationRecyclerView;
    private ListConsolidationAdapter listConsolidationAdapter;
    private List<ListConsolidationModel> listConsolidation;
    private List<ListConsolidationModel> listCheckedConsolidation;
    private List<PostResultModel> postResult;
    private ConsolidationServices mService;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AppCompatButton btApprove;
    private View errorPage, loading, linearCheckAll;
    private RobotoRegularTextView tvError;
    private CheckBox checkAll;
    private RobotoRegularTextView checkAllText;
    private RobotoRegularTextView dataCount;
    private int activityBRequestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorPage = findViewById(R.id.errorPage);
        tvError = findViewById(R.id.tvError);
        loading = findViewById(R.id.loading);
        linearCheckAll = findViewById(R.id.linearCheckAll);
        dataCount = findViewById(R.id.dataCount);
        checkAll = findViewById(R.id.checkAll);
        checkAllText = findViewById(R.id.checkAllText);
        linearCheckAll.setVisibility(View.GONE);

        // Get Retrofit Service
        SharedPreferences prefs = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        String BASEURL = prefs.getString("BASEURL", "");
        if (!BASEURL.equals("") && Patterns.WEB_URL.matcher(BASEURL).matches()) {
            ApiUtils.BASE_URL = BASEURL;
        }
        else{
            // Base URL Minimal
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("BASEURL");
            editor.commit();
            ApiUtils.BASE_URL = "http://115.85.78.90:8734/BOS/";
        }
        mService = ApiUtils.getConsolidationServices();

        // Setting RecyclerView
        listConsolidationRecyclerView = this.findViewById(R.id.listconsolidationrecycleview);
        RecyclerView.LayoutManager merchandiseLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listConsolidationRecyclerView.setLayoutManager(merchandiseLayoutManager);
        listConsolidation = new ArrayList<>();
        listConsolidationAdapter = new ListConsolidationAdapter(listConsolidation);
        listConsolidationRecyclerView.setAdapter(listConsolidationAdapter);
        listConsolidationAdapter.setOnItemClickListener(new ListConsolidationAdapter.ListConsolidationClickListener()
        {
            @Override
            public void onItemClick(int position, View v)
            {
                // Load Form Detail
                Intent intent = new Intent();
                intent.setClass(v.getContext(), DetailActivity.class);
                intent.putExtra("CONID", listConsolidationAdapter.getConID(position));
                startActivityForResult(intent, activityBRequestCode);
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        final TextView tvToolbar = toolbar.findViewById(R.id.toolbar_title);
        Typeface font = Typeface.createFromAsset(MainActivity.this.getAssets(), "Roboto-Bold.ttf");
        tvToolbar.setTypeface(font);
        tvToolbar.setTextSize(22);

        searchView = findViewById(R.id.search_view);
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listConsolidationAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.trim().length() == 0){
                    listConsolidationAdapter.getFilter().filter("");
                    return false;
                } else {
                    listConsolidationAdapter.getFilter().filter(newText);
                    return false;
                }
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
            }
        });

        // Swipe to refresh
        mSwipeRefreshLayout = this.findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                showError(false, getString(R.string.errorMsg));
                showProgress(true);
                loadListConsolidation();
            }
        });

        // Load Data
        btApprove = findViewById(R.id.btApprove);
        btApprove.setVisibility(View.GONE);
        showError(false, getString(R.string.errorMsg));
        showProgress(true);
        loadListConsolidation();

        // Check All
        checkAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for(ListConsolidationModel data : listConsolidationAdapter.getFilteredData()){
                    if(checkAllText.getText().equals(getString(R.string.selectAll))) {
                        data.setChecked(true);
                    }
                    else{
                        data.setChecked(false);
                    }
                }
                listConsolidationAdapter.notifyDataSetChanged();
                if(checkAllText.getText().equals(getString(R.string.selectAll))) {
                    checkAllText.setText(R.string.deselectAll);
                }
                else{
                    checkAllText.setText(R.string.selectAll);
                }
            }
        });

        // Button Approve
        btApprove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listCheckedConsolidation = new ArrayList<>();
                // Check if any data was checked
                boolean hasAnyChecked = false;
                for(ListConsolidationModel data : listConsolidation){
                    if(data.getChecked()){
                        // Set bool to true
                        hasAnyChecked = true;
                        // Add to list checked consolidation
                        ListConsolidationModel item = new ListConsolidationModel(
                                data.getConID(),
                                data.getArtNo(),
                                data.getArtName(),
                                data.getFromWhsCode(),
                                data.getFromWhsName(),
                                data.getToWhsCode(),
                                data.getToWhsName(),
                                data.getFromBQty(),
                                data.getFromEQty(),
                                data.getToBQty(),
                                data.getToEQty()
                        );
                        listCheckedConsolidation.add(item);
                    }
                }
                // If nothing is checked
                if(!hasAnyChecked){
                    Toast.makeText(MainActivity.this, "Please choose at least 1 data", Toast.LENGTH_SHORT).show();
                } else {
                    // Mass Approve Process
                    showProgress(true);
                    approveProcess(listCheckedConsolidation);
                }
            }
        });
    }

    private void showProgress(boolean visible) {
        if (visible) {
            loading.setVisibility(View.VISIBLE);
            linearCheckAll.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            btApprove.setVisibility(View.GONE);
        } else {
            loading.setVisibility(View.GONE);
            linearCheckAll.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            btApprove.setVisibility(View.VISIBLE);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    private void showError(boolean visible, String errorMsg) {
        if (visible) {
            loading.setVisibility(View.GONE);
            errorPage.setVisibility(View.VISIBLE);
            tvError.setText(errorMsg);
            linearCheckAll.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            listConsolidationRecyclerView.setVisibility(View.GONE);
            btApprove.setVisibility(View.GONE);
        } else {
            errorPage.setVisibility(View.GONE);
            linearCheckAll.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            listConsolidationRecyclerView.setVisibility(View.VISIBLE);
            btApprove.setVisibility(View.VISIBLE);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    private void approveProcess(List<ListConsolidationModel> listCheckedConsolidation){
        String approvedDate = Calendar.getInstance().getTime().toString();
        postResult = new ArrayList<>();
        List<PostDataModel> postData = new ArrayList();

        int iterate = 0;
        int count = 0;

        for(ListConsolidationModel dataArray : listCheckedConsolidation){
            if (iterate < 10 && count < listCheckedConsolidation.size()) {
                String dataCONID = dataArray.getConID();
                String allocationID = dataArray.getFromWhsCode() + dataArray.getToWhsCode() + approvedDate;

                PostDataModel model = new PostDataModel();
                model.setType("MAIN");
                model.setConID(dataCONID);
                model.setItemStyle("");
                model.setItemCode("");
                model.setSize("");
                model.setSuggestion(0.0);
                model.setFromEQty(0.0);
                model.setToEQty(0.0);
                model.setTotalFromEQty(0.0);
                model.setTotalToEQty(0.0);
                model.setAllocationID(allocationID);
                model.setApproveDate(approvedDate);
                postData.add(iterate, model);

                iterate++;
            }
            else {
                executePost(postData);

                postData.clear();
                iterate = 0;

                String dataCONID = dataArray.getConID();
                String allocationID = dataArray.getFromWhsCode() + dataArray.getToWhsCode() + approvedDate;

                PostDataModel model = new PostDataModel();
                model.setType("MAIN");
                model.setConID(dataCONID);
                model.setItemStyle("");
                model.setItemCode("");
                model.setSize("");
                model.setSuggestion(0.0);
                model.setFromEQty(0.0);
                model.setToEQty(0.0);
                model.setTotalFromEQty(0.0);
                model.setTotalToEQty(0.0);
                model.setAllocationID(allocationID);
                model.setApproveDate(approvedDate);
                postData.add(iterate, model);

                iterate++;
            }
            count++;
        }
        executePost(postData);
        Toast.makeText(MainActivity.this, "Approval Process Completed", Toast.LENGTH_SHORT).show();
        loadListConsolidation();
    }

    private void executePost(List<PostDataModel> postData){
        mService.postKonsolidasi(postData).enqueue(new Callback<List<PostResultModel>>()
        {
            @Override
            public void onResponse(Call<List<PostResultModel>> call, Response<List<PostResultModel>> response)
            {
                postResult.clear();
                if (response.isSuccessful()) {
                    for (PostResultModel postResponse : response.body()) {
                        PostResultModel item = new PostResultModel(
                                postResponse.getConID(),
                                postResponse.getStatusCode(),
                                postResponse.getStatusDesc()
                        );
                        postResult.add(item);
                        Log.d("MainActivity", postResult.get(0).getStatusDesc().toString());
                    }
                } else{
                    int statusCode  = response.code();
                    try {
                        Log.e("MainActivity", "error posting to API, Error Code: " + statusCode + ", Message: " + response.errorBody().string());
                        showError(true, getString(R.string.errorMsg) + " Error Code: " + statusCode + ", Message: " + response.errorBody().toString());
                    } catch (IOException e) {
                        Log.e("MainActivity", "error posting to API, Error Code: " + statusCode + ", Message: " + e.getMessage().toString());
                        showError(true, getString(R.string.errorMsg) + " Error Code: " + statusCode + ", Message: " + e.getMessage().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PostResultModel>> call, Throwable t){
                Log.e("MainActivity", "error posting to API, Url:" + call.request().url(), t);
                showError(true, getString(R.string.errorMsg) + " Error: " + t.getMessage().toString());
            }
        });
    }

    private void loadListConsolidation(){
        mService.getListKonsolidasi().enqueue(new Callback<List<ListConsolidationModel>>() {
            @Override
            public void onResponse(Call<List<ListConsolidationModel>> call, Response<List<ListConsolidationModel>> response) {
                if (searchView.isSearchOpen()) {
                    searchView.closeSearch();
                }
                if (checkAll.isChecked()){
                    checkAll.setChecked(false);
                    checkAllText.setText(R.string.selectAll);
                }
                listConsolidation.clear();
                if(response.isSuccessful()) {
                    for (ListConsolidationModel listConsolidationResponse : response.body()) {
                        ListConsolidationModel item = new ListConsolidationModel(
                                listConsolidationResponse.getConID(),
                                listConsolidationResponse.getArtNo(),
                                listConsolidationResponse.getArtName(),
                                listConsolidationResponse.getFromWhsCode(),
                                listConsolidationResponse.getFromWhsName(),
                                listConsolidationResponse.getToWhsCode(),
                                listConsolidationResponse.getToWhsName(),
                                listConsolidationResponse.getFromBQty(),
                                listConsolidationResponse.getFromEQty(),
                                listConsolidationResponse.getToBQty(),
                                listConsolidationResponse.getToEQty()
                        );
                        if (hasDuplicate(listConsolidation, listConsolidationResponse.getConID())){
                            Log.d("DUPLICATE", "DUPLICATE: " + listConsolidationResponse.getConID());
                        }else {
                            listConsolidation.add(item);
                        }
                    }
                    listConsolidationAdapter.notifyDataSetChanged();
                    dataCount.setText(getString(R.string.dataCount) + (listConsolidation.size() + ""));
                    showError(false, getString(R.string.errorMsg));
                    showProgress(false);
                    Log.d("MainActivity", "posts loaded from API");
                }else {
                    showProgress(false);
                    int statusCode  = response.code();
                    try {
                        Log.e("MainActivity", "error loading from API, Error Code: " + statusCode + ", Message: " + response.errorBody().string());
                        showError(true, getString(R.string.errorMsg) + " Error Code: " + statusCode + ", Message: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("MainActivity", "error loading from API, Error Code: " + statusCode + ", Message: " + e.getMessage().toString());
                        showError(true, getString(R.string.errorMsg) + " Error Code: " + statusCode + ", Message: " + e.getMessage().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ListConsolidationModel>> call, Throwable t) {
                showProgress(false);
                Log.e("MainActivity", "error loading from API, Url:" + call.request().url(), t);
                if (t.getMessage().contains("End of input")){
                    showError(true, getString(R.string.noData));
                }
                else {
                    showError(true, getString(R.string.errorMsg) + " Error: " + t.getMessage().toString());
                }
            }
        });
    }

    public boolean hasDuplicate(List<ListConsolidationModel> list, String name) {
        for (ListConsolidationModel item : list) {
            if (item.getConID().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_bar_main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        MenuItem item1 = menu.findItem(R.id.action_setting);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_setting:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Exit Application")
                    .setMessage("Do you want to exit the application?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface arg0, int arg1)
                        {
                            MainActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == activityBRequestCode && resultCode == RESULT_OK){
            if (searchView.isSearchOpen()) {
                searchView.closeSearch();
            }
            showError(false, getString(R.string.errorMsg));
            showProgress(true);
            loadListConsolidation();
        }
    }
}
