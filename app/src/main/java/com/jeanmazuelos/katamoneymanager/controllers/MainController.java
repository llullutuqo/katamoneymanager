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

package com.jeanmazuelos.katamoneymanager.controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jeanmazuelos.katamoneymanager.data.DataModel;
import com.jeanmazuelos.katamoneymanager.data.KMMDataBaseHelper;
import com.jeanmazuelos.katamoneymanager.models.Account;
import com.jeanmazuelos.katamoneymanager.models.AccountingEntry;
import com.jeanmazuelos.katamoneymanager.models.Category;
import com.jeanmazuelos.katamoneymanager.utils.TransformUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Controller class that centralices all functions to save
 * DataObjects to the SQLIte database.
 *
 * @see KMMDataBaseHelper
 */
public class MainController {
    KMMDataBaseHelper dataManager;

    public MainController(Context context){
        dataManager = new KMMDataBaseHelper(context);
    }

    private void saveCategory(Category data){
        SQLiteDatabase db =dataManager.getWritableDatabase();
        if(data.getId()==null)
            db.insert(Category.TABLE_NAME, null,data.getContent() );
        else
            db.update(Category.TABLE_NAME,
                    data.getContent(),
                    " id= ? ",new String[]{data.getId().toString()} );

    }

    private void saveAccount(Account data) {
        SQLiteDatabase db =dataManager.getWritableDatabase();
        if(data.getId()==null)
            db.insert(Account.TABLE_NAME, null,data.getContent() );
        else
            db.update(Account.TABLE_NAME,
                    data.getContent(),
                    " id= ? ",new String[]{data.getId().toString()} );
    }

    private void saveAccountEntry(AccountingEntry data){
        SQLiteDatabase db =dataManager.getWritableDatabase();
        if(data.getId()==null)
            db.insert(AccountingEntry.TABLE_NAME, null,data.getContent() );
        else
            db.update(AccountingEntry.TABLE_NAME,
                    data.getContent(),
                    " id= ? ",new String[]{data.getId().toString()} );
    }

    private void deleteCategory(Category data){
        SQLiteDatabase db =dataManager.getWritableDatabase();
        data.setActive("0");
        db.update(Category.TABLE_NAME, data.getContent(), " id= ? ",new String[]{data.getId().toString()} );

    }

    private void deleteAccount(Account data) {

    }

    private void deleteAccountEntry(AccountingEntry data){
    }

    public List<Category> getAllCategories(){
        Cursor c = dataManager.getReadableDatabase()
                .query(Category.TABLE_NAME,
                        null,
                        DataModel.FIELD_ACTIVE + "=? ",
                        new String[]{DataModel.ACTIVE},
                        null,
                        null,
                        null);
        List<Category> categories = new ArrayList<Category>();

        while (c.moveToNext()){
            Category cl = new Category(c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4));
            categories.add(cl);
        }
        return categories;
    }

    public List<Account> getAllAccounts(){
        Cursor c = dataManager.getReadableDatabase()
                .query(
                        Account.TABLE_NAME,
                        null,
                        DataModel.FIELD_ACTIVE + "=? ",
                        new String[]{DataModel.ACTIVE},
                        null,
                        null,
                        null);
        List<Account> accounts = new ArrayList<Account>();

        while (c.moveToNext()){
            Account cl = new Account(c.getInt(0),
                    c.getString(1),
                    c.getDouble(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5));
            accounts.add(cl);
        }
        return accounts;
    }
    public List<AccountingEntry> getAllAccountEntryByAccountId(Integer idAccount){

        List<AccountingEntry> accountEntries = new ArrayList<AccountingEntry>();
        String where=null;
        String[] elements = null;
        if(idAccount!= null){
            where =DataModel.FIELD_ACTIVE + "=? AND " +  AccountingEntry.FIELD_ACCOUNT_ID + "=?";
            elements = new String[]{DataModel.ACTIVE, String.valueOf(idAccount)};
        } else {
            where =DataModel.FIELD_ACTIVE + "=? ";
            elements = new String[]{DataModel.ACTIVE};
        }

        Cursor c = dataManager.getReadableDatabase()
                .query(
                        AccountingEntry.TABLE_NAME,
                        null,
                        where,
                        elements,
                        null,
                        null,
                        null);

        while (c.moveToNext()){
            AccountingEntry cl = new AccountingEntry(c.getInt(0),
                    c.getDouble(1),
                    c.getLong(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getInt(7),
                    c.getInt(8));
            accountEntries.add(cl);
        }
        return accountEntries;
    }

    public List<String> getAllAccountingByDate(){
        List<String> elements = new ArrayList<String>();
        Cursor c = dataManager.getReadableDatabase()
                .rawQuery("SELECT fecha, tipo, sum(monto) from ASIENTO where activo = ? group by 1, 2 ORDER BY fecha, tipo",
                        new String[]{DataModel.ACTIVE});
        while (c.moveToNext()){
            Date date = new Date(c.getLong(0));
            String type = c.getString(1);
            Double amount = c.getDouble(2);
            String item =String.format(" %s %s %4.2f", TransformUtils.dateToString(date), type, amount);
            elements.add(item);
        }
        return elements;
    }


    /**
     * Gets the total sum of all {@link AccountingEntry} active by Type
     * @param type Indicates the type of {@link AccountingEntry} to sum
     * @return
     */
    public Double getTotalByType(String type){
        Double total = null;
        Cursor c = dataManager.getReadableDatabase()
                .rawQuery("SELECT  sum(monto) from ASIENTO WHERE tipo = ? and activo = ?",
                        new String[]{type, DataModel.ACTIVE });
        while (c.moveToNext()){
            total = c.getDouble(0);
        }
        if(total==null)
            total = 0.00;
        return total;
    }

    public Double getTotal(){
        Double tI = getTotalByType("Ingreso");
        Double tE = getTotalByType("Egreso");
        return  tI - tE;
    }

    public List<AccountingEntry> getAllAccountingEntry(){
        return getAllAccountEntryByAccountId(null);
    }

    public void saveDataModelObject(DataModel data){
        if(data instanceof Category)
            saveCategory((Category) data);
        else if (data instanceof Account)
            saveAccount((Account)data);
        else if(data instanceof AccountingEntry)
            saveAccountEntry((AccountingEntry)data);
    }

    /**
     *
     * @param dataList
     */
    public  void deleteDataModelObjects(List< ? extends DataModel> dataList){
        for (DataModel obj:dataList ) {
            String tableName= null;
            if(obj instanceof Category)
                tableName = Category.TABLE_NAME;
            else if (obj instanceof Account)
                tableName = Account.TABLE_NAME;
            else if(obj instanceof AccountingEntry)
                tableName = AccountingEntry.TABLE_NAME;

            if(tableName != null){
                SQLiteDatabase db =dataManager.getWritableDatabase();
                obj.setActive(DataModel.INACTIVE);
                db.update(tableName,
                        obj.getActiveContent(),
                        " id= ? ",new String[]{obj.getId().toString()} );
            }
        }
    }

}
