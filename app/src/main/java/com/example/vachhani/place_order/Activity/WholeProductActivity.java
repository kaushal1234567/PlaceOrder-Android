package com.example.vachhani.place_order.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vachhani.place_order.Data.DataContext;
import com.example.vachhani.place_order.Data.TableCart;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.Utility;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_whole_product)
public class WholeProductActivity extends BaseActivity {

    ProgressDialog pd;
    DataContext dataContext;
    public int qty = 1;

    @ViewById
    ImageView imgProduct;

    @ViewById
    RelativeLayout rlProduct;


    @ViewById
    Toolbar toolbar;

    @ViewById
    TextView txtProductName, txtPrice, txtDesc, txtTitle;

    @ViewById
    Button btnAddOrder, btnAddFav;

    String productName, productPrice, productDetails, productId, productImg;


    @OptionsMenuItem
    MenuItem menu_item;

    @AfterViews
    public void init() {

        pd = Utility.getDialog(this);
        setSupportActionBar(toolbar);
        txtTitle.setText(getString(R.string.whole_item));
        dataContext = new DataContext(this);
        productName = getIntent().getStringExtra("productName");
        productPrice = getIntent().getStringExtra("productPrice");
        productDetails = getIntent().getStringExtra("productDetails");
        productId = getIntent().getStringExtra("productId");
        productImg = getIntent().getStringExtra("productImg");
        txtProductName.setText(productName);
        txtPrice.setText(productPrice + " rs");
        txtDesc.setText(productDetails);
        Picasso.with(getApplicationContext()).load(productImg).into(imgProduct);
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddToCart();
            }
        });
/*
        View actionView = MenuItemCompat.getActionView(menu_item);
        txtBadge= actionView.findViewById(R.id.txtBadge);
        txtBadge.setText("121212121");*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.menu_item);
        menuItem.getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item: {
                // Do something
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

//    public void setBadge(){
//        txtBadge.setText("12121");
//    }

    @OptionsItem(R.id.menu_item)
    void onMenuItemClick(MenuItem menuItem) {
        startActivity(new Intent(WholeProductActivity.this, CartActivity_.class));
    }

    AlertDialog dialog;

    //On click of add to cart dialog will open to select quantity and then cart will be displayed.
    void onAddToCart() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_qty_dialog, null);

        final TextView txtQty = alertLayout.findViewById(R.id.txtQty);
        Button btnPlus = alertLayout.findViewById(R.id.btnPlus);
        Button btnMinus = alertLayout.findViewById(R.id.btnMinus);
        Button btnDone = alertLayout.findViewById(R.id.btnDone);


        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //this is set the view from XML inside AlertDialog
        alertDialog.setView(alertLayout);

        //sets initial quantity
        txtQty.setText(String.valueOf(qty));
        //increments the quantity
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty++;
                txtQty.setText(String.valueOf(qty));

            }
        });

        //decrements the quantity
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty > 1) {
                    qty--;
                    txtQty.setText(String.valueOf(qty));
                }
            }
        });

        //will move to cart activity
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                TableCart tableCart = new TableCart();
                tableCart.product_id = productId;
                tableCart.product_name = productName;
                tableCart.product_img = productImg;
                tableCart.price = Integer.parseInt(productPrice);
                tableCart.qty = qty;
                Log.d("table", String.valueOf(tableCart));
                try {
                    dataContext.userObjectSet.save(tableCart);
                } catch (AdaFrameworkException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(WholeProductActivity.this, CartActivity_.class));
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dialog != null)
            dialog.dismiss();
    }
}
