package com.example.bazar;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.CreditCardViewHolder> {

    ArrayList<String> credit_card_number;
    ArrayList<String> credit_card_date;
    TextView info_display;
    //Constructor
    public CreditCardAdapter(ArrayList<String> credit_card_number,ArrayList<String> credit_card_date, TextView card_update) {
    this.credit_card_date = credit_card_date;
    this.credit_card_number = credit_card_number;
    this.info_display = card_update;
    }

    @NonNull
    @Override
    public CreditCardAdapter.CreditCardViewHolder  onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.payment_adapter,null);
        return new CreditCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditCardViewHolder creditCardViewHolder, final int i) {
        creditCardViewHolder.bind(credit_card_number.get(i),credit_card_date.get(i));
    }

    @Override
    public int getItemCount() {
        return credit_card_date.size();
    }

    class CreditCardViewHolder extends RecyclerView.ViewHolder{
        public TextView card_number_display, expiry_display;
        public CardView cardView;


        public CreditCardViewHolder(@NonNull View itemView) {
            super(itemView);
            card_number_display = (TextView) itemView.findViewById(R.id.card_number_display);

            expiry_display = (TextView) itemView.findViewById(R.id.expiry_date_display);
            cardView = (CardView) itemView.findViewById(R.id.payment_item);
            cardView.setBackgroundColor(0xE6E6E6);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        info_display.setText(card_number_display.getText().toString());

                }
            });

        }

        public void bind(final String card_number, final String expiry_date){
            card_number_display.setText(card_number);
            expiry_display.setText(expiry_date);
        }
    }
}
