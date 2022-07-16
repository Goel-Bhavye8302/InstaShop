package com.ai.game.instashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.Model.MessagesModel;
import com.ai.game.instashop.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatMessagesAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<MessagesModel> list;
    enum VIEW_TYPE {SENDER, RECEIVER};

    public ChatMessagesAdapter(Context context, ArrayList<MessagesModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE.SENDER.ordinal()){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_sender_rv_layout, parent, false);
            return new SenderViewholder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_reciever_rv_layout, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessagesModel model = list.get(position);

        DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
        String dateString = dateFormat.format(new Date(model.getTimeStamp())).toString();

        if(holder.getClass() == SenderViewholder.class){
            ((SenderViewholder) holder).senderMessage.setText(model.getMessage());
            ((SenderViewholder) holder).senderTime.setText(dateString.toLowerCase(Locale.ROOT));
        }
        else{
            ((ReceiverViewHolder) holder).receiverMessage.setText(model.getMessage());
            ((ReceiverViewHolder) holder).receiverTime.setText(dateString.toLowerCase(Locale.ROOT));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(FirebaseAuth.getInstance().getUid().equals(list.get(position).getUid())){
            return VIEW_TYPE.SENDER.ordinal();
        }
        return VIEW_TYPE.RECEIVER.ordinal();
    }

    class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView receiverMessage, receiverTime;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMessage = itemView.findViewById(R.id.receiver_message);
            receiverTime = itemView.findViewById(R.id.receiver_message_time);
        }
    }

    class SenderViewholder extends RecyclerView.ViewHolder{
        TextView senderMessage, senderTime;
        public SenderViewholder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.sender_message);
            senderTime = itemView.findViewById(R.id.sender_message_time);
        }
    }
}
