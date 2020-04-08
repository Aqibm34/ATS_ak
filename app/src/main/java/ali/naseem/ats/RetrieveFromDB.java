package ali.naseem.ats;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RetrieveFromDB extends AppCompatActivity {

    private RecyclerView mrecyclerView;
    private DatabaseReference databaseReference;

//My commits
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_data);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Ats");
        databaseReference.keepSynced(true);
        mrecyclerView = findViewById(R.id.recyclerView);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(RetrieveFromDB.this));

        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrieveFromDB.this.finish();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<data, dataViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<data, dataViewHolder>
                (data.class, R.layout.row_recyclerview, dataViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(dataViewHolder datView, data data, int position) {
                datView.setNRC(data.getNrc());
                datView.setRegno(data.getNumber());
                datView.setEmail(data.getEmail());
                datView.setChalan(data.getChalan());
            }
        };
        mrecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class dataViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public dataViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setNRC(String nrc) {

            TextView nrcTV = mView.findViewById(R.id.nrc_TV);
            nrcTV.setText(nrc);
        }

        public void setRegno(String regno) {
            TextView reg_TV = mView.findViewById(R.id.regnumber_TV);
            reg_TV.setText(regno);
        }

        public void setEmail(String email) {
            TextView email_TV = mView.findViewById(R.id.email_TV);
            email_TV.setText(email);
        }

        public void setChalan(String chalan) {
            TextView chalan_TV = mView.findViewById(R.id.chalan_TV);
            chalan_TV.setText(chalan);

        }
    }
}
