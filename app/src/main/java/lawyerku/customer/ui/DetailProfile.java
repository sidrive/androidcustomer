package lawyerku.customer.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.MainActivity;
import lawyerku.customer.R;

public class DetailProfile extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btnSimpanProfile)
    public void saveProfile(){
        Toast.makeText(this, "Profile Berhasil Disimpan", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(DetailProfile.this, MainActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.lnSaldo)
    public void showSaldo(){
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.redeem_saldo, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.txtReedemSaldo);
//        userInput.setText(String.valueOf(barang.getStokBarang()));
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Reedem",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

//                                barang.setStokBarang(Integer.valueOf(userInput.getText().toString()));
//                                barang.setUpdateTerakhir(System.currentTimeMillis());
//                                showLoading(true,holder.viewProgress);
//                                updateStok(barang,holder);
                                Toast.makeText(DetailProfile.this, "Saldo Berhasil Direedem, silahkan tunggu konfirmasi admin", Toast.LENGTH_LONG).show();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
