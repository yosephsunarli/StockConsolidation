package com.beone_solution.stockconsolidation.data.remote;


import com.beone_solution.stockconsolidation.model.DetailConsolidationModel;
import com.beone_solution.stockconsolidation.model.ListConsolidationModel;
import com.beone_solution.stockconsolidation.model.PostDataModel;
import com.beone_solution.stockconsolidation.model.PostResultModel;
import com.beone_solution.stockconsolidation.model.SearchResultModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ConsolidationServices
{
    @GET("getListKonsolidasi")
    Call<List<ListConsolidationModel>> getListKonsolidasi();

    @GET("getDetailKonsolidasi/{KonsolidasiNo}")
    Call<List<DetailConsolidationModel>> getDetailKonsolidasi(@Path("KonsolidasiNo") String conID);

    @POST("postKonsolidasi")
    Call<List<PostResultModel>> postKonsolidasi(@Body List<PostDataModel> post);
}
