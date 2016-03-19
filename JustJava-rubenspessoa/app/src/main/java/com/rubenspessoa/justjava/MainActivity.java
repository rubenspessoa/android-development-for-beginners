package com.rubenspessoa.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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

        String message = generateOrderSummary(nameCustomer, count, wcChecked, cChecked);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + nameCustomer);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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
    //private void displayPrice(int number) {
    //    TextView priceTextView = (TextView) findViewById(R.id.price_view);
    //    priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    //}

    /**
     * This method displays the given text on the screen.
     */
    //private void displayMessage(String message) {
    //    TextView priceTextView = (TextView) findViewById(R.id.price_view);
    //    priceTextView.setText(message);
    //}

    /**
     * This method calculates the price for the order.
     * @param quantity
     * @param whippedCream
     * @param chocolate
     * @return
     */

    private int calculatePrice(int quantity, boolean whippedCream, boolean chocolate) {
        int basePrice = 5;

        if (whippedCream) {
           basePrice++;
        }

        if (chocolate) {
            basePrice += 2;
        }

        return basePrice * quantity;
    }

    /**
     * This method generates a summary for the order.
     * @param name
     * @param quantity
     * @param whippedCreamChecked
     * @param chocolateChecked
     * @return
     */

    private String generateOrderSummary(String name, int quantity, boolean whippedCreamChecked, boolean chocolateChecked) {
        int price = calculatePrice(quantity, whippedCreamChecked, chocolateChecked);

        String priceMessage = "Name: "+ name + "\n" +
                "Add whipped cream? "+ whippedCreamChecked + "\n" +
                "Chocolate? "+ chocolateChecked + "\n" +
                "Quantity: " + quantity + "\n" +
                "Total: $ " + price + "\n" +
                "Thank you!";

        return priceMessage;
    }
}