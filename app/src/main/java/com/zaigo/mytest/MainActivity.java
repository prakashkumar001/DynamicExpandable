package com.zaigo.mytest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zaigo.mytest.model.GetBoxAttributeModel;
import com.zaigo.mytest.model.GetBoxAttributeResponse;
import com.zaigo.mytest.restapi.ApiClient;
import com.zaigo.mytest.restapi.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

//    R stands RadioGroup
//    S stands Spinner
//    C stands Checkbox
//    T stands TextView

    // I stants ImageView

    //    String viewTypes[] = {"R", "S", "C", "T"};
    List<View> allViewInstance = new ArrayList<View>();
    JSONObject jsonObject = new JSONObject();
    private JSONObject optionsObj;
    private static final int SELECTED_PIC = 1;
    String filepath="";

    ArrayList<String> val = new ArrayList<>();

    List<GetBoxAttributeResponse> resp;
    ImageView btn;
    HashMap<String, String> mapinput;
    LinearLayout viewProductLayout;

    boolean validation =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         viewProductLayout = (LinearLayout) findViewById(R.id.customOptionLL);
        getatributeApiCalling();
       /* try {


            jsonObject = new JSONObject(loadJSONFromAsset());

            System.out.println("rrrrrrrrrrrrrrrrrrrr"+jsonObject);
            JSONArray customOptnList = jsonObject.getJSONArray("product_options");

            for (int noOfCustomOpt = 0; noOfCustomOpt < customOptnList.length(); noOfCustomOpt++) {

                JSONObject eachData = customOptnList.getJSONObject(noOfCustomOpt);
                TextView customOptionsName = new TextView(MainActivity.this);
                customOptionsName.setTextSize(18);
                customOptionsName.setPadding(0, 15, 0, 15);
                customOptionsName.setText(eachData.getString("option_name"));
                viewProductLayout.addView(customOptionsName);

                if (eachData.getString("option_type").equals("S")) {

                    final JSONArray dropDownJSONOpt = eachData.getJSONArray("variants");
                    ArrayList<String> SpinnerOptions = new ArrayList<String>();
                    for (int j = 0; j < dropDownJSONOpt.length(); j++) {
                        String optionString = dropDownJSONOpt.getJSONObject(j).getString("variant_name");
                        SpinnerOptions.add(optionString);
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = null;
                    spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spiner_row, SpinnerOptions);
                    Spinner spinner = new Spinner(MainActivity.this);

                    spinner.setBackgroundResource(R.drawable.edittext_border);
                    allViewInstance.add(spinner);
                    spinner.setAdapter(spinnerArrayAdapter);
                    spinner.setSelection(0, false);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            try {
                                String variant_name = dropDownJSONOpt.getJSONObject(position).getString("variant_name");
                                Toast.makeText(getApplicationContext(), variant_name + "", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                        }

                    });
                    viewProductLayout.addView(spinner);
                }


                if (eachData.getString("option_type").equals("R")) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.topMargin = 3;
                    params.bottomMargin = 3;

                    final JSONArray radioButtonJSONOpt = eachData.getJSONArray("variants");
                    RadioGroup rg = new RadioGroup(MainActivity.this); //create the RadioGroup
                    allViewInstance.add(rg);
                    for (int j = 0; j < radioButtonJSONOpt.length(); j++) {

                        RadioButton rb = new RadioButton(MainActivity.this);
                        rg.addView(rb, params);
                        if (j == 0)
                            rb.setChecked(true);
                        rb.setLayoutParams(params);
                        rb.setTag(radioButtonJSONOpt.getJSONObject(j).getString("variant_name"));
                        rb.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        String optionString = radioButtonJSONOpt.getJSONObject(j).getString("variant_name");
                        rb.setText(optionString);
                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {

                                View radioButton = group.findViewById(checkedId);
                                String variant_name = radioButton.getTag().toString();
                                Toast.makeText(getApplicationContext(), variant_name + "", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                    viewProductLayout.addView(rg, params);
                }



                if (eachData.getString("option_type").equals("C")) {


                    JSONArray checkBoxJSONOpt = eachData.getJSONArray("variants");

                    for (int j = 0; j < checkBoxJSONOpt.length(); j++) {

                        if (!(checkBoxJSONOpt.getJSONObject(j).getString("variant_name").equalsIgnoreCase("NO"))) {
                            CheckBox chk = new CheckBox(MainActivity.this);
                            chk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            allViewInstance.add(chk);
                            chk.setTag(checkBoxJSONOpt.getJSONObject(j).getString("variant_name"));
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.topMargin = 3;
                            params.bottomMargin = 3;
                            String optionString = checkBoxJSONOpt.getJSONObject(j).getString("variant_name");
                            chk.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    String variant_name = v.getTag().toString();
                                    Toast.makeText(getApplicationContext(), variant_name + "", Toast.LENGTH_LONG).show();
                                }
                            });
                            chk.setText(optionString);
                            viewProductLayout.addView(chk, params);
                        }
                    }
                }
                if (eachData.getString("option_type").equals("T")) {
                    TextInputLayout til = new TextInputLayout(MainActivity.this);
                    til.setHint(getString(R.string.app_name));
                    EditText et = new EditText(MainActivity.this);
                    et.setBackgroundResource(R.drawable.edittext_border);
                    et.setPadding(10,20,10,20);
                    til.addView(et);
                    allViewInstance.add(et);
                    viewProductLayout.addView(til);
                }



                if (eachData.getString("option_type").equals("I")) {

                     btn = new ImageView(MainActivity.this);
                    btn.setImageResource(R.drawable.ic_launcher_background);

                    btn.setPadding(10,20,10,20);

                    allViewInstance.add(btn);
                    btn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                          //  String variant_name = v.getTag().toString();
                            Toast.makeText(getApplicationContext(),   "Capture Test", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, SELECTED_PIC);
                        }
                    });

                    viewProductLayout.addView(btn);
                }




            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
    }

/*
    public void getDataFromDynamicViews(View v) {
        try {
            JSONArray customOptnList = jsonObject.getJSONArray("product_options");
            optionsObj = new JSONObject();
            for (int noOfViews = 0; noOfViews < customOptnList.length(); noOfViews++) {
                JSONObject eachData = customOptnList.getJSONObject(noOfViews);

                if (eachData.getString("option_type").equals("S")) {
                    Spinner spinner = (Spinner) allViewInstance.get(noOfViews);

                    JSONArray dropDownJSONOpt = eachData.getJSONArray("variants");
                    String variant_name = dropDownJSONOpt.getJSONObject(spinner.getSelectedItemPosition()).getString("variant_name");
                    Log.d("variant_name", variant_name + "");
                    optionsObj.put(eachData.getString("option_name"),
                            "" + variant_name);

                    System.out.println("valuesssssssssssssss"+variant_name);
                }

                if (eachData.getString("option_type").equals("R")) {
                    RadioGroup radioGroup = (RadioGroup) allViewInstance.get(noOfViews);
                    RadioButton selectedRadioBtn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    Log.d("variant_name", selectedRadioBtn.getTag().toString() + "");
                    optionsObj.put(eachData.getString("option_name"),
                            "" + selectedRadioBtn.getTag().toString());
                }

                if (eachData.getString("option_type").equals("C")) {
                    CheckBox tempChkBox = (CheckBox) allViewInstance.get(noOfViews);
                    if (tempChkBox.isChecked()) {
                        optionsObj.put(eachData.getString("option_name"), tempChkBox.getTag().toString());
                    }
                    Log.d("variant_name", tempChkBox.getTag().toString() + "");
                }
                if (eachData.getString("option_type").equals("T")) {
                    TextView textView = (TextView) allViewInstance.get(noOfViews);
                    if (!textView.getText().toString().equalsIgnoreCase(""))
                        optionsObj.put(eachData.getString("option_name"), textView.getText().toString());
                    else
                        optionsObj.put(eachData.getString("option_name"), textView.getText().toString());
                    Log.d("variant_name", textView.getText().toString() + "");
                }

                if (eachData.getString("option_type").equals("I")) {
                    ImageView textView = (ImageView) allViewInstance.get(noOfViews);


                    optionsObj.put(eachData.getString("option_name"),filepath);

                   */
/* if (val.size()>0)

                        for(int i = 0;i<val.size();i++){



                    }

*//*

                  */
/*  else
                        optionsObj.put(eachData.getString("option_name"), val);
                    Log.d("variant_name", textView.getText().toString() + "");*//*

                }

            }

            String outputData = (optionsObj + "").replace(",", "\n");
            outputData = outputData.replaceAll("[{}]","");
            ((TextView) findViewById(R.id.showData)).setText(outputData);
            Log.d("optionsObj", optionsObj + "");
            hideSoftKeyboard(findViewById(R.id.layout));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    public void hideSoftKeyboard(View v) {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = MainActivity.this.getAssets().open("yourfilename.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PIC:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                     filepath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    Drawable drawable = new BitmapDrawable(bitmap);
                  //  btn.setImageBitmap(bitmap);

                    val.add(filepath);
                    btn.setImageDrawable(drawable);

                    System.out.println("Pathhhhhhhhhhhhh"+filepath+","+drawable);
                }
                break;
            default:
                break;
        }
    }




    public void getatributeApiCalling() {



       /* Gson gson = new GsonBuilder()
                .registerTypeAdapter(GetBoxAttributeResponse.class, new GetBoxAttributeResponse.AccountStateDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();*/

        ApiInterface apiService =
                ApiClient.getClientArrayParse().create(ApiInterface.class);


        Map<String, String> map = new HashMap<>();
        map.put("appid", "f1470afb42145b8eb83319c7c156ade4");

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        // show it
        progressDoalog.show();
        progressDoalog.setCancelable(false);
        ///  kalrajen@gmail.com    12345
        Call<GetBoxAttributeModel> call = apiService.getBoxattrib(map, "14", "2", "getboxattribute");
        call.enqueue(new Callback<GetBoxAttributeModel>() {
            @Override
            public void onResponse(Call<GetBoxAttributeModel> call, Response<GetBoxAttributeModel> response) {
             //   optionsObj = new JSONObject();

                System.out.println("RESPOSESSSSSSSSS"+new Gson().toJson(response.body().getResponse()));


                 resp =response.body().getResponse();

                System.out.println("RESPOSESSSSSSSSS222222222222222"+new Gson().toJson(resp));

                JSONArray jsonObject = null;

                try {
                    jsonObject = new JSONArray(new Gson().toJson(response.body().getResponse()));
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject resultsobject = jsonObject.getJSONObject(i);

                        Log.i("mat", "matn" + response.body().getResponse().get(i).getFieldid());
                     final   GetBoxAttributeResponse boxattri = response.body().getResponse().get(i);



                        Object intervention = resultsobject.get("optionvalue");
                        // JSONObject  dataObject = resultsobject.getJSONObject("optionvalue");
                        // Log.i("nnnn", "nnnnxyz" + dataObject);
                        if (intervention instanceof JSONArray) {

                            JSONArray flagarray = resultsobject.getJSONArray("optionvalue");
                            Log.i("nnnn", "nnnnxy" + flagarray);

                            if (boxattri.getAttributetype().trim().equalsIgnoreCase("Single Select box")) {
                                if(boxattri.getIsrequired().trim().equalsIgnoreCase("1")){
                                    TextView customOptionsName = new TextView(MainActivity.this);
                                    customOptionsName.setTextSize(18);
                                    customOptionsName.setPadding(0, 15, 0, 15);
                                    customOptionsName.setText(boxattri.getAttributename()+" *");
                                    viewProductLayout.addView(customOptionsName);
                                }else {
                                    TextView customOptionsName = new TextView(MainActivity.this);
                                    customOptionsName.setTextSize(18);
                                    customOptionsName.setPadding(0, 15, 0, 15);
                                    customOptionsName.setText(boxattri.getAttributename());
                                    viewProductLayout.addView(customOptionsName);
                                }



                               final ArrayList<String> SpinnerOptions = new ArrayList<String>();
                                for (int j = 0; j < flagarray.length(); j++) {
                                    SpinnerOptions.add(flagarray.getString(j));
                                }



                               // final JSONArray dropDownJSONOpt = eachData.getJSONArray("optionvalue");

                              /*  for (int j = 0; j < dropDownJSONOpt.length(); j++) {
                                    String optionString = dropDownJSONOpt.getJSONObject(j).getString("variant_name");
                                    SpinnerOptions.add(optionString);
                                }*/

                                ArrayAdapter<String> spinnerArrayAdapter = null;
                                spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spiner_row, SpinnerOptions);
                                Spinner spinner = new Spinner(MainActivity.this);

                                spinner.setBackgroundResource(R.drawable.edittext_border);
                                allViewInstance.add(spinner);
                                spinner.setAdapter(spinnerArrayAdapter);
                                spinner.setSelection(0, false);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                        try {
                                           // String variant_name = dropDownJSONOpt.getJSONObject(position).getString("variant_name");
                                           // Toast.makeText(getApplicationContext(), variant_name + "", Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

/*
                                      try{
                                          optionsObj.put(boxattri.getFieldid(),SpinnerOptions.get(position));
                                      }catch (Exception e){

                                      }

*/

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {
                                    }

                                });
                                viewProductLayout.addView(spinner);
                            }




                        } else {



                            if (boxattri.getAttributetype().trim().equalsIgnoreCase("Text box")) {


                                if(boxattri.getIsrequired().trim().equalsIgnoreCase("1")){
                                    TextView customOptionsName = new TextView(MainActivity.this);
                                    customOptionsName.setTextSize(18);
                                    customOptionsName.setPadding(0, 15, 0, 15);
                                    customOptionsName.setText(boxattri.getAttributename()+" *");
                                    viewProductLayout.addView(customOptionsName);
                                }else {
                                    TextView customOptionsName = new TextView(MainActivity.this);
                                    customOptionsName.setTextSize(18);
                                    customOptionsName.setPadding(0, 15, 0, 15);
                                    customOptionsName.setText(boxattri.getAttributename());
                                    viewProductLayout.addView(customOptionsName);
                                }



                                TextInputLayout til = new TextInputLayout(MainActivity.this);
                                til.setHint(getString(R.string.app_name));
                                EditText et = new EditText(MainActivity.this);
                                et.setBackgroundResource(R.drawable.edittext_border);
                                et.setPadding(10,20,10,20);
                                til.addView(et);
                                allViewInstance.add(et);
                                viewProductLayout.addView(til);

                               // optionsObj.put(boxattri.getFieldid(),et.getText().toString());
                            }



                            if (boxattri.getAttributetype().trim().equalsIgnoreCase("Textarea")) {

                                if(boxattri.getIsrequired().trim().equalsIgnoreCase("1")){
                                    TextView customOptionsName = new TextView(MainActivity.this);
                                    customOptionsName.setTextSize(18);
                                    customOptionsName.setPadding(0, 15, 0, 15);
                                    customOptionsName.setText(boxattri.getAttributename()+" *");
                                    viewProductLayout.addView(customOptionsName);
                                }else {
                                    TextView customOptionsName = new TextView(MainActivity.this);
                                    customOptionsName.setTextSize(18);
                                    customOptionsName.setPadding(0, 15, 0, 15);
                                    customOptionsName.setText(boxattri.getAttributename());
                                    viewProductLayout.addView(customOptionsName);
                                }


                                TextInputLayout til = new TextInputLayout(MainActivity.this);
                                til.setHint(getString(R.string.app_name));
                                EditText et = new EditText(MainActivity.this);
                                et.setBackgroundResource(R.drawable.edittext_border);
                                et.setPadding(10,20,10,20);
                                til.addView(et);
                                allViewInstance.add(et);
                                viewProductLayout.addView(til);

                               // optionsObj.put(boxattri.getFieldid(),et.getText().toString());
                            }


                            if (boxattri.getAttributetype().trim().equalsIgnoreCase("Image")) {

                                if(boxattri.getIsrequired().trim().equalsIgnoreCase("1")){
                                    TextView customOptionsName = new TextView(MainActivity.this);
                                    customOptionsName.setTextSize(18);
                                    customOptionsName.setPadding(0, 15, 0, 15);
                                    customOptionsName.setText(boxattri.getAttributename()+" *");
                                    viewProductLayout.addView(customOptionsName);
                                }else {
                                    TextView customOptionsName = new TextView(MainActivity.this);
                                    customOptionsName.setTextSize(18);
                                    customOptionsName.setPadding(0, 15, 0, 15);
                                    customOptionsName.setText(boxattri.getAttributename());
                                    viewProductLayout.addView(customOptionsName);
                                }


                                TextInputLayout til = new TextInputLayout(MainActivity.this);
                                til.setHint(getString(R.string.app_name11));
                                EditText et = new EditText(MainActivity.this);
                                et.setBackgroundResource(R.drawable.edittext_border);
                                et.setPadding(10,20,10,20);
                                til.addView(et);
                                allViewInstance.add(et);
                                viewProductLayout.addView(til);

                                // optionsObj.put(boxattri.getFieldid(),et.getText().toString());
                            }

/*
                            if (boxattri.getFieldid().equalsIgnoreCase("field_22")) {
                                field_22.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[6] = "field_22";
                                    txt_field_22.setText("Box Ref No *");
                                }
                            }
                            if (boxattri.getFieldid().equalsIgnoreCase("field_24")) {
                                field_24.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[7] = "field_24";
                                    txt_field_24.setText("Box Loc No *");
                                }
                            }
                            if (boxattri.getFieldid().equalsIgnoreCase("field_23")) {
                                field_23.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[8] = "field_23";
                                    txt_field_23.setText("Work Point  *");
                                }
                            }
                            if (boxattri.getFieldid().equalsIgnoreCase("field_21")) {
                                field_21.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[9] = "field_21";
                                    txt_field_21.setText("Box Type Notes *");
                                }
                            }
                            if (boxattri.getFieldid().equalsIgnoreCase("field_33")) {
                                field_33.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[10] = "field_33";
                                    txt_field_33.setText("Address *");
                                }
                            }
                            if (boxattri.getFieldid().equalsIgnoreCase("field_30")) {
                                field_30.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[11] = "field_30";
                                    txt_field_30.setText("Notes *");
                                }
                            }

                            if (boxattri.getFieldid().equalsIgnoreCase("field_31")) {
                                field_31.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[12] = "field_31";
                                    txt_field_31.setText("Length *");
                                }
                            }

                            if (boxattri.getFieldid().equalsIgnoreCase("field_32")) {
                                field_32.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[13] = "field_32";
                                    txt_field_32.setText("Section *");
                                }
                            }




                            if (boxattri.getFieldid().equalsIgnoreCase("attachment1")) {
                                field_34.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[14] = "field_34";
                                    txt_field_32.setText("Attachment1 *");
                                }
                            }


                            if (boxattri.getFieldid().equalsIgnoreCase("attachment2")) {
                                field_35.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[15] = "field_35";
                                    txt_field_35.setText("Attachment2 *");
                                }
                            }

                            if (boxattri.getFieldid().equalsIgnoreCase("attachment3")) {
                                field_36.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[16] = "field_36";
                                    txt_field_36.setText("Attachment3 *");
                                }
                            }

                            if (boxattri.getFieldid().equalsIgnoreCase("attachment4")) {
                                field_37.setVisibility(View.VISIBLE);
                                if (!boxattri.getIsrequired().equalsIgnoreCase("0")) {
                                    req[17] = "field_37";
                                    txt_field_37.setText("Attachment4 *");
                                }*/
                         //   }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

               /* sp_boxtype.setItems(boxtype);
                sp_cabletype.setItems(cabletype);
                sp_nofblock.setItems(nofblockage);
                sp_nofbores.setItems(nofbores);
                sp_nofcables.setItems(nofcables);
                sp_outcome.setItems(secoutcome);
                sp_boxtype.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                    //mmm assign address
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        str_boxtype = item;
                    }
                });

                sp_cabletype.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                    //mmm assign address
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        str_cabletype = item;
                    }
                });

                sp_nofblock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                    //mmm assign address
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        str_nofblock = item;
                    }
                });

                sp_nofbores.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                    //mmm assign address
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        str_nofbores = item;
                    }
                });

                sp_nofcables.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                    //mmm assign address
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        str_nofcables = item;
                    }
                });

                sp_outcome.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                    //mmm assign address
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        str_outcome = item;
                    }
                });*/
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<GetBoxAttributeModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAGggggggg", "EEEEEEEEEEEEEEEe" + t.toString());
                progressDoalog.dismiss();
            }
        });
    }


    public void getDataFromDynamicViews(View v) {
        try {

            System.out.println("TESTTTTTTTTTTTTTTTTTTTT"+new Gson().toJson(resp));
            mapinput = new HashMap<>();

            JSONArray jsonObject = null;



            try {
                jsonObject = new JSONArray(new Gson().toJson(resp));

                optionsObj = new JSONObject();
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject resultsobject = jsonObject.getJSONObject(i);

                    Log.i("mat", "matn" + resp.get(i).getFieldid());
                    final   GetBoxAttributeResponse boxattri = resp.get(i);


                    System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTT"+(new Gson().toJson(jsonObject)));

                    Object intervention = resultsobject.get("optionvalue");
                    // JSONObject  dataObject = resultsobject.getJSONObject("optionvalue");
                    // Log.i("nnnn", "nnnnxyz" + dataObject);
                    if (intervention instanceof JSONArray) {


                       // Log.i("nnnn", "nnnnxy" + flagarray);

                        if (boxattri.getAttributetype().trim().equalsIgnoreCase("Single Select box")) {


                         /*   Spinner spinner = (Spinner) allViewInstance.get(noOfViews);

                            JSONArray dropDownJSONOpt = eachData.getJSONArray("variants");
                            String variant_name = dropDownJSONOpt.getJSONObject(spinner.getSelectedItemPosition()).getString("variant_name");
                            Log.d("variant_name", variant_name + "");
                            optionsObj.put(eachData.getString("option_name"),
                                    "" + variant_name);*/

                            Spinner spinner = (Spinner) allViewInstance.get(i);
                            JSONArray flagarray = resultsobject.getJSONArray("optionvalue");

                            String vvv = flagarray.getString(spinner.getSelectedItemPosition());

                            //String variant_name = dropDownJSONOpt.getJSONObject(spinner.getSelectedItemPosition()).toString();
                            Log.d("variant_name", vvv + "");
                            optionsObj.put(boxattri.getFieldid(),
                                    "" + vvv);

                            mapinput.put(boxattri.getFieldid(),vvv);


                        }




                    } else {


                        if(boxattri.getIsrequired().equalsIgnoreCase("0")){


                            if (boxattri.getAttributetype().trim().equalsIgnoreCase("Text box")) {

                                TextView textView = (TextView) allViewInstance.get(i);
                                if (!textView.getText().toString().equalsIgnoreCase(""))
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());
                                else
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());

                            }



                            if (boxattri.getAttributetype().trim().equalsIgnoreCase("Textarea")) {

                                TextView textView = (TextView) allViewInstance.get(i);
                                if (!textView.getText().toString().equalsIgnoreCase(""))
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());
                                else
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());

                            }


                            if (boxattri.getAttributetype().trim().equalsIgnoreCase("Image")) {

                                TextView textView = (TextView) allViewInstance.get(i);
                                if (!textView.getText().toString().equalsIgnoreCase(""))
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());
                                else
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());

                            }

                            System.out.println("yyyyyyyyyyyyyyyyyyyyyyy11111111111");
                        }else if(boxattri.getIsrequired().equalsIgnoreCase("1")){
                            if (boxattri.getAttributetype().trim().equalsIgnoreCase("Text box")) {
                                TextView textView = (TextView) allViewInstance.get(i);
                                if (!textView.getText().toString().equalsIgnoreCase("")) {
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());

                                    System.out.println("EMPTY1111111111111111");
                                    validation = true;

                                } else {


                                    validation = false;
                                    System.out.println("EMPTY22222222222222222");
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());
                                    break;
                                    //  break;
                                }
                            }


                            if (boxattri.getAttributetype().trim().equalsIgnoreCase("Textarea")) {
                                TextView textView = (TextView) allViewInstance.get(i);
                                if (!textView.getText().toString().equalsIgnoreCase("")) {
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());

                                    System.out.println("EMPTY1111111111111111");
                                    validation = true;

                                } else {

                                    validation = false;
                                    System.out.println("EMPTY22222222222222222");
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());
                                    break;


                                    //  break;
                                }
                            }

                            if (boxattri.getAttributetype().trim().equalsIgnoreCase("Image")) {
                                TextView textView = (TextView) allViewInstance.get(i);
                                if (!textView.getText().toString().equalsIgnoreCase("")) {
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());

                                    System.out.println("EMPTY1111111111111111");
                                    validation = true;

                                } else {

                                    validation = false;
                                    System.out.println("EMPTY22222222222222222");
                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());
                                    break;


                                    //  break;
                                }
                            }

                            System.out.println("yyyyyyyyyyyyyyyyyyyyyyy22222222222222");
                        }


/*
                        if (boxattri.getAttributetype().trim().equalsIgnoreCase("Text box")) {



                            TextView textView = (TextView) allViewInstance.get(i);


                            if(boxattri.getIsrequired().trim().equalsIgnoreCase("1")){

                                System.out.println("OOOOOOOOOOOOOOO11111111111");

                                if (!textView.getText().toString().equalsIgnoreCase("")) {


                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());
                                    //    mapinput.put(boxattri.getFieldid(),textView.getText().toString());
                                    System.out.println("yyyyyyyyyyyyyyyyyyyyyyy22222222222222");
                                    validation = true;


                                }else{
                                    validation = false;
                                    System.out.println("yyyyyyyyyyyyyyyyyyyyyyy");
                                 //   break;

                                    //  optionsObj.put(boxattri.getFieldid(), textView.getText().toString());
                                }



                                // validation = false;
                                //   mapinput.put(boxattri.getFieldid(),textView.getText().toString());




                            }else{
                                System.out.println("OOOOOOOOOOOOOOO222222222222");

                                validation = false;
                            }



                        }
*/



/*
                        if (boxattri.getAttributetype().trim().equalsIgnoreCase("Textarea")) {





                            TextView textView = (TextView) allViewInstance.get(i);



                            if(boxattri.getIsrequired().trim().equalsIgnoreCase("1")){

                                System.out.println("OOOOOOOOOOOOOOO11111111111");

                                if (!textView.getText().toString().equalsIgnoreCase("")) {


                                    optionsObj.put(boxattri.getFieldid(), textView.getText().toString());
                                    //    mapinput.put(boxattri.getFieldid(),textView.getText().toString());
                                     System.out.println("yyyyyyyyyyyyyyyyyyyyyyy22222222222222");
                                    validation = true;


                                }else{
                                    validation = false;
                                    System.out.println("yyyyyyyyyyyyyyyyyyyyyyy");
                                   // break;

                                  //  optionsObj.put(boxattri.getFieldid(), textView.getText().toString());
                                }



                               // validation = false;
                                //   mapinput.put(boxattri.getFieldid(),textView.getText().toString());




                            }else{
                                System.out.println("OOOOOOOOOOOOOOO222222222222");

                                validation = false;
                            }






                              */
/*  optionsObj.put(boxattri.getFieldid(), textView.getText().toString());

                                mapinput.put(boxattri.getFieldid(),textView.getText().toString());*//*



                        }
*/


                        //   }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


          /*
            JSONArray customOptnList = jsonObject.getJSONArray("product_options");
            optionsObj = new JSONObject();
            for (int noOfViews = 0; noOfViews < customOptnList.length(); noOfViews++) {
                JSONObject eachData = customOptnList.getJSONObject(noOfViews);

                if (eachData.getString("option_type").equals("S")) {
                    Spinner spinner = (Spinner) allViewInstance.get(noOfViews);

                    JSONArray dropDownJSONOpt = eachData.getJSONArray("variants");
                    String variant_name = dropDownJSONOpt.getJSONObject(spinner.getSelectedItemPosition()).getString("variant_name");
                    Log.d("variant_name", variant_name + "");
                    optionsObj.put(eachData.getString("option_name"),
                            "" + variant_name);

                    System.out.println("valuesssssssssssssss"+variant_name);
                }

                if (eachData.getString("option_type").equals("R")) {
                    RadioGroup radioGroup = (RadioGroup) allViewInstance.get(noOfViews);
                    RadioButton selectedRadioBtn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    Log.d("variant_name", selectedRadioBtn.getTag().toString() + "");
                    optionsObj.put(eachData.getString("option_name"),
                            "" + selectedRadioBtn.getTag().toString());
                }

                if (eachData.getString("option_type").equals("C")) {
                    CheckBox tempChkBox = (CheckBox) allViewInstance.get(noOfViews);
                    if (tempChkBox.isChecked()) {
                        optionsObj.put(eachData.getString("option_name"), tempChkBox.getTag().toString());
                    }
                    Log.d("variant_name", tempChkBox.getTag().toString() + "");
                }
                if (eachData.getString("option_type").equals("T")) {
                    TextView textView = (TextView) allViewInstance.get(noOfViews);
                    if (!textView.getText().toString().equalsIgnoreCase(""))
                        optionsObj.put(eachData.getString("option_name"), textView.getText().toString());
                    else
                        optionsObj.put(eachData.getString("option_name"), textView.getText().toString());
                    Log.d("variant_name", textView.getText().toString() + "");
                }

                if (eachData.getString("option_type").equals("I")) {
                    ImageView textView = (ImageView) allViewInstance.get(noOfViews);


                    optionsObj.put(eachData.getString("option_name"),filepath);


                }

            }*/


          if(validation == true){
              String outputData = (optionsObj + "").replace(",", "\n");
              outputData = outputData.replaceAll("[{}]","");
              ((TextView) findViewById(R.id.showData)).setText(outputData);
              Log.d("optionsObj", optionsObj + "");
              hideSoftKeyboard(findViewById(R.id.layout));
          }else{

              Toast.makeText(this, "VALIDATION", Toast.LENGTH_SHORT).show();
          }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}