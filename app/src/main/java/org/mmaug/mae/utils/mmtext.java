package org.mmaug.mae.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.reflect.Field;

/**
 * Created by htoomyintnaung on 23/05/2014.
 */
public class mmtext {

    public static final int TEXT_ZAWGYI = 0;
    public static final int TEXT_UNICODE = 1;
    public static final int TEXT_XPartial = 2;

    public static void prepareActivity(Activity a, int EncodedText, boolean SamsungSafe,
                                       boolean SyllabelBreak) {
        ViewGroup root = (ViewGroup) a.getWindow().getDecorView().getRootView();
        prepareActivity(a, root, EncodedText, SamsungSafe, SyllabelBreak);
    }

    public static void prepareViewGroup(Context c, ViewGroup vg, int EncodedText, boolean SamsungSafe,
                                        boolean SyllabelBreak) {
        prepareActivity(c, vg, EncodedText, SamsungSafe, SyllabelBreak);
    }

    public static boolean isTextZawGyiProbably(String s) {
        return MyanmarZawgyiConverter.isZawgyiEncoded(s);
    }

    public static void prepareView(Context c, View v, int EncodedText, boolean SamsungSafe,
                                   boolean SyllabelBreak) {

        Typeface tf = Typeface.createFromAsset(c.getAssets(), "mymm.ttf");

        if (v instanceof TextView) {
            ((TextView) v).setTypeface(tf);
            ((TextView) v).setText(
                    mmtext.processText(((TextView) v).getText().toString(), EncodedText, SamsungSafe,
                            SyllabelBreak));
        }
    }

    public static void setActionTitle(Activity a, String title, int EncodedText,
                                      boolean SamsungSafe) {
        Typeface tf = Typeface.createFromAsset(a.getAssets(), "mymm.ttf");
        int titleId = a.getResources().getIdentifier("action_bar_title", "id", "android");
        TextView yourTextView = (TextView) a.findViewById(titleId);
        yourTextView.setText(processText(title, EncodedText, SamsungSafe, true));
        yourTextView.setPadding(0, 6, 0, 0);
        yourTextView.setTypeface(tf);
    }

    public static CharSequence getMMText(View v) {
        StringBuilder sb = new StringBuilder();
        if (v instanceof TextView) {
            String st = ((TextView) v).getText().toString();
            for (int i = 0; i < st.length(); i++) {
                if (st.charAt(i) >= (0x1000+0xD000) && st.charAt(i) <= (0x109f+0xD000)) {
                    sb.append((char) ((int) st.charAt(i) - 0xD000));
                } else {
                    sb.append(st.charAt(i));
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static void prepareActivity(Context c, ViewGroup root, int EncodedText,
                                        boolean SamsungSafe, boolean SyllabelBreak) {

        Typeface tf = Typeface.createFromAsset(c.getAssets(), "mymm.ttf");

        if (root == null) return;
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
                ((TextView) v).setText(
                        mmtext.processText(((TextView) v).getText().toString(), EncodedText, SamsungSafe,
                                SyllabelBreak));
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(tf);
                ((Button) v).setText(
                        mmtext.processText(((Button) v).getText().toString(), EncodedText, SamsungSafe,
                                SyllabelBreak));
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(tf);
                ((EditText) v).setHint(
                        mmtext.processText(((EditText) v).getHint().toString(), EncodedText, SamsungSafe,
                                SyllabelBreak));
            } else if (v instanceof ViewGroup) {
                prepareActivity(c, (ViewGroup) v, EncodedText, SamsungSafe, SyllabelBreak);
            }
        }
    }

    private static int dp2pix(Activity c, int dp) {
        DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }

    public static void prepareParagraphView(Activity c, TextView tv) {

        Display display = c.getWindowManager().getDefaultDisplay();
        int totalw = display.getWidth() - (dp2pix(c, 17) * 2);

        String s = tv.getText().toString();
        Typeface font = Typeface.createFromAsset(c.getAssets(), "mymm.ttf");
        Paint p = new Paint();
        p.setTypeface(font);
        p.setTextSize(tv.getTextSize());

        StringBuilder breakedString = new StringBuilder();
        StringBuilder tempString = new StringBuilder();

        int lastpostbreak = 0;
        int breaked = 0;
        int pos = 0;
        while (true) {

            if (charIsBreak(s.charAt(pos))) {
                tempString.append(s.charAt(pos));
                int tmpwidth = (int) p.measureText(tempString.toString());

                if (tmpwidth > totalw) {
                    String toadd = tempString.substring(0, (lastpostbreak - breaked));
                    breakedString.append(toadd.trim() + "\n");
                    tempString.delete(0, (lastpostbreak - breaked));
                    breaked += (lastpostbreak - breaked);
                }
                lastpostbreak = pos;
            } else if (charIsEnter(s.charAt(pos))) {
                tempString.append(s.charAt(pos));
                String toadd = tempString.substring(0, (tempString.length()));
                breakedString.append(toadd);
                breaked += (tempString.length());
                tempString.delete(0, (tempString.length()));
                lastpostbreak = pos;
            } else {
                tempString.append(s.charAt(pos));
            }

            pos++;
            if (pos >= s.length()) break;
        }

        breakedString.append(tempString);
        tv.setText(breakedString.toString());
    }

    private static boolean charIsEnter(char c) {
        if (c == '\n') {
            return true;
        }
        return false;
    }

    private static boolean charIsBreak(char c) {
        if (c == 0x200b || c == ' ' || c == '\t') return true;
        return false;
    }

    public static String processText(String original_text, int EncodedText, boolean SamsungSafe,
                                     boolean SyllabelBreak) {
        switch (EncodedText) {
            case TEXT_UNICODE:
                original_text = mmtext.Uni2XP(original_text);
                break;
            case TEXT_XPartial:

                break;
            case TEXT_ZAWGYI:
                original_text = mmtext.ZG2XP(original_text);
                break;
        }

        if (SyllabelBreak) {
            original_text = MyBreak.parser(original_text);
        }

        if (SamsungSafe) {
            original_text = ShiftCodes(original_text);
        }
        return original_text;
    }

    public static String ShiftCodes(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= 0x1000 && s.charAt(i) <= 0x109f) {
                sb.append((char) ((int) s.charAt(i) + 0xD000));
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    private static String[] zawgi = new String[]{
            "န္႔", "န္႔", "္", "်", "ျ", "ြ", "ွ", "ၚ", "ၥ", "ၦ", "ၧ", "ၨ", "ၩ", "ၪ", "ၫ", "ၬ", "ၭ", "ၮ",
            "ၯ", "ၰ", "ၱ", "ၲ", "ၳ", "ၴ", "ၵ", "ၶ", "ၷ", "ၸ", "ၹ", "ၺ", "ၻ", "ၼ", "ၽ", "ၾ", "ၿ", "ႀ", "ႁ",
            "ႂ", "ႃ", "ႄ", "ႅ", "ႆ", "ႇ", "ႈ", "ႉ", "ႊ", "ႋ", "ႌ", "ႍ", "ႎ", "ႏ", "႑", "႒", "႔", "႕", "႖",
            "႗", "ြၽ", "ွၽ", "႐", "ၫ", "႑"
    };

    private static String[] xp = new String[]{
            "န့်", "န့်", "်", "ႀ", "ႅ", "ႍ", "႐", "ါၗ", "ၨ", "ၩ", "ၩ", "ၪ", "ၫ", "ၬ", "ၭ", "ၮ", "ၰ",
            "ဍၲ", "ၳၲၳ", "ၴ", "ၵ", "ၵ", "ၷ", "ၷ", "ၸ", "ၹ", "ၺ", "ၻ", "ၼ", "ၽ", "ၾ", "ၿ", "ႀ", "ႄ", "ႅ",
            "ႄ", "ႅ", "ႄ", "ႅ", "ႄ", "ႌ", "ႏ", "႑", "႒", "႐႐ဴ", "ႎ", "ၥ", "ၦ", "ၧ", "ဵ", "ၙ", "ဏၱ", "ၘ",
            "႔", "႕", "ၶ", "ဋၮ", "ႁ", "ႂ", "ရ", "ည", "ဏၱ"
    };

    public static String XP2ZG(String s) {

        for (int i = 0; i < xp.length; i++) {
            s = s.replace(xp[i], zawgi[i]);
        }
        return s;
    }

    public static String ZG2UNI(String s) {
        return Rabbit.uni2zg(s);
    }

    public static String ZG2XP(String s) {

        for (int i = xp.length - 1; i >= 0; i--) {
            s = s.replace(zawgi[i], xp[i]);
        }
        return s;
    }

    public static String Uni2XP(String str) {

/* uni1004103A1039 */
        str = str.replace("\u1004\u103A\u1039", "ၤ");
/* uni10391000 */
        str = str.replace("\u1039\u1000", "ၠ");
/* uni10391001 */
        str = str.replace("\u1039\u1001", "ၡ");
/* uni10391002 */
        str = str.replace("\u1039\u1002", "ၢ");
/* uni10391003 */
        str = str.replace("\u1039\u1003", "ၣ");
/* uni10391005 */
        str = str.replace("\u1039\u1005", "ၨ");
/* uni10391006 */
        str = str.replace("\u1039\u1006", "ၩ");
/* uni10391007 */
        str = str.replace("\u1039\u1007", "ၪ");
/* uni10391008 */
        str = str.replace("\u1039\u1008", "ၫ");
/* uni1039100F */
        str = str.replace("\u1039\u100F", "ၴ");
/* uni10391010 */
        str = str.replace("\u1039\u1010", "ၵ");
/* uni10391011 */
        str = str.replace("\u1039\u1011", "ၷ");
/* uni10391012 */
        str = str.replace("\u1039\u1012", "ၸ");
/* uni10391013 */
        str = str.replace("\u1039\u1013", "ၹ");
/* uni10391014 */
        str = str.replace("\u1039\u1014", "ၺ");
/* uni10391015 */
        str = str.replace("\u1039\u1015", "ၻ");
/* uni10391016 */
        str = str.replace("\u1039\u1016", "ၼ");
/* uni10391017 */
        str = str.replace("\u1039\u1017", "ၽ");
/* uni10391018 */
        str = str.replace("\u1039\u1018", "ၾ");
/* uni10391019 */
        str = str.replace("\u1039\u1019", "ၿ");
/* uni103B103D */
        str = str.replace("\u103B\u103D", "ႁ");
/* uni103C103D */
        str = str.replace("\u103C\u103D", "ႅxႍ");
/* uni103B103E */
        str = str.replace("\u103B\u103E", "ႂ");
/* uni103C103E */
        str = str.replace("\u103C\u103E", "ႅx႑");
/* uni103B103D103E */
        str = str.replace("\u103B\u103D\u103E", "ႃ");
/* uni103C103D103E */
        str = str.replace("\u103C\u103D\u103E", "ႅxႎ");
        str = str.replace("\u103d\u103e", "ႎ");
/* uni103E1030 */
        //ERROR str = str.replace("\u103E\u1030","");
        //specials
/* uni100C1039100C */
        str = str.replace("\u100C\u1039\u100C", "ၘ");
/* uni100B1039100C */
        //ERROR str = str.replace("\u100B\u1039\u100C","");
/* uni100F1039100D */
        str = str.replace("\u100F\u1039\u100D", "ဏ​ၱ");
/* uni100F1039100B */
        str = str.replace("\u100F\u1039\u100B", "ဏ​ၮ");
/* uni100F1039100C */
        str = str.replace("\u100F\u1039\u100C", "ဏ​ၰ");
/* uni100F1039100F */
        str = str.replace("\u100F\u1039\u100F", "ဏၴ​");
/* uni100F1039100E */
        str = str.replace("\u100F\u1039\u100E", "ဏ​ၳ");
/* uni100D1039100E */
        str = str.replace("\u100D\u1039\u100E", "ဎ​ၲ");
/* uni100D1039100D */
        str = str.replace("\u100D\u1039\u100D", "ဍ​ၮ");
/* uni100B1039100B */
        str = str.replace("\u100B\u1039\u100B", "ဋ​ၮ");
/* uni100710391009 */
        str = str.replace("\u1007\u1039\u1009", "ဥ​ၼ");
/* uni10391009 */
        str = str.replace("\u1039\u1009", "");
/* uni1039100B */
        str = str.replace("\u1039\u100B", "ၮ");
/* uni1039100Bvar */
        //ERROR str = str.replace("\u1039\u100B","");
/* uni1039100C */
        str = str.replace("\u1039\u100C", "ၰ");
/* uni1039100D */
        str = str.replace("\u1039\u100D", "ၱ");
/* uni1039100E */
        str = str.replace("\u1039\u103E", "ၳ");
/* uni1039100F */
        str = str.replace("\u1039\u100F", "ၴ");

        str = str.replace("ါ်", "ါၗ");

        str = str.replace("ဉ\u103a", "ဥ​်");
/* uni1039101B */
        //ERROR str = str.replace("\u1039\u101B","");
/* uni10391020 */
        //ERROR str = str.replace("\u1039\u1020","");
/* uni10391021 */
        //ERROR str = str.replace("\u1039\u1021","");
/* uni10391022 */
        //ERROR str = str.replace("\u1039\u1022","");
/* uni10511039100B */
        //ERROR str = str.replace("\u1051\u1039\u100B","");
/* uni10511039100C */
        //ERROR str = str.replace("\u1051\u1039\u100C","");
/* uni1039105A */
        //ERROR str = str.replace("\u1039\u105A","");
/* uni1039105B */
        //ERROR str = str.replace("\u1039\u105B","");
/* uni1039105C */
        //ERROR str = str.replace("\u1039\u105C","");
/* uni1039105D */
        //ERROR str = str.replace("\u1039\u105D","");

        char[] sawBefore = new char[]{
                '\u1025', '\u100A', '\u103B', '\u103C', '\u103D', '\u103E', '\u1039',
        };

        StringBuilder sb = new StringBuilder();
        int state = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x1000 && c <= 0x1021) {
                if (state == 2) {
                    state = 1;
                } else {
                    state = 0;
                }
            }
            if (c == 0x1039) state = 2;
            if (isContain(sawBefore, c)) state = 1;
            if (state == 1 && c == 0x102f) c = 'ဳ';
            if (state == 1 && c == 0x1030) c = 'ဴ';

            sb.append(c);
        }
        str = sb.toString();

        char[] ukm = new char[]{'ႀ', 'ႍ', '႐', 'ု', 'ူ', 'န', 'ႅ'};
        //UKM
        sb = new StringBuilder();
        state = 0;
        boolean move = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x1000 && c <= 0x1021) {
                state = 0;
                if (c != 'န') {
                    move = false;
                } else {
                    move = true;
                }
            }
            if (isContain(ukm, c)) move = true;
            if (c == '့') {
                if (move) {
                    c = '႔';
                }
            }
            sb.append(c);
        }
        str = sb.toString();

        // eVowel Arrangement
        sb = new StringBuilder();
        state = 0;
        int idx = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x1000 && c <= 0x1021) {
                if (state == 2) {
                    state = 0;
                } else {
                    state = 0;
                    idx = i;
                }
            }
            if (c == 0x1039) state = 2;
            if (c == 0x1031) {
                sb.insert(idx, c);
            } else {
                sb.append(c);
            }
        }
        str = sb.toString();

        // Big Ya Yit
        char[] longcons = new char[]{
                '\u1000', '\u1003', '\u1006', '\u100F', '\u1010', '\u1011', '\u1018', '\u101C', '\u101E',
                '\u101F'
        };

        sb = new StringBuilder();
        state = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isContain(longcons, c)) {
                state = 1;
            } else if (c >= 0x1000 && c <= 0x1021) state = 0;

            if (state == 1 && c == 0x103C) c = 'ႄ';
            sb.append(c);
        }
        str = sb.toString();

        // Ya Yit Rearrangement
        sb = new StringBuilder();
        state = 0;
        idx = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x1000 && c <= 0x1021) {
                if (state == 2) {
                    state = 0;
                } else {
                    state = 0;
                    idx = i;
                }
            }
            if (c == 0x1039) state = 2;
            if (c == 0x103c) {
                sb.insert(idx, c);
            } else if (c == 'ႄ') {
                sb.insert(idx, 'ႄ');
            } else {
                sb.append(c);
            }
        }
        str = sb.toString();

        //Kinzi
        sb = new StringBuilder();
        state = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'ၤ') {
                state = 1;
            } else {
                sb.append(c);
            }
            if (state == 1 && (c >= 0x1000 && c <= 0x1021)) {
                sb.append('ၤ');
                state = 0;
            }
        }
        str = sb.toString();

        // Myanmar Above Base Replacements
        str = str.replace("ၤ\u102d", "ၥ");
        str = str.replace("ၤ\u102e", "ၦ");
        str = str.replace("ၤ\u1036", "ၧ");
        str = str.replace("\u102d\u1036", "ဵ");

        char[] na_after = new char[]{
                '\u102F', '\u1030', '\u103B', 'ႁ', 'ႃ', 'ႂ', '\u103D', 'ႎ', '\u103E',
        };
        // NA Shape
        sb = new StringBuilder();
        state = 0;
        idx = -1;
        boolean shorten = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x1000 && c <= 0x1021) {
                state = 0;
                idx = -1;
                shorten = false;
            }
            if (c == 'ႄ' || c == 0x103c) shorten = true;
            if (c == 'န') {
                if (shorten) {
                    c = 'ၙ';
                } else {
                    idx = i;
                }
            }
            if (isContain(na_after, c)) {
                if (idx != -1) {
                    sb.setCharAt(idx, 'ၙ');
                    idx = -1;
                }
            }

            sb.append(c);
        }
        str = sb.toString();

        return str;
    }

    private static boolean isContain(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) return true;
        }
        return false;
    }

    public static void embedToolbar(Toolbar toolbar, float paddingInDp, int EncodedText,boolean SamsungSafe, boolean SyllabelBreak) {
        Context c = toolbar.getContext();
        getActionBarTextView(toolbar)
                .setPadding(0, Math.round(dpToPX(dpToPX(paddingInDp, c), c)), 0, 0);
        mmtext.prepareView(c, getActionBarTextView(toolbar), EncodedText, SamsungSafe, SyllabelBreak);
    }

    private static float dpToPX(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    private static TextView getActionBarTextView(Toolbar tb) {
        TextView titleTextView = null;

        try {
            Field f = tb.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(tb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titleTextView;
    }
}

