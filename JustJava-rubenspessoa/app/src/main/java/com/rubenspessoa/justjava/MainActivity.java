package com.rubenspessoa.justjava;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameText = (EditText) findViewById(R.id.text_name);
        String nameCustomer = nameText.getText().toString();

        CheckBox wcCb = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        boolean wcChecked = wcCb.isChecked();

        CheckBox cCb = (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean cChecked = cCb.isChecked();

        String priceMessage = generateOrderSummary(nameCustomer, count, wcChecked, cChecked);
        displayMessage(priceMessage);
    }

    /**
     * Increase by 1 the count of Coffees.
     * @param view
     */

    public void increaseCount(View view) {
        count++;
        display(count);
    }

    /**
     * Decrease by 1 the count of Coffees.
     * @param view
     */

    public void decreaseCount(View view) {
        if (count > 0) {
            count--;
        }
        display(count);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_view);
        priceTextView.setText(message);
    }

    private String generateOrderSummary(String name, int quantity, boolean whippedCreamChecked, boolean chocolateChecked) {
        int price = count * 5;

        if (whippedCreamChecked) {
            price += count * 1;
        }

        if (chocolateChecked) {
            price += count * 2;
        }

        String priceMessage = "Name: "+ name + "\n" +
                "Add whipped cream? "+ whippedCreamChecked + "\n" +
                "Chocolate? "+ chocolateChecked + "\n" +
                "Quantity: " + quantity + "\n" +
                "Total: $ " + price + "\n" +
                "Thank you!";

        return priceMessage;
    }
}