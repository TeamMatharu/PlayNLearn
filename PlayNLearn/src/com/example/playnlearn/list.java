package com.example.playnlearn;

import java.util.ArrayList;

import com.example.playnlearn.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class list extends ArrayAdapter<String> {
	private final Activity context;
	ArrayList<String> ar ;
	ArrayList<String> br ;
	ArrayList<byte []> img;
	byte[] b;
	private final Integer imageId;

	

	public list(Activity context, ArrayList<String> ar,ArrayList<String> br , Integer imageId ,ArrayList<byte []> img ) {
		super(context, R.layout.row, ar);
		this.context = context;
		this.ar = ar;
		this.b=b;
		this.img=img;
		this.br = br;
		this.imageId = imageId;
		
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.row, null, true);
		TextView txtUser = (TextView) rowView.findViewById(R.id.List_username);
		TextView txtOther = (TextView) rowView.findViewById(R.id.list_other);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
		txtUser.setText(ar.get(position));
		txtOther.setText(br.get(position));
		
		 Bitmap bm = BitmapFactory.decodeByteArray(img.get(position), 0, img.get(position).length);
		imageView.setImageBitmap(bm);
		
		
		
		return rowView;
	}
}
