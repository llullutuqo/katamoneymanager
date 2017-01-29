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

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.jeanmazuelos.katamoneymanager.models.Category;
import com.jeanmazuelos.katamoneymanager.utils.TransformUtils;


/**
 * Category editor activity implemented from BaseEditorActivity
 */
public class CategoryEditActivity extends BaseEditorActivity<Category> {

    private EditText etId,etName,etDescription;
    private Spinner spCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        initControls();

        if(isEditingObject()){
            etId.setVisibility(View.VISIBLE);
            etId.setText(TransformUtils.nullToEmpty(getDataObject().getId()));
        } else {
            dataObject = new Category();
            etId.setText("");
            etId.setVisibility(View.INVISIBLE);
        }

        etName.setText(TransformUtils.nullToEmpty(getDataObject().getName()));
        etDescription.setText(TransformUtils.nullToEmpty(getDataObject().getDescription()));
    }

    /**
     * Initialises all the user interface controls
     */
    private void initControls(){
        etId = (EditText) findViewById(R.id.etId);
        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        spCurrency = (Spinner) findViewById(R.id.spCurrency);
    }

    @Override
    protected boolean isValidObject() {
        return true;
    }

    /**
     * Implements the updateDataObjectFromUI from BaseEditorActivity
     */
    @Override
    protected void updateDataObjectFromUI() {
        getDataObject().setName(etName.getText().toString());
        getDataObject().setDescription(etDescription.getText().toString());
        getDataObject().setCurrency(spCurrency.getSelectedItem().toString());
    }
}
