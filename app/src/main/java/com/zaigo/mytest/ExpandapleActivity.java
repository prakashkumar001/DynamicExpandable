package com.zaigo.mytest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.zaigo.mytest.model.ChildStoreList;
import com.zaigo.mytest.model.Product;
import com.zaigo.mytest.model.StoreCategory;
import com.zaigo.mytest.model.StoreCategoryModel;
import com.zaigo.mytest.model.StoreChildModel;
import com.zaigo.mytest.restapi.ApiClient;
import com.zaigo.mytest.restapi.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpandapleActivity extends AppCompatActivity {
    List<StoreCategory> storeCategoryparentList = new ArrayList<>();
    private List<String> mDataList = new ArrayList<>();
    List<String> ChildList;
    // List<MovieCategory> movieCategories = new ArrayList<>();

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle = new ArrayList<>();
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


    List<Product> result = new ArrayList<Product>();
    List<Product> testProduct = new ArrayList<>();

    List<String> vvvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandaple);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        /*expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);*/
        mainProductCategoryApiCalling();
    }


    public void mainProductCategoryApiCalling() {


        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);


        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ExpandapleActivity.this);
        progressDoalog.setMessage("Loading....");

        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);


        Call<StoreCategoryModel> call = apiService.getStoreCategory();
        call.enqueue(new Callback<StoreCategoryModel>() {
            @Override
            public void onResponse(Call<StoreCategoryModel> call, final Response<StoreCategoryModel> response) {


                Log.e("TAG", "OrderHistoryModelTestsssss: " + new Gson().toJson(response.body()));
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());


                storeCategoryparentList.clear();
                mDataList.clear();


                if (response.body() != null) {
                    storeCategoryparentList.addAll(response.body().getStoreList().get(0).getCategories());


                    System.out.println("storeimageeeeee" + response.body().getStoreList().get(0).getStoreImage());


                    for (int i = 0; i < response.body().getStoreList().get(0).getCategories().size(); i++) {
                        mDataList.add(response.body().getStoreList().get(0).getCategories().get(i).getName());


                        System.out.println("MAINNNNNNNNNNNNNNN" + response.body().getStoreList().get(0).getCategories().get(i).getMainId());


                    }

                    childListDeatilsApiCalling(0);

                }


                progressDoalog.dismiss();


            }

            @Override
            public void onFailure(Call<StoreCategoryModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg", "OrderHistoryModelerrrrrrrorrrrrrrr" + t.toString());

                progressDoalog.dismiss();
            }
        });

    }


    public void childListDeatilsApiCalling(final int posi) {


        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);


        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(ExpandapleActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        // progressDoalog.setIndeterminate(true);
        progressDoalog.setCancelable(false);

/*
        Super Admin Login Details:
        Username: admin@zaigoinfotech.com
        Password: Ex58D??Yy44-PaBG
        */

        Call<StoreChildModel> call = apiService.getStoreChildList(storeCategoryparentList.get(posi).getMainId());
        call.enqueue(new Callback<StoreChildModel>() {
            @Override
            public void onResponse(Call<StoreChildModel> call, final Response<StoreChildModel> response) {

                Log.e("TAG", "CMAIN: " + storeCategoryparentList.get(posi).getMainId());

                //    movieCategories.clear();
                //Log.e("TAG", "childddddddddddddddd: " + new Gson().toJson(response.body()));
//                System.out.println("SHIPPINGSSSSSSSSS"+response.body().getBillingAddress().getFirstName()+",,"+response.body().getFirstName());


                if (response.body() != null) {


                   // Gson gson=new Gson();
                    List<StoreChildModel.StoreListBean.ProductsBean> storeChildModel=response.body().getStore_list().get(0).getProducts();
                    Log.e("Prakash","Prakash"+ storeChildModel);

                    expandData(storeChildModel,storeCategoryparentList.get(posi).getName());

                    if (mDataList.size() != posi + 1) {
                        childListDeatilsApiCalling(posi + 1);

                    }




                    // result.add(response.body().getStoreList().get(0).getProducts());


                    Log.e("TAG", "ChildProducrrrrrrrrrrrrrr: " + new Gson().toJson(response.body().getStore_list().get(0).getProducts()));




                }


                progressDoalog.dismiss();

                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<StoreChildModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg", "OrderHistoryModelerrrrrrrorrrrrrrr" + t.toString());

                progressDoalog.dismiss();
            }
        });

    }


    public void expandData(List<StoreChildModel.StoreListBean.ProductsBean> results, String name) {

       /* for (int i = 0; i < storeCategoryparentList.size(); i++) {

            expandableListTitle.add(storeCategoryparentList.get(i).getName());


            System.out.println("FFFFFFFFFFFFFFFFFFFF" + new Gson().toJson(results.get(0)));


          */

       expandableListTitle.add(name);

        ChildList = new ArrayList<String>();

        //   System.out.println("SIZEEEEEEEEEEEEE"+result.size());
        for (int ii = 0; ii < results.size(); ii++) {

            ChildList.add(results.get(ii).getTitle());
        }

        expandableListDetail.put(name, ChildList);
       // }
        expandableListAdapter = new CustomExpandableListAdapter(ExpandapleActivity.this, expandableListTitle,expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        System.out.println("QQQQQQQQQQQQQQQQ" + name + "," +ChildList);
           // Log.e("TAG", "RESULTTTTTTTTT: " + new Gson().toJson(result));
        //System.out.println("SIZEEEEEEEEEEEEE" + result.size());

    }

}
