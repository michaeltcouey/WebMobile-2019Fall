package com.example.vijaya.myorder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 10;
    final int PEPPERONI_PRICE = 2;
    final int SAUSAGE_PRICE = 2;
    final int HAM_PRICE = 2;
    final int MOZZARELLA_PRICE = 1;
    final int MUSHROOMS_PRICE = 1;
    final int BLACK_OLIVES_PRICE = 1;

    int quantity = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        // check if pepperoni is selected
        CheckBox pepperoni = (CheckBox) findViewById(R.id.pepperoni_checked);
        boolean hasPepperoni = pepperoni.isChecked();

        // check if sausage is selected
        CheckBox sausage = (CheckBox) findViewById(R.id.sausage_checked);
        boolean hasSausage = sausage.isChecked();

        // check if ham is selected
        CheckBox ham = (CheckBox) findViewById(R.id.ham_checked);
        boolean hasHam = ham.isChecked();

        // check if mozzarella is selected
        CheckBox mozzarella = (CheckBox) findViewById(R.id.mushrooms_checked);
        boolean hasMozzarella = mozzarella.isChecked();

        // check if mushrooms is selected
        CheckBox mushrooms = (CheckBox) findViewById(R.id.mushrooms_checked);
        boolean hasMushrooms = mushrooms.isChecked();

        // check if sausage is selected
        CheckBox blackOlives = (CheckBox) findViewById(R.id.black_olives_checked);
        boolean hasBlackOlives = blackOlives.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasPepperoni, hasSausage, hasHam, hasMozzarella, hasMushrooms, hasBlackOlives);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasPepperoni, hasSausage, hasHam, hasMozzarella, hasMushrooms, hasBlackOlives, totalPrice);

        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents

    }

    public void sendEmail(String name, String output) {
        // Write the relevant code for triggering email

        // Hint to accomplish the task
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }



    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    public String createOrderSummary(String userInputName, boolean hasPepparoni, boolean hasSausage, boolean hasHam, boolean hasMozzarella, boolean hasMushrooms, boolean hasBlackOlives, float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_pepperoni, boolToString(hasPepparoni)) + "\n" +
                getString(R.string.order_summary_sausage, boolToString(hasSausage)) + "\n" +
                getString(R.string.order_summary_ham, boolToString(hasHam)) + "\n" +
                getString(R.string.order_summary_mozzarella, boolToString(hasMozzarella)) + "\n" +
                getString(R.string.order_summary_mushrooms, boolToString(hasMushrooms)) + "\n" +
                getString(R.string.order_summary_black_olives, boolToString(hasBlackOlives)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + " Pizzas.\n" +
                getString(R.string.order_summary_total_price, price) + "\n" +
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasPepparoni, boolean hasSausage, boolean hasHam, boolean hasMozzarella, boolean hasMushrooms, boolean hasBlackOlives) {
        int basePrice = PIZZA_PRICE;
        if (hasPepparoni) {
            basePrice += PEPPERONI_PRICE;
        }
        if (hasSausage) {
            basePrice += SAUSAGE_PRICE;
        }
        if (hasHam) {
            basePrice += HAM_PRICE;
        }
        if (hasMozzarella) {
            basePrice += MOZZARELLA_PRICE;
        }
        if (hasMushrooms) {
            basePrice += MUSHROOMS_PRICE;
        }
        if (hasBlackOlives) {
            basePrice += BLACK_OLIVES_PRICE;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of Pizzas by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 30) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than thirty Pizzas. (That's a lot!!!)");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_many_pizzas);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select atleast one cup of coffee");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_pizzas);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
    public void reDirectToOrderSummary(View view) {
        Intent redirect = new Intent (MainActivity.this, OrderSummary.class);
        startActivity(redirect);

    }

}

