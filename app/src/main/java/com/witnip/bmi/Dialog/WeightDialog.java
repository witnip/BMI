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

public class WeightDialog extends AppCompatDialogFragment {
    private EditText txtWeightTracker;
    private Button btnCancel,btnDone;

    private WeightDialog.WeightTrackerDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.weight_tracker_dialog,null);

        txtWeightTracker = view.findViewById(R.id.txtWeightTracker);
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
                Double weight = Double.parseDouble(txtWeightTracker.getText().toString());
                listener.setWeight(weight);
                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (WeightDialog.WeightTrackerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement WeightTrackerDialogListener");
        }
    }

    public interface WeightTrackerDialogListener{
        void setWeight(Double weight);
    }
}
