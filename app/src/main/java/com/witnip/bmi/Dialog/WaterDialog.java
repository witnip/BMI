package com.witnip.bmi.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.witnip.bmi.R;

public class WaterDialog extends AppCompatDialogFragment {
    private EditText txtWaterIntake;
    private Button btnCancel,btnDone;

    private WaterDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.water_dialog,null);

        txtWaterIntake = view.findViewById(R.id.txtWaterIntake);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnDone = view.findViewById(R.id.btnDone);

        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double waterIntake = Double.parseDouble(txtWaterIntake.getText().toString());
                listener.setWaterIntake(waterIntake);
                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (WaterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement WaterDialogListener");
        }
    }

    public interface WaterDialogListener{
        void setWaterIntake(Double waterIntake);
    }
}
