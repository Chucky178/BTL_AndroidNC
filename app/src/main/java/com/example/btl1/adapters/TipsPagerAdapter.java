package com.example.btl1.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.btl1.fragments.LyThuyetFragment;
import com.example.btl1.fragments.ThucHanhFragment;

public class TipsPagerAdapter extends FragmentStateAdapter {

    public TipsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new LyThuyetFragment();
        else
            return new ThucHanhFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
