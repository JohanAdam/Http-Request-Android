package com.nyan.johan.json;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by johan on 21/6/2017.
 */

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ContactAdapter extends
        RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private ArrayList<Contacts> mContacts;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public ContactAdapter(Context context, ArrayList<Contacts> contacts) {
        mContacts = contacts;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    //LAYOUT YG DIGUNAKAN "list_item"
    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    //BIND data TO list_item view
    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Contacts contact = mContacts.get(position);

        // Set item views based on your views and data model
        TextView name_tv = viewHolder.nameTextView;
        name_tv.setText(contact.getName());
        TextView email_tv = viewHolder.emailTv;
        email_tv.setText(contact.getEmail());
        TextView gender_tv = viewHolder.genderTv;
        gender_tv.setText(contact.getGender());
    }

    @Override
    public int getItemCount() {
        if (mContacts != null) {
            return mContacts.size();
        }
        return 0;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView,emailTv,genderTv;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            emailTv = (TextView) itemView.findViewById(R.id.contact_email);
            genderTv = (TextView) itemView.findViewById(R.id.contact_gender);

            itemView.setOnClickListener(this);

        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Contacts contacts = mContacts.get(position);
                // We can access the data within the views
                Toast.makeText(mContext, " mah name is " + contacts.getName() + "\n this is mah " +
                        "email " + contacts.getEmail() + "\n and this is mah address " + contacts
                        .getAddress(), Toast
                        .LENGTH_SHORT).show();
            }
        }
    }
}