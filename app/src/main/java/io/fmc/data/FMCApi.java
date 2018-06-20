package io.fmc.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.fmc.data.models.Announcement;
import io.fmc.data.models.User;
import io.fmc.di.AppController;
import io.fmc.ui.posts.PostModel;
import io.fmc.ui.users.UserModel;

/**
 * Created by sundayakinsete on 17/05/2018.
 */

public class FMCApi {


    public FMCApi() {
        AppController.getAppComponent().inject(this);
    }


    public static void authenticateUserWithEmailAndPassword(final User user, final UserModel.OnLoginListener onLoginListener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    onLoginListener.onComplete(user);
                }else{
                    onLoginListener.onError(task.getException().getMessage().toString());
                }
            }
        });
    }

    public static void createUserAccountWithEmailAndPassword(final User user, final UserModel.OnCreateAccountListener onCreateAccountListener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    onCreateAccountListener.onComplete(user);
                }else{
                    onCreateAccountListener.onError(task.getException().getMessage().toString());
                }
            }
        });
    }

    public static void recoverUserPassword(String email, final UserModel.OnPasswordRecoverListener onPasswordRecoverListener) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    onPasswordRecoverListener.onComplete("Password reset link has been sent to your email address.");
                }else{
                    onPasswordRecoverListener.onError(task.getException().toString());
                }
            }
        });
    }

    public static void listenToPostChanges(final PostModel.OnPostsFetched onPostsFetched) {
        FirebaseDatabase.getInstance().getReference("announcements").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("databaseError", String.valueOf(dataSnapshot));

                List<Announcement> announcements = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    announcements.add(snapshot.getValue(Announcement.class));
                }
                onPostsFetched.onPostItemsFetched(announcements);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("databaseError", String.valueOf(databaseError));
            }
        });
    }
}
