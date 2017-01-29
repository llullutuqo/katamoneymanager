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
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jeanmazuelos.katamoneymanager.controllers.MainController;
import com.jeanmazuelos.katamoneymanager.data.DataModel;


/**
 * Base class for activities that requires a Editor functionality.
 *
 * An activity should be an editor if has the next needs:
 *  - Has a user interface to edit attributes from a object from a class extended fromDataModel.
 *  - Has a button to save the edited attributes.
 *  - It requires to validate the input information from the interface before save the dataObject
 *
 * @param <T>
 */
public abstract class BaseEditorActivity<T extends DataModel> extends AppCompatActivity {

    public static final String KEY_INTENT_OBJECT = "KeyForOb";

    T dataObject = null;
    boolean isEditingObject = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);
        dataObject = (T)getIntent().getSerializableExtra(KEY_INTENT_OBJECT);
        if(dataObject==null){
            isEditingObject = false;
        }
    }


    /**
     * Indicates if the dataObject is a new Object or is a object
     * got from  getIntent().getSerializableExtra using the KEY_INTENT_OBJECT
     * @return
     */
    protected boolean isEditingObject(){
        return  isEditingObject;
    }


    /**
     * Gets the dataObject that is editing.
     * @see DataModel
     * @return Gets the dataObject that is editing
     */
    protected T getDataObject(){
        return dataObject;
    }

    /**
     * Calls updateDataObjectFromUI to update the dataObject
     * next calls to isValidObject to determine if the dataObject
     * should be saved to the data base.
     * This function should be called from a button
     * @param view a view Object
     */
    public void onSaveDataObject(View view){

        updateDataObjectFromUI();

        if(isValidObject()){
            MainController mainController = new MainController(this);
            mainController.saveDataModelObject(dataObject);
            finish();
        }
    }

    /**
     * Determines if the dataObject is a valid object.
     * This method should be implemented
     * @return true when the dataObject is valid, else false
     */
    protected abstract boolean isValidObject();


    /**
     * Update the dataObject attributes from the user interface.
     * This method should be implemented
     * @return
     */
    protected abstract void updateDataObjectFromUI();
}
