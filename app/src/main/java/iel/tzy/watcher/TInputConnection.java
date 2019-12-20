package iel.tzy.watcher;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

/**
 * Created by tu zhen yu on 2017/12/1.
 * {@link InputConnection} 是输入法和View交互的纽带。
 * {@link InputConnectionWrapper} 是 InputConnection 的代理类,可以代理EditText的InputConnection，监听和拦截软键盘的各种输入事件。
 * 注：用 {@link View#setOnKeyListener(View.OnKeyListener)} 监听软键盘的按键点击事件对有些键盘无效(比如谷歌输入法)，
 * 最好用InputConnection去监听。
 */

public class TInputConnection extends InputConnectionWrapper {
    private final static String TAG = "TInputConnection";

    private BackspaceListener mBackspaceListener;

    /**
     * Initializes a wrapper.
     * <p>
     * <p><b>Caveat:</b> Although the system can accept {@code (InputConnection) null} in some
     * places, you cannot emulate such a behavior by non-null {@link InputConnectionWrapper} that
     * has {@code null} in {@code target}.</p>
     *
     * @param target  the {@link InputConnection} to be proxied.
     * @param mutable set {@code true} to protect this object from being reconfigured to target
     *                another {@link InputConnection}.  Note that this is ignored while the target is {@code null}.
     */
    public TInputConnection(InputConnection target, boolean mutable) {
        super(target, mutable);
    }

    public interface BackspaceListener {
        /**
         * @return true 代表消费了这个事件
         * */
        boolean onBackspace();
    }

    /**
     * 当软键盘删除文本之前，会调用这个方法通知输入框，我们可以重写这个方法并判断是否要拦截这个删除事件。
     * 在谷歌输入法上，点击退格键的时候不会调用{@link #sendKeyEvent(KeyEvent event)}，
     * 而是直接回调这个方法，所以也要在这个方法上做拦截；
     * */
    @Override
    public boolean deleteSurroundingText(int beforeLength, int afterLength) {
        boolean res = super.deleteSurroundingText(beforeLength, afterLength);
        Log.d(TAG, String.format("deleteSurroundingText: [beforeLength = %d, afterLength = %d], result=%d", beforeLength, afterLength, res ? 1 : 0));
        return res;
    }

    public void setBackspaceListener(BackspaceListener backspaceListener) {
        this.mBackspaceListener = backspaceListener;
    }

    /**
     * 当在软件盘上点击某些按钮（比如退格键，数字键，回车键等），该方法可能会被触发（取决于输入法的开发者），
     * 所以也可以重写该方法并拦截这些事件，这些事件就不会被分发到输入框了
     * */
    @Override
    public boolean sendKeyEvent(KeyEvent event) {
        if( event.getKeyCode() == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN){
            if(mBackspaceListener != null && mBackspaceListener.onBackspace()){
                return true;
            }
        }
        return super.sendKeyEvent(event);
    }

    @Override
    public boolean setComposingText(CharSequence text, int newCursorPosition) {
        if (!Toggles.isEnable("setComposingText")) {
            Log.d(TAG, String.format("setComposingText: [text = %s, newCursorPosition = %d], result = %d", text, newCursorPosition, 1));
            return true;
        }
        boolean res = super.setComposingText(text, newCursorPosition);
        Log.d(TAG, String.format("setComposingText: [text = %s, newCursorPosition = %d], result = %d", text, newCursorPosition, res ? 1 : 0));
        return res;
    }

    @Override
    public CharSequence getTextBeforeCursor(int n, int flags) {
        if (!Toggles.isEnable("getTextBeforeCursor")) {
            Log.d(TAG, String.format("getTextBeforeCusor: [n=%d, flags=%d], result=%s", n, flags, null));
            return null;
        }
        CharSequence res = super.getTextBeforeCursor(n, flags);
        Log.d(TAG, String.format("getTextBeforeCusor: [n=%d, flags=%d], result=%s", n, flags, res));
        return res;
    }

    @Override
    public CharSequence getTextAfterCursor(int n, int flags) {
        if (!Toggles.isEnable("getTextAfterCursor")) {
            Log.d(TAG, String.format("getTextAfterCursor: [flags=%d], result=%s", flags, null));
            return null;
        }
        CharSequence res = super.getTextAfterCursor(n, flags);
        Log.d(TAG, String.format("getTextAfterCursor: [n=%d, flags=%d], result=%s", n, flags, res));
        return res;
    }

    @Override
    public CharSequence getSelectedText(int flags) {
        if (!Toggles.isEnable("getSelectedText")) {
            Log.d(TAG, String.format("getSelectedText: [flags=%d], result=%s", flags, null));
            return null;
        }
        CharSequence res = super.getSelectedText(flags);
        Log.d(TAG, String.format("getSelectedText: [flags=%d], result=%s", flags, res));
        return res;
    }

    @Override
    public int getCursorCapsMode(int reqModes) {
        int res = super.getCursorCapsMode(reqModes);
        Log.d(TAG, String.format("getCursorCapsMode: [reqModes=%d], result=%d", reqModes, res));
        return res;
    }

    @Override
    public ExtractedText getExtractedText(ExtractedTextRequest request, int flags) {
        if (!Toggles.isEnable("getExtractedText")) {
            Log.d(TAG, String.format("getExtractedText: [flags=%d], result=%s", flags, null));
            return null;
        }
        ExtractedText res = super.getExtractedText(request, flags);
        Log.d(TAG, String.format("getExtractedText: [flags=%d], result=%s", flags, res));
        return res;
    }

    @Override
    public boolean deleteSurroundingTextInCodePoints(int beforeLength, int afterLength) {
        boolean res = super.deleteSurroundingTextInCodePoints(beforeLength, afterLength);
        Log.d(TAG, String.format("deleteSurroundingTextInCodePoints: [beforeLength = %d, afterLength = %d], result=%d", beforeLength, afterLength, res ? 1 : 0));
        return res;
    }

    @Override
    public boolean setComposingRegion(int start, int end) {
        if (!Toggles.isEnable("setComposingRegion")) {
            Log.d(TAG, String.format("setComposingRegion: [start = %d, end = %d], result=%d", start, end, 0));
            return true;
        }
        boolean res = super.setComposingRegion(start, end);
//        boolean res = true;
        Log.d(TAG, String.format("setComposingRegion: [start = %d, end = %d], result=%d", start, end, res ? 1 : 0));
        return res;
    }

    @Override
    public boolean finishComposingText() {
        if (!Toggles.isEnable("finishComposingText")) {
            Log.d(TAG, String.format("finishComposingText"));
            return true;
        }
        boolean res = super.finishComposingText();
        Log.d(TAG, String.format("finishComposingText"));
        return res;
    }

    @Override
    public boolean commitText(CharSequence text, int newCursorPosition) {
        if (!Toggles.isEnable("commitText")) {
            Log.d(TAG, String.format("commitText: [text = %s, newCursorPosition = %d], result=%d", text, newCursorPosition, 1));
            return true;
        }
        boolean res = super.commitText(text, newCursorPosition);
        Log.d(TAG, String.format("commitText: [text = %s, newCursorPosition = %d], result=%d", text, newCursorPosition, res ? 1 : 0));
        return res;
    }

    @Override
    public boolean commitCompletion(CompletionInfo text) {
        boolean res = super.commitCompletion(text);
        Log.d(TAG, String.format("commitCompletion: [text = %s], result=%d", text, res ? 1 : 0));
        return res;
    }

    @Override
    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        boolean res = super.commitCorrection(correctionInfo);
        Log.d(TAG, String.format("commitCorrection: [correctionInfo = %s], result=%d", correctionInfo, res ? 1 : 0));
        return res;
    }

    @Override
    public boolean setSelection(int start, int end) {
        if (!Toggles.isEnable("setSelection")) {
            return true;
        }
        boolean res = super.setSelection(start, end);
        Log.d(TAG, String.format("setSelection: [start = %d, end = %d], result = %d", start, end, res ? 1 : 0));
        return res;
    }

    @Override
    public boolean performEditorAction(int editorAction) {
        boolean res = super.performEditorAction(editorAction);
        Log.d(TAG, String.format("performEditorAction: [editorAction = %d], result = %d", editorAction, res ? 1 : 0));
        return res;
    }

    @Override
    public boolean performContextMenuAction(int id) {
        return super.performContextMenuAction(id);
    }

    @Override
    public boolean beginBatchEdit() {
        boolean res = super.beginBatchEdit();
        Log.d(TAG, String.format("beginBatchEdit"));
        return res;
    }

    @Override
    public boolean endBatchEdit() {
        boolean res = super.endBatchEdit();
        Log.d(TAG, String.format("endBatchEdit"));
        return res;
    }
}
