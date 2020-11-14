package io.fmc.firebaseTest;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.fmc.data.models.AnnouncementPost;

public class FirebaseTest {
    @Test
    public void debugTimestamp() {
        long timeStamp = 1592020956691L;
        String r = getTimeDate(timeStamp);
        System.out.println(r);

        Instant instant = Instant.ofEpochSecond(timeStamp);
        Date date = Date.from(instant);
        SimpleDateFormat sfd = new SimpleDateFormat("E MMM d yyyy", Locale.getDefault());

        System.out.println(sfd.format(date));

    }

    @Test
    public void setUp() throws Exception {

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("Church Events");

        mDatabase.getDatabase();

        FirebaseDatabase.getInstance().getReference("Church Events").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("databaseError", String.valueOf(dataSnapshot));
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);

                List<AnnouncementPost> announcementPosts = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("Firebase obj key ", Objects.requireNonNull(snapshot.getKey()));
                    announcementPosts.add(snapshot.getValue(AnnouncementPost.class));
                }
                System.out.println(announcementPosts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("databaseError", String.valueOf(databaseError));
            }
        });

    }

    public static String getTimeDate(long timestamp) {
        try {
            Date netDate = (new Date(timestamp));
            SimpleDateFormat sfd = new SimpleDateFormat("E MMM d yyyy", Locale.getDefault());
            return sfd.format(netDate);
        } catch (Exception e) {
            return "date error";
        }
    }


}
