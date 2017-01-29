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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.jeanmazuelos.katamoneymanager.models.Account;
import com.jeanmazuelos.katamoneymanager.utils.TransformUtils;

public class AccountEditActivity extends BaseEditorActivity<Account> {
    EditText etId,etName,etAmount;
    Spinner spAccountType,spCurrency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);

        initControls();

        if(isEditingObject()){
            etId.setVisibility(View.VISIBLE);
        } else {
            dataObject = new Account();
            etId.setVisibility(View.INVISIBLE);
        }
        etId.setText(TransformUtils.nullToEmpty(dataObject.getId()));
        etName.setText(TransformUtils.nullToEmpty(dataObject.getName()));
        etAmount.setText(TransformUtils.nullToEmpty(dataObject.getAmount()));
    }

    private void initControls(){
        etId= (EditText)findViewById(R.id.etId);
        etName = (EditText)findViewById(R.id.etName);
        etAmount = (EditText)findViewById(R.id.etAmount);
        spAccountType = (Spinner)findViewById(R.id.spAccountType);
        spCurrency= (Spinner)findViewById(R.id.spCurrency);
    }

    @Override
    protected boolean isValidObject() {
        return true;
    }

    @Override
    protected void updateDataObjectFromUI() {
        dataObject.setName(etName.getText().toString());
        dataObject.setAmount(Double.parseDouble(etAmount.getText().toString()));
        dataObject.setCurrency(spCurrency.getSelectedItem().toString());
        dataObject.setType(spAccountType.getSelectedItem().toString());
    }
}
