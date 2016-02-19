package com.mapbar.obd.net.android.obd.page;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mapbar.obd.net.android.R;

/**
 * Created by yun on 16/1/18.
 */
public class VehicleConditionPage extends TabPage {
    private final Context mContext;

    public VehicleConditionPage(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public View onCreateView() {
        return View.inflate(mContext, R.layout.layout_vehicle_codition, null);
    }

    @Override
    public void init(View view) {
        final TextView viewById = (TextView) view.findViewById(R.id.tv_vehicle);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewById.setText("onClick");
            }
        });

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}
