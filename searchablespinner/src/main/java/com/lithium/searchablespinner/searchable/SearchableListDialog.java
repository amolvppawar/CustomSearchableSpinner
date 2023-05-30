/*
    This coustomised view design of spinner to make it is searchable
 */
package com.lithium.searchablespinner.searchable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.lithium.searchablespinner.R;

import java.io.Serializable;
import java.util.List;

public class SearchableListDialog extends DialogFragment implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener, CustomArrayAdapter.ContactsAdapterListener {

    private static final String ITEMS = "items";

    private CustomArrayAdapter listAdapter;

    private RecyclerView _listViewItems;

    private SearchableItem _searchableItem;

    private OnSearchTextChanged _onSearchTextChanged;

    private SearchView _searchView;

    private String _strTitle;

    private String _strPositiveButtonText;

    private DialogInterface.OnClickListener _onClickListener;

    public SearchableListDialog() {

    }

    public static SearchableListDialog newInstance(List items) {
        SearchableListDialog multiSelectExpandableFragment = new
                SearchableListDialog();

        Bundle args = new Bundle();
        args.putSerializable(ITEMS, (Serializable) items);

        multiSelectExpandableFragment.setArguments(args);

        return multiSelectExpandableFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Getting the layout inflater to inflate the view in an alert dialog.
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        // Crash on orientation change #7
        // Change Start
        // Description: As the instance was re initializing to null on rotating the device,
        // getting the instance from the saved instance
        if (null != savedInstanceState) {
            _searchableItem = (SearchableItem) savedInstanceState.getSerializable("item");
        }
        // Change End

        View rootView = inflater.inflate(R.layout.dialog_searchable_spinner, null);
        setData(rootView);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(rootView);
        String strPositiveButton = _strPositiveButtonText == null ? "CLOSE" : _strPositiveButtonText;
       // alertDialog.setPositiveButton(strPositiveButton, _onClickListener);

       // String strTitle = _strTitle == null ? "Select Item" : _strTitle;
       // alertDialog.setTitle(strTitle);
        RecyclerView recyclerView=rootView.findViewById(R.id.listItems);
        TextView btnHome=rootView.findViewById(R.id.btnHome);
        TextView spinnerTitle=rootView.findViewById(R.id.spinnerTitle);
        List fi= (List) getArguments().getSerializable(ITEMS);
        spinnerTitle.setText(fi.get(0).toString());
        TextView btncancle=rootView.findViewById(R.id.btncancle);
        btnHome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(getDialog()!=null){
                                getDialog().dismiss();
                            }
                        }
                    });
        btncancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(getDialog()!=null){
                                getDialog().dismiss();
                            }
                        }
                    });
                    if(getRecyclerViewHeight(recyclerView)<500){
                        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        recyclerView.setLayoutParams(params);
                    }
        final AlertDialog dialog = alertDialog.create();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);
        return dialog;
    }
    public int getRecyclerViewHeight(RecyclerView recyclerView) {
       try {
           RecyclerView.Adapter adapter = recyclerView.getAdapter();
           if (adapter == null) {
               return 0;
           }

           int itemCount = adapter.getItemCount();
           int itemHeight = 0;
           int recyclerViewHeight = 0;

           RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

           for (int i = 0; i < itemCount; i++) {
               View itemView = layoutManager.findViewByPosition(i);
               if (itemView != null) {
                   // Measure the height of the item view
                   itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                   itemHeight = itemView.getMeasuredHeight();
                   recyclerViewHeight += itemHeight;
               }
           }
           return recyclerViewHeight;
       }catch (Exception e){

       }
       return 0;
    }

    public static int getLVHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        int height = 0;
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            View view = adapter.getView(i, null, listView);
            view.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            height += view.getMeasuredHeight();
        }
        height += listView.getDividerHeight() * (count - 1);
        return height;
    }
    // Crash on orientation change #7
    // Change Start
    // Description: Saving the instance of searchable item instance.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("item", _searchableItem);
        super.onSaveInstanceState(outState);
    }
    // Change End

    public void setTitle(String strTitle) {
        _strTitle = strTitle;
    }

    public void setPositiveButton(String strPositiveButtonText) {
        _strPositiveButtonText = strPositiveButtonText;
    }

    public void setPositiveButton(String strPositiveButtonText, DialogInterface.OnClickListener onClickListener) {
        _strPositiveButtonText = strPositiveButtonText;
        _onClickListener = onClickListener;
    }

    public void setOnSearchableItemClickListener(SearchableItem searchableItem) {
        this._searchableItem = searchableItem;
    }

    public void setOnSearchTextChangedListener(OnSearchTextChanged onSearchTextChanged) {
        this._onSearchTextChanged = onSearchTextChanged;
    }

    private void setData(View rootView) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context
                .SEARCH_SERVICE);

        _searchView = (SearchView) rootView.findViewById(R.id.search);
        _searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName
                ()));
        _searchView.setIconifiedByDefault(false);
        _searchView.setOnQueryTextListener(this);
        _searchView.setOnCloseListener(this);
        _searchView.clearFocus();
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(_searchView.getWindowToken(), 0);

        List<String> items = (List) getArguments().getSerializable(ITEMS);

        _listViewItems = (RecyclerView) rootView.findViewById(R.id.listItems);
        listAdapter = new CustomArrayAdapter(getActivity(), items,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        _listViewItems.setLayoutManager(mLayoutManager);
        _listViewItems.setAdapter(listAdapter);
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        dismiss();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        _searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
//        listAdapter.filterData(s);
        if (TextUtils.isEmpty(s)) {
//                _listViewItems.clearTextFilter();
            ((CustomArrayAdapter) _listViewItems.getAdapter()).getFilter().filter(null);
        } else {
            ((CustomArrayAdapter) _listViewItems.getAdapter()).getFilter().filter(s);
        }
        if (null != _onSearchTextChanged) {
            _onSearchTextChanged.onSearchTextChanged(s);
        }
        return true;
    }

    @Override
    public void onContactSelected(String contact,int position) {
        _searchableItem.onSearchableItemClicked(listAdapter.getItem(position),position);
        getDialog().dismiss();
    }

    public interface SearchableItem<T> extends Serializable {
        void onSearchableItemClicked(T item, int position);
    }

    public interface OnSearchTextChanged {
        void onSearchTextChanged(String strText);
    }

}

