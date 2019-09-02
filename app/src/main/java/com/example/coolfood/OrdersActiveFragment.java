package com.example.coolfood;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.coolfood.adapter.OrderActiveViewHolder;
import com.example.coolfood.model.Offer;
import com.example.coolfood.model.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersActiveFragment extends Fragment {

    private RecyclerView recyclerView;

    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceOffer;
    FirebaseRecyclerOptions<Order> options;
    FirebaseRecyclerAdapter<Order, OrderActiveViewHolder> adapter;
    private FirebaseAuth firebaseAuth;
    private int quantity;

    public OrdersActiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_orders_active, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("OrderActive");
        databaseReferenceOffer = FirebaseDatabase.getInstance().getReference().child("Offer");
        final Query query = databaseReference.orderByChild("user").equalTo(user.getEmail());
        options = new FirebaseRecyclerOptions.Builder<Order>().setQuery(query, Order.class).build();

        adapter = new FirebaseRecyclerAdapter<Order, OrderActiveViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderActiveViewHolder holder, int position, @NonNull final Order model) {
                holder.storeNameAO.setText(model.getStoreName());

                final int mid = model.getPickupFrom().toString().length() / 2; //get the middle of the String
                String[] parts = {model.getPickupFrom().toString().substring(0, mid), model.getPickupFrom().toString().substring(mid)};
                String[] parts1 = {model.getGetPickupUntil().toString().substring(0, mid), model.getGetPickupUntil().toString().substring(mid)};
                holder.pickupTimeAO.setText(parts[0] + "." + parts[1] + " - " + parts1[0] +"."+parts1[1]);

                holder.cancelTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        databaseReferenceOffer.child(model.getOfferId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Offer offer = dataSnapshot.getValue(Offer.class);
                                quantity = Integer.parseInt(offer.getQuantity()) + Integer.parseInt(model.getQuantity());
                                databaseReferenceOffer.child(model.getOfferId()).child("quantity").setValue(Integer.toString(quantity));
                                databaseReference.child(model.getOrderId()).removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });

                holder.qrCodeIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showImage(model.getOrderId());
                    }
                });
            }

            public void showImage(String orderId) {
                Dialog builder = new Dialog(v.getContext());
                builder.setCanceledOnTouchOutside(true);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                    }
                });

                ImageView imageView = new ImageView(v.getContext());
                Bitmap qrcode = generateQRcode(orderId);
                imageView.setImageBitmap(qrcode);
                builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                builder.show();
            }

            @NonNull
            @Override
            public OrderActiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.active_order_layout, viewGroup, false);
                return new OrderActiveViewHolder(view);
            }
        };

        recyclerView = v.findViewById(R.id.activeOrdersRecyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.startListening();
        recyclerView.setAdapter(adapter);

        return v;
    }

    public Bitmap generateQRcode(String text) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Bitmap bitmap = null;
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
