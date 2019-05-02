package pl.idzikdev.mapper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pl.idzikdev.R;
import pl.idzikdev.model.Note;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Note> noteList;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void clear(){
        noteList.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.simple_row,parent,false);
            viewHolder.name=(TextView)convertView.findViewById(R.id.textName);
            viewHolder.date=(TextView)convertView.findViewById(R.id.textDate);
            viewHolder.category=(TextView)convertView.findViewById(R.id.textCategory);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.name.setText("Task: \n"+noteList.get(position).getName());
        viewHolder.date.setText("Date: "+noteList.get(position).getDate());
        viewHolder.category.setText("Category: "+noteList.get(position).getCategory());

        return convertView;
    }
    class ViewHolder{
        TextView name;
        TextView date;
        TextView category;
    }
}
