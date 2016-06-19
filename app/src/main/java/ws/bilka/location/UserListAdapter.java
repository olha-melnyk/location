package ws.bilka.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class UserListAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> mUserList;

    public UserListAdapter(Context context, List<User> userList) {
        this.mContext = context;
        this.mUserList = userList;
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = mUserList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_friends,parent,false);
        }

        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        userName.setText(user.getName());

        return convertView;
    }

    public void update() {
        notifyDataSetChanged();
    }

    public Context getContext() {
        return mContext;
    }
}

