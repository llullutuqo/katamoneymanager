/****************************************************************************
 ** Copyright (C) 2017 Jean Mazuelos(LlulluTuqo)
 ** Author Web: http://jeanmazuelos.com/blog
 **
 ** This file is part of the Kata Money Manager Android App.
 ** LGPL V3 License Usage
 **
 ** You should use this file under the terms of the
 ** GNU LESSER GENERAL PUBLIC LICENSE - https://www.gnu.org/licenses/lgpl-3.0.txt
 **
 ** Additionally, any Redistributions of source code must retain the above
 ** copyright notice, this condition and the following disclaimer.
 **
 ** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 ** "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 ** LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 ** A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 ** OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 ** SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 ** LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 ** DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 ** THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 ** (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 ** OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE."
 **
 ****************************************************************************/

package com.jeanmazuelos.katamoneymanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeanmazuelos.katamoneymanager.adapters.DataModelArrayAdapter;
import com.jeanmazuelos.katamoneymanager.controllers.MainController;
import com.jeanmazuelos.katamoneymanager.data.DataModel;
import com.jeanmazuelos.katamoneymanager.models.Account;
import com.jeanmazuelos.katamoneymanager.models.AccountingEntry;
import com.jeanmazuelos.katamoneymanager.models.Category;

import java.util.ArrayList;
import java.util.List;

public class DetailsItemActivity extends AppCompatActivity {
    public static final String KEY = "type";

    Account dataAccount;

    String type = null;

    DataModelArrayAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item);
        type = getIntent().getStringExtra(KEY);

        initFab();

        initControls();

        dataAccount = (Account)getIntent().getSerializableExtra(AccountingEntryEditActivity.ACCOUNT_KEY_OBJECT);
    }

    @Override
    public void onResume(){
        super.onResume();
        loadDetails();
    }

    private void initControls(){
        ListView lv = (ListView) findViewById(R.id.lvVisor);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataModel data = (DataModel)adapterView.getAdapter().getItem(i);
                if(data!= null){
                    Intent it = null;
                    if(Category.TABLE_NAME.equals(type)){
                         it = new Intent(DetailsItemActivity.this, CategoryEditActivity.class);
                    } else if(Account.TABLE_NAME.equals(type)) {
                        it = new Intent(DetailsItemActivity.this, AccountEditActivity.class);
                    } else if(AccountingEntry.TABLE_NAME.equals(type)){
                        it = new Intent(DetailsItemActivity.this, AccountingEntryEditActivity.class);
                        it.putExtra(AccountingEntryEditActivity.ACCOUNT_KEY_OBJECT,
                                dataAccount);
                    }
                    if(it !=null){
                        it.putExtra(BaseEditorActivity.KEY_INTENT_OBJECT, data);
                        startActivity(it);
                        return true;
                    }
                }
                return false;
            }
        });
        if(Account.TABLE_NAME.equals(type)) {
            //Calls AccountingEntry on double TAP item
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                private static final long DOUBLE_CLICK_TIME_DELTA = 300;

                long lastClickTime = 0;

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    long clickTime = System.currentTimeMillis();
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                        Intent it = new Intent(DetailsItemActivity.this,
                                DetailsItemActivity.class);

                        it.putExtra(DetailsItemActivity.KEY, AccountingEntry.TABLE_NAME);
                        it.putExtra(AccountingEntryEditActivity.ACCOUNT_KEY_OBJECT,
                                (DataModel) adapterView.getAdapter().getItem(i));
                        //HACK: is not posible to call a same activity itself
                        finish();
                        startActivity(it);
                    }
                    lastClickTime = clickTime;
                }
            });
        }
    }


    /**
     * Initializes the floating buttons (Add and delete)
     */
    private void initFab(){
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Category.TABLE_NAME.equals(type)){
                    Intent it = new Intent(DetailsItemActivity.this, CategoryEditActivity.class);
                    startActivity(it);
                } else if(Account.TABLE_NAME.equals(type)) {
                    Intent it = new Intent(DetailsItemActivity.this, AccountEditActivity.class);
                    startActivity(it);
                } else if(AccountingEntry.TABLE_NAME.equals(type)){
                    Intent it = new Intent(DetailsItemActivity.this, AccountingEntryEditActivity.class);
                    it.putExtra(AccountingEntryEditActivity.ACCOUNT_KEY_OBJECT,
                            dataAccount);
                    startActivity(it);
                }
            }
        });
        FloatingActionButton fabDel = (FloatingActionButton) findViewById(R.id.fabDelete);
        fabDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ListView lv = (ListView)findViewById(R.id.lvVisor);
            SparseBooleanArray sp = lv.getCheckedItemPositions();
            if(sp == null || sp.size() <= 0){
                Snackbar.make(view,
                        "You must select at least one item to proced to delete",
                        Snackbar.LENGTH_SHORT).show();
                return;
            }
            int count = sp.size();

            List<DataModel> list = new ArrayList<DataModel>();
            for (int i = 0; i < sp.size(); i++) {
                if (sp.valueAt(i)){
                    list.add((DataModel) lv.getAdapter().getItem(sp.keyAt(i)));
                }
            }
            if(list.size()>0){
                MainController mainController = new MainController(DetailsItemActivity.this);
                mainController.deleteDataModelObjects(list);
                loadDetails();
            }
            }
        });
    }


    /**
     * Load the items details on the ListView
     */
    private  void loadDetails(){

        MainController mainController = new MainController(this);
        ListView lv = (ListView) findViewById(R.id.lvVisor);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        List<? extends DataModel> data = null;
        if(Category.TABLE_NAME.equals(type)){
            data = mainController.getAllCategories();
            tvTitle.setText(getString(R.string.action_category));
        } else if(Account.TABLE_NAME.equals(type)){
            data = mainController.getAllAccounts();
            tvTitle.setText(getString(R.string.action_account));
        } else if (AccountingEntry.TABLE_NAME.equals(type)){
            data = mainController.getAllAccountEntryByAccountId(dataAccount.getId());
            tvTitle.setText(getString(R.string.action_account_entry));
        } else{
            Toast.makeText(this, "Not implemented.", Toast.LENGTH_LONG);
            finish();
        }
        if(data != null){
            databaseAdapter = new DataModelArrayAdapter(this,
                    android.R.layout.simple_list_item_multiple_choice,
                    data);
            lv.setAdapter(databaseAdapter);
        }
    }
}
