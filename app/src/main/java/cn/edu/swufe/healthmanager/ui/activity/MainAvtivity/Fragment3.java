package cn.edu.swufe.healthmanager.ui.activity.MainAvtivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import cn.edu.swufe.healthmanager.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_page3, container, false);

        return view;
    }
}
