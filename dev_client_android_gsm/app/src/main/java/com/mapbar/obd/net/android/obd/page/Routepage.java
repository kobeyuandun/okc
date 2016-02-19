package com.mapbar.obd.net.android.obd.page;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mapbar.obd.net.android.MainActivity;
import com.mapbar.obd.net.android.R;
import com.mapbar.obd.net.android.framework.Configs;
import com.mapbar.obd.net.android.framework.model.MAnimation;

/**
 * Created by yun on 16/1/18.
 */
public class Routepage extends TabPage {
    private final Context mContext;

    public Routepage(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public View onCreateView() {
        return View.inflate(mContext, R.layout.layout_route, null);
    }

    @Override
    public void init(View view) {
        final TextView viewById = (TextView) view.findViewById(R.id.tv_route);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).showPage(Configs.VIEW_POSITION_MAIN, Configs.VIEW_POSITION_TEST, Configs.DATA_TYPE_NONE, null, Configs.VIEW_POSITION_NONE, MAnimation.push_left_in, MAnimation.push_left_out);
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
