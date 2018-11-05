package com.beone_solution.stockconsolidation;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beone_solution.stockconsolidation.adapter.DetailConsolidationAdapter;
import com.beone_solution.stockconsolidation.data.remote.ApiUtils;
import com.beone_solution.stockconsolidation.data.remote.ConsolidationServices;
import com.beone_solution.stockconsolidation.model.DetailConsolidationModel;
import com.beone_solution.stockconsolidation.model.PostDataModel;
import com.beone_solution.stockconsolidation.model.PostResultModel;
import com.beone_solution.stockconsolidation.util.GlideUtils;
import com.beone_solution.stockconsolidation.widget.RobotoBoldTextView;
import com.beone_solution.stockconsolidation.widget.RobotoRegularTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity
{
    private String CONID;
    private ConsolidationServices mService;
    private List<DetailConsolidationModel> detailConsolidation;
    private List<PostResultModel> postResult;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View loading, content;

    // Items
    private Toolbar toolbar;
    // Header
    private ImageView dtl_image;
    private RobotoRegularTextView dtl_artno;
    private RobotoRegularTextView dtl_artname;
    private RobotoRegularTextView dtl_collection;
    private RobotoRegularTextView dtl_weeks;
    private RobotoRegularTextView dtl_sellthru;
    private RobotoRegularTextView dtl_retailprice;
    private RobotoRegularTextView dtl_disc;
    private RobotoRegularTextView dtl_from;
    private RobotoRegularTextView dtl_to;
    private RobotoBoldTextView total_suggestion_qty;
    private RobotoBoldTextView total_from_end_qty;
    private RobotoBoldTextView total_to_end_qty;
    private AppCompatButton btApprove;
    // Detail
    private DetailConsolidationAdapter detailConsolidationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState==null) {
            CONID = getIntent().getStringExtra("CONID");
            mService = ApiUtils.getConsolidationServices();
            detailConsolidation = new ArrayList<>();
            bindItem();
        }

        // Swipe to refresh
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                showProgress(true);
                loadDetail(CONID);
            }
        });
    }

    private void showProgress(boolean visible) {
        if (visible) {
            loading.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            btApprove.setVisibility(View.GONE);
        } else {
            loading.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            btApprove.setVisibility(View.VISIBLE);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    private void bindItem(){
        setActionBarTitle();

        loading = findViewById(R.id.loading);
        mSwipeRefreshLayout = this.findViewById(R.id.mSwipeRefreshLayout);

        // Header
        dtl_image = findViewById(R.id.dtl_image);
        dtl_artno = findViewById(R.id.dtl_artno);
        dtl_artname = findViewById(R.id.dtl_artname);
        dtl_collection = findViewById(R.id.dtl_collection);
        dtl_weeks = findViewById(R.id.dtl_weeks);
        dtl_sellthru = findViewById(R.id.dtl_sellthru);
        dtl_retailprice = findViewById(R.id.dtl_retailprice);
        dtl_disc = findViewById(R.id.dtl_disc);
        dtl_from = findViewById(R.id.dtl_from);
        dtl_to = findViewById(R.id.dtl_to);
        total_suggestion_qty = findViewById(R.id.suggest_total);
        total_from_end_qty = findViewById(R.id.end_from_total);
        total_to_end_qty = findViewById(R.id.end_to_total);

        // Button Approve
        btApprove = findViewById(R.id.btApprove);
        btApprove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String approvedDate = Calendar.getInstance().getTime().toString();

                postResult = new ArrayList<>();
                List<PostDataModel> data = new ArrayList();

                double sumFromEnd = 0;
                double sumToEnd = 0;
                for (DetailConsolidationModel detailData : detailConsolidation){
                    sumFromEnd += (detailData.getFromBQty() - detailData.getSuggestedQty());
                    sumToEnd += (detailData.getToBQty() + detailData.getSuggestedQty());
                }
                int dataindex = 0;
                for (DetailConsolidationModel detailData : detailConsolidation) {
                    String allocationID = detailData.getFromWhsCode() + detailData.getToWhsCode() + approvedDate;

                    PostDataModel model = new PostDataModel();
                    model.setType("DETAIL");
                    model.setConID(CONID);
                    model.setItemStyle(detailData.getArtNo());
                    model.setItemCode(detailData.getItemCode());
                    model.setSize(detailData.getSize());
                    model.setSuggestion(detailData.getSuggestedQty());
                    model.setFromEQty(detailData.getFromBQty() - detailData.getSuggestedQty());
                    model.setToEQty(detailData.getToBQty() + detailData.getSuggestedQty());
                    model.setTotalFromEQty(sumFromEnd);
                    model.setTotalToEQty(sumToEnd);
                    model.setAllocationID(allocationID);
                    model.setApproveDate(approvedDate);
                    data.add(dataindex, model);
                    dataindex++;
                }

                mService.postKonsolidasi(data).enqueue(new Callback<List<PostResultModel>>()
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
                                Toast.makeText(DetailActivity.this, postResult.get(0).getStatusDesc(), Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                onBackPressed();
                            }
                        } else{
                            int statusCode  = response.code();
                            try {
                                Log.e("DetailActivity", "error posting to API, Error Code: " + statusCode + ", Message: " + response.errorBody().string());
                                Toast.makeText(DetailActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Log.e("DetailActivity", "error posting to API, Error Code: " + statusCode + ", Message: " + e.getMessage().toString());
                                Toast.makeText(DetailActivity.this, getString(R.string.errorMsg) + " Error Code: " + statusCode + ", Message: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PostResultModel>> call, Throwable t){
                        Log.e("DetailActivity", "error posting to API, Url:" + call.request().url(), t);
                        Toast.makeText(DetailActivity.this, getString(R.string.errorMsg), Toast.LENGTH_SHORT);
                    }
                });
            }
        });

        // Detail
        // Setting RecyclerView
        RecyclerView detailConsolidationRecyclerView = this.findViewById(R.id.detailconsolidationrecyclerview);
        RecyclerView.LayoutManager merchandiseLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        detailConsolidationRecyclerView.setLayoutManager(merchandiseLayoutManager);
        detailConsolidationAdapter = new DetailConsolidationAdapter(detailConsolidation, new OnEditTextChanged()
        {
            @Override
            public void afterTextChanged(int position, String charSeq)
            {
                updateTotal();
            }
        });
//        detailConsolidationAdapter = new DetailConsolidationAdapter(detailConsolidation);
        detailConsolidationRecyclerView.setAdapter(detailConsolidationAdapter);
        showProgress(true);
        loadDetail(CONID);
    }

    private void updateTotal(){
        double sumSuggestion = 0;
        double sumFromEnd = 0;
        double sumToEnd = 0;
        for (DetailConsolidationModel data : detailConsolidation){
            sumSuggestion += data.getSuggestedQty();
            sumFromEnd += (data.getFromBQty() - data.getSuggestedQty());
            sumToEnd += (data.getToBQty() + data.getSuggestedQty());
        }
        total_suggestion_qty.setText(String.format("%.0f", sumSuggestion));
        total_from_end_qty.setText(String.format("%.0f", sumFromEnd));
        total_to_end_qty.setText(String.format("%.0f", sumToEnd));
    }

    private void loadDetail(String conID){
        mService.getDetailKonsolidasi(conID).enqueue(new Callback<List<DetailConsolidationModel>>()
        {
            @Override
            public void onResponse(Call<List<DetailConsolidationModel>> call, Response<List<DetailConsolidationModel>> response)
            {
                detailConsolidation.clear();
                if (response.isSuccessful()) {
                    for (DetailConsolidationModel detailConsolidationResponse : response.body()) {
                        DetailConsolidationModel item = new DetailConsolidationModel(
                                detailConsolidationResponse.getConID(),
                                detailConsolidationResponse.getConImage(),
                                detailConsolidationResponse.getArtNo(),
                                detailConsolidationResponse.getArtName(),
                                detailConsolidationResponse.getCollection(),
                                detailConsolidationResponse.getOfWeeks(),
                                detailConsolidationResponse.getSellThru(),
                                detailConsolidationResponse.getRetailPrice(),
                                detailConsolidationResponse.getDisc(),
                                detailConsolidationResponse.getFromWhsCode(),
                                detailConsolidationResponse.getFromWhsName(),
                                detailConsolidationResponse.getToWhsCode(),
                                detailConsolidationResponse.getToWhsName(),
                                detailConsolidationResponse.getItemCode(),
                                detailConsolidationResponse.getSize(),
                                detailConsolidationResponse.getFromBQty(),
                                detailConsolidationResponse.getToBQty(),
                                detailConsolidationResponse.getSuggestedQty()
                        );
                        detailConsolidation.add(item);
                    }
                    bindData(detailConsolidation);
                } else{
                    int statusCode  = response.code();
                    try {
                        Log.e("DetailActivity", "error loading from API, Error Code: " + statusCode + ", Message: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("DetailActivity", "error loading from API, Error Code: " + statusCode);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DetailConsolidationModel>> call, Throwable t){
                Log.e("DetailActivity", "error loading from API, Url:" + call.request().url(), t);
            }
        });
    }

    private void bindData(List<DetailConsolidationModel> data){
        // Header
        dtl_artno.setText(data.get(0).getArtNo() + "");
        dtl_artname.setText(data.get(0).getArtName() + "");
        dtl_collection.setText(data.get(0).getCollection() + "");
        dtl_weeks.setText(data.get(0).getOfWeeks() + "");
        dtl_sellthru.setText(String.format("%.2f", data.get(0).getSellThru()));
        dtl_retailprice.setText(getPriceFormat(data.get(0).getRetailPrice()));
        dtl_disc.setText(String.format("%.2f", data.get(0).getDisc()));
        dtl_from.setText(data.get(0).getFromWhsName());
        dtl_to.setText(data.get(0).getToWhsName());

        // Image
        if (data.get(0).getConImage() != null && !data.get(0).getConImage().isEmpty() && !data.get(0).getConImage().equals("null")) {
            GlideUtils.loadImage(data.get(0).getConImage(), dtl_image);
        }

        // Detail
        detailConsolidationAdapter.notifyDataSetChanged();
        updateTotal();
        showProgress(false);
    }

    public static String getPriceFormat(double price) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return String.format("%s,00", formatRupiah.format(price));
    }

    // Set Actionbar title
    private void setActionBarTitle(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        TextView tvToolbar = toolbar.findViewById(R.id.toolbar_title);
        Typeface font = Typeface.createFromAsset(DetailActivity.this.getAssets(), "Roboto-Bold.ttf");
        tvToolbar.setTypeface(font);
        tvToolbar.setTextSize(22);
        tvToolbar.setText(getResources().getString(R.string.app_name));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
