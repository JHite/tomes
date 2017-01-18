package com.hitej.android.metalarchives;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import de.loki.metallum.MetallumException;


/**
 * Created by jhite on 5/17/16.
 */
public class SearchQueryFragment extends Fragment {

    private static boolean isInflated;

    private static final String TAG = "SearchQueryFragment";

    private static final String ARG_SEARCH_TYPE = "Search Type";
    // 5/23 - making these public to not declare over & over
    public static final String BAND_QUERY = "Band query";
    public static final String ALBUM_QUERY = "Album query";
    public static final String ARTIST_QUERY = "Artist query";
    public static final String SONG_QUERY = "Song query";

    private static String searchTypePlaceholder;


    public static SearchQueryFragment newInstance(){
        return new SearchQueryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_search_query, container, false);

        final String searchType = getActivity().getIntent()
                .getStringExtra(MASearchActivity.EXTRA_SEARCH_TYPE);

        final String fallbackSearchType = "BAND_QUERY";

        //setInflated to true
        setIsInflated(true);

        final EditText queryText = (EditText)view.findViewById(R.id.search_query);

        //instatiate radio buttons
        final RadioGroup searchRadioGroup =
                (RadioGroup)view.findViewById(R.id.search_radiogroup);
        RadioButton radioButtonBand =
                (RadioButton)view.findViewById(R.id.search_radiobutton_band);
        RadioButton radioButtonAlbum =
                (RadioButton)view.findViewById(R.id.search_radiobutton_album);
        RadioButton radioButtonSong =
                (RadioButton)view.findViewById(R.id.search_radiobutton_song);
        RadioButton radioButtonArtist =
                (RadioButton)view.findViewById(R.id.search_radiobutton_artist);
        Button buttonSubmitQuery = (Button)view.findViewById(R.id.search_button_submit);

        //logic to prevent crashes in case searchType not supplied as extra
        //I.E Back arrow from Band Info back to SearchActivity
        if (searchType != null)
            toggleSearchRadioButton(view, searchType);
        else
            toggleSearchRadioButton(view, fallbackSearchType);

        buttonSubmitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "search button clicked");

                 String queryTextString = queryText.getText().toString();
                if(TextUtils.isEmpty(queryTextString)){
                    Toast.makeText(getContext()
                                    , "Please enter your search query"
                                    , Toast.LENGTH_SHORT)
                                    .show();
                }else {
                    //hide soft keyboard
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(queryText.getWindowToken(), 0);
                    //end hide code

                     queryTextString = queryText.getText().toString();
                    Log.i(TAG, "show search results fragment");
                  if (searchRadioGroup.getCheckedRadioButtonId()
                          == R.id.search_radiobutton_band){
                      Log.i(TAG, "Band radio btn selected");
                      searchTypePlaceholder = BAND_QUERY;
                    //TODO: finish radio button logic
                  } else {  Log.i(TAG, "fix this shit in onCLick");}

                switch (searchTypePlaceholder) {
                    case BAND_QUERY:
                        //try{
                            ((MASearchActivity)getActivity())
                                .showBandSearchResults(queryTextString);
                        Log.i(TAG, "fragment transaction made in onclick!");
                        break;
                       // }
                        //catch (NullPointerException exception){
                        //Log.d(TAG, exception.getMessage());
                          //  break;



                }
                    /*
                    //build intent using
                    Log.i(TAG, getArguments().getString(ARG_SEARCH_TYPE));
                    Intent intent = MASearchActivity.newIntent(getContext()
                            , getArguments().getString(ARG_SEARCH_TYPE)
                            , true);
                    ;
                    startActivity(intent); */
                }
            }
        });

        Log.i(TAG,searchType + " = searchType");



        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        setIsInflated(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(ARG_SEARCH_TYPE, searchTypePlaceholder);
        //TODO:save searchType into this ARG^^ for rotation saving


    }

    public static boolean isInflated() {
        return isInflated;
    }

    public static void setIsInflated(boolean isInflated) {
        SearchQueryFragment.isInflated = isInflated;
    }

    /*
    public void updateUI(){

        if(getArguments().getString(ARG_SEARCH_TYPE) != null){
            toggleSearchRadioButton(getView(),getArguments().getString(ARG_SEARCH_TYPE));
        }
        else
            return;
    }
    */

    public void toggleSearchRadioButton(View view, String searchType){

        switch (searchType) {
            case (BAND_QUERY):
                RadioButton button = (RadioButton)view.findViewById(R.id.search_radiobutton_band);
                button.toggle();
                Log.i(TAG,"Band toggled");
                break;
            case (ALBUM_QUERY):
                button = (RadioButton)view.findViewById(R.id.search_radiobutton_album);
                button.toggle();
                Log.i(TAG,"Album toggled");
                break;
            case (ARTIST_QUERY):
                button = (RadioButton)view.findViewById(R.id.search_radiobutton_artist);
                button.toggle();
                Log.i(TAG,"Artist toggled");
                break;
            case(SONG_QUERY):
                button = (RadioButton)view.findViewById(R.id.search_radiobutton_song);
                button.toggle();
                Log.i(TAG,"Song toggled");
                break;
            default:
                button = (RadioButton)view.findViewById(R.id.search_radiobutton_band);
                button.toggle();

        }
    }
}



