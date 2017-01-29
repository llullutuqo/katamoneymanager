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

import com.jeanmazuelos.katamoneymanager.data.DataModel;

import java.util.List;

/**
 * Created by Admin on 18/01/2017.
 */
public class Account extends DataModel {
    public static final  String TABLE_NAME = "CUENTA";
    public static final  String FIELD_NAME = "nombre";
    public static final  String FIELD_AMOUNT = "monto_apertura";
    public static final  String FIELD_CURRENCY = "codigo_moneda";
    public static final  String FIELD_TYPE = "codigo_tipo";


    String name;
    Double amount;
    String currency;
    String type;


    public Account(){
        setActive(ACTIVE);
    }

    public Account(Integer idValue,
                   String nameValue,
                   Double amountValue,
                   String currencyValue,
                   String typeValue ,
                   String activeValue){

        name = nameValue;
        amount = amountValue;
        currency = currencyValue;
        type = typeValue;
        setActive(activeValue);
        setId(idValue);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    @Override
    public ContentValues getContent() {
        ContentValues cn = new ContentValues();
        if(name!= null)
            cn.put(FIELD_NAME,name);
        if(amount!=null)
            cn.put(FIELD_AMOUNT,amount);
        if(currency != null)
            cn.put(FIELD_CURRENCY, currency);
        if(type!=null)
            cn.put(FIELD_TYPE, type);
        if(getActive() != null)
            cn.put(FIELD_ACTIVE, getActive());
        return cn;
    }

    @Override
    public String toString() {
        return name;
    }
}
