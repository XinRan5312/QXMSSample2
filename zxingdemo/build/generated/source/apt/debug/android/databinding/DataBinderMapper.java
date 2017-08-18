
package android.databinding;
import cn.bingoogolapple.qrcode.zxingdemo.BR;
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 23;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case cn.bingoogolapple.androidcommon.adapter.R.layout.bga_adapter_item_databinding_dummy:
                    return cn.bingoogolapple.androidcommon.adapter.databinding.BgaAdapterItemDatabindingDummyBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case 1020854369: {
                if(tag.equals("layout/bga_adapter_item_databinding_dummy_0")) {
                    return cn.bingoogolapple.androidcommon.adapter.R.layout.bga_adapter_item_databinding_dummy;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"
            ,"itemEventHandler"
            ,"model"
            ,"viewHolder"};
    }
}