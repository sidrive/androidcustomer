package lawyerku.customer.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import lawyerku.customer.R;

public class MitraTabView extends LinearLayout {

    public MitraTabView(Context context) {
        super(context);
    }

    public MitraTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setupWithViewPager(ViewPager viewPager) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_tab_history, this, true);
        ((TabLayout) findViewById(R.id.tablayout)).setupWithViewPager(viewPager);
        createTab();
    }

    private void createTab() {
        ((TabLayout) findViewById(R.id.tablayout)).getTabAt(0).setCustomView(generateSearchTabItem("Perkara Baru"));
        ((TabLayout) findViewById(R.id.tablayout)).getTabAt(1).setCustomView(generateSearchTabItem("Perkara Berjalan"));
        ((TabLayout) findViewById(R.id.tablayout)).getTabAt(2).setCustomView(generateSearchTabItem("History"));
    }

    private TextView generateSearchTabItem(String title) {
        TextView singleTab = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tab, null);
        singleTab.setText(title);
        return singleTab;
    }
}
