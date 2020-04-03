package com.example.sqlite_image_plust;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
public class Adapter_DoVat extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<DoVat> arrayList;
    ArrayList<DoVat> arrayListFull = new ArrayList<>();
    int layout;
    public Adapter_DoVat(Context context, ArrayList<DoVat> arrayList, int layout) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListFull.addAll(arrayList);
        this.layout = layout;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<DoVat> filterArrayList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterArrayList.addAll(arrayListFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DoVat x:arrayListFull){
                    if(x.getTen().toLowerCase().contains(filterPattern)){
                        filterArrayList.add(x);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterArrayList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((ArrayList<DoVat>) results.values);
            notifyDataSetChanged();
        }
    };
    private class ViewHolder{
        TextView txtTenDoVatCustom,txtMoTaCustom;
        ImageView imgHinhCustom;
        LinearLayout linearLayout;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);
            viewHolder.imgHinhCustom = convertView.findViewById(R.id.imgHinhCustom);
            viewHolder.txtMoTaCustom = convertView.findViewById(R.id.txtTenDoVatCustom);
            viewHolder.txtTenDoVatCustom = convertView.findViewById(R.id.txtTenDoVatCustom);
            viewHolder.linearLayout = convertView.findViewById(R.id.linearLayout);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        DoVat doVat = arrayList.get(position);
        viewHolder.txtMoTaCustom.setText(doVat.getMota());
        viewHolder.txtTenDoVatCustom.setText(doVat.getTen());
        byte[] HinhAnh = doVat.getHinh();
        viewHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder buider = new AlertDialog.Builder(context);
                buider.setTitle("Message");
                buider.setIcon(R.mipmap.ic_launcher);
                buider.setMessage("Do you want to delete ?");
                buider.setPositiveButton("Yes",(dialog,which)->{
                    String ten = arrayList.get(position).getTen();
                    arrayList.remove(position);
                    MainActivity.database.delete_DoVat(ten);
                    notifyDataSetChanged();
                });
                buider.setNegativeButton("No", (dialog,which)->{
                        buider.setCancelable(true);
                    }
                );
                AlertDialog al = buider.create();
                al.show();
                return false;
            }
        });
        //Chuyen byte sang bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(HinhAnh,0,HinhAnh.length);
        viewHolder.imgHinhCustom.setImageBitmap(bitmap);
        return convertView;
    }
}
