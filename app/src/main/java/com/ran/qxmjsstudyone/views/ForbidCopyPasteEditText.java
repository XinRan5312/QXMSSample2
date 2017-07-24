package com.ran.qxmjsstudyone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

/**
 * Created by houqixin on 2017/7/14.
 */

public class ForbidCopyPasteEditText extends EditText {
    private boolean isFirstDraw = true;

    public ForbidCopyPasteEditText(Context context) {
        super(context);
    }

    public ForbidCopyPasteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForbidCopyPasteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirstDraw) {
            forbidCopyPaste();
            setEditTextForbitInputSpace();
            isFirstDraw=false;
        }
    }

    private void forbidCopyPaste() {
        if (this == null) {
            return;
        }
        this.setLongClickable(false);
        this.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        this.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
    }

    private void setEditTextForbitInputSpace() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" "))
                    return "";
                else
                    return null;
            }
        };
        this.setFilters(new InputFilter[]{filter});
    }

}
