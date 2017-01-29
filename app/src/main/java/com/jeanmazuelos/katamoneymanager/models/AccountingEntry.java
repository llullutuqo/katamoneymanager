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

package com.jeanmazuelos.katamoneymanager.models;

import android.content.ContentValues;
import android.content.Intent;

import com.jeanmazuelos.katamoneymanager.data.DataModel;
import com.jeanmazuelos.katamoneymanager.utils.TransformUtils;

import java.util.Date;

/**
 * Created by Admin on 18/01/2017.
 */
public class AccountingEntry extends DataModel {

    public static final  String TABLE_NAME = "ASIENTO";
    public static final  String FIELD_DUE = "monto";
    public static final  String FIELD_DATE = "fecha";
    public static final  String FIELD_TITLE = "titulo";
    public static final  String FIELD_DESCRIPTION = "descripcion";
    public static final  String FIELD_TYPE = "tipo";
    public static final  String FIELD_ACCOUNT_ID = "CUENTA_id";
    public static final  String FIELD_CATEGORY_ID = "CATEGORIA_id";

    public static final String FORMAT_STRING_ENTRY = "%s %s  -> %4.2f";

    Category category;
    Account account;
    Double due;
    Date dateDue;
    String tittle;
    String type;
    String description;

    public AccountingEntry(){
        setActive(ACTIVE);
    }

    public Account getAccount() {
        return account;
    }

    public AccountingEntry(Integer id, Double due, Long date,
                           String tittle, String description, String type,
                           String active,Integer accountId, Integer categoryId){
        this.setId(id);
        this.setActive(active);
        this.setDateDue(new Date(date));
        this.tittle = tittle;
        this.description = description;
        this.type = type;
        this.due = due;
        Account ac = new Account();
        ac.setId(accountId);
        Category cat = new Category();
        cat.setId(categoryId);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getDue() {
        return due;
    }

    public void setDue(Double due) {
        this.due = due;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public ContentValues getContent() {
        ContentValues cn = new ContentValues();

        if(getDue()!=null)
            cn.put(FIELD_DUE, getDue());
        if(getDateDue() != null)
            cn.put(FIELD_DATE, getDateDue().getTime());
        if(getTittle()!=null)
            cn.put(FIELD_TITLE, getTittle());
        if(getDescription()!=null)
            cn.put(FIELD_DESCRIPTION, getDescription());
        if(getType()!=null)
            cn.put(FIELD_TYPE,getType());
        if(getCategory()!=null)
            cn.put(FIELD_CATEGORY_ID, getCategory().getId());
        if(getAccount()!=null)
            cn.put(FIELD_ACCOUNT_ID, getAccount().getId());
        if(getActive() != null)
            cn.put(FIELD_ACTIVE, getActive());
        return cn;
    }

    @Override
    public String toString() {

        return String.format(FORMAT_STRING_ENTRY,TransformUtils.dateToString(dateDue), tittle, due);
    }
}
