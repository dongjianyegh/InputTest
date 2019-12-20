package iel.tzy.watcher;

import java.util.HashMap;
import java.util.Map;

import iel.tzy.R;

public class Toggles {
    static boolean[] sToggles = new boolean[20];

    public static void reset() {
        for (int i = 0; i < sToggles.length; ++i) {
            sToggles[i] = true;
        }
    }

    public static boolean isEnable(String func) {
        return sToggles[funcIdxs.get(func)];
    }

    public static void setEnable(int idx, boolean enable) {
        sToggles[idx] = enable;
    }

    private static final Map<String, Integer> funcIdxs = new HashMap<>();
    static {
        int idx = 0;
        funcIdxs.put("setComposingText", idx++);
        funcIdxs.put("setComposingRegion", idx++);
        funcIdxs.put("finishComposingText", idx++);
        funcIdxs.put("getTextBeforeCursor", idx++);
        funcIdxs.put("getTextAfterCursor", idx++);
        funcIdxs.put("getExtractedText", idx++);
        funcIdxs.put("getSelectedText", idx++);
        funcIdxs.put("setSelection", idx++);
        funcIdxs.put("deleteSurroundingText", idx++);
        funcIdxs.put("commitText", idx++);
        funcIdxs.put("updateSelection", idx++);
    }

    public static int[] checkIds = {
            R.id.setComposingText,
            R.id.setComposingRegion,
            R.id.finishComposingText,
            R.id.getTextBeforeCursor,
            R.id.getTextAfterCursor,
            R.id.getExtractedText,
            R.id.getSelectedText,
            R.id.setSelection,
            R.id.deleteSurroundingText,
            R.id.commitText,
            R.id.updateSelection,
    };
}
