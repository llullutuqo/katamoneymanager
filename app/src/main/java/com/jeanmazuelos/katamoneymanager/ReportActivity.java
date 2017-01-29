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

import android.opengl.ETC1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeanmazuelos.katamoneymanager.controllers.MainController;

import java.util.List;

public class ReportActivity extends AppCompatActivity {

    ListView lvList;
    TextView tvTotalIngres, tvTotalEgress,tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initControls();
    }

    /**
     * Initialises all the user interface controls
     */
    private void initControls(){
        lvList = (ListView)findViewById(R.id.lvList);
        tvTotalIngres = (TextView)findViewById(R.id.tvTotalIngres);
        tvTotalEgress = (TextView)findViewById(R.id.tvTotalEgress);
        tvTotal = (TextView)findViewById(R.id.tvTotal);
    }

    @Override
    public void onResume(){
        super.onResume();
        loadDetails();
    }

    private void loadDetails(){
        MainController mn = new MainController(this);
        List<String> items = mn.getAllAccountingByDate();
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                items);
        lvList.setAdapter(adapters);
        Double eI = mn.getTotalByType("Ingreso");
        Double eT = mn.getTotalByType("Egreso");
        Double total = eI - eT;

        tvTotalIngres.setText(String.valueOf(eI));
        tvTotalEgress.setText(String.valueOf(eT));
        tvTotal.setText(String.valueOf(total));
    }
}
