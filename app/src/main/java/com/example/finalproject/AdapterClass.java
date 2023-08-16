package com.example.finalproject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class AdapterClass extends RecyclerView.Adapter<AdapterClass.TasksViewHolder> {
    interface FavouriteClickListner {
        public void favouriteClicked(ImaginaryFriend selectediF);
    }
    private Context applicationContext;
    public List<ImaginaryFriend> imaginaryFriendList;
    FavouriteClickListner favListner;
    public AdapterClass(Context applicationContext, List<ImaginaryFriend> iFList) {
        this.applicationContext = applicationContext;
        this.imaginaryFriendList = iFList;
        favListner = (FavouriteClickListner)applicationContext;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.recyclerview_row, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        ImaginaryFriend t = imaginaryFriendList.get(position);
        holder.iFTextView.setText(t.getFirstName());
    }

    @Override
    public int getItemCount() {
        return imaginaryFriendList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView iFTextView;

        public TasksViewHolder(View itemView) {
            super(itemView);
            iFTextView= itemView.findViewById(R.id.recyclerRow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ImaginaryFriend friend = imaginaryFriendList.get(getAdapterPosition());
            favListner.favouriteClicked(friend);

        }
    }


}