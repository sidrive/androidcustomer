package lawyerku.customer.base;

import android.app.Fragment;
import android.os.Bundle;

public abstract class BaseFragment extends Fragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragmentComponent();
    }

    protected abstract void setupFragmentComponent();
}
