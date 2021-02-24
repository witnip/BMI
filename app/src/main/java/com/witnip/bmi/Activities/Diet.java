package com.witnip.bmi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.witnip.bmi.R;

public class Diet extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView txtEarlyMorning,txtBreakfast,txtBrunch,txtLunch,txtSnack,txtDinner,txtBedTime;
    Spinner spDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtEarlyMorning = findViewById(R.id.txtEarlyMorning);
        txtBreakfast = findViewById(R.id.txtBreakfast);
        txtBrunch = findViewById(R.id.txtBrunch);
        txtLunch = findViewById(R.id.txtLunch);
        txtSnack = findViewById(R.id.txtSnack);
        txtDinner = findViewById(R.id.txtDinner);
        txtBedTime = findViewById(R.id.txtBedTime);

        spDays = findViewById(R.id.spDays);

        String days [] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,days);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDays.setAdapter(arrayAdapter);
        spDays.setOnItemSelectedListener(this);

    }


    private void setDiet(String day) {
        String sundayDiet[] = {
<<<<<<< HEAD
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 1968fbd3f3da5a0ab172fd85d10cb29d0bec2990
=======
>>>>>>> origin/main
                "► METHIDANA (5g)WITH\n► LUKEWARM (150ml) WATER + APPLE",
                "► 2 BOILED EGGWHITE\n► TONED MILK (200ml)\n",
                "► DIET NAMKEEN (1bowl) +\n► GREEN TEA (1 cup) + FRUIT",
                "► SEASONAL CURRY (1 bowl) +\n► CHAPATI (2) + BOILED RICE(1 bowl)\n► BOONDI RAITA (1 bowl)\n► SALAD (1 plate)\n",
                "► BHELPOORI (1 katori)\n► LASSI\n",
                "► VEG BIRYANI (LESS OIL) (1 plate) + CURD (1bowl)\n► SALAD (1 plate)\n► SUGAR-FREE PUDDING (1 bowl)",
                "► TONED MILK (150 ml)\n► HIGH FIBRE BISCUITS (2)"
        };

        String mondayDiet[] = {
                "► LAX SEED (5g)WITH\n► LUKE(150ml)WARM WATER + APPLE (1)",
                "► VEGETABLE OATS(60g)\n► TONED MILK (200ml)\n",
                "► APPLEJUICEFRESH (150ml)\n► 2 WALNUTS + FRUIT",
                "► DAL (1 bowl) + CHAPATI (2)\n► SEASONAL VEGETABLE(1 bowl)\n► RICE(1 BOWL)\n► SALAD (1 PLATE)\n► CURD (1 BOWL)",
                "► GREEN TEA ( 1 cup)\n► SPROUTS(40 g)\n► BISCUITS\n",
                "► VEGETABLE + CHAPATI(2)\n► SALAD (1 plate)",
                "► TONED MILK (150 ml)\n► HIGH FIBRE BISCUITS (2)"
        };

        String tuesdayDiet[] = {
                "► DALCHINI (5g)WITH\n► LUKEWARM WATER (150ml)\n► KIWI (1)",
                "► VEGETABLE OATS(60g)\n► TONED MILK (200ml)\n",
                "► BUTTERMILK (250)\n► 2 ALMONDS + FRUIT\n",
                "► KADHI (1 BOWL) + CHAPATI (2)\n► RICE(1 bowl)\n► SALAD (1 PLATE)\n► VEGETABLE RAITA (1 BOWL)",
                "► FRIED IDLI (40g)\n► LEMON WATER",
                "► MATAR PANEER (1 bowl)\n► CHAPATI (2)\n► SALAD (1 plate) + RICE",
                "► TONED MILK (150 ml)\n► HIGH FIBRE BISCUITS (2)"
        };

        String wednesdayDiet[] = {
                "► METHIDANA (5g)WITH\n► LUKEWARM (150ml) WATER + APPLE",
                "► VEGETABLE POHA (60G) + TONED MILK (200ml)",
                "► FRUIT CHAAT(1 bowl) + 2 WALNUTS",
                "► EGG CURRY (1 bowl) + CHAPATI (2)\n► BOILED RICE(1 bowl)\n► SALAD (1 plate)\n► CURD (1 bowl)\n",
                "► JUICE(200 ml)\n► BHUNA CHANNA (40 g)",
                "► SEASONAL VEGETABLE (1 bowl)\n► CHAPATI (2)\nSALAD (1 plate)\n► SUGARFREE RICE KHEER (1 bowl)",
                "► TONED MILK (150 ml)\n► HIGH FIBRE BISCUITS (2)"
        };

        String thursdayDiet[] = {
                "► AMLA (5g) + LEMON\n► DROPS WITH LUKE WARM WATER (150ml)\n► KIWI (1)\n",
                "► VEGETABLE UPMA (60g)\n► TONED MILK (200ml)",
                "► LEMON WATER (200ml)\n► 4 ALMONDS + FRUIT\n",
                "► DAL (1 bowl) + CHAPATI (2)\n► SEASONAL VEGETABLE(1 bowl)\n► SALAD (1 plate) + CURD (1 bowl)",
                "► DIET NAMKEEN (60 g) \n► GREEN TEA (1 cup) \n► BISCUIT\n",
                "► NUTRELLA (1 bowl)\n► CHAPATI (2) + SALAD (1 plate) + CURD (1 bowl)",
                "► TONED MILK (150 ml)\n► HIGH FIBRE BISCUITS (2)"
        };

        String fridayDiet[] = {
                "► ALOEVERA JUICE(10ml) WITH LUKE WARM WATER (150ml)\n► DRIED APRICOT (2)",
                "► VEGETABLE\nSANDWICH/BESAN CHEELA (60g) \n► TONED MILK (200ml)\n",
                "► VEGETABLESOUP (1cup)\n► 2 WALNUTS + FRUIT\n",
                "► CHICKEN CURRY LESS AMOUNT\nCHAPATI (2)\n► RICE + SALAD + CURD",
                "► SWEET CORN (50 g) + MILK SHAKE(100 ml)\n",
                "► SEASONAL VEGETABLE (1 bowl) + CHAPATI (2)\n► SALAD (1 plate)",
                "► TONED MILK (150 ml)\n► HIGH FIBRE BISCUITS (2)"
        };

        String saturdayDiet[] = {
                "► LEMON + HONEY DROP WITH LUKEWARM WATER (150ml)\n► APPLE (1)",
                "► VEGETABLE\nVERMICELLI (60g) + TONED MILK (200ml)",
                "► SPROUTS CHAAT(1 bowl) +\n► 4 ALMONDS + FRUIT",
                "► DAL (1 bowl) + CHAPATI (2)\n► SEASONAL VEGETABLE(1 bowl)\n► SALAD (1 plate) + CURD (1 bowl)",
                "► FRUIT CUSTARD (1 bowl) + 2BOILED EGG",
                "► DOSA/IDLI (2/4)\n► SAMBHAR (1 bowl) +COCONUT CHUTNEY (20g)\n",
                "► TONED MILK (150 ml)\n► HIGH FIBRE BISCUITS (2)"
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
                "METHIDANA (5g)WITH\nLUKEWARM (150ml) WATER + APPLE",
                "2 BOILED\nEGGWHITE +\nTONED MILK\n(200ml)\n",
                "DIET NAMKEEN (1bowl) +\nGREEN TEA (1 cup) + FRUIT",
                "SEASONAL CURRY (1 bowl) +\nCHAPATI (2) + BOILED RICE(1 bowl) + BOONDI RAITA (1 bowl)\nSALAD (1 plate)\n",
                "BHELPOORI (1 katori)\nLASSI\n",
                "VEG BIRYANI (LESS OIL)\n(1 plate) + CURD (1bowl)\nSALAD (1 plate)\nSUGAR-FREE PUDDING (1 bowl)",
                "TONED MILK (150 ml)\nHIGH FIBRE BISCUITS (2)"
        };

        String mondayDiet[] = {
                "FLAX SEED (5g)WITH\nLUKE(150ml)WARM WATER + APPLE (1)",
                "VEGETABLE OATS(60g)\nTONED MILK (200ml)\n",
                "APPLEJUICEFRESH (150ml)\n2 WALNUTS +\nFRUIT",
                "DAL (1 bowl) + CHAPATI (2) +\nSEASONAL VEGETABLE(1 bowl)\nRICE(1 BOWL) + SALAD (1 PLATE)\nCURD (1 BOWL)",
                "GREEN TEA ( 1 cup)\nSPROUTS(40 g)\nBISCUITS\n",
                "VEGETABLE + CHAPATI(2)\nSALAD (1 plate)",
                "TONED MILK (150 ml)\nHIGH FIBRE BISCUITS (2)"
        };

        String tuesdayDiet[] = {
                "DALCHINI (5g)WITH\nLUKEWARM WATER (150ml)\nKIWI (1)",
                "VEGETABLE DALIYA (40g)\nTONED MILK (200ml)",
                "BUTTERMILK (250) + 2\nALMONDS + FRUIT\n",
                "KADHI (1 BOWL) + CHAPATI (2)\nRICE(1 bowl)\nSALAD (1 PLATE)\nVEGETABLE RAITA (1 BOWL)",
                "FRIED IDLI (40g) +\nLEMON WATER",
                "MATAR PANEER (1 bowl)\nCHAPATI (2)\nSALAD (1 plate) + RICE",
                "TONED MILK (150 ml)\nHIGH FIBRE BISCUITS (2)"
        };

        String wednesdayDiet[] = {
                "METHIDANA (5g)WITH\nLUKEWARM (150ml) WATER + APPLE",
                "VEGETABLE POHA (60G) + TONED MILK (200ml)",
                "FRUIT CHAAT(1 bowl) + 2 WALNUTS",
                "EGG CURRY (1 bowl) + CHAPATI\n(2)\nBOILED RICE(1 bowl) +\nSALAD (1 plate)\nCURD (1 bowl)\n",
                "JUICE(200 ml)\nBHUNA CHANNA (40 g)",
                "SEASONAL VEGETABLE (1 bowl)\nCHAPATI (2)\nSALAD (1 plate)\nSUGARFREE RICE KHEER (1 bowl)",
                "TONED MILK (150 ml)\nHIGH FIBRE BISCUITS (2)"
        };

        String thursdayDiet[] = {
                "AMLA (5g) + LEMON\nDROPS WITH LUKE WARM WATER (150ml)\nKIWI (1)\n",
                "VEGETABLE UPMA\n(60g)\nTONED MILK (200ml)",
                "LEMON WATER (200ml) +\n4 ALMONDS + FRUIT\n",
                "DAL (1 bowl) + CHAPATI (2)\nSEASONAL VEGETABLE(1 bowl)\nSALAD (1 plate) + CURD (1 bowl)",
                "DIET NAMKEEN (60 g) \nGREEN TEA (1 cup) \nBISCUIT\n",
                "NUTRELLA (1 bowl)\nCHAPATI (2) + SALAD (1 plate) + CURD (1 bowl)",
                "TONED MILK (150 ml)\nHIGH FIBRE BISCUITS (2)"
        };

        String fridayDiet[] = {
                "ALOEVERA JUICE(10ml) WITH LUKE WARM WATER (150ml)\nDRIED APRICOT (2)",
                "VEGETABLE\nSANDWICH/BESAN CHEELA (60g) \nTONED MILK (200ml)\n",
                "VEGETABLESOUP (1cup)\n2 WALNUTS + FRUIT\n",
                "CHICKEN CURRY LESS AMOUNT\nCHAPATI (2)\nRICE + SALAD + CURD",
                "SWEET CORN (50 g) + MILK SHAKE(100 ml)\n",
                "SEASONAL VEGETABLE (1 bowl) + CHAPATI (2)\nSALAD (1 plate)",
                "TONED MILK (150 ml)\nHIGH FIBRE BISCUITS (2)"
        };

        String saturdayDiet[] = {
                "LEMON + HONEY DROP WITH LUKEWARM WATER (150ml)\nAPPLE (1)",
                "VEGETABLE\nVERMICELLI (60g) + TONED MILK (200ml)",
                "SPROUTS CHAAT(1 bowl) +\n4 ALMONDS + FRUIT",
                "DAL (1 bowl) + CHAPATI (2)\nSEASONAL VEGETABLE(1 bowl)\nSALAD (1 plate) + CURD (1 bowl)",
                "FRUIT CUSTARD (1 bowl) + 2BOILED EGG",
                "DOSA/IDLI (2/4)\nSAMBHAR (1 bowl) +COCONUT CHUTNEY (20g)\n",
                "TONED MILK (150 ml)\nHIGH FIBRE BISCUITS (2)"
>>>>>>> cf0e5dc29b293050a03c4c0e8c8ea976a83eb35f
>>>>>>> 1968fbd3f3da5a0ab172fd85d10cb29d0bec2990
=======
>>>>>>> origin/main
        };

        switch (day){
            case "Sunday" :
            {
                setDietValue(sundayDiet);
            }
            break;
            case "Monday" :
            {
                setDietValue(mondayDiet);
            }
            break;
            case "Tuesday" :
            {
                setDietValue(tuesdayDiet);
            }
            break;
            case "Wednesday" :
            {
                setDietValue(wednesdayDiet);
            }
            break;
            case "Thursday" :
            {
                setDietValue(thursdayDiet);
            }
            break;
            case "Friday" :
            {
                setDietValue(fridayDiet);
            }
            break;
            case "Saturday" :
            {
                setDietValue(saturdayDiet);
            }
            break;
            default:{
                Toast.makeText(this, "No Day is selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setDietValue(String[] Diet) {
        txtEarlyMorning.setText(Diet[0]);
        txtBreakfast.setText(Diet[1]);
        txtBrunch.setText(Diet[2]);
        txtLunch.setText(Diet[3]);
        txtSnack.setText(Diet[4]);
        txtDinner.setText(Diet[5]);
        txtBedTime.setText(Diet[6]);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String day = parent.getItemAtPosition(position).toString().trim();
        setDiet(day);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "No Day is selected", Toast.LENGTH_SHORT).show();
    }
}