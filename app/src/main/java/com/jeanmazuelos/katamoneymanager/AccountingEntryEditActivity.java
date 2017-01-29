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

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.jeanmazuelos.katamoneymanager.adapters.DataModelArrayAdapter;
import com.jeanmazuelos.katamoneymanager.controllers.MainController;
import com.jeanmazuelos.katamoneymanager.models.Account;
import com.jeanmazuelos.katamoneymanager.models.AccountingEntry;
import com.jeanmazuelos.katamoneymanager.models.Category;
import com.jeanmazuelos.katamoneymanager.utils.TransformUtils;

import java.util.Date;
import java.util.List;

public class AccountingEntryEditActivity extends BaseEditorActivity<AccountingEntry> {

    public  static final String ACCOUNT_KEY_OBJECT =  "ACCOUNT_KEY";

    Account dataAccount;
    Spinner spCategory,spDueType;
    EditText etId, etDate,etTittle,etDescription,etAmount;
    Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting_entry_edit);
        dataAccount = (Account)getIntent().getSerializableExtra(AccountingEntryEditActivity.ACCOUNT_KEY_OBJECT);
        initControls();

        if(isEditingObject()){
            etId.setVisibility(View.VISIBLE);
            selectedDate = dataObject.getDateDue();
        } else {
            dataObject = new AccountingEntry();
            etId.setVisibility(View.INVISIBLE);
            selectedDate = new Date();
        }

        etId.setText(TransformUtils.nullToEmpty(dataObject.getId()));
        etTittle.setText(TransformUtils.nullToEmpty(dataObject.getTittle()));
        etDescription.setText(TransformUtils.nullToEmpty(dataObject.getDescription()));
        etAmount.setText(TransformUtils.nullToEmpty(dataObject.getDue()));
        etDate.setText(TransformUtils.dateToString(selectedDate));
        //TODO: Load data to spinners

    }

    private void initControls(){

        spCategory = (Spinner) findViewById(R.id.spCategory);
        spDueType = (Spinner) findViewById(R.id.spDueType);
        etDate = (EditText) findViewById(R.id.etDate);
        etId = (EditText) findViewById(R.id.etId);
        etTittle = (EditText) findViewById(R.id.etTittle);
        etDescription= (EditText) findViewById(R.id.etDescription);
        etAmount =(EditText) findViewById(R.id.etAmount);

        //Init the Category Spinner
        MainController mainController = new MainController(this);
        List<Category> data = mainController.getAllCategories();
        DataModelArrayAdapter databaseAdapter = new DataModelArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                data);
        spCategory.setAdapter(databaseAdapter);
    }

    /**
     * Shows a DatePickerDialog to select a date.
     * @param v
     */
    public void onSetDate(View v){

        DatePickerDialog dp = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        /*selectedDate.setMonth(i1+1);
                        selectedDate.setYear(i);
                        selectedDate.setDate(i2);*/
                        selectedDate= new Date(i-1900,i1,i2);
                        etDate.setText(TransformUtils.dateToString(selectedDate));
                    }
                },
                selectedDate.getYear()+1900,
                selectedDate.getMonth(),
                selectedDate.getDate());
        dp.show();
    }
    @Override
    protected boolean isValidObject()
    {
        //TODO: implement all validations here
        return true;
    }

    @Override
    protected void updateDataObjectFromUI() {
        dataObject.setType(spDueType.getSelectedItem().toString());
        dataObject.setAccount(dataAccount);
        dataObject.setDateDue(selectedDate);
        dataObject.setCategory((Category)spCategory.getSelectedItem());
        dataObject.setTittle(etTittle.getText().toString());
        dataObject.setDescription(etDescription.getText().toString());
        dataObject.setDue(Double.parseDouble(etAmount.getText().toString()));
    }

}
