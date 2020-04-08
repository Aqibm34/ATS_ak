package ali.naseem.ats;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

public class ChalanAdapter extends RecyclerView.Adapter<ChalanAdapter.ViewHolder> {

    private ArrayList<Chalan> chalans;

    public ChalanAdapter(ArrayList<Chalan> chalans) {
        this.chalans = chalans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chalan_list_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CheckBox checkBox = viewHolder.checkBox;
        final Chalan chalan = chalans.get(i);
        checkBox.setText(chalan.getName());
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setSelected(chalan.isSelected());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                chalan.setSelected(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chalans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
