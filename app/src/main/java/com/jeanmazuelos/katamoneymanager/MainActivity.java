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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jeanmazuelos.katamoneymanager.controllers.MainController;
import com.jeanmazuelos.katamoneymanager.models.Account;
import com.jeanmazuelos.katamoneymanager.models.Category;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public  void onButtonClick(View v){
        Intent it = null;
        switch (v.getId()){
            case R.id.btCategory:
                it = new Intent(this, DetailsItemActivity.class );
                it.putExtra(DetailsItemActivity.KEY, Category.TABLE_NAME);
                break;
            case R.id.btReport:
                it = new Intent(this, ReportActivity.class);
                break;
            case R.id.btAccounts:
                it = new Intent(this, DetailsItemActivity.class );
                it.putExtra(DetailsItemActivity.KEY, Account.TABLE_NAME);
                break;
         }
        if(it!=null){
            startActivity(it);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_acerca_de:
                Intent it = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(it);
                break;
            case R.id.action_salir:
                finish();
                break;
        }
        return false;
    }
}
