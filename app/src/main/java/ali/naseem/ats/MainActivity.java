package ali.naseem.ats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Chalan> chalans = new ArrayList<>();
    private ChalanAdapter adapter;
    private EditText registrationNumberEditText, nrcEditText, emailEditText;
    private View submitButton, loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_books);
        addChalans();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        submitButton = findViewById(R.id.submitButton);
        loader = findViewById(R.id.loader);
        registrationNumberEditText = findViewById(R.id.registrationNumberEditText);
        nrcEditText = findViewById(R.id.nrcEditText);
        emailEditText = findViewById(R.id.emailEditText);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ChalanAdapter(chalans);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSubmit();
            }
        });
    }

    private void handleSubmit() {
        String nrc = nrcEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String number = registrationNumberEditText.getText().toString().trim();
        if (TextUtils.isEmpty(nrc)) {
            nrcEditText.setError("Cannot Be Blank");
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid email");
            return;
        }

        if (TextUtils.isEmpty(number)) {
            registrationNumberEditText.setError("Cannot Be Empty");
            return;
        }

        StringBuffer sb = new StringBuffer();
        int totalSelected = 0;
        double totalAmount = 0;
        for (Chalan chalan : chalans) {
            if (chalan.isSelected()) {
                sb.append(String.format("%s = %s\n", chalan.getName(), "" + chalan.getCost()));
                totalSelected++;
                totalAmount += chalan.getCost();
            }
        }

        if (totalSelected == 0) {
            Toast.makeText(this, "No Chalan Is Selected", Toast.LENGTH_SHORT).show();
            return;
        }
        String ticketNumber = UUID.randomUUID().toString();
        sb.append("\nTotal Amount = Rs." + totalAmount + "/- Payable for Ticket " + ticketNumber);
//        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
        sendMail(email, ticketNumber, sb.toString());
    }

    private void sendMail(final String email, final String ticketNumber, final String body) {
        submitButton.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://naseemali925.000webhostapp.com/send_mail.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(MainActivity.this, "Mail Sent Successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Mail Could Not Be Sent", Toast.LENGTH_SHORT).show();
                        }
                        submitButton.setVisibility(View.VISIBLE);
                        loader.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Some Error Occurred. Please Try Again Later", Toast.LENGTH_SHORT).show();
                submitButton.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("toemail", email);
                map.put("body", body);
                map.put("ticketNumber", ticketNumber);
                return map;
            }
        };
        queue.add(stringRequest);
    }

    private void addChalans() {
        chalans.add(new Chalan("General", 300));
        chalans.add(new Chalan("Violation of Road Rules", 200));
        chalans.add(new Chalan("Ticket less Travel", 200));
        chalans.add(new Chalan("Disobeying orders from the Authorities and Refusing to Share Information", 500));
        chalans.add(new Chalan("Driving an Unauthorized Vehicle without License", 1000));
        chalans.add(new Chalan("Driving Without License", 500));
        chalans.add(new Chalan("Driving Regardless of Disqualification", 500));
        chalans.add(new Chalan("Over-Speeding", 400));
        chalans.add(new Chalan("Dangerous / Rash Driving", 400));
        chalans.add(new Chalan("Driving Under the Influence of Alcohol or Intoxicating Substance", 2000));
        chalans.add(new Chalan("Oversized Vehicles", 100));
        chalans.add(new Chalan("Driving When Mentally/Physically Unfit,First-Time Offence", 200));
        chalans.add(new Chalan("Accident Related Offences", 2000));
        chalans.add(new Chalan("Driving Uninsured Vehicle (without Insurance)  and/or Imprisonment of up to 3 months.", 1000));
        chalans.add(new Chalan("Racing and Speeding", 500));
        chalans.add(new Chalan("Vehicle Without Permit", 5000));
        chalans.add(new Chalan("Aggregators (Violations of Licensing Conditions)", 200));
        chalans.add(new Chalan("Overloading", 1000));
        chalans.add(new Chalan("Overloading of Passengers", 100));
        chalans.add(new Chalan("Not Wearing Seatbelt", 100));
        chalans.add(new Chalan("Overloading of Two-Wheelers", 100));
        chalans.add(new Chalan("Not Wearing Helmet", 100));
        chalans.add(new Chalan("Not Providing Way for Emergency Vehicles", 200));
        chalans.add(new Chalan("Offences by Juveniles", 100));
        chalans.add(new Chalan("Power of Officers to Impound Documents", 300));
        chalans.add(new Chalan("Offences Committed by Enforcing Officers", 200));
    }
}
