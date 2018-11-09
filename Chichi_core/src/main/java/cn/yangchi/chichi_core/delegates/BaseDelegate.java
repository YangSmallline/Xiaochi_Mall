package cn.yangchi.chichi_core.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public abstract class BaseDelegate extends SwipeBackFragment {

    private Unbinder mUnbiner=null;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle saveInstancestate,View rootview);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=null;

        if (setLayout() instanceof Integer) {
            rootview=inflater.inflate((Integer) setLayout(),container,false);
            Toast.makeText(getActivity(), (Integer) setLayout(),Toast.LENGTH_SHORT).show();
        }else if (setLayout() instanceof View){
            rootview= (View) setLayout();
        }

        if (rootview != null) {
            mUnbiner=ButterKnife.bind(this,rootview);
            onBindView(savedInstanceState,rootview);
        }

        return rootview;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if ((mUnbiner != null)) {
            mUnbiner.unbind();
        }
    }
}
