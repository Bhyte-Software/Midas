package com.bhyte.midas.HelperClasses;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bhyte.midas.User.FAQS.AllFaqsFragment;
import com.bhyte.midas.User.FAQS.GetStartedFaqsFragment;
import com.bhyte.midas.User.FAQS.OtherFaqsFragment;
import com.bhyte.midas.User.FAQS.VirtualCardFaqsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new GetStartedFaqsFragment();
            case 2:
                return new VirtualCardFaqsFragment();
            case 3:
                return new OtherFaqsFragment();
            case 0:
            default:
                return new AllFaqsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
