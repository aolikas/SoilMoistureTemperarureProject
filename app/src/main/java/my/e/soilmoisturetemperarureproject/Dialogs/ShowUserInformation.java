package my.e.soilmoisturetemperarureproject.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import my.e.soilmoisturetemperarureproject.Model.UserData;
import my.e.soilmoisturetemperarureproject.R;

public class ShowUserInformation extends AppCompatDialogFragment {

    private TextView txtUserName, txtUserEmail, txtUserId;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        String userId = user.getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(userId);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    UserData userData = snapshot.getValue(UserData.class) ;
                   // String name = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                    //String email = Objects.requireNonNull(snapshot.child("userEmail").getValue()).toString();
                    assert userData != null;
                    txtUserName.setText(userData.getUserName());
                    txtUserEmail.setText(userData.getUserEmail());
                    txtUserId.setText(userId);
                } else {
                    Toast.makeText(getActivity(), "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_show_user, null);

        txtUserName = view.findViewById(R.id.dialog_show_user_name);
        txtUserEmail = view.findViewById(R.id.dialog_show_user_email);
        txtUserId = view.findViewById(R.id.dialog_show_user_id);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });


        return builder.create();
    }
}
